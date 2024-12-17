package com.example.proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8080)
class ActorProxyTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testOK() throws Exception {
		stubFor(get(urlEqualTo("/api/actores/1")).withHeader("accept", equalTo("application/json"))
				.willReturn(aResponse().withStatus(200).withFixedDelay(3000)
						.withBody("{ \"id\": 1, \"nombre\":\"John\",\"apellidos\":\"Doe\"}")));

		var p = new ActorProxy();

		var actual = p.getActor(1);

		assertTrue(actual.isPresent());
		assertEquals(new Actor(1, "John", "Doe"), actual.get());
	}

	@Test
	void testKO() throws Exception {
		stubFor(get(urlEqualTo("/api/actores/1")).withHeader("accept", equalTo("application/json"))
				.willReturn(aResponse().withStatus(404)));

		var p = new ActorProxy();

		var actual = p.getActor(1);

		assertTrue(actual.isEmpty());
	}

}
