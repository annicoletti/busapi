package br.com.nicoletti.busapi;

import java.math.BigInteger;

public class MainLong {

	public static void main(String[] args) {

		BigInteger pooling = new BigInteger("1");
		BigInteger timeout = new BigInteger("0");

		System.err.println("ANTES");
		System.err.println("pooling: " + pooling);
		System.err.println("timeout: " + timeout);

		int retval = pooling.compareTo(timeout);

		if (retval > 0) {
			System.out.println("pooling is greater than timeout");
			pooling = new BigInteger("10000");
			timeout = new BigInteger("50000");

		} else if (retval < 0) {
			System.out.println("pooling is less than timeout");
		} else {
			System.out.println("pooling is equal to timeout");
		}

		System.err.println("DEPOIS");
		System.err.println("pooling: " + pooling);
		System.err.println("timeout: " + timeout);

	}

}
