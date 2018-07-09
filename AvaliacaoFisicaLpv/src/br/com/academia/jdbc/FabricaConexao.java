package br.com.academia.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaConexao {
	public Connection getConexao() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(
					 "jdbc:postgresql://localhost/avaliacao_fisica_lpv", "postgres", "maiki");
			//System.out.println("Conectou!!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao conectar com o banco de dados");
		}
		return connection;
	}
}
