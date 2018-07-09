package br.com.academia.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

public class Exercicio {
	private String nomeExercicio;
	private long id;
	private Calendar data;
	private Calendar tempoInicio;
	private Calendar tempoFinal;
	private Calendar duracao;
	private float distancia;
	private float caloriasPerdidas;
	private int passos;
	private long codigoAluno;
	
	public Exercicio() {
		this("", 0, 0.0f, 0.0f, 0);
	}

	public Exercicio(String nomeExercicio, long id,float distancia, float caloriasPerdidas, int passos) {
		this.nomeExercicio = nomeExercicio;
		this.id = id;
		this.data = Calendar.getInstance();
		this.tempoInicio = Calendar.getInstance();
		this.tempoFinal = Calendar.getInstance();
		this.duracao = Calendar.getInstance();
		this.distancia = distancia;
		this.caloriasPerdidas = caloriasPerdidas;
		this.passos = passos;
	}

	public String getNomeExercicio() {
		return nomeExercicio;
	}

	public void setNomeExercicio(String nomeExercicio) {
		this.nomeExercicio = nomeExercicio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Calendar getTempoInicio() {
		return tempoInicio;
	}

	public void setTempoInicio(Calendar tempoInicio) {
		this.tempoInicio = tempoInicio;
	}

	public Calendar getTempoFinal() {
		return tempoFinal;
	}

	public void setTempoFinal(Calendar tempoFinal) {
		this.tempoFinal = tempoFinal;
	}

	public Calendar getDuracao() {
		return duracao;
	}

	public void setDuracao(Calendar duracao) {
		this.duracao = duracao;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public float getCaloriasPerdidas() {
		return caloriasPerdidas;
	}

	public void setCaloriasPerdidas(float caloriasPerdidas) {
		this.caloriasPerdidas = caloriasPerdidas;
	}

	public int getPassos() {
		return passos;
	}

	public void setPassos(int passos) {
		this.passos = passos;
	}

	public long getCodigoAluno() {
		return codigoAluno;
	}

	public void setCodigoAluno(long codigoAluno) {
		this.codigoAluno = codigoAluno;
	}

	@Override
	public String toString() {
		return String.format("%S", nomeExercicio);
	}
	
	public String toStringCompleto() {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formataHoraComSegundo = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formataHoraSemSegundo = new SimpleDateFormat("HH:mm");
		return String.format(
				"------ Detalhes do exercício ------\r\n" + 
				"Exercicio: %s"
				+ "\nData:%s"
				+ "\nTempo: %s"+" - " + "%s"
				+ "\nDuração: %s"
				+ "\nDistância: %s" + " Km"
				+ "\nCalorias Perdidas: %s" + " Kcal"
				+ "\nPassos: %s\n",
				nomeExercicio, formataData.format(data.getTime()), formataHoraSemSegundo.format(tempoInicio.getTime()), formataHoraSemSegundo.format(tempoFinal.getTime()),
				formataHoraComSegundo.format(duracao.getTime()), distancia, caloriasPerdidas, passos);
	}
	
	public static class OrdenaExercicioData implements Comparator<Exercicio>{

		@Override
		public int compare(Exercicio o1, Exercicio o2) {
			return o1.getData().compareTo(o2.getData());
		}
		
	}
}