package br.com.academia.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExercicioDetalhado extends Exercicio{
	private float velocidadeMedia;
	private float velocidadeMaxima;
	private Calendar ritmoMedia;
	private Calendar ritmoMaximo;
	private float menorElevacao;
	private float maiorElevacao;
	private List<Ritmo> ritmos;
	
	public ExercicioDetalhado() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public ExercicioDetalhado(float velocidadeMedia, float velocidadeMaxima, float menorElevacao, float maiorElevacao) {
		ritmos = new ArrayList<Ritmo>();
		this.velocidadeMedia = velocidadeMedia;
		this.velocidadeMaxima = velocidadeMaxima;
		this.ritmoMedia = Calendar.getInstance();
		this.ritmoMaximo = Calendar.getInstance();
		this.menorElevacao = menorElevacao;
		this.maiorElevacao = maiorElevacao;
	}

	public float getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(float velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	public float getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

	public void setVelocidadeMaxima(float velocidadeMaxima) {
		this.velocidadeMaxima = velocidadeMaxima;
	}

	public Calendar getRitmoMedia() {
		return ritmoMedia;
	}

	public void setRitmoMedia(Calendar ritmoMedia) {
		this.ritmoMedia = ritmoMedia;
	}

	public Calendar getRitmoMaximo() {
		return ritmoMaximo;
	}

	public void setRitmoMaximo(Calendar ritmoMaximo) {
		this.ritmoMaximo = ritmoMaximo;
	}

	public float getMenorElevacao() {
		return menorElevacao;
	}

	public void setMenorElevacao(float menorElevacao) {
		this.menorElevacao = menorElevacao;
	}

	public float getMaiorElevacao() {
		return maiorElevacao;
	}

	public void setMaiorElevacao(float maiorElevacao) {
		this.maiorElevacao = maiorElevacao;
	}

	
	
	public List<Ritmo> getRitmos() {
		return ritmos;
	}

	public void setRitmos(List<Ritmo> ritmos) {
		this.ritmos = ritmos;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	public String toStringCompleto() {
		SimpleDateFormat formataMinutoESegundo = new SimpleDateFormat("mm:ss");
		
		return super.toString() + String.format(
				"\nVelocidade Média: %s" + " Km/h"
				+ "\nVelocidade Máxima: %s" + " Km/h"
				+ "\nRitmo Médio: %s" + "/Km"
				+ "\nRitmo Máximo: %s" + "/Km"
				+ "\nMenor Elevação: %s" + " m"
				+ "\nMaior Elevação: %s" + "m"
				//lista os ritmos
				+ "\n%s",
				velocidadeMedia, velocidadeMaxima, formataMinutoESegundo.format(ritmoMedia.getTime()), formataMinutoESegundo.format(ritmoMaximo.getTime()),
				menorElevacao, maiorElevacao, listarRitmos());
	}
	
	public void adicionarRitmo(Ritmo ritmo) {
		ritmos.add(ritmo);
	}
	
	public String listarRitmos() {
		StringBuilder listaRitmos = new StringBuilder();
		
		listaRitmos.append("------ Ritmo ------\n");
		for (Ritmo ritmo : ritmos) {
			listaRitmos.append(ritmo.toString() +"\n");
		}
		
		return listaRitmos.toString();
	}
	
	public List<Ritmo> listaDeRitmos(){
		return ritmos;
	}
}
