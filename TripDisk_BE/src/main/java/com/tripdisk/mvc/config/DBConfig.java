package com.tripdisk.mvc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.tripdisk.mvc.model.dao")
public class DBConfig {

}
