package com.aston.task3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractDaoIntegrationTest {

    @Container
    protected static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @BeforeAll
    static void beforeAll() {
        // Динамически настраиваем Hibernate для использования контейнера
        System.setProperty("hibernate.connection.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgresqlContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgresqlContainer.getPassword());
    }

    @AfterAll
    static void afterAll() {
        // Очищаем системные свойства после тестов
        System.clearProperty("hibernate.connection.url");
        System.clearProperty("hibernate.connection.username");
        System.clearProperty("hibernate.connection.password");
    }
}
