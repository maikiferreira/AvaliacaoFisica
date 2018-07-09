package br.com.academia.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Aluno {
	private long id;
	private String nome;
	private String sexo;
	private int altura;
	private float peso;
	private Calendar dataNascimento;
	private String email;
	private String cpf;
	private String whatsapp;
	
	public Aluno() {
		this("", "", 0 ,0.0f , "", "", "");
	}

	public Aluno(String nome, String sexo, int altura, float peso, String email, String cpf, String whatsapp) {
		this.nome = nome;
		this.sexo = sexo;
		this.altura = altura;
		this.peso = peso;
		this.dataNascimento = Calendar.getInstance();
		this.email = email;
		this.cpf = cpf;
		this.whatsapp = whatsapp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public String toStringCompleto() {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		
		return String.format(
				"------ Usuário ------\r\n" + 
				"Nome: %s"
				+ "\nSexo: %s"
				+ "\nAltura: %s" + " cm"
				+ "\nPeso: %s" + " Kg"
				+ "\nData de Nascimento: %s"
				+ "\nEmail: %s"
				+ "\nCpf: %s"
				+ "\nWhastsapp: %s", 
				nome, sexo, altura, peso, formataData.format(dataNascimento.getTime()), email, cpf, whatsapp);
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
}
