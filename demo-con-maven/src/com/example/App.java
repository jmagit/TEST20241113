package com.example;

import com.example.redis.ExternalCounter;

import redis.clients.jedis.UnifiedJedis;

public class App {

	public static void main(String[] args) {
		var calc = new Calculadora();
		
		//System.out.println("Suma enteros: " + calc.suma(2, 2));
		//System.out.println("Suma reales: " + calc.suma(2.0, 1.5));
		var cont = new ExternalCounter("megusta-total", new UnifiedJedis("redis://localhost:6379"));
		System.out.println(cont.get());
		System.out.println(cont.increment());
		System.out.println(cont.get());
	}

}
