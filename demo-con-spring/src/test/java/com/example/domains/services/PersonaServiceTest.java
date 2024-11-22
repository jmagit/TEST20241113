package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

import com.example.domains.contracts.repositories.PersonaRepository;
import com.example.domains.contracts.services.PersonaService;
import com.example.domains.entities.Persona;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.testutils.IntegrationTest;
import com.example.testutils.UnitTest;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class PersonaServiceTest {
	PersonaRepository dao;
	PersonaService srv;
	
	@Nested
	@Order(1)
	@UnitTest
	@DisplayName("Pruebas unitarias")
	class Unit {
		List<Persona> listaFake;
		
		@BeforeEach
		void setUp() throws Exception {
			listaFake = new ArrayList<Persona>(Arrays.asList(
					Persona.creaPersona("Anonimo"),
					new Persona(1, "Pepito", "GRILLO"),
					new Persona(2, "Carmelo", "COTON"), 
					new Persona(3, "Capitan", "TAN")
					));
			dao = mock(PersonaRepository.class);
			srv = new PersonaServiceImpl(dao);
		}
		
		@Nested
		@Order(1)
		@DisplayName("Casos Validos")
		class OK {
			@Test
			@DisplayName("ctor: crea una instancia del servicio")
			void testCtor_PersonaService() {
				assertNotNull(srv);
			}
			
			@Test
			@DisplayName("getAll: recupera multiples entidades")
			void testGetAll_isNotEmpty() {
				when(dao.findAll()).thenReturn(listaFake);

				var obtenido = srv.getAll();
				
				assertNotNull(obtenido);
				assertEquals(4, obtenido.size());
				verify(dao, times(1)).findAll();
			}
			@Test
			@DisplayName("getAll: recupera 0 entidades porque esta vacia")
			void testGetAll_isEmpty() {
				when(dao.findAll()).thenReturn(new ArrayList<Persona>());
				
				var obtenido = srv.getAll();
				
				assertNotNull(obtenido);
				assertEquals(0, obtenido.size());
				verify(dao, times(1)).findAll();
			}
		
			@Test
			@DisplayName("getOne: recupera la entidad por su ID")
			void testGetOne() {
				when(dao.findById(1)).thenReturn(Optional.of(new Persona(1, "Pepito", "Grillo")));
				
				var obtenido = srv.getOne(1).orElseGet(() -> fail("Empty"));
				
				assertAll("Propiedades", 
						() -> assertEquals(1, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
	
			@Test
			@DisplayName("add: añade una entidad sin ID")
			void testAdd_withoutKey() {
				var item = new Persona(0, "Pepito", "Grillo");
				when(dao.save(any(Persona.class))).thenReturn(new Persona(1, "Pepito", "Grillo"));

				var obtenido = assertDoesNotThrow(() -> srv.add(item));

				assertNotNull(obtenido);
				assertAll("Propiedades", 
						() -> assertEquals(1, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
			
			@Test
			@DisplayName("add: añade una entidad con ID")
			void testAdd_withKey() {
				var item = new Persona(1, "Pepito", "Grillo");
				when(dao.existsById(1)).thenReturn(false);
				when(dao.save(any(Persona.class))).thenReturn(new Persona(1, "Pepito", "Grillo"));

				var obtenido = assertDoesNotThrow(() -> srv.add(item));

				assertNotNull(obtenido);
				assertAll("Propiedades", 
						() -> assertEquals(1, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
			
			@Test
			@DisplayName("modify: modifica una entidad existente")
			void testModify() {
				var item = new Persona(1, "Pepito", "Grillo");
				when(dao.existsById(1)).thenReturn(true);
				when(dao.findById(1)).thenReturn(Optional.of(new Persona(1, "PEPITO", "GRILLO")));
				when(dao.save(any(Persona.class))).thenReturn(item);
				
				var obtenido = assertDoesNotThrow(() -> srv.modify(item));
				
				assertAll("Propiedades", 
						() -> assertEquals(1, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
			
			@Test
			@DisplayName("delete: borra una entidad existente")
			void testDelete() {
				var item = new Persona(1, "Pepito", "Grillo");
				doNothing().when(dao).delete(item);

				assertDoesNotThrow(() -> srv.delete(item));
			}

			@Test
			@DisplayName("deleteById: borra un ID existente")
			void testDeleteById() {
				doNothing().when(dao).deleteById(1);

				assertDoesNotThrow(() -> srv.deleteById(1));
			}

		}
		
		@Nested
		@DisplayName("Casos Invalidos")
		class KO {
			@Test
			@DisplayName("ctor: lanza una IllegalArgumentException al crear una instancia del servicio sin dao")
			void testCtor_PersonaService() {
				var ex = assertThrows(IllegalArgumentException.class, () -> new PersonaServiceImpl(null));
				assertEquals("dao is null", ex.getMessage());
			}
			
			@Test
			@DisplayName("getOne: recupera un Optional.empty cuando no encuentra el ID")
			void testGetOne() {
				when(dao.findById(2)).thenReturn(Optional.empty());
				
				var obtenido = srv.getOne(2);
				
				assertTrue(obtenido.isEmpty());
				verify(dao, times(1)).findById(2);
			}
	
			@Test
			@DisplayName("add: lanza una InvalidDataException si no le pasan la entidad a añadir")
			void testAdd_Null() {
				var ex = assertThrows(InvalidDataException.class, () -> srv.add(null));
				assertEquals("No puede ser nulo", ex.getMessage());
			}
	
			@Test
			@DisplayName("add: lanza una InvalidDataException si le pasan una entidad con datos invalidos")
			void testAdd_InvalidData() {
				var item = mock(Persona.class);
				when(item.isInvalid()).thenReturn(true);
				when(item.isValid()).thenReturn(false);
				when(item.getErrorsMessage()).thenReturn("error forzado");
				
				var ex = assertThrows(InvalidDataException.class, () -> srv.add(item));
				
				assertEquals("error forzado", ex.getMessage());
				verify(dao, never()).save(item);
			}
	
			@Test
			@DisplayName("add: lanza una DuplicateKeyException si se intenta añadir una entidad que ya existe")
			void testAdd_Duplicate() {
				when(dao.existsById(1)).thenReturn(true);
				var item = new Persona(1, "Pepito", "Grillo");
				
				assertThrows(DuplicateKeyException.class, () -> srv.add(item));
				
				verify(dao, never()).save(item);
			}
			
			@Test
			@DisplayName("modify: lanza una InvalidDataException si no le pasan la entidad a modificar")
			void testModify_Null() {
				var ex = assertThrows(InvalidDataException.class, () -> srv.modify(null));
				assertEquals("No puede ser nulo", ex.getMessage());
			}
	
			@Test
			@DisplayName("modify: lanza una InvalidDataException si le pasan una entidad con datos invalidos")
			void testModify_InvalidData() {
				var item = mock(Persona.class);
				when(item.isInvalid()).thenReturn(true);
				when(item.isValid()).thenReturn(false);
				when(item.getErrorsMessage()).thenReturn("error forzado");
				
				var ex = assertThrows(InvalidDataException.class, () -> srv.modify(item));
				
				assertEquals("error forzado", ex.getMessage());
				verify(dao, times(0)).save(item);
			}
	
			@Test
			@DisplayName("modify: lanza una NotFoundException si se intenta modificar una entidad que no existe")
			void testModify_NotFoundException() {
				when(dao.existsById(1)).thenReturn(false);
				var item = new Persona(1, "Pepito", "Grillo");
				
				assertThrows(NotFoundException.class, () -> srv.modify(item));
				
				verify(dao, times(0)).save(item);
			}
			
			@Test
			@DisplayName("delete: lanza una InvalidDataException si no le pasan la entidad a borrar")
			void testDelete_Null() {
				var ex = assertThrows(InvalidDataException.class, () -> srv.delete(null));
				assertEquals("No puede ser nulo", ex.getMessage());
			}
	
		}
	}
	
	@Nested
	@Order(2)
	@IntegrationTest
	@DisplayName("Pruebas de integracion")
	class Integration {
		@BeforeEach
		void setUp() throws Exception {
			dao = new PersonaRepositoryMock(); // new PersonaRepositoryImpl();
			srv = new PersonaServiceImpl(dao);
		}
		
		@Nested
		@Order(1)
		@DisplayName("Casos Validos")
		class OK {
			@Test
			@DisplayName("ctor: crea una instancia del servicio")
			void testCtor_PersonaService() {
				assertNotNull(srv);
			}
			
			@Test
			@DisplayName("getAll: recupera multiples entidades")
			void testGetAll_isNotEmpty() {
				var obtenido = srv.getAll();
				
				assertNotNull(obtenido);
				assertEquals(4, obtenido.size());
			}
//			@Test
//			@DisplayName("getAll: recupera 0 entidades porque esta vacia")
//			void testGetAll_isEmpty() {
//				var obtenido = srv.getAll();
//				
//				assertNotNull(obtenido);
//				assertEquals(0, obtenido.size());
//			}
		
			@Test
			@DisplayName("getOne: recupera la entidad por su ID")
			void testGetOne() {
				var obtenido = srv.getOne(1).orElseGet(() -> fail("Empty"));
				
				assertAll("Propiedades", 
						() -> assertEquals(1, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
	
			@Test
			@Order(1) // Apesta
			@DisplayName("add: añade una entidad sin ID")
			void testAdd_withoutKey() {
				var item = new Persona(0, "Pepito", "Grillo");

				var obtenido = assertDoesNotThrow(() -> srv.add(item));

				assertNotNull(obtenido);
				assertAll("Propiedades", 
//						() -> assertEquals(11, obtenido.getId()),
						() -> assertTrue(obtenido.getId() > 0, "Id"),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
			
			@Test
			@DisplayName("add: añade una entidad con ID")
			void testAdd_withKey() {
				var item = new Persona(2, "Pepito", "Grillo");

				var obtenido = assertDoesNotThrow(() -> srv.add(item));

				assertNotNull(obtenido);
				assertAll("Propiedades", 
						() -> assertEquals(2, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
			
			@Test
			@Order(2) // Apesta
			@DisplayName("modify: modifica una entidad existente")
			void testModify() {
				var item = new Persona(1, "Pepito", "Grillo");
				
				var obtenido = assertDoesNotThrow(() -> srv.modify(item));
				
				assertAll("Propiedades", 
						() -> assertEquals(1, obtenido.getId()),
						() -> assertEquals("Pepito", obtenido.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenido.getApellidos().get(), "Apellidos"));
			}
			
			@Test
			@DisplayName("delete: borra una entidad existente")
			void testDelete() {
				var item = new Persona(1, "Pepito", "Grillo");

				assertDoesNotThrow(() -> srv.delete(item));
			}

			@Test
			@Order(3) // Apesta
			@DisplayName("deleteById: borra un ID existente")
			void testDeleteById() {
				assertDoesNotThrow(() -> srv.deleteById(1));
			}

			@Test
			@DisplayName("wrokflow: flujo de creación, modificación y borrado")
			void testWrokflowCRUD() {
				// add
				var obtenidoAdd = assertDoesNotThrow(() -> srv.add(new Persona(0, "Pepito", "Grillo")));

				assertNotNull(obtenidoAdd);
				assertAll("Propiedades Add", 
//						() -> assertEquals(1, obtenidoAdd.getId()),
						() -> assertTrue(obtenidoAdd.getId() > 0, "Id"),
						() -> assertEquals("Pepito", obtenidoAdd.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenidoAdd.getApellidos().get(), "Apellidos"));
				
				// update
				var obtenidoModify = assertDoesNotThrow(() -> srv.modify(new Persona(obtenidoAdd.getId(), obtenidoAdd.getNombre().toUpperCase(), obtenidoAdd.getApellidos().get())));
				
				assertNotNull(obtenidoModify);
				assertAll("Propiedades Modify", 
						() -> assertEquals(obtenidoAdd.getId(), obtenidoModify.getId(), "Id"),
						() -> assertEquals("PEPITO", obtenidoModify.getNombre(), "Nombre"),
						() -> assertEquals("Grillo", obtenidoModify.getApellidos().get(), "Apellidos"));
				
				// delete
				assertDoesNotThrow(() -> srv.deleteById(obtenidoAdd.getId()));
				
				// verify
				assertTrue(srv.getOne(obtenidoAdd.getId()).isEmpty());
			}

		}
		
		@Nested
		@DisplayName("Casos Invalidos")
		class KO {
			@Test
			@DisplayName("ctor: lanza una IllegalArgumentException al crear una instancia del servicio sin dao")
			void testCtor_PersonaService() {
				var ex = assertThrows(IllegalArgumentException.class, () -> new PersonaServiceImpl(null));
				assertEquals("dao is null", ex.getMessage());
			}
			
			@Test
			@DisplayName("getOne: recupera un Optional.empty cuando no encuentra el ID")
			void testGetOne() {
				var obtenido = srv.getOne(2);
				
				assertTrue(obtenido.isEmpty());
			}
	
//			@Test
//			@DisplayName("add: lanza una InvalidDataException si no le pasan la entidad a añadir")
//			void testAdd_Null() {
//				var ex = assertThrows(InvalidDataException.class, () -> srv.add(null));
//				assertEquals("No puede ser nulo", ex.getMessage());
//			}
//	
			@Test
			@DisplayName("add: lanza una InvalidDataException si le pasan una entidad con datos invalidos")
			void testAdd_InvalidData() {
				var item = new Persona(1, "  ", "Grillo");
				
				var ex = assertThrows(InvalidDataException.class, () -> srv.add(item));
				
				assertEquals("ERRORES: nombre: no puede estar en blanco.", ex.getMessage());
			}
	
			@Test
			@DisplayName("add: lanza una DuplicateKeyException si se intenta añadir una entidad que ya existe")
			void testAdd_Duplicate() {
				var item = new Persona(1, "Pepito", "Grillo");
				
				assertThrows(DuplicateKeyException.class, () -> srv.add(item));
			}
			
//			@Test
//			@DisplayName("modify: lanza una InvalidDataException si no le pasan la entidad a modificar")
//			void testModify_Null() {
//				var ex = assertThrows(InvalidDataException.class, () -> srv.modify(null));
//				assertEquals("No puede ser nulo", ex.getMessage());
//			}
	
			@Test
			@DisplayName("modify: lanza una InvalidDataException si le pasan una entidad con datos invalidos")
			void testModify_InvalidData() {
				var item = new Persona(1, "Pepito", "  ");
				
				var ex = assertThrows(InvalidDataException.class, () -> srv.modify(item));
				
				assertEquals("ERRORES: apellidos: no puede estar en blanco.", ex.getMessage());
			}
	
			@Test
			@DisplayName("modify: lanza una NotFoundException si se intenta modificar una entidad que no existe")
			void testModify_NotFoundException() {
				var item = new Persona(2, "Pepito", "Grillo");
				
				assertThrows(NotFoundException.class, () -> srv.modify(item));
			}
			
//			@Test
//			@DisplayName("delete: lanza una InvalidDataException si no le pasan la entidad a borrar")
//			void testDelete_Null() {
//				assertThrows(InvalidDataException.class, () -> srv.delete(null));
//				assertEquals("No puede ser nulo", ex.getMessage());
//			}
	
		}
	}
	
}
