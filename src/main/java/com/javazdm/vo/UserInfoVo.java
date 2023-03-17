package com.javazdm.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserInfoVo implements Serializable {
    private String id;
    private String nickName;
    private String userName;
    private String avatar;
    private Integer sex;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String token;
}
