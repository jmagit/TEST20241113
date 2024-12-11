package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSumaDoubleDoubleSinDecimales() {
		var calculadora = new Calculadora();
		
		var actual = calculadora.suma(1.0, 2.0);
		
		assertEquals(3, actual);
	}

	@Test
	void testSumaDoubleDoubleConDecimales() {
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
	void testSumaIntIntMax() {
		var calculadora = new Calculadora();
		
		var actual = calculadora.suma(Integer.MAX_VALUE, 1);
		
		assertEquals(Integer.MIN_VALUE, actual);
	}

	@Test
	void testSumaBigDecimal() {
		var calculadora = new Calculadora();
		
		var actual = calculadora.suma(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2));
		
		assertEquals(BigDecimal.valueOf(0.3), actual);
	}
}
