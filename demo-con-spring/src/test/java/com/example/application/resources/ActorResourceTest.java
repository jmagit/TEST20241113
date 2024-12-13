package com.example.application.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
	private MockMvc mockMvc;
	
//    @Autowired
//    TestRestTemplate restTemplate;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void test() throws Exception {
		mockMvc
			.perform(get("/actores/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.size()").value(3)
				);
	}

}
