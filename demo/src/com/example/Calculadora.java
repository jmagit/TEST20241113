package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	private double RoundIEEE754(double o) {
		return (new BigDecimal(o))
				.setScale(16, RoundingMode.HALF_UP)
				.doubleValue();
	}

	public double suma(double a, double b) {
		return RoundIEEE754(a + b);
	}
	
	public BigDecimal suma(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}
	
	public int suma(int a, int b) {
		return a + b;
	}
}
