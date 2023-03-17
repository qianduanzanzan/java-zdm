package com.javazdm.controller;

import com.javazdm.service.impl.UserServiceImpl;
import com.javazdm.vo.LoginVo;
import com.javazdm.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Slf4j
public class LoginController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespBean login(@RequestBody LoginVo loginVo) {
        return userServiceImpl.login(loginVo);
    }
}
