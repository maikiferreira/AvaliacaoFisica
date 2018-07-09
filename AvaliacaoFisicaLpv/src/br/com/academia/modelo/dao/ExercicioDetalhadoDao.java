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
import br.com.academia.modelo.ExercicioDetalhado;

public class ExercicioDetalhadoDao {
	Connection conexao;
	
	/**
	 * 
	 * @param con possui a conexao com o banco de dados.
	 */
	public ExercicioDetalhadoDao(Connection con) {
		conexao = con;
	}

	/**
	 * Insere um Exercicio Detalhado no banco de dados
	 * 
	 * @param exercicio é o objeto que vai ser salvo.
	 * @param idUsuario é o cod do usuario dono do exercicio.
	 */
	public void insere(ExercicioDetalhado exercicio,long idAluno) {
		
		String sql = "INSERT INTO exercicio_detalhado " +
		"(cod_aluno, nome, data_exercicio, tempo_inicio, tempo_final, duracao, distancia, calorias_perdidas, passos, "
		+ "velocidade_media, velocidade_maxima, ritmo_medio, ritmo_maximo,maior_elevacao,menor_elevacao)" +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idAluno);
			
			ps.setString(2, exercicio.getNomeExercicio());
			
			Date data = new Date(exercicio.getData().getTimeInMillis());
			ps.setDate(3, data);
			
			ps.setLong(4, exercicio.getTempoInicio().getTimeInMillis());
			
			ps.setLong(5, exercicio.getTempoFinal().getTimeInMillis());
			
			ps.setLong(6, exercicio.getDuracao().getTimeInMillis());
			
			ps.setFloat(7, exercicio.getDistancia());
			ps.setFloat(8, exercicio.getCaloriasPerdidas());
			ps.setInt(9, exercicio.getPassos());
			ps.setFloat(10, exercicio.getVelocidadeMedia());
			ps.setFloat(11, exercicio.getVelocidadeMaxima());
			
			ps.setLong(12,  exercicio.getRitmoMedia().getTimeInMillis());
			
			ps.setLong(13, exercicio.getRitmoMaximo().getTimeInMillis());
			
			ps.setFloat(14, exercicio.getMaiorElevacao());
			ps.setFloat(15, exercicio.getMenorElevacao());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Busca um exercicio detalhado pelo nome
	 * @param nome é o nome do exercicio
	 * @return exercicio se for encontrado e null se não for..
	 */
	public ExercicioDetalhado buscaExercicioDetalhadoPorNome(String nome) {
		
		ExercicioDetalhado exercicio = new ExercicioDetalhado();
		
		String sql = "SELECT *  FROM exercicio_detalhado WHERE nome = ?";
		
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
			exercicio.setVelocidadeMedia(rs.getFloat("velocidade_media"));
			exercicio.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
			
			Calendar ritmoMedio = Calendar.getInstance();
			ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
			exercicio.setRitmoMedia(ritmoMedio);
			
			Calendar ritmoMaximo = Calendar.getInstance();
			ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
			exercicio.setRitmoMaximo(ritmoMaximo);
			
			rs.close();
			ps.close();
			return exercicio;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Busca um exercicio detalhado pelo cod do exercicio
	 * @param idExercicio é o codigo do exercicio que se deseja obter.
	 * @return exercicio se for encontrado e null se não for.
	 */
	public ExercicioDetalhado buscaExercicioDetalhadoPorCodExercicio(long idExercicio){
		ExercicioDetalhado exercicio = new ExercicioDetalhado();
		
		String sql = "SELECT * FROM exercicio_detalhado WHERE cod_exercicio_detalhado = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idExercicio);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			exercicio.setId(rs.getLong("cod_exercicio_detalhado"));
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
			exercicio.setVelocidadeMedia(rs.getFloat("velocidade_media"));
			exercicio.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
			
			Calendar ritmoMedio = Calendar.getInstance();
			ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
			exercicio.setRitmoMedia(ritmoMedio);
			
			Calendar ritmoMaximo = Calendar.getInstance();
			ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
			exercicio.setRitmoMaximo(ritmoMaximo);			
			
			return exercicio;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param idUsuario é o id do usuario dono do exercicio pesquisado.
	 * @param exerciciox e o exercicio pesquisado
	 * @return exercicio se for encontrado e null se não for.
	 */
	public ExercicioDetalhado pesquisar(long idAluno, ExercicioDetalhado exerciciox) {
		ExercicioDetalhado exercicio = new ExercicioDetalhado();
		String sql = "SELECT * FROM exercicio_detalhado WHERE cod_aluno = ? AND  data_exercicio = ? AND tempo_inicio BETWEEN ? AND ? ";
		//ExercicioDetalhado exercicio = new ExercicioDetalhado();
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idAluno);
			
			Date data = new Date(exerciciox.getData().getTimeInMillis());
			ps.setDate(2, data);
			
			ps.setLong(3, exerciciox.getTempoInicio().getTimeInMillis());
			
			ps.setLong(4, exerciciox.getTempoFinal().getTimeInMillis());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				exercicio.setId(rs.getLong("cod_exercicio_detalhado"));
				
				
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
				exercicio.setVelocidadeMedia(rs.getFloat("velocidade_media"));
				exercicio.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
				
				Calendar ritmoMedio = Calendar.getInstance();
				ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
				exercicio.setRitmoMedia(ritmoMedio);
				
				Calendar ritmoMaximo = Calendar.getInstance();
				ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
				exercicio.setRitmoMaximo(ritmoMaximo);
				
				exercicio.setMaiorElevacao(rs.getFloat("maior_elevacao"));
				exercicio.setMenorElevacao(rs.getFloat("menor_elevacao"));
				
				//System.out.println(exercicio.toString());
				return exercicio;

			}
			
			
			
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	} 

