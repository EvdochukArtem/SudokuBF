package org.exmpl.service.dao;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan({"org.exmpl.service.dao", "org.exmpl.service.db"})
public class FieldDaoTestConfiguration {

    @Bean
    public DataSource dataSource() {
        return JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "", "");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
