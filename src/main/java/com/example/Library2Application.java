package com.example;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mapper") // 扫描Mapper接口
public class Library2Application {

    public static void main(String[] args) {
        SpringApplication.run(Library2Application.class, args);
    }

}
