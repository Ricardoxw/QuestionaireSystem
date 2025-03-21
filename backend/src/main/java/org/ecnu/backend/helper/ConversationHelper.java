package org.ecnu.backend.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecnu.backend.entity.ChatRound;
import org.ecnu.backend.entity.Conversation;
import org.ecnu.backend.mapper.ChatRoundMapper;
import org.ecnu.backend.mapper.ConversationMapper;
import org.ecnu.backend.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ConversationHelper {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ChatRoundMapper chatRoundMapper;

    @Autowired
    private ConversationMapper conversationMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String REDIS_KEY_PREFIX = "conversation:rounds:";
    private static final String CONVERSATION_KEY_PREFIX = "conversation:";

    /**
     * 缓存会话轮次到 Redis
     *
     * @param conversationId 会话 ID
     * @param chatRound      轮次信息
     */
    public void cacheChatRoundToRedis(Long conversationId, ChatRound chatRound) {
        String key = REDIS_KEY_PREFIX + conversationId;
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        try {
            String chatRoundJson = objectMapper.writeValueAsString(chatRound);
            listOps.rightPush(key, chatRoundJson); // 将轮次信息添加到列表中
            redisTemplate.expire(key, 60, TimeUnit.MINUTES); // 设置缓存有效期为 60 分钟
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建并缓存整个 Conversation 对象到 Redis
     */
    public Long createAndCacheConversationToRedis(Long surveyorId) {
        // 使用雪花算法生成新的 Conversation ID
        Long conversationId = SnowflakeIdGenerator.generateId();
        // 创建新的 Conversation 对象
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setSurveyorId(surveyorId);
        conversation.setConversationStartTime(new Timestamp(System.currentTimeMillis()).toLocalDateTime());

        conversationMapper.insertConversation(conversation);

        // 缓存 Conversation 对象到 Redis
        String key = CONVERSATION_KEY_PREFIX + conversation.getId();
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        try {
            String conversationJson = objectMapper.writeValueAsString(conversation);
            hashOps.put(key, "conversation", conversationJson);
            redisTemplate.expire(key, 60, TimeUnit.MINUTES); // 设置缓存有效期为 60 分钟
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conversation.getId();
    }

    /**
     * 从 Redis 获取会话轮次
     *
     * @param conversationId 会话 ID
     * @return 轮次信息列表
     */
    public List<ChatRound> getChatRoundsFromRedis(Long conversationId) {
        String key = REDIS_KEY_PREFIX + conversationId;
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        List<String> roundStrings = listOps.range(key, 0, -1); // 获取列表中的所有轮次信息
        if (roundStrings == null || roundStrings.isEmpty()) {
            return null;
        }
        List<ChatRound> chatRounds;
        // 将字符串解析为 ChatRound 对象列表
        chatRounds = roundStrings.stream()
                .map(this::parseChatRound)
                .collect(Collectors.toList());
        if(chatRounds==null){
            chatRounds = new ArrayList<>();
        }
        return chatRounds;
    }

    /**
     * 从 Redis 获取整个 Conversation 对象
     *
     * @param conversationId 会话 ID
     * @return Conversation 对象
     */
    public Conversation getConversationFromRedis(Long conversationId) {
        String key = CONVERSATION_KEY_PREFIX + conversationId;
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String conversationJson = hashOps.get(key, "conversation");
        if (conversationJson == null) {
            return null;
        }
        try {
            return objectMapper.readValue(conversationJson, Conversation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存会话轮次和会话对象到 MySQL
     *
     * @param conversationId 会话 ID
     */
    public void saveChatRoundsAndConversationToMySQL(Long conversationId) {
        // 获取缓存中的轮次信息
        List<ChatRound> chatRounds = getChatRoundsFromRedis(conversationId);
        if (chatRounds != null && !chatRounds.isEmpty()) {
            chatRounds.forEach(chatRoundMapper::insertChatRound);
        }

        // 获取缓存中的会话对象
        Conversation conversation = getConversationFromRedis(conversationId);
        if (conversation != null) {
            conversationMapper.insertConversation(conversation);
        }

        // 清除 Redis 中的缓存
        redisTemplate.delete(REDIS_KEY_PREFIX + conversationId);
        redisTemplate.delete(CONVERSATION_KEY_PREFIX + conversationId);
    }

    /**
     * 解析字符串为 ChatRound 对象
     *
     * @param value JSON 格式的字符串
     * @return ChatRound 对象
     */
    private ChatRound parseChatRound(String value) {
        try {
            return objectMapper.readValue(value, ChatRound.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}