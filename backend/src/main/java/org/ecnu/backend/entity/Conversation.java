package org.ecnu.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "deepseek_chat_conversations")
public class Conversation {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "surveyor_id", nullable = false) // 新增字段
    private Long surveyorId;

    @Column(name = "conversation_start_time", nullable = false)
    private LocalDateTime conversationStartTime;

    @Column(name = "conversation_end_time")
    private LocalDateTime conversationEndTime;

    public Conversation() {
    }

    public Conversation(String conversationId, Long surveyorId, LocalDateTime conversationStartTime) {
        this.surveyorId = surveyorId;
        this.conversationStartTime = conversationStartTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSurveyorId() {
        return surveyorId;
    }

    public void setSurveyorId(Long surveyorId) {
        this.surveyorId = surveyorId;
    }

    public LocalDateTime getConversationStartTime() {
        return conversationStartTime;
    }

    public void setConversationStartTime(LocalDateTime conversationStartTime) {
        this.conversationStartTime = conversationStartTime;
    }

    public LocalDateTime getConversationEndTime() {
        return conversationEndTime;
    }

    public void setConversationEndTime(LocalDateTime conversationEndTime) {
        this.conversationEndTime = conversationEndTime;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", surveyorId=" + surveyorId +
                ", conversationStartTime=" + conversationStartTime +
                ", conversationEndTime=" + conversationEndTime +
                '}';
    }
}