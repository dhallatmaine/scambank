package com.vecowski.scambankcustomer;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class PostgresMockConfiguration {

    private static final PostgreSQLContainer<?> CONTAINER;

    static {
        CONTAINER = new PostgreSQLContainer<>();
        CONTAINER.start();
    }

    @Bean
    @Primary
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(CONTAINER.getJdbcUrl());
        dataSource.setUsername(CONTAINER.getUsername());
        dataSource.setPassword(CONTAINER.getPassword());
        dataSource.setDriverClassName(CONTAINER.getDriverClassName());
        return dataSource;
    }

}
