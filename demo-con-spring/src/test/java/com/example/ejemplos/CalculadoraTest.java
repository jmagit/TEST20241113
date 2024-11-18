package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas de la clase Calculadora")
class CalculadoraTest {
	private Calculadora calculadora;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		calculadora = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	class Suma {
		@Nested
		class OK {
			@Test
			@DisplayName("Prueba dos reales sin decimales")
			void testSumaDoubleDoubleSinDecimales() {
//				var calculadora = new Calculadora();

				var actual = calculadora.suma(1.0, 2.0);

				assertEquals(3, actual);
			}

			@Test
			@DisplayName("Prueba dos reales con decimales")
			void testSumaDoubleDoubleConDecimales() {
//				var calculadora = new Calculadora();

				var actual = calculadora.suma(1.1, 0.2);

				assertEquals(1.3, actual);
			}

			@Test
			@DisplayName("Prueba que no se produzca el error con los decimales en IEEE754")
			void testSumaDoubleDoubleIEEE754() {
				var calculadora = new Calculadora();

				var actual = calculadora.suma(0.1, 0.2);

				assertEquals(0.1, calculadora.suma(1, -0.9));
				assertEquals(0.3, actual);
			}

			@Test
			void testSumaIntInt() {
				var calculadora = new Calculadora();

				var actual = calculadora.suma(2, 2);

				assertEquals(4, actual);
			}

			@Test
			void testSumaBigDecimal() {
				var calculadora = new Calculadora();

				var actual = calculadora.suma(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2));

				assertEquals(BigDecimal.valueOf(0.3), actual);
			}

		}

		@Nested
		class KO {
			@Test
			@DisplayName("Prueba el desbordamiento en los enteros")
			void testSumaIntIntMax() {
				var calculadora = new Calculadora();

				var actual = calculadora.suma(Integer.MAX_VALUE, 1);

				assertEquals(Integer.MIN_VALUE, actual);
			}
		}
	}

	@Nested
	class Divide {
		@Nested
		class OK {
			@Test
			void divideEnteros() {
				assertEquals(0, calculadora.divide(1, 2));
			}

		}

		@Nested
		class KO {
			@Test
			void dividePorCeroEnteros() {
				assertThrows(ArithmeticException.class, () -> calculadora.divide(1, 0));
			}

			@Test
			void divideDobles() {
				assertEquals(Double.POSITIVE_INFINITY, calculadora.divide(1.0, 0.0));
			}
		}
	}
}
