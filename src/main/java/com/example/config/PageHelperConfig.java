package com.example.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@MapperScan("com.example.mapper")
@Configuration
public class PageHelperConfig {

    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 设置数据库类型，可选值为 "mysql", "oracle", "mariadb", "sqlite", "hsqldb", "postgresql", "db2", "sqlserver", "informix"，根据实际情况设置
        properties.setProperty("helperDialect", "mysql");
        // 设置为 true 时，会将 RowBounds 第一个参数 offset 当成 pageNum 使用
        properties.setProperty("offsetAsPageNum", "true");
        // 设置为 true 时，使用 RowBounds 分页会进行 count 查询
        properties.setProperty("rowBoundsWithCount", "true");
        // 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
