package org.ecnu.backend.mapper;

import org.apache.ibatis.annotations.*;
import org.ecnu.backend.entity.ChatRound;

import java.util.List;

@Mapper
public interface ChatRoundMapper {
    @Insert("INSERT INTO deepseek_chat_rounds (conversation_id, round_number, messages, round_start_time, round_end_time) VALUES (#{conversationId}, #{roundNumber}, #{messages}, #{roundStartTime}, #{roundEndTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertChatRound(ChatRound chatRound);

    @Select("SELECT * FROM deepseek_chat_rounds WHERE conversation_id = #{conversationId}")
    List<ChatRound> getChatRoundsByConversationId(Long conversationId);

    @Delete("DELETE FROM deepseek_chat_rounds WHERE conversation_id = #{conversationId}")
    void deleteChatRoundsByConversationId(Long conversationId);

    @Insert("CREATE TABLE IF NOT EXISTS deepseek_chat_rounds (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "conversation_id BIGINT NOT NULL, " +
            "round_number INT NOT NULL, " +
            "messages TEXT NOT NULL, " +
            "round_start_time DATETIME(6) NOT NULL, " +
            "round_end_time DATETIME(6) NOT NULL, " +
            "INDEX idx_conversation_id (conversation_id), " +
            "INDEX idx_round_number (round_number))")
    void createTable();

    @Select("SELECT COUNT(*) > 0 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'deepseek_chat_rounds'")
    boolean checkChatRoundTableExists();
}