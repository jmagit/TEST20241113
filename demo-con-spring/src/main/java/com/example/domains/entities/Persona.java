package com.example.domains.entities;

import java.util.Objects;
import java.util.Optional;

import com.example.domains.core.entities.EntityValidatable;

public class Persona implements EntityValidatable {
	private int id;
	private String nombre;
	private String apellidos;
	
	private Persona() { }
	
	public Persona(int id, String nombre, String apellidos) {
		this.id = id;
		setNombre(nombre);
		setApellidos(apellidos);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		if(nombre == null)
			throw new IllegalArgumentException("El nombre no puede ser nulo.");
		this.nombre = nombre;
	}
	public Optional<String> getApellidos() {
		return Optional.ofNullable(apellidos);
	}
	public void setApellidos(String apellidos) {
		if(apellidos == null)
			throw new IllegalArgumentException("Los apellidos no pueden ser nulo.");
		this.apellidos = apellidos;
	}
	
	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
	}


	public static Persona creaPersona(String nombre, String apellidos) {
//		return null;
//		throw new ArithmeticException();
		return new Persona(0, nombre, apellidos);
	}

	public static Persona creaPersona(String nombre) {
//		return null;
		var p = new Persona();
		p.setNombre(nombre);
		return p;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Persona p) {
			return id == p.id;
		} else {
			return false;
		}
	}

	@Override
	public String getErrorsMessage() {
		var msg = new StringBuilder();
		if(nombre.isBlank())
			msg.append(" nombre: no puede estar en blanco.");
		if(apellidos != null && apellidos.isBlank())
			msg.append(" apellidos: no puede estar en blanco.");
		return msg.isEmpty() ? "" : "ERRORES:" + msg.toString();
	}
}
