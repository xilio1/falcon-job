package cn.xilio.falcon.job.admin.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RemoteUtils {
    public static final String ACCESS_TOKEN = "FALCON-JOB-ACCESS-TOKEN";

    public static Object postBody(String url, String accessToken, int timeout, Object body, Class<?> returnTargClassOfT) {
        HttpURLConnection connection;
        BufferedReader bufferedReader;
        try {
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(timeout * 1000);
            connection.setConnectTimeout(timeout * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");
            if (accessToken != null && accessToken.trim().length() > 0) {
                connection.setRequestProperty(ACCESS_TOKEN, accessToken);
            }
            connection.connect();
            if (body != null) {
                String requestBody = new Gson().toJson(body);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
                dataOutputStream.flush();
                dataOutputStream.close();
            }
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                return "error";
            }
            // result
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            String resultJson = result.toString();

            Map map = new Gson().fromJson(resultJson, Map.class);
            return map;

        } catch (Exception e) {
            return "error";
        }

    }
}
