package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.academia.modelo.Ritmo;

public class RitmoDao {
	Connection conexao;
	
	public RitmoDao(Connection con) {
		conexao = con;
	}

	/**
	 * Recebe um objeto <code>ritmo</code> e o <code>idExercicio</code> que é um long onde armazana o id do exercicio
	 * que o objeto <code>ritmo</code> pertence.Entao é armazenado no banco de dados.
	 * @param ritmo É o objeto que sera armazenado no banco de dados
	 * @param idExercicio É o id do exercicio que o objeto <code>ritmo</code> pertence.
	 */
	public void insere(Ritmo ritmo, long idExercicio) {
		
		String sql = "INSERT INTO ritmo " +
		"(cod_exercicio_detalhado, distancia, ritmo)" +
		"VALUES (?,?,?)";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idExercicio);
			ps.setFloat(2, ritmo.getDistancia());
			
			ps.setLong(3, ritmo.getRitmo().getTimeInMillis());
 			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Ritmo> pesquisaRitmo(long idExercicio) {
		List<Ritmo> listaDeRitmo = new ArrayList<Ritmo>();
		
		String sql = "SELECT * FROM ritmo WHERE cod_exercicio_detalhado = ?";
		
		PreparedStatement ps;
		
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idExercicio);
			
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(ps);
			
			while(rs.next()){
				Ritmo ritmo =new Ritmo();
				
				ritmo.setCodRitmo(rs.getLong("cod_ritmo"));
				ritmo.setCodExercicioDetalhado(rs.getLong("cod_exercicio_detalhado"));
				ritmo.setDistancia(rs.getFloat("distancia"));

				Calendar rit = Calendar.getInstance();
				rit.setTimeInMillis(rs.getLong("ritmo"));
				ritmo.setRitmo(rit);
				
				listaDeRitmo.add(ritmo);
			}
			return listaDeRitmo;
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
}
