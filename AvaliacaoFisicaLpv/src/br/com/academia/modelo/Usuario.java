package br.com.academia.modelo;

public class Usuario {
	private long id;
	private String usuario;
	private String senha;
	private String papel;
	
	public Usuario() {
		this.usuario = "";
		this.senha = "";
		this.papel = "";
	}

	public Usuario(long id, String usuario, String senha, String papel) {
		this();
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.papel = papel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	@Override
	public String toString() {
		return "Id: " + id + "\nUsuario: " + usuario + "\nSenha " + senha + "\nPapel=" + papel;
	}
	
	
}
