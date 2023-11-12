package com.malith.mysystem;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class AbstractTestContainerUnitTest {
    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                mySQLContainer.getJdbcUrl(),
                mySQLContainer.getUsername(),
                mySQLContainer.getPassword()
        ).load();
        flyway.migrate();

        System.out.println();
    }

    @Container
    protected static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>("mysql:latest")
                    .withDatabaseName("malith-dao")
                    .withUsername("malith")
                    .withPassword("Malith11a");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry){
        registry.add(
                "spring.datasource.url",
                mySQLContainer::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",
                mySQLContainer::getUsername
        );
        registry.add(
                "spring.datasource.password",
                mySQLContainer::getPassword
        );
    }

    private static DataSource getDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(mySQLContainer.getDriverClassName())
                .url(mySQLContainer.getJdbcUrl())
                .username(mySQLContainer.getUsername())
                .password(mySQLContainer.getPassword()).build();
    }

    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker FAKER = new Faker();

}
