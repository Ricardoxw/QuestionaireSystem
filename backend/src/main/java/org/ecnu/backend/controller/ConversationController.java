package org.ecnu.backend.controller;

import org.ecnu.backend.entity.ChatRound;
import org.ecnu.backend.entity.Conversation;
import org.ecnu.backend.helper.ConversationHelper;
import org.ecnu.backend.service.ConversationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {
    @Autowired
    private ConversationManagementService conversationManagementService;

    @Autowired
    private ConversationHelper conversationHelper;

    // 创建会话
    @PostMapping("/create")
    public Long createConversation(@RequestBody Long surveyorId) {
        return conversationHelper.createAndCacheConversationToRedis(surveyorId);
    }

    // 结束会话
    @PutMapping("/end/{conversationId}")
    public void endConversation(@PathVariable Long conversationId) {
        conversationHelper.saveChatRoundsAndConversationToMySQL(conversationId);
    }

    // 添加轮次
    @PostMapping("/chatRounds/{conversationId}")
    public void addChatRound(@PathVariable Long conversationId, @RequestBody String userMessage) {
        List<ChatRound> chatRounds = conversationHelper.getChatRoundsFromRedis(conversationId);
        ChatRound chatRound = new ChatRound();
        chatRound.setConversationId(conversationId);
        chatRound.setRoundNumber(chatRounds.size()+1);
        conversationHelper.cacheChatRoundToRedis(conversationId, chatRound);
    }

    // 查询会话
    @GetMapping("/select/{id}")
    public Conversation getConversation(@PathVariable Long id) {
        return conversationManagementService.getConversationById(id);
    }

    // 查询所有会话
    @GetMapping("/select/all")
    public List<Conversation> getAllConversations() {
        return conversationManagementService.getAllConversations();
    }

    // 删除会话
    @DeleteMapping("/delete/{id}")
    public void deleteConversation(@PathVariable Long id) {
        conversationManagementService.deleteConversation(id);
    }
}