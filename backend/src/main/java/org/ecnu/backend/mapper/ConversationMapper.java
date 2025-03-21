package org.ecnu.backend.mapper;

import org.apache.ibatis.annotations.*;
import org.ecnu.backend.entity.Conversation;

import java.util.List;

@Mapper
public interface ConversationMapper {
    @Insert("INSERT INTO deepseek_chat_conversations (surveyor_id, conversation_start_time, conversation_end_time) VALUES (#{surveyorId}, #{conversationStartTime}, #{conversationEndTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertConversation(Conversation conversation);

    @Select("SELECT * FROM deepseek_chat_conversations WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "surveyorId", column = "surveyor_id"),
            @Result(property = "conversationStartTime", column = "conversation_start_time"),
            @Result(property = "conversationEndTime", column = "conversation_end_time"),
            @Result(property = "chatRounds", column = "id", javaType = List.class, many = @Many(select = "org.ecnu.backend.mapper.ChatRoundMapper.getChatRoundsByConversationId"))
    })
    Conversation getConversationById(Long id);

    @Select("SELECT * FROM deepseek_chat_conversations")
    List<Conversation> getAllConversations();

    @Delete("DELETE FROM deepseek_chat_conversations WHERE id = #{id}")
    void deleteConversation(Long id);


    @Insert("CREATE TABLE IF NOT EXISTS deepseek_chat_conversations (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "surveyor_id BIGINT NOT NULL, " +
            "conversation_start_time DATETIME(6) NOT NULL, " +
            "conversation_end_time DATETIME(6), " +
            "INDEX idx_surveyor_id (surveyor_id))")
    void createTable();

    @Select("SELECT COUNT(*) > 0 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'deepseek_chat_conversations'")
    boolean checkConversationTableExists();
}
