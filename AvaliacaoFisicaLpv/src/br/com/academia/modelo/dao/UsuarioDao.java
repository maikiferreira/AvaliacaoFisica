package br.com.academia.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.academia.modelo.Usuario;

public class UsuarioDao {
	Connection conexao;
	
	public UsuarioDao(Connection con) {
		conexao = con;
	}
	
	public void insere(Usuario usuario) {
		
		String sql = "INSERT INTO usuario " +
		"(nome_usuario, senha, papel)" +
		"VALUES (?,?,?)";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getPapel());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario buscaPorNomeUsuario(String nomeUsuario) {
		Usuario usuario = new Usuario();
		
		String sql = "SELECT *  FROM usuario WHERE nome_usuario = ?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setString(1, nomeUsuario);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				usuario.setId(rs.getLong("cod_usuario"));
				usuario.setUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPapel(rs.getString("papel"));
				
				
				return usuario;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void excluirUsuario(Usuario usuario) {
		String sql = "DELETE FROM usuario WHERE  cod_usuario=?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			ps.setLong(1, usuario.getId());
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void alterarusuario(Usuario usuario) {
		String sql = "UPDATE usuario SET nome_usuario=?, senha=?, papel=? WHERE cod_usuario=?";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getPapel());
			ps.setLong(4, usuario.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Usuario> todosUsuarios() {
		List<Usuario> listaDeAlunos = new ArrayList<Usuario>();
		
		String sql = "SELECT * FROM usuario";
		
		PreparedStatement ps;
		
		try {
			ps = conexao.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Usuario usuario = new Usuario();
				
				usuario.setId(rs.getLong("cod_usuario"));
				usuario.setUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPapel(rs.getString("papel"));
				
				listaDeAlunos.add(usuario);
			}
			return listaDeAlunos;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
