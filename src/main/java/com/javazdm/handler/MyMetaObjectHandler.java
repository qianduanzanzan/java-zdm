package com.javazdm.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//自动填充的插件
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.setFieldValByName("createAt", LocalDateTime.parse(dateFormat.format(new Date()), df), metaObject);
        this.setFieldValByName("updateAt", LocalDateTime.parse(dateFormat.format(new Date()), df), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.setFieldValByName("updateAt", LocalDateTime.parse(dateFormat.format(new Date()), df), metaObject);
    }
}
