package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
@ComponentScan(basePackages = "com.example")
class ActorResourceTest {
//	@MockBean
//	SecurityFilterChain filterChain;
//	@MockBean
//	RestTemplate restTemplate;
//	@MockBean
//	RestTemplateBuilder builder;
//	@MockBean
//	LoadBalancedExchangeFilterFunction filterFunction;

	@Autowired
    private MockMvc mockMvc;
	
//	@MockBean
//	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Value
	static class ActorShortMock implements ActorShort {
		int id;
		String nombre;
	}
	@Test
	void testEntorno() {
		fail("Not yet implemented");
	}
	
//	@Test
//	void testGetAllString() throws Exception {
//		List<ActorShort> lista = new ArrayList<>(
//		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
//		        		new ActorShortMock(2, "Carmelo Coton"),
//		        		new ActorShortMock(3, "Capitan Tan")));
//		when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
//		mockMvc.perform(get("/actores/v1").queryParam("mode" ,"short").accept(MediaType.APPLICATION_JSON))
//			.andExpectAll(
//					status().isOk(), 
//					content().contentType("application/json"),
//					jsonPath("$.size()").value(3)
//					);
////		mvc.perform(get("/actores/v1").accept(MediaType.APPLICATION_XML))
////			.andExpectAll(
////					status().isOk(), 
////					content().contentType("application/json"),
////					jsonPath("$.size()").value(3)
////					);
//	}
//
//	@Test
//	void testGetAllPageable() throws Exception {
//		List<ActorDTO> lista = new ArrayList<>(
//		        Arrays.asList(new ActorDTO(1, "Pepito", "GRILLO"),
//		        		new ActorDTO(2, "Carmelo", "COTON"),
//		        		new ActorDTO(3, "Capitan", "TAN")));
//
//		when(srv.getByProjection(PageRequest.of(0, 20), ActorDTO.class))
//			.thenReturn(new PageImpl<>(lista));
//		mockMvc.perform(get("/actores/v1").queryParam("page", "0"))
//			.andExpectAll(
//				status().isOk(), 
//				content().contentType("application/json"),
//				jsonPath("$.content.size()").value(3),
//				jsonPath("$.size").value(3)
//				);
//	}
//
//	@Test
//	void testGetOne() throws Exception {
//		int id = 1;
//		var ele = new Actor(id, "Pepito", "Grillo");
//		when(srv.getOne(id)).thenReturn(Optional.of(ele));
//		mockMvc.perform(get("/actores/v1/{id}", id))
//			.andExpect(status().isOk())
//	        .andExpect(jsonPath("$.id").value(id))
//	        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
//	        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()))
//	        .andDo(print());
//	}
//	@Test
//	void testGetOne404() throws Exception {
//		int id = 1;
//		var ele = new Actor(id, "Pepito", "Grillo");
//		when(srv.getOne(id)).thenReturn(Optional.empty());
//		mockMvc.perform(get("/actores/v1/{id}", id))
//			.andExpect(status().isNotFound())
//			.andExpect(jsonPath("$.title").value("Not Found"))
//	        .andDo(print());
//	}
//
//	@Test
//	void testCreate() throws Exception {
//		int id = 1;
//		var ele = new Actor(id, "PEPITO", "GRILLO");
//		when(srv.add(ele)).thenReturn(ele);
//		mockMvc.perform(post("/actores/v1")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
//			)
//			.andExpect(status().isCreated())
//	        .andExpect(header().string("Location", "http://localhost/actores/v1/1"))
//	        .andDo(print())
//	        ;
//	}

//	@Test
//	void testUpdate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDelete() {
//		fail("Not yet implemented");
//	}

}