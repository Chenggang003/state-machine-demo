package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 17:11
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.demo.mapper")
public class StateMachineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineDemoApplication.class,args);
    }

}
