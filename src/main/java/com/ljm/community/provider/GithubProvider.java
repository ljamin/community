package com.ljm.community.provider;

import com.alibaba.fastjson.JSON;
import com.ljm.community.dto.AccessTokenDTO;
import com.ljm.community.dto.GithubUesr;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component：将当前的类初始化到spring容器的上下文
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
       MediaType mediaType = MediaType.get("application/json; charset=utf-8");
       OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONBytes(accessTokenDTO),mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String split =string.split("&")[0].split("=")[1];
            System.out.println(split);
            return split;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUesr getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUesr githubUesr = JSON.parseObject(string, GithubUesr.class);
            return githubUesr;
        } catch (IOException e) {
        }
        return null;
    }
}
