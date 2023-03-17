package com.javazdm.controller;

import com.javazdm.service.impl.UserServiceImpl;
import com.javazdm.vo.RespBean;
import com.javazdm.vo.UserPageNationVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public RespBean getUserList(@Valid UserPageNationVo userPageNationVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return userServiceImpl.getUserList(userPageNationVo);
    }
}
