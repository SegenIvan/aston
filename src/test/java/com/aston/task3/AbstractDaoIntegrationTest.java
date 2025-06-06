package com.aston.task3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
public abstract class AbstractDaoIntegrationTest {

    @Container
    protected static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    private static Connection connection;

    @BeforeAll
    static void beforeAll() throws SQLException {
        System.setProperty("hibernate.connection.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgresqlContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgresqlContainer.getPassword());
        connection = DriverManager.getConnection(
                postgresqlContainer.getJdbcUrl(),
                postgresqlContainer.getUsername(),
                postgresqlContainer.getPassword());

    }

    @AfterEach
    void cleanDatabase() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("TRUNCATE TABLE USERS");
        }


    @AfterAll
    static void afterAll() {
        System.clearProperty("hibernate.connection.url");
        System.clearProperty("hibernate.connection.username");
        System.clearProperty("hibernate.connection.password");
    }
}
