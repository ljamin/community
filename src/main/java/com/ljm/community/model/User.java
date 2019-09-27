package com.ljm.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private String gmtCreate;
    private String gmtModified;
    private String bio;
    private String avatarUrl;
}
