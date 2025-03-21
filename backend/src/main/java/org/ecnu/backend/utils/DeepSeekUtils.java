package org.ecnu.backend.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * DeepSeek API 工具类
 */
public class DeepSeekUtils {

    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static String API_KEY; // API 密钥
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 初始化 API 密钥
     *
     * @param apiKey API 密钥
     */
    public static void initApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    /**
     * 发送消息并获取响应
     *
     * @param messages    消息列表
     * @param modelParams 模型参数
     * @return 响应内容
     */
    public static String sendMessage(List<Map<String, String>> messages, Map<String, Object> modelParams) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // 构建请求体
        Map<String, Object> requestBody = Map.of(
                "model", modelParams.get("model"),
                "messages", messages,
                "temperature", modelParams.get("temperature"),
                "max_tokens", modelParams.get("max_tokens")
        );

        // 创建 HTTP 实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送 POST 请求
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, Map.class);

        // 检查响应状态码
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // 获取响应体
            Map<String, Object> responseBody = responseEntity.getBody();
            if (responseBody != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, String> message = (Map<String, String>) choice.get("message");
                    return message.get("content");
                }
            }
        }

        return "Sorry, I couldn't process your request.";
    }

    /**
     * 处理对话（支持新对话和继续对话）
     *
     * @param messages    消息列表
     * @param userContent 用户消息内容
     * @param modelParams 模型参数
     * @return 响应内容
     */
    public static String handleConversation(List<Map<String, String>> messages, String userContent, Map<String, Object> modelParams) {
        messages.add(Map.of("role", "user", "content", userContent));
        String response = sendMessage(messages, modelParams);
        messages.add(Map.of("role", "assistant", "content", response));
        return response;
    }

    /**
     * 添加消息到对话
     *
     * @param messages 消息列表
     * @param role     消息角色（user 或 assistant）
     * @param content  消息内容
     */
    public static void addMessage(List<Map<String, String>> messages, String role, String content) {
        messages.add(Map.of("role", role, "content", content));
    }
}