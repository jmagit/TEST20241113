package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.PersonaRepository;
import com.example.domains.contracts.services.PersonaService;
import com.example.domains.entities.Persona;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

//@Service
public class PersonaServiceImpl implements PersonaService {
	private PersonaRepository dao;

	public PersonaServiceImpl(PersonaRepository dao) {
		if(dao == null) throw new IllegalArgumentException("dao is null");
		this.dao = dao;
	}

	@Override
	public List<Persona> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Persona> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Persona add(Persona item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		if(item.getId() != 0 && dao.existsById(item.getId()))
			throw new DuplicateKeyException("Ya existe");
		return dao.save(item);
	}

	@Override
	public Persona modify(Persona item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		if(!dao.existsById(item.getId()))
			throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Persona item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
}
