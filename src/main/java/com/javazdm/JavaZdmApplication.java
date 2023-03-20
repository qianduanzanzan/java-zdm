package com.javazdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(scanBasePackages = "com.javazdm")
@MapperScan("com.javazdm")
//@CrossOrigin(origins = "http://127.0.0.1:5173")
@EnableTransactionManagement(proxyTargetClass = true)
public class JavaZdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaZdmApplication.class, args);
    }

}
