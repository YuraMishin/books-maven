package com.mishinyura.booksmaven.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DirtiesContext
@Testcontainers
@ActiveProfiles("test")
@Rollback(value = true)
public abstract class BaseTest {
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @Container
    private static final PostgreSQLContainer<?> CONTAINER =
            new PostgreSQLContainer<>("postgres:14");

    @DynamicPropertySource
    static void initProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driverClassName", CONTAINER::getDriverClassName);
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.liquibase.url", CONTAINER::getJdbcUrl);
        registry.add("spring.liquibase.password", CONTAINER::getPassword);
        registry.add("spring.liquibase.user", CONTAINER::getUsername);
    }
}
