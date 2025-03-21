package org.ecnu.backend.service;

import org.ecnu.backend.entity.ChatRound;
import org.ecnu.backend.entity.Conversation;
import org.ecnu.backend.mapper.ChatRoundMapper;
import org.ecnu.backend.mapper.ConversationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationManagementService {
    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private ChatRoundMapper chatRoundMapper;

    // 创建对话
    public Conversation createConversation(Conversation conversation) {
        conversationMapper.insertConversation(conversation);
        return conversation;
    }

    // 获取对话详情（包括轮次信息）
    public Conversation getConversationById(Long id) {
        return conversationMapper.getConversationById(id);
    }

    // 获取所有对话
    public List<Conversation> getAllConversations() {
        return conversationMapper.getAllConversations();
    }

    // 创建轮次
    public ChatRound createChatRound(ChatRound chatRound) {
        chatRoundMapper.insertChatRound(chatRound);
        return chatRound;
    }

    // 获取对话的所有轮次
    public List<ChatRound> getChatRoundsByConversationId(Long conversationId) {
        return chatRoundMapper.getChatRoundsByConversationId(conversationId);
    }

    public void deleteConversation(Long id) {
        // 删除所有相关的轮次
        chatRoundMapper.deleteChatRoundsByConversationId(id);
        // 删除对话
        conversationMapper.deleteConversation(id);
    }
}