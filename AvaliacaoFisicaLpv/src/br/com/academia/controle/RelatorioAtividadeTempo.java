package br.com.academia.controle;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.dao.AlunoDao;
import br.com.academia.modelo.dao.ExercicioDao;
import br.com.academia.modelo.dao.ExercicioDetalhadoDao;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class RelatorioAtividadeTempo {

	 private Connection conexao = new FabricaConexao().getConexao();
	 
	 @FXML
	 private AnchorPane relatorioAtividadeTempoAnchorPane;

	 @FXML
	 private TextArea dadosTextArea;
	 
	 	@FXML
	    private void initialize() {
	 		dadosTextArea.setText("");
	    	dadosTextArea.setEditable(false);
	 		gerarRelatorio();
	    	
	    }
	    
	    /**
	     * 	Gera e exibe o relatorio dos usuarios com a maior distancia percorrida, maior numero de calorias perdidas,
	     * 	maior numero de passos, maior velocidade e a data em que ocorrem.
	     */
	    public void gerarRelatorio() {
	    	AlunoDao alunoDao = new AlunoDao(conexao);
	    	ExercicioDao exercicioDao = new ExercicioDao(conexao);
	    	ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(conexao);
	    	
	    	ArrayList<Exercicio> exercicioList = new ArrayList<Exercicio>();
	    	ArrayList<ExercicioDetalhado> exercicioDetalhadoList = new ArrayList<ExercicioDetalhado>();
	    	List<Aluno> listaDeUsuarios = alunoDao.todosAlunos();
	    	dadosTextArea.setText("");
	    	
	    	if(listaDeUsuarios != null) {
	    		for (int i = 0; i < listaDeUsuarios.size(); i++) {
	    			Calendar maiorDuracao = Calendar.getInstance();
	    			SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyy");
	    			SimpleDateFormat formataHoraComSegundo = new SimpleDateFormat("HH:mm:ss");
	    			
	    			try {
						maiorDuracao.setTime(formataHoraComSegundo.parse("00:00:00"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
	    			
	    			float maiorDistanciaPercorrida = 0;
	    			float maiorNumeroCaloriasPerdidas = 0;
	    			int maiorNumeroPassos = 0;
	    			float maiorVelocidadeMaxima = 0;
	    			
	    			Calendar dataMaiorDuracao = Calendar.getInstance();
	    			Calendar dataMaiorDistanciaPercorrida = Calendar.getInstance();
	    			Calendar dataNumeroCaloriasPerdidas = Calendar.getInstance();
	    			Calendar dataMaiorNumeroPassos = Calendar.getInstance();
	    			Calendar dataMaiorVelocidadeMaxima = Calendar.getInstance();
	    			
	    			exercicioList = (ArrayList<Exercicio>) exercicioDao.pesquisaExerciciosUsuario(listaDeUsuarios.get(i).getId());
	    			if(exercicioList != null) {
	    				for (int j = 0; j < exercicioList.size(); j++) {
							//se o primeiro for maior e positivo
	    					if(exercicioList.get(j).getDuracao().compareTo(maiorDuracao) > 0) {
								maiorDuracao = exercicioList.get(j).getDuracao();
								dataMaiorDuracao = exercicioList.get(j).getData();
								
							}
	    					if(exercicioList.get(j).getDistancia() > maiorDistanciaPercorrida) {
	    						maiorDistanciaPercorrida = exercicioList.get(j).getDistancia();
	    						dataMaiorDistanciaPercorrida = exercicioList.get(j).getData();
	    					}
	    					if(exercicioList.get(j).getCaloriasPerdidas() > maiorNumeroCaloriasPerdidas) {
	    						maiorNumeroCaloriasPerdidas = exercicioList.get(j).getCaloriasPerdidas();
	    						dataNumeroCaloriasPerdidas = exercicioList.get(j).getData();
	    					}
	    					
	    					if(exercicioList.get(j).getPassos() > maiorNumeroPassos) {
	    						maiorNumeroPassos = exercicioList.get(j).getPassos();
	    						dataMaiorNumeroPassos = exercicioList.get(j).getData();
	    					}
						}
	    			}
	    			
	    			exercicioDetalhadoList = (ArrayList<ExercicioDetalhado>) exercicioDetalhadoDao.pesquisaExercicioDetalhadoUsuario(listaDeUsuarios.get(i).getId());
	    			if(exercicioDetalhadoList != null) {
	    				for (int j = 0; j < exercicioDetalhadoList.size(); j++) {
	    					if(exercicioDetalhadoList.get(j).getDuracao().compareTo(maiorDuracao) > 0) {
								maiorDuracao = exercicioDetalhadoList.get(j).getDuracao(); 
								dataMaiorDuracao = exercicioDetalhadoList.get(j).getData();;
							}
	    					if(exercicioDetalhadoList.get(j).getDistancia() > maiorDistanciaPercorrida) {
	    						maiorDistanciaPercorrida = exercicioDetalhadoList.get(j).getDistancia();
	    						dataMaiorDistanciaPercorrida = exercicioDetalhadoList.get(j).getData();;
	    					}
	    					if(exercicioDetalhadoList.get(j).getCaloriasPerdidas() > maiorNumeroCaloriasPerdidas) {
	    						maiorNumeroCaloriasPerdidas = exercicioDetalhadoList.get(j).getCaloriasPerdidas();
	    						dataNumeroCaloriasPerdidas = exercicioDetalhadoList.get(j).getData();
	    					}
	    					
	    					if(exercicioDetalhadoList.get(j).getPassos() > maiorNumeroPassos) {
	    						maiorNumeroPassos = exercicioDetalhadoList.get(j).getPassos();
	    						dataMaiorNumeroPassos = exercicioDetalhadoList.get(j).getData();
	    					}
	    					if(exercicioDetalhadoList.get(j).getVelocidadeMaxima() > maiorVelocidadeMaxima) {
	    						maiorVelocidadeMaxima = exercicioDetalhadoList.get(j).getVelocidadeMaxima();
	    						dataMaiorVelocidadeMaxima = exercicioDetalhadoList.get(j).getData();
	    					}
						}
	    			}
	    			
	    			dadosTextArea.appendText("\n\nNome: " + listaDeUsuarios.get(i).getNome());
	    			dadosTextArea.appendText("\nDuraca " + formataHoraComSegundo.format(maiorDuracao.getTime()) + "\tData: " + formataData.format(dataMaiorDuracao.getTime()));
	    			dadosTextArea.appendText("\ndistancia " + maiorDistanciaPercorrida + "\tData: " + formataData.format(dataMaiorDistanciaPercorrida.getTime()));
	    			dadosTextArea.appendText("\ncalorias " + maiorNumeroCaloriasPerdidas + "\tData: " + formataData.format(dataNumeroCaloriasPerdidas.getTime()));
	    			dadosTextArea.appendText("\npassos " + maiorNumeroPassos + "\tData: " + formataData.format(dataMaiorNumeroPassos.getTime()));
	    			dadosTextArea.appendText("\nvelocidade max " + maiorVelocidadeMaxima +  "\tData: " + formataData.format(dataMaiorVelocidadeMaxima.getTime()) + "\n");
	    			
	    		}
	    	}
	    }
}
