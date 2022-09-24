package br.com.nicoletti.busapi.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Postgres {

	private static final String URL_POSTGRES = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";

	@Value("${api.postgres.server}")
	private String server;

	@Value("${api.postgres.port}")
	private String port;

	@Value("${api.postgres.database}")
	private String database;

	@Value("${api.postgres.schema}")
	private String schema;

	@Value("${api.postgres.user}")
	private String user;

	@Value("${api.postgres.password}")
	private String password;

	private static Connection connection;

	@PostConstruct
	private void init() throws SQLException {
		String url = String.format(URL_POSTGRES, server, port, database, schema);
		connection = DriverManager.getConnection(url, user, password);
	}

	public static Connection getConnection() {
		return connection;
	}

}
