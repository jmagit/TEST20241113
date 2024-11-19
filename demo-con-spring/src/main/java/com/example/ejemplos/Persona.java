package com.example.ejemplos;

import java.util.Optional;

public class Persona {
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
		var p = new Persona();
		p.setNombre(nombre);
		return p;
	}

}
