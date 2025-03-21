package org.ecnu.backend.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "deepseek_chat_rounds")
public class ChatRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @Column(name = "round_number", nullable = false)
    private int roundNumber;

    @Column(name = "messages", nullable = false)
    private String messages; // 存储 JSON 格式的 Map<String, String>

    @Column(name = "round_start_time", nullable = false)
    private LocalDateTime roundStartTime;

    @Column(name = "round_end_time", nullable = false)
    private LocalDateTime roundEndTime;

    public ChatRound() {
    }

    public ChatRound(Long conversationId, String roundId, int roundNumber, String messages, LocalDateTime roundStartTime, LocalDateTime roundEndTime) {
        this.conversationId = conversationId;
        this.roundNumber = roundNumber;
        this.messages = messages;
        this.roundStartTime = roundStartTime;
        this.roundEndTime = roundEndTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public LocalDateTime getRoundStartTime() {
        return roundStartTime;
    }

    public void setRoundStartTime(LocalDateTime roundStartTime) {
        this.roundStartTime = roundStartTime;
    }

    public LocalDateTime getRoundEndTime() {
        return roundEndTime;
    }

    public void setRoundEndTime(LocalDateTime roundEndTime) {
        this.roundEndTime = roundEndTime;
    }

    public static String convertMessagesToJson(List<Map<String, String>> messages) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(messages);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert messages to JSON", e);
        }
    }

    @Override
    public String toString() {
        return "DeepSeekChatRound{" +
                "id=" + id +
                ", conversationId='" + conversationId + '\'' +
                ", roundNumber=" + roundNumber +
                ", messages='" + messages + '\'' +
                ", roundStartTime=" + roundStartTime +
                ", roundEndTime=" + roundEndTime +
                '}';
    }
}