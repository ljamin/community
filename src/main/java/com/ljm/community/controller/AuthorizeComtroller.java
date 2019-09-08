package com.ljm.community.controller;

import com.ljm.community.POJO.AccessToken;
import com.ljm.community.POJO.GithubUesr;
import com.ljm.community.mapper.UserMapper;
import com.ljm.community.model.User;
import com.ljm.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Currency;
import java.util.UUID;

@Controller
public class AuthorizeComtroller {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private  String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){
        System.out.println(code);
        System.out.println(state);
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirectUri);
        accessToken.setState(state);
        String accessToken1 = githubProvider.getAccessToken(accessToken);
        GithubUesr githubUser = githubProvider.getGithubUser(accessToken1);
        if(githubUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(String.valueOf(System.currentTimeMillis()));
            user.setGmtModified(user.getGmtCreate());
            user.setBio(githubUser.getBio());
            userMapper.insert(user);
            //登录成功，写cookie和session
            request.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }


}
