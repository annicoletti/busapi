package br.com.nicoletti.busapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainDatas {

	public static void main(String[] args) {

		Date firstDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDate);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);

		System.out.println("firstDate: " + firstDate);

		System.out.println("month: " + month);
		System.out.println("year: " + year);

//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);

//		while (calendar.get(Calendar.YEAR) < 2023) {
//			calendar.add(Calendar.MONTH, 1);
//			System.out.println(calendar.getTime());
//		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// define data inicial com o primeiro dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date dataInicial = calendar.getTime();

		// define data final com o ultimo dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dataFinal = calendar.getTime();

		System.err.println("DATA INICIO = " + sdf.format(dataInicial));
		System.err.println("DATA FINAL = " + sdf.format(dataFinal));


	}
}
