package com.example;

import java.math.BigDecimal;

public class Calculadora {
	public double suma(double a, double b) {
		return a + b;
	}
	
	public BigDecimal suma(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}
	
	public int suma(int a, int b) {
		return a + b;
	}
}
