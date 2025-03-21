package org.ecnu.backend;

import org.ecnu.backend.mapper.ChatRoundMapper;
import org.ecnu.backend.mapper.ConversationMapper;
import org.ecnu.backend.mapper.SurveyorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    @Autowired
    private SurveyorMapper surveyorMapper;
    @Autowired
    private ConversationMapper conversationMapper;
    @Autowired
    private ChatRoundMapper chatRoundMapper;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 检查并创建表
        if (!surveyorMapper.checkSurveyorTableExists()) {
            surveyorMapper.createTable();
        }
        if (!conversationMapper.checkConversationTableExists()) {
            conversationMapper.createTable();
        }
        if (!chatRoundMapper.checkChatRoundTableExists()) {
            chatRoundMapper.createTable();
        }
    }
}
