package com.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

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
//			assertAll("Propiedades", 
//					() -> assertEquals(0, p.getId()),
//					() -> assertEquals("Pepito", p.getNombre(), "Nombre"),
//					() -> assertEquals("Grillo", p.getApellidos().get(), "Apellidos"));
			assertThat("Propiedades", p, allOf(
					hasProperty("id", greaterThanOrEqualTo(0)),
					hasProperty("nombre", equalTo("Pepito")),
					hasProperty("apellidos", equalTo(Optional.of("Grillo")))
					));
		}
		@Test
		void createPersonaSinApellidos() {
			var p = Persona.creaPersona("Pepito");

			assertNotNull(p);
			Assumptions.assumeFalse(true, "Pendiente de terminar");
			assertEquals("Persona [id=0, nombre=Pepito, apellidos=null]", p.toString());
			//assertEquals(p, Persona.creaPersona("Otra"));
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
	}
}
