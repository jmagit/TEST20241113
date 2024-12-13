package com.example.application.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;

@RestController
@RequestMapping("/actores")
public class ActorResource {
	@Value
	static class ActorShort {
		int id;
		String nombre;
	}
	@GetMapping(path = "/v1")
	public List<?> getAll(@RequestParam(required = false, defaultValue = "largo") String modo) {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShort(1, "Pepito Grillo"),
		        		new ActorShort(2, "Carmelo Coton"),
		        		new ActorShort(3, "Capitan Tan")));
		return lista;
	}
}
