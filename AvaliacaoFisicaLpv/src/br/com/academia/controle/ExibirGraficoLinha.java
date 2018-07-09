package br.com.academia.controle;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.dao.AlunoDao;
import br.com.academia.modelo.dao.ExercicioDao;
import br.com.academia.modelo.dao.ExercicioDetalhadoDao;
import br.com.academia.modelo.dao.RitmoDao;
import br.com.funcoes.FuncoesGrafico;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ExibirGraficoLinha {
	private Connection con = new FabricaConexao().getConexao();
	
	private AlunoDao alunoDao = new AlunoDao(con);
	private ExercicioDao exercicioDao = new ExercicioDao(con);
	private ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(con);
	List<Exercicio> atividadesList; 
	
	@FXML
    private LineChart<String, Number> graficoLinha;

    @FXML
    private DatePicker dataInicioDatePicker;

    @FXML
    private DatePicker dateTerminoDatePicker;

    @FXML
    private ComboBox<Exercicio> atividadesComboBox;

    @FXML
    public void initialize() {
    	dataInicioDatePicker.setValue(LocalDate.now());
		dateTerminoDatePicker.setValue(LocalDate.now());
    	geraGrafico();
    }

    @FXML
    void pesquisar() {
    	geraGrafico();
    }
    
    @FXML
    void obterAividade() {
    	plotarGrafico(graficoLinha, atividadesList);
    }
    
	private void plotarGrafico(LineChart<String, Number> grafico,List<Exercicio> exercicios) {
    	grafico.getData().clear();
   
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exercicio : exercicios) {
    		Aluno aluno = alunoDao.buscaPorId(exercicio.getCodigoAluno());
    		//sdf.format(exer.getData().getTime()),exer.getDuracao().get(Calendar.MINUTE) + exer.getDuracao().get(Calendar.HOUR_OF_DAY) * 60)
			serie.getData().add(new XYChart.Data<>(String.format("%s - %s min", aluno.getNome() , exercicio.getDuracao().get(Calendar.MINUTE) + exercicio.getDuracao().get(Calendar.HOUR_OF_DAY) * 60)
					, exercicio.getCaloriasPerdidas()));
		}
    	
    	grafico.getData().add(serie);
	}
    
    public void geraGrafico() {
    	atividadesList = new ArrayList<Exercicio>();
    	atividadesList.addAll(exercicioDao.listarTodosExercicios());
    	atividadesList.addAll(exercicioDetalhadoDao.listarTodosExerciciosDetalhados());
    	
    	FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicioDatePicker.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dateTerminoDatePicker.getValue());
    	Calendar dataDeInicio = datInicio;
  		Calendar dataDeFim = datFim;
  		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
  		
  		try {
  			try {
				dataDeInicio.setTime(formataData.parse(formataData.format(dataDeInicio.getTime())));
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  			try {
				dataDeFim.setTime(formataData.parse(formataData.format(dataDeFim.getTime())));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  		} catch (ParseException e) {
  			e.printStackTrace();
  		}
        	
        		List<Ritmo> ritmosList = new ArrayList<Ritmo>();
        		RitmoDao ritmoDao = new RitmoDao(con);
        		List<Exercicio> exerciciosValidos = new ArrayList<Exercicio>();
        		
        		
        		for (Exercicio exercicio : atividadesList) {
    				if(exercicio instanceof ExercicioDetalhado) {
    					ritmosList.addAll(ritmoDao.pesquisaRitmo(exercicio.getId())); 
    				}
    			}
        		
        		for (Exercicio exercicio : atividadesList) {
        			if(exercicio.getData().compareTo(dataDeInicio) >=0 && exercicio.getData().compareTo(dataDeFim) <= 0) {
        	      		
        	      		if(exercicio instanceof ExercicioDetalhado) {
        	      			ritmosList.addAll(ritmoDao.pesquisaRitmo(exercicio.getId())); 
        	      			((ExercicioDetalhado)exercicio).setRitmos(ritmosList);
        	      		}
        	      		exerciciosValidos.add(exercicio);
            		}
  			}
        		
    		exerciciosValidos.sort(new Comparator<Exercicio>() {
				@Override
				public int compare(Exercicio o1, Exercicio o2) {
					return o1.getData().compareTo(o2.getData());
				}
			} );

    		atividadesComboBox.setItems(FXCollections.observableList(exerciciosValidos));
    
    }                    
    
	
}
