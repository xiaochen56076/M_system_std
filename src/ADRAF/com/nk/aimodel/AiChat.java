package ADRAF.com.nk.aimodel;

// @Author：nskdf
// @Time：2026-06-06-12-00
// @Project：ADARF_P

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AiChat {
    private static final String AI_API_URL = "https://api.deepseek.com/chat/completions";
    private static final String AI_API_KEY = "sk-46a2e65970ff4c86add479a126005abf";
    private static final String AI_Content = "你是一个药物不良反应分析助手。" +
            "请根据以下患者主诉，分析可能是哪些常见药物引起的不良反应，并给出简短建议。注意，你的分析仅供参考，不能替代医生诊断。" +
            "患者主诉：{用户输入的症状}" +
            "请按以下格式回复：" +
            "【可能相关药物】：列出2-3种可疑药物" +
            "【分析说明】：简要分析原因" +
            "【建议】：给出安全提醒";

    public static String callai(String word) throws IOException, InterruptedException {

        //构建客户端
        HttpClient Client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

        //构建请求数据包的对象
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AI_API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + AI_API_KEY)
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(createjson(word)))
                .build();

        //等待数据的回复
        HttpResponse<String> response = Client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("状态码：" + response.statusCode() + "\n内容：" + response.body());
        }

        String me = new JSONObject(response.body())
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return me;

    }


    //构建json体
    private static String createjson(String word) {
        //创建消息对象
        JSONObject Msg = new JSONObject();
        Msg.put("role", "system");
        Msg.put("content", AI_Content);

        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", word);

        //创建消息数组
        JSONArray msgArr = new JSONArray().put(Msg).put(userMsg);


        //创建最外层的请求体
        JSONObject body = new JSONObject();
        body.put("model", "deepseek-chat");
        body.put("messages", msgArr);

        return body.toString();
    }

}
