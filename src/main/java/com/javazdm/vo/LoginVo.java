package com.javazdm.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginVo {
    @NotBlank(message = "账号未传")
    @Size(min = 6, max = 15, message = "账号长度在6-15之间")
    private String username;
    @NotBlank(message = "密码未传")
    private String password;

    @NotBlank(message = "验证码未传")
    private String code;
    private String codeId;
}
