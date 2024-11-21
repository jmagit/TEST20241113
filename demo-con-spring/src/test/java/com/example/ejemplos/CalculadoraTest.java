package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.testutils.PrivateMethod;
import com.example.testutils.SmokeTest;

@DisplayName("Pruebas de la clase Calculadora")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
			@Tag("smoke")
			@Order(2)
			void testSumaDoubleDoubleSinDecimales() {
//				var calculadora = new Calculadora();

				var actual = calculadora.suma(1.0, 2.0);

				assertEquals(3, actual);
			}

			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			@DisplayName("Prueba repetida dos reales sin decimales")
			void testSumaRepetida() {
//				var calculadora = new Calculadora();

				var actual = calculadora.suma(2, 2);

				assertEquals(4, actual);
			}
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			@DisplayName("Prueba repetida dos reales sin decimales (no hacer)")
			void testSumaRepetidaMal(RepetitionInfo repetitionInfo) {
//				var calculadora = new Calculadora();

				var actual = calculadora.suma(repetitionInfo.getCurrentRepetition(), repetitionInfo.getCurrentRepetition());

				assertEquals(2 * repetitionInfo.getCurrentRepetition(), actual);
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
			@Order(1)
			void testSumaIntInt() {
				var calculadora = new Calculadora();

				var actual = calculadora.suma(2, 2);

				assertEquals(4, actual);
			}
			@ParameterizedTest(name = "{index} => {0} + {1} = {2}")
			@CsvSource({ 
				"1,2,3", 
				"2,-3,-1",
				"-1,2,1",
				"-2,-3,-5",
				"1,0,1",
				"0.1,0.2,0.3"
				})
			void testSumaDeEnteros(double operando1, double operando2, double resultado) {
				var calculadora = new Calculadora();

				var actual = calculadora.suma(operando1, operando2);

				assertEquals(resultado, actual);
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

	@ParameterizedTest
	@ValueSource(doubles = {0.1+0.2})
	void RoundIEEE754Test(double caso) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		var actual = (double)PrivateMethod.exec(calculadora, "roundIEEE754", new Class[] { double.class }, caso);
		
		assertEquals(0.3, actual);
	}
	@Nested
	class Divide {
		@Nested
		class OK {
			@Test
			@SmokeTest
			void divideEnteros() {
				assertEquals(0, calculadora.divide(1, 2));
			}

			@Test
			void divideDobles() {
				assertEquals(0.5, calculadora.divide(1.0, 2.0));
			}

		}

		@Nested
		class KO {
			@Test
			void dividePorCeroEnteros() {
				assertThrows(ArithmeticException.class, () -> calculadora.divide(1, 0));
			}

			@Test
			void dividePorCeroDobles() {
				assertThrows(ArithmeticException.class, () -> calculadora.divide(1.0, 0.0));
			}

			@Test
			void dividePorCeroDoblesConTry() {
				try {
					calculadora.divide(1.0, 0.0);
					fail("Falta la excepcion");
				} catch (Exception e) {
				}
			}

			@Test
			void malEjemploDePruebas() {
				calculadora.divide(1, 1);
			}
		}
	}
	@Nested
	class Mockea {
		@Test
		void suma() {
			var calc = mock(Calculadora.class);
			when(calc.suma(anyInt(), anyInt())).thenReturn(3).thenReturn(2).thenReturn(1);
			assertEquals(3, calc.suma(2, 2));
			assertEquals(2, calc.suma(2, 2));
			assertEquals(1, calc.suma(2, 2));
			assertEquals(1, calc.suma(2, 2));
			assertEquals(1, calc.suma(1, 1));
			verify(calc,times(1)).suma(1, 1);
			verify(calc,times(4)).suma(2, 2);
			verify(calc,never()).suma(1, 2);
		}
	}
}
