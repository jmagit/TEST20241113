package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Persona;

class PersonaTest {
	@Nested
	@Tag("smoke")
	class OK {

		@Test
		void createPersona() {
			var p = Persona.creaPersona("Pepito", "Grillo");

			assertNotNull(p);
			assertTrue(p.isValid());
//			assertEquals("Persona [id=0, nombre=Pepito, apellidos=Grillo]", p.toString());
			assertAll("Propiedades", 
					() -> assertEquals(0, p.getId()),
					() -> assertEquals("Pepito", p.getNombre(), "Nombre"),
					() -> assertEquals("Grillo", p.getApellidos().get(), "Apellidos"));
		}
		@Test
		void createPersonaSinApellidos() {
			var p = Persona.creaPersona("Pepito");

			assertNotNull(p);
			assertTrue(p.isValid());
			Assumptions.assumeFalse(true, "Pendiente de terminar");
			assertEquals("Persona [id=0, nombre=Pepito, apellidos=null]", p.toString());
			assertAll("Propiedades", 
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
		@Test
		void createPersonaConNombreEnBlanco() {
			var p = Persona.creaPersona("  ", "Grillo");
			assertEquals("ERRORES: nombre: no puede estar en blanco.", p.getErrorsMessage());
			assertFalse(p.isValid());
			assertTrue(p.isInvalid());
		}
		@Test
		void createPersonaConApellidosEnBlanco() {
			var p = Persona.creaPersona("Pepito", "");
			assertEquals("ERRORES: apellidos: no puede estar en blanco.", p.getErrorsMessage());
			assertFalse(p.isValid());
			assertTrue(p.isInvalid());
		}
		@Test
		void createPersonaConNombre_y_ApellidosEnBlanco() {
			var p = Persona.creaPersona("", "  ");
			assertEquals("ERRORES: nombre: no puede estar en blanco. apellidos: no puede estar en blanco.", p.getErrorsMessage());
			assertFalse(p.isValid());
			assertTrue(p.isInvalid());
		}
	}
}
