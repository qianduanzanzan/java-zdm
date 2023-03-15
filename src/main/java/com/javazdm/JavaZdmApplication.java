package com.javazdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.javazdm")
@MapperScan("com.javazdm")
@EnableTransactionManagement(proxyTargetClass = true)
public class JavaZdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaZdmApplication.class, args);
    }

}
