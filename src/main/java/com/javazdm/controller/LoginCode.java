package com.javazdm.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.javazdm.vo.LoginVo;
import com.javazdm.vo.RespBean;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("")
@Slf4j
public class LoginCode {

    @Resource
    DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public RespBean getCode(@RequestParam("codeId") String codeId) throws IOException {
        String text = defaultKaptcha.createText();
        redisTemplate.opsForValue().set(codeId, text, 60 * 5, TimeUnit.SECONDS);
        ByteArrayOutputStream out = null;
        BufferedImage image = defaultKaptcha.createImage(text);
        out = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",out);
        return RespBean.success(Base64.getEncoder().encodeToString(out.toByteArray()));
    }
}
