package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.academia.modelo.Exercicio;

/**
 * 
 * @author Maiki Ferreira
 * @version 0.1
 */
public class ExercicioDao {
	Connection conexao;
	
	public ExercicioDao(Connection con) {
		conexao = con;
	}

	/**
	 * Insere um objeto <code>exercicio</code> no banco de dados.
	 * @param exercicio é o objeto que vai ser armazenado no banco de dados.
	 * @param idUsuario e o id do usuario que o exercicio pertence.
	 */
	public void insere(Exercicio exercicio,long cod_aluno) {
		
		String sql = "INSERT INTO exercicio " +
		"(cod_aluno, nome, data_exercicio, tempo_inicio, tempo_final, duracao, distancia, calorias_perdidas, passos)" +
		"VALUES (?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, cod_aluno);
			
			ps.setString(2, exercicio.getNomeExercicio());
			
			Date data = new Date(exercicio.getData().getTimeInMillis());
			ps.setDate(3, data);
			
			ps.setLong(4, exercicio.getTempoInicio().getTimeInMillis());
			
			ps.setLong(5, exercicio.getTempoFinal().getTimeInMillis());
			
			ps.setLong(6, exercicio.getDuracao().getTimeInMillis());
			
			ps.setFloat(7, exercicio.getDistancia());
			ps.setFloat(8, exercicio.getCaloriasPerdidas());
			ps.setInt(9, exercicio.getPassos());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Busca um exercicio no banco de dados pelo nome
	 * 
	 * @param nome que sera pesquisado
	 * @return exercicio se for encontrado ou null se não for encontrado
	 */
	public Exercicio buscaExercicioPorNome(String nome) {
		
		Exercicio exercicio = new Exercicio();
		
		String sql = "SELECT *  FROM exercicio WHERE nome = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setString(1, nome);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			exercicio.setId(rs.getLong("cod_exercicio"));
			exercicio.setCodigoAluno(rs.getLong("cod_aluno"));
			exercicio.setNomeExercicio(rs.getString("nome"));
			
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("data_exercicio"));
			exercicio.setData(data);
			
			Calendar tempoInicio = Calendar.getInstance();
			tempoInicio.setTimeInMillis(rs.getLong("tempo_inicio"));
			exercicio.setTempoInicio(tempoInicio);
			
			Calendar tempoFinal = Calendar.getInstance();
			tempoFinal.setTimeInMillis(rs.getLong("tempo_final"));
			exercicio.setTempoFinal(tempoFinal);
			
			Calendar duracao = Calendar.getInstance();
			duracao.setTimeInMillis(rs.getLong("duracao"));
			exercicio.setDuracao(duracao);
			
			exercicio.setDistancia(rs.getFloat("distancia"));
			exercicio.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
			exercicio.setPassos(rs.getInt("passos"));
			
			
			return exercicio;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Busca um exercicio no banco de dados pelo codigo do aluno
	 * 
	 * @param codUsuario é o cod do usuario dono do exercicio
	 * @return exercicio se for encontrado ou null se não for encontrado
	 */
	public Exercicio buscaExercicioPorCodUsuario(long cod_aluno) {
		
		Exercicio exercicio = new Exercicio();
		
		String sql = "SELECT *  FROM exercicio WHERE cod_aluno = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, cod_aluno);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			exercicio.setId(rs.getLong("cod_exercicio"));
			exercicio.setCodigoAluno(rs.getLong("cod_usuario"));
			exercicio.setNomeExercicio(rs.getString("nome"));
			
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("data_exercicio"));
			exercicio.setData(data);
			
			Calendar tempoInicio = Calendar.getInstance();
			tempoInicio.setTimeInMillis(rs.getLong("tempo_inicio"));
			exercicio.setTempoInicio(tempoInicio);
			
			Calendar tempoFinal = Calendar.getInstance();
			tempoFinal.setTimeInMillis(rs.getLong("tempo_final"));
			exercicio.setTempoFinal(tempoFinal);
			
			Calendar duracao = Calendar.getInstance();
			duracao.setTimeInMillis(rs.getLong("duracao"));
			exercicio.setDuracao(duracao);
			
			exercicio.setDistancia(rs.getFloat("distancia"));
			exercicio.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
			exercicio.setPassos(rs.getInt("passos"));
			
			
			return exercicio;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void alterar(Exercicio exercicio) {
		String sql = "UPDATE exercicio SET nome=?, data_exercicio=?, tempo_inicio=?, tempo_final=?, duracao=?, distancia=?, calorias_perdidas=?, passos=?"
				+ " WHERE cod_exercicio=?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			
			
			ps.setString(1, exercicio.getNomeExercicio());
			
			Date date = new Date(exercicio.getData().getTimeInMillis());
			ps.setDate(2, date);
			
			ps.setLong(3, exercicio.getTempoInicio().getTimeInMillis());
			
			ps.setLong(4, exercicio.getTempoFinal().getTimeInMillis());
			
			ps.setLong(5, exercicio.getDuracao().getTimeInMillis());
			
			ps.setFloat(6, exercicio.getDistancia());
			ps.setFloat(7, exercicio.getCaloriasPerdidas());
			ps.setInt(8, exercicio.getPassos());
			ps.setLong(9, exercicio.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void remove(Exercicio exercicio) {
		String sql = "DELETE FROM exercicio WHERE cod_exercicio=?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, exercicio.getId());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 * @param idUsuario e o codigo do usuario dono do exercicio
	 * @param exercicio é o suposto exercicio do aluno
	 * @return	exercicio se for encontrado ou null se não for encontrado
	 */
	public Exercicio pesquisar(long cod_aluno, Exercicio exercicio) {
		
		
		String sql = "SELECT * FROM exercicio WHERE cod_aluno = ? AND  data_exercicio = ? AND tempo_inicio BETWEEN ? AND ? ";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, cod_aluno);
			
			Date data = new Date(exercicio.getData().getTimeInMillis());
			ps.setDate(2, data);
			
			ps.setLong(3, exercicio.getTempoInicio().getTimeInMillis());
			
			ps.setLong(4, exercicio.getTempoFinal().getTimeInMillis());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				exercicio.setId(rs.getLong("cod_exercicio"));
				exercicio.setCodigoAluno(rs.getLong("cod_aluno"));
				exercicio.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicio.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicio.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicio.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicio.setDuracao(duracaos);
				
				exercicio.setDistancia(rs.getFloat("distancia"));
				exercicio.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicio.setPassos(rs.getInt("passos"));
				
				return exercicio;
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	/**
	 * Pesquisa se existe algum exercicio com a mesma data e hora do exercicioDetalhado. 
	 * 
	 * @param cod_usuario é o codigo do usuario dono do exercicio
	 * @param data_exercicio é a data em que o exercicio foi realizado
	 * @param tempo_inicio é a hora em que o exercicio começou.
	 * @param tempo_final é a hora em que o exercicio terminou..
	 * @return exercicio se for encontrado ou null se não for encontrado
	 */
	public Exercicio pesquisaExercicioDetalhadoNaTabelaExercicio(long cod_aluno, Calendar data_exercicio, Calendar tempo_inicio, Calendar tempo_final) {
		String sql = "SELECT * FROM exercicio WHERE cod_aluno = ? AND  data_exercicio = ? AND tempo_inicio BETWEEN ? AND ? ";
		
		Exercicio exercicio = new Exercicio();
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, cod_aluno);
			
			Date data = new Date(data_exercicio.getTimeInMillis());
			ps.setDate(2, data);
			
			//Date tempoInicio = new Date(tempo_inicio.getTimeInMillis()); 
			ps.setLong(3, tempo_inicio.getTimeInMillis());
			
			ps.setLong(4, tempo_final.getTimeInMillis());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				exercicio.setId(rs.getLong("cod_exercicio"));
				exercicio.setCodigoAluno(rs.getLong("cod_usuario"));
				exercicio.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicio.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicio.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicio.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicio.setDuracao(duracaos);
				
				exercicio.setDistancia(rs.getFloat("distancia"));
				exercicio.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicio.setPassos(rs.getInt("passos"));
				
				return exercicio;
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Pesquisa todos os exercicios do usuario
	 * 
	 * @param idUsuario é o id do usuario dono dos exercicios
	 * @return listaDeExercicio se for encontrado ou null se não for encontrado
	 */
	public List<Exercicio> pesquisaExerciciosUsuario(long cod_aluno) {
		List<Exercicio> listaDeExercicio = new ArrayList<Exercicio>();
		
		String sql = "SELECT * FROM exercicio WHERE cod_aluno = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, cod_aluno);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Exercicio exercicio = new Exercicio();
				
				exercicio.setId(rs.getLong("cod_exercicio"));
				
				exercicio.setCodigoAluno(rs.getLong("cod_aluno"));
				exercicio.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicio.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicio.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicio.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicio.setDuracao(duracaos);
				
				exercicio.setDistancia(rs.getFloat("distancia"));
				exercicio.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicio.setPassos(rs.getInt("passos"));
				
				listaDeExercicio.add(exercicio);
			}
			return listaDeExercicio;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	public List<Exercicio> listarTodosExercicios() {
		List<Exercicio> listaDeExercicio = new ArrayList<Exercicio>();
		
		String sql = "SELECT * FROM exercicio";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Exercicio exercicio = new Exercicio();
				
				exercicio.setId(rs.getLong("cod_exercicio"));
				
				exercicio.setCodigoAluno(rs.getLong("cod_aluno"));
				exercicio.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicio.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicio.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicio.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicio.setDuracao(duracaos);
				
				exercicio.setDistancia(rs.getFloat("distancia"));
				exercicio.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicio.setPassos(rs.getInt("passos"));
				
				listaDeExercicio.add(exercicio);
			}
			return listaDeExercicio;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
