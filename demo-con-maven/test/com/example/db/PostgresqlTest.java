package com.example.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

class PostgresqlTest {

	//@Container
	PostgreSQLContainer<?> postgres = PostgreSQLContainerSingleton.getContainer();
//			new PostgreSQLContainer<>("postgres:17-alpine")
//	//.withCopyFileToContainer(MountableFile.forClasspathResource("init-db.sql"), "/docker-entrypoint-initdb.d/");
//	.withInitScript("init-db.sql");
	DBConnectionProvider connectionProvider;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		postgres.start();
		connectionProvider = new DBConnectionProvider(postgres.getJdbcUrl(), postgres.getUsername(),
				postgres.getPassword());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConnection() {
	    try (Connection conn = this.connectionProvider.getConnection()) {
	        var cmd = conn.prepareStatement("select count(*) from vets");
	        ResultSet rs = cmd.executeQuery();
	        rs.next();
	        var actual = rs.getLong(1);
	        assertEquals(6, actual);
	      } catch (SQLException e) {
	        throw new RuntimeException(e);
	      }
	}
}
