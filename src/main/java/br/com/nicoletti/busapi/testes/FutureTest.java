package br.com.nicoletti.busapi.testes;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTest {

	private static ExecutorService executorService = Executors.newFixedThreadPool(1);

	public static void main(String[] args) throws InterruptedException {

		Future<Double> future = executorService.submit(new Callable<Double>() {

			@Override
			public Double call() throws Exception {
				TimeUnit.SECONDS.sleep(2);
				return 2000D;
			}

		});

		enrolando();

		try {

			Double result = future.get(2, TimeUnit.SECONDS);
			System.out.println(result);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}

	}

	public static void enrolando() {
		long soma = 0;

		for (int i = 0; i < 1_000_000_000; i++) {
			soma += i;
		}

		System.out.println(soma);
	}

}
