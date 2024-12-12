package com.example.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import redis.clients.jedis.UnifiedJedis;

@Testcontainers
public class RedisTest {
	private ExternalCounter cont;

	@Container
	public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:alpine"))
	    .withExposedPorts(6379);

	@BeforeEach
    public void setUp(TestReporter testReporter) {
//		redis.start();
        String address = redis.getHost();
        Integer port = redis.getFirstMappedPort();
        
        testReporter.publishEntry("address", address);
        testReporter.publishEntry("port", port.toString());
        
        cont = new ExternalCounter("megusta-total", new UnifiedJedis(String.format("redis://%s:%s", address, port)));
    }

	@AfterEach
	void tearDown() throws Exception {
		redis.stop();
	}

    @Test
    public void testInit() {
    	var actual = cont.get();
    	
    	assertEquals(0, actual);
    }

    @Test
    public void testIncr() {
    	var actual = cont.increment();
    	
    	assertEquals(1, actual);
    }

}
