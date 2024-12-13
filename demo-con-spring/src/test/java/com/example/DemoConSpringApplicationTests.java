package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoConSpringApplicationTests {
	@LocalServerPort
	private Integer port;
	@Value("http://localhost:${local.server.port}")
	private String baseURL;

	@Test
	void contextLoads() {
		assertEquals("http://localhost:" + port.toString(), baseURL);
	}

}