	public ExercicioDetalhado pesquisaExercicioNaTabelaExercicioDetalhado(long cod_aluno, Calendar data_exercicio, Calendar tempo_inicio, Calendar tempo_final){
		String sql = "SELECT * FROM exercicio_detalhado WHERE cod_aluno = ? AND  data_exercicio = ? AND tempo_inicio BETWEEN ? AND ? ";
		
		ExercicioDetalhado exercicio = new ExercicioDetalhado();
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
				exercicio.setId(rs.getLong("cod_exercicio_detalhado"));
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
				exercicio.setVelocidadeMedia(rs.getFloat("velocidade_media"));
				exercicio.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
				
				Calendar ritmoMedio = Calendar.getInstance();
				ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
				exercicio.setRitmoMedia(ritmoMedio);
				
				Calendar ritmoMaximo = Calendar.getInstance();
				ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
				exercicio.setRitmoMaximo(ritmoMaximo);
				
				exercicio.setMaiorElevacao(rs.getFloat("maior_elevacao"));
				exercicio.setMenorElevacao(rs.getFloat("menor_elevacao"));
			
				//System.out.println(exercicio.toString());
				return exercicio;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Exercicio> pesquisaExercicioDetalhadoUsuarioList(long idAluno) {
		List<Exercicio> listaDeExercicioDetalhado = new ArrayList<Exercicio>();
		
		String sql = "SELECT * FROM exercicio_detalhado WHERE cod_aluno = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idAluno);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ExercicioDetalhado exercicioDetalhado = new ExercicioDetalhado();
				
				exercicioDetalhado.setId(rs.getLong("cod_exercicio_detalhado"));
				
				
				exercicioDetalhado.setCodigoAluno(rs.getLong("cod_aluno"));
				exercicioDetalhado.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicioDetalhado.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicioDetalhado.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicioDetalhado.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicioDetalhado.setDuracao(duracaos);
				
				exercicioDetalhado.setDistancia(rs.getFloat("distancia"));
				exercicioDetalhado.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicioDetalhado.setPassos(rs.getInt("passos"));
				exercicioDetalhado.setVelocidadeMedia(rs.getFloat("velocidade_media"));
				exercicioDetalhado.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
				
				Calendar ritmoMedio = Calendar.getInstance();
				ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
				exercicioDetalhado.setRitmoMedia(ritmoMedio);
				
				Calendar ritmoMaximo = Calendar.getInstance();
				ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
				exercicioDetalhado.setRitmoMaximo(ritmoMaximo);
				
				exercicioDetalhado.setMaiorElevacao(rs.getFloat("maior_elevacao"));
				exercicioDetalhado.setMenorElevacao(rs.getFloat("menor_elevacao"));
				
				
				
				listaDeExercicioDetalhado.add(exercicioDetalhado);
			}
			return listaDeExercicioDetalhado;
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public List<ExercicioDetalhado> pesquisaExercicioDetalhadoUsuario(long idAluno) {
		List<ExercicioDetalhado> listaDeExercicioDetalhado = new ArrayList<ExercicioDetalhado>();
		
		String sql = "SELECT * FROM exercicio_detalhado WHERE cod_aluno = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, idAluno);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ExercicioDetalhado exercicioDetalhado = new ExercicioDetalhado();
				
				exercicioDetalhado.setId(rs.getLong("cod_exercicio_detalhado"));
				
				
				exercicioDetalhado.setCodigoAluno(rs.getLong("cod_aluno"));
				exercicioDetalhado.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicioDetalhado.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicioDetalhado.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicioDetalhado.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicioDetalhado.setDuracao(duracaos);
				
				exercicioDetalhado.setDistancia(rs.getFloat("distancia"));
				exercicioDetalhado.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicioDetalhado.setPassos(rs.getInt("passos"));
				exercicioDetalhado.setVelocidadeMedia(rs.getFloat("velocidade_media"));
				exercicioDetalhado.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
				
				Calendar ritmoMedio = Calendar.getInstance();
				ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
				exercicioDetalhado.setRitmoMedia(ritmoMedio);
				
				Calendar ritmoMaximo = Calendar.getInstance();
				ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
				exercicioDetalhado.setRitmoMaximo(ritmoMaximo);
				
				exercicioDetalhado.setMaiorElevacao(rs.getFloat("maior_elevacao"));
				exercicioDetalhado.setMenorElevacao(rs.getFloat("menor_elevacao"));
				
				
				
				listaDeExercicioDetalhado.add(exercicioDetalhado);
			}
			return listaDeExercicioDetalhado;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Exercicio> listarTodosExerciciosDetalhados() {
		List<Exercicio> listaDeExercicio = new ArrayList<Exercicio>();
		
		String sql = "SELECT * FROM exercicio_detalhado";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ExercicioDetalhado exercicioDetalhado = new ExercicioDetalhado();
				
				exercicioDetalhado.setId(rs.getLong("cod_exercicio_detalhado"));
				
				
				exercicioDetalhado.setCodigoAluno(rs.getLong("cod_aluno"));
				exercicioDetalhado.setNomeExercicio(rs.getString("nome"));
				
				Calendar datas = Calendar.getInstance();
				datas.setTime(rs.getDate("data_exercicio"));
				exercicioDetalhado.setData(datas);
				
				Calendar tempoInicios = Calendar.getInstance();
				tempoInicios.setTimeInMillis(rs.getLong("tempo_inicio"));
				exercicioDetalhado.setTempoInicio(tempoInicios);
				
				Calendar tempoFinals = Calendar.getInstance();
				tempoFinals.setTimeInMillis(rs.getLong("tempo_final"));
				exercicioDetalhado.setTempoFinal(tempoFinals);
				
				Calendar duracaos = Calendar.getInstance();
				duracaos.setTimeInMillis(rs.getLong("duracao"));
				exercicioDetalhado.setDuracao(duracaos);
				
				exercicioDetalhado.setDistancia(rs.getFloat("distancia"));
				exercicioDetalhado.setCaloriasPerdidas(rs.getFloat("calorias_perdidas"));
				exercicioDetalhado.setPassos(rs.getInt("passos"));
				exercicioDetalhado.setVelocidadeMedia(rs.getFloat("velocidade_media"));
				exercicioDetalhado.setVelocidadeMaxima(rs.getFloat("velocidade_maxima"));
				
				Calendar ritmoMedio = Calendar.getInstance();
				ritmoMedio.setTimeInMillis(rs.getLong("ritmo_medio"));
				exercicioDetalhado.setRitmoMedia(ritmoMedio);
				
				Calendar ritmoMaximo = Calendar.getInstance();
				ritmoMaximo.setTimeInMillis(rs.getLong("ritmo_maximo"));
				exercicioDetalhado.setRitmoMaximo(ritmoMaximo);
				
				exercicioDetalhado.setMaiorElevacao(rs.getFloat("maior_elevacao"));
				exercicioDetalhado.setMenorElevacao(rs.getFloat("menor_elevacao"));
				
				listaDeExercicio.add(exercicioDetalhado);
			}
			return listaDeExercicio;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
