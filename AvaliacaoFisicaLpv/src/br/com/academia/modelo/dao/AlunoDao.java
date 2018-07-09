package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.academia.modelo.Aluno;

public class AlunoDao {
	Connection conexao;
	
	public AlunoDao(Connection con) {
		conexao = con;
	}

	/**
	 * Insere um Objeto <code>usuario</code> no banco de dados.
	 * @param usuario é o objeto que vai ser inserido no banco de dados.
	 */
	public void insere(Aluno usuario) {
		
		String sql = "INSERT INTO aluno " +
		"(nome, sexo, altura, peso, datanascimento, email, cpf, whatsapp)" +
		"VALUES (?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getSexo());
			ps.setFloat(3, usuario.getAltura());
			ps.setFloat(4, usuario.getPeso());
			
			Date date = new Date(usuario.getDataNascimento().getTimeInMillis());
			ps.setDate(5, date);
			
			ps.setString(6, usuario.getEmail());
			ps.setString(7, usuario.getCpf());
			ps.setString(8, usuario.getWhatsapp());
			
			ps.execute();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Pesquisa um <code>usuario</code> no banco de dados que possui email igual ao passsado
	 * como parametro, se encontrar retorna um objeto <code>usuario</code>, se não encontrar
	 * retorna null.
	 * 
	 * @param email é a string que sera usada para pesquisa no banco de dados.
	 * @return retorna um objeto <code>usuario</code>.
	 */
	public Aluno buscaPorEmail(String email) {
		Aluno usuario = new Aluno();
		
		String sql = "SELECT *  FROM aluno WHERE email = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			usuario.setId(rs.getLong("cod_aluno"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSexo(rs.getString("sexo"));
			usuario.setAltura(rs.getInt("altura"));
			usuario.setPeso(rs.getFloat("peso"));
			
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("datanascimento"));
			usuario.setDataNascimento(data);
			
			usuario.setEmail(rs.getString("email"));
			
			usuario.setCpf(rs.getString("cpf"));
			usuario.setWhatsapp(rs.getString("whatsapp"));
			
			return usuario;
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public Aluno buscaPorId(Long id) {
		Aluno usuario = new Aluno();
		
		String sql = "SELECT *  FROM aluno WHERE cod_aluno = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			usuario.setId(rs.getLong("cod_aluno"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSexo(rs.getString("sexo"));
			usuario.setAltura(rs.getInt("altura"));
			usuario.setPeso(rs.getFloat("peso"));
			
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("datanascimento"));
			usuario.setDataNascimento(data);
			
			usuario.setEmail(rs.getString("email"));
			
			usuario.setCpf(rs.getString("cpf"));
			usuario.setWhatsapp(rs.getString("whatsapp"));
			
			return usuario;
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Pesquisa um <code>usuario</code> no banco de dados que possui nome igual ao passsado
	 * como parametro, se encontrar retorna um objeto <code>usuario</code>, se não encontrar
	 * retorna null.
	 * @param nome é a String que sera usada para busca no banco de dados
	 * @return retorna um objeto <code>usuario</code>;
	 */
	public Aluno buscaPorNome(String nome) {
		Aluno usuario = new Aluno();
		
		String sql = "SELECT *  FROM aluno WHERE nome = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setString(1, nome);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			usuario.setId(rs.getLong("cod_aluno"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSexo(rs.getString("sexo"));
			usuario.setAltura(rs.getInt("altura"));
			usuario.setPeso(rs.getFloat("peso"));
			
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("datanascimento"));
			usuario.setDataNascimento(data);
			
			usuario.setEmail(rs.getString("email"));
			usuario.setCpf(rs.getString("cpf"));
			usuario.setWhatsapp(rs.getString("whatsapp"));
			
			return usuario;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void excluirAluno(Aluno aluno) {
		String sql = "DELETE FROM aluno WHERE  cod_aluno=?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, aluno.getId());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void alterarAluno(Aluno aluno) {
		String sql = "UPDATE aluno SET nome=?, sexo=?, altura=?, peso=?, datanascimento=?, email=?, cpf=?, whatsapp=? WHERE cod_aluno=?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			ps.setString(1, aluno.getNome());
			ps.setString(2, aluno.getSexo());
			ps.setInt(3, aluno.getAltura());
			ps.setFloat(4, aluno.getPeso());
			
			Date date = new Date(aluno.getDataNascimento().getTimeInMillis());
			ps.setDate(5, date);
			
			ps.setString(6, aluno.getEmail());
			ps.setString(7, aluno.getCpf());
			ps.setString(8, aluno.getWhatsapp());
			
			ps.setLong(9, aluno.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna uma lista com todos os usuarios armazenados no banco de dados.
	 * @return retorna uma lista de <code>usuario</code>.
	 */
	public List<Aluno> todosAlunos() {
		List<Aluno> listaDeAlunos = new ArrayList<Aluno>();
		
		String sql = "SELECT * FROM aluno";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Aluno usuario = new Aluno();
				
				usuario.setId(rs.getLong("cod_aluno"));
				usuario.setNome(rs.getString("nome"));
				usuario.setSexo(rs.getString("sexo"));
				usuario.setAltura(rs.getInt("altura"));
				usuario.setPeso(rs.getFloat("peso"));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("datanascimento"));
				usuario.setDataNascimento(data);

				usuario.setEmail(rs.getString("email"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setWhatsapp(rs.getString("whatsapp"));
				
				listaDeAlunos.add(usuario);
			}
			return listaDeAlunos;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
