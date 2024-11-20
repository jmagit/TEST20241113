package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PersonaTest {
	@Nested
	@Tag("smoke")
	class OK {

		@Test
		void createPersona() {
			var p = Persona.creaPersona("Pepito", "Grillo");

			assertNotNull(p);
//			assertEquals("Persona [id=0, nombre=Pepito, apellidos=Grillo]", p.toString());
			assertAll("Propiedade", 
					() -> assertEquals(0, p.getId()),
					() -> assertEquals("Pepito", p.getNombre(), "Nombre"),
					() -> assertEquals("Grillo", p.getApellidos().get(), "Apellidos"));
		}
		@Test
		void createPersonaSinApellidos() {
			var p = Persona.creaPersona("Pepito");

			assertNotNull(p);
			Assumptions.assumeFalse(true, "Pendiente de terminar");
			assertEquals("Persona [id=0, nombre=Pepito, apellidos=null]", p.toString());
			assertAll("Propiedade", 
					() -> assertEquals(0, p.getId()),
					() -> assertEquals("Pepito", p.getNombre(), "Nombre"),
					() -> assertTrue(p.getApellidos().isEmpty(), "Apellidos"));
		}
	}

	@Nested
	class KO {
		@Test
		void createPersonaSinNombre() {
			var ex = assertThrows(IllegalArgumentException.class, () -> Persona.creaPersona(null, "Grillo"));
//			var ex = assertThrows(IllegalArgumentException.class, () -> Persona.creaPersona("Grillo", null));
			assertEquals("El nombre no puede ser nulo.", ex.getMessage());
		}
		@Test
		void createPersonaSinApellidos() {
			assertThrows(IllegalArgumentException.class, () -> Persona.creaPersona("Grillo", null ));
		}
	}
}
