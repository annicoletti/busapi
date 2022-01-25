package br.com.nicoletti.busapi;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainRandom {

	public static void main(String[] args) {

		for (int i = 0; i <= 1000000; i++) {
			generateRandomNumbers();
		}

	}

	/**
	 * @throws RuntimeException
	 */
	private static void generateRandomNumbers() throws RuntimeException {
		int maximo = 25;
		int quantidade = 15;
		Random random = new Random();

		Set<Integer> numeros = new TreeSet<>();
		while (numeros.size() < quantidade) {
			int rand = random.nextInt(maximo) + 1;
			numeros.add(rand);
		}

		System.err.println("quantidade: " + numeros.size() + " valores: " + numeros);
		if (numeros.contains(0) || numeros.contains(26)) {
			throw new RuntimeException("Deu ruim");
		}
	}

}
