package br.com.nicoletti.busapi.resources.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeMap;

import br.com.nicoletti.busapi.beans.lottery.enums.LotteryTypeGame;
import br.com.nicoletti.busapi.connection.Postgres;

public class ResultGameDAO {

	private Connection connection = Postgres.getConnection();

	public TreeMap<Integer, Integer> countNumbersByDateRange(LotteryTypeGame typeGame, Date init, Date end) {
		TreeMap<Integer, Integer> out = new TreeMap<>();

		String sql = "SELECT rn.\"number\", count(*) as soma FROM result_game rg INNER JOIN raffle_number rn "
				+ "ON rn.fk_game_resultion_id = rg.id WHERE rg.draw_date between ? AND ? "
				+ "AND rg.game_type = ? GROUP BY rn.\"number\" ORDER BY soma DESC";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setDate(1, new java.sql.Date(init.getTime()));
			prepareStatement.setDate(2, new java.sql.Date(end.getTime()));
			prepareStatement.setString(3, typeGame.getName());

			ResultSet query = prepareStatement.executeQuery();
			while (query.next()) {
				int number = query.getInt("number");
				int soma = query.getInt("soma");
				out.put(number, soma);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	public Long countQuantityGameByRangeDate(Date init, Date end, LotteryTypeGame typeGame) {
		String sql = "SELECT COUNT(*) as soma FROM result_game rg WHERE rg.draw_date BETWEEN ? AND ? AND rg.game_type = ?";
		Long out = 0L;

		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setDate(1, new java.sql.Date(init.getTime()));
			prepareStatement.setDate(2, new java.sql.Date(end.getTime()));
			prepareStatement.setString(3, typeGame.getName());

			ResultSet query = prepareStatement.executeQuery();
			if (query.next()) {
				out = query.getLong("soma");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

}
