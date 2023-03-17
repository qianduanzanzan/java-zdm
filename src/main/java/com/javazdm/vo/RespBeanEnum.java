package com.javazdm.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端出错"),

    ACCPWD_NOT_EMPTY(50001, "账号或密码不能为空"),
    ACC_ERROR(50002, "账号错误"),
    PWD_ERROR(50003, "密码错误"),
    NAME_NOT_EMPTY(50004, "用户名不能为空"),
    ACC_NOT_EMPTY(50005, "账号不能为空"),
    PWD_NOT_EMPTY(50006, "密码不能为空"),
    NOT_LOGIN(52001, "登陆已过期"),
    ACCOUNT_REPEAT(50008, "账户重复"),
    NAME_REPEAT(50009, "用户名重复"),
    PROD_NOT_SKU(50010, "该产品没有库存信息，不能启用"),
    SKU_INSUFFICIENT(50011, "库存不足"),
    CUS_INFO_ERROR(50012, "用户信息错误"),
    PROD_IN_CART(50013, "该产品已存在购物车"),
    ;

    private final Integer code;
    private final String msg;
}
