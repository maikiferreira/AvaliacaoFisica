package br.com.academia.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Ritmo {
	private Calendar ritmo;
	private float distancia;
	private long codRitmo;
	private long codExercicioDetalhado;
	
	public Ritmo() {
		this(0.0f);
	}

	public Ritmo(float distancia) {
		this.ritmo = Calendar.getInstance();
		this.distancia = distancia;
	}

	public Calendar getRitmo() {
		return ritmo;
	}

	public void setRitmo(Calendar ritmo) {
		this.ritmo = ritmo;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public long getCodRitmo() {
		return codRitmo;
	}

	public void setCodRitmo(long codRitmo) {
		this.codRitmo = codRitmo;
	}

	public long getCodExercicioDetalhado() {
		return codExercicioDetalhado;
	}

	public void setCodExercicioDetalhado(long codExercicioDetalhado) {
		this.codExercicioDetalhado = codExercicioDetalhado;
	}

	@Override
	public String toString() {
		SimpleDateFormat formataMinutoESegundo = new SimpleDateFormat("mm:ss");
		return String.format("%s" + " Km: "+ " %s", distancia, formataMinutoESegundo.format(ritmo.getTime()) );
	}
}
