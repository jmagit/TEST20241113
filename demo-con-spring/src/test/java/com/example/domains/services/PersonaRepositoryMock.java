package com.example.domains.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.domains.contracts.repositories.PersonaRepository;
import com.example.domains.entities.Persona;

public class PersonaRepositoryMock implements PersonaRepository {
	List<Persona> lista = new ArrayList<Persona>(Arrays.asList(
				Persona.creaPersona("Anonimo"),
				new Persona(1, "Pepito", "Grillo"),
				new Persona(2, "Carmelo", "COTON"), 
				new Persona(3, "Capitan", "TAN")
				));

	@Override
	public List<Persona> findAll() {
		return lista;
	}

	@Override
	public Optional<Persona> findById(Integer id) {
		if(id == 1) return Optional.of(lista.get(1));
		return Optional.empty();
	}

	@Override
	public boolean existsById(Integer id) {
		if(id == 1 || id == 11) return true;
		return false;
	}

	@Override
	public Persona save(Persona item) {
		if(item.getId() == 0)
			item.setId(11);
		return item;
	}

	@Override
	public void delete(Persona item) {
	}

	@Override
	public void deleteById(Integer id) {
	}

}
