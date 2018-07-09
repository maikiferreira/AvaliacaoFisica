package br.com.academia.controle;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.dao.AlunoDao;
import br.com.academia.modelo.dao.RitmoDao;
import br.com.funcoes.FuncoesGrafico;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ExibirGraficoDetalhadoController {
	
	private Connection con = new FabricaConexao().getConexao();
	
	public static Exercicio exercicio = new Exercicio();
	
	private FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
	
	private AlunoDao alunoDao = new AlunoDao(con);
	private RitmoDao ritmoDao = new RitmoDao(con);
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	
	@FXML
    private DatePicker dataFinalDatePicker;

    @FXML
    private DatePicker dataInicioDatePicker;

    @FXML
    private RadioButton duracaoExercicioRadio;

    @FXML
    private TextField distanciaTotalLabel;

    @FXML
    private RadioButton distanciaPercorridaRadio;

    @FXML
    private RadioButton caloriasPerdidasRadio;

    @FXML
    private RadioButton ritmoMedioRadio;

    @FXML
    private Text dataTermino;

    @FXML
    private RadioButton velocidadeMediaRadio;

    @FXML
    private Text dataInicoText;

    @FXML
    private TextField totalPassosLabel;

    @FXML
    private RadioButton passosRadio;

    @FXML
    private TextField totalCalorias;

    @FXML
    private TextField distanciaMediaLabel;

    @FXML
    private HBox hboxRadios;

    @FXML
    private ComboBox<Aluno> alunosComboBox;

    @FXML
    private ToggleGroup grafico;

    @FXML
    private StackedBarChart<String, Number> graficoBarra;

    @FXML
    private TextField mediaCaloriasLabel;

    @FXML
    private SwingNode swingNode;

    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
    	dataInicoText.setVisible(false);
    	dataInicioDatePicker.setVisible(false);
    	dataTermino.setVisible(false);
    	dataFinalDatePicker.setVisible(false);
    	hboxRadios.setVisible(false);
    	graficoBarra.setVisible(false);
    	gridPane.setVisible(false);
    	
    	dataInicioDatePicker.setValue(LocalDate.now());
    	dataFinalDatePicker.setValue(LocalDate.now());
    	
    	alunos = alunoDao.todosAlunos();
		alunosComboBox.setItems(FXCollections.observableList(alunos));
		
		
    	
    }
    
    @FXML
    void duracaoRadio() {
    	atualizaGrafico();
    }

    @FXML
    void distanciaRadio() {
    	atualizaGrafico();
    }

    @FXML
    void caloriasRadio() {
    	atualizaGrafico();
    }

    @FXML
    void passosRadio() {
    	atualizaGrafico();
    }

    @FXML
    void velocidadeRadio() {
    	atualizaGrafico();
    }

    @FXML
    void ritmoRadio() {
    	atualizaGrafico();
    }

    @FXML
    void obterAluno() {
    	dataInicoText.setVisible(true);
    	dataInicioDatePicker.setVisible(true);
    	dataTermino.setVisible(true);
    	dataFinalDatePicker.setVisible(true);
    	hboxRadios.setVisible(true);
    	graficoBarra.setVisible(true);
    	gridPane.setVisible(true);
    	
    	atualizaGrafico();
    	
    }
    
    public void atualizaGrafico() {
    		List<Exercicio> exerciciosList = new ArrayList<Exercicio>();
        	SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        	
        	
        	
        	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicioDatePicker.getValue());
        	Calendar datFim = funcoesAuxiliares.pegaData(dataFinalDatePicker.getValue());
        	
        	exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
        	//exerciciosList = exercicioDao.pesquisaExerciciosUsuario(alunosComboBox.getValue().getId());
        	//exerciciosList.addAll(exercicioDao.pesquisaExerciciosUsuario(alunosComboBox.getValue().getId()));
        	
        	for (Exercicio exercicio : exerciciosList) {
				if(exercicio instanceof ExercicioDetalhado) {
					List<Ritmo> ritmoList = ritmoDao.pesquisaRitmo(exercicio.getId());
					for (Ritmo ritmo : ritmoList) {
						((ExercicioDetalhado)exercicio).adicionarRitmo(ritmo);
					}
				}
			}
        	
      		Calendar dataDeInicio = funcoesAuxiliares.pegaData(dataInicioDatePicker.getValue());
    		Calendar dataDeFim = funcoesAuxiliares.pegaData(dataFinalDatePicker.getValue());
    		try {
    			dataDeInicio.setTime(formataData.parse(formataData.format(dataDeInicio.getTime())));
    			dataDeFim.setTime(formataData.parse(formataData.format(dataDeFim.getTime())));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
    		exibeDados(exerciciosList);
    				
    		if(duracaoExercicioRadio.isSelected()) {
    			plotarGraficoDuracao(graficoBarra, exerciciosList);
    		}
    		else {
    			if(distanciaPercorridaRadio.isSelected()) {
        			plotarGraficoDistancia(graficoBarra, exerciciosList);
        		}
        		else {
        			if(caloriasPerdidasRadio.isSelected()) {
        				plotarGraficoCalorias(graficoBarra, exerciciosList);
        			}
        			else {
        				if(passosRadio.isSelected()) {
        					plotarGraficoPassos(graficoBarra, exerciciosList);
        				}
        				else {
        					if(velocidadeMediaRadio.isSelected()) {
        						plotarGraficoVelocidadeMedia(graficoBarra, exerciciosList);
        					}
        					else {
        						plotarGraficoRitmoMedio(graficoBarra, exerciciosList);
        					}
        				}
        			}
        		}
    		}
        	
    }
    
    public void plotarGraficoDuracao(StackedBarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()),exer.getDuracao().get(Calendar.MINUTE) + exer.getDuracao().get(Calendar.HOUR_OF_DAY) * 60));
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoDistancia(StackedBarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), exer.getDistancia()));
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoCalorias(StackedBarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), exer.getCaloriasPerdidas()));
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoPassos(StackedBarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), exer.getPassos()));
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoVelocidadeMedia(StackedBarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		if(exer instanceof ExercicioDetalhado) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), ((ExercicioDetalhado) exer).getVelocidadeMedia()));
    		}
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoRitmoMedio(StackedBarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		if(exer instanceof ExercicioDetalhado) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()),((ExercicioDetalhado) exer).getRitmoMedia().get(Calendar.MINUTE) + ((ExercicioDetalhado) exer).getRitmoMedia().get(Calendar.HOUR_OF_DAY) * 60));
    		}
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void exibeDados(List<Exercicio> exerciciosList) {
    	int totalPassos = 0;
 		float distanciaMedia = 0;
 		float distanciaTotal = 0;
 		float mediaCaloriasPerdidas = 0;
 		float caloriasPerdidas = 0;
 		
		for (Exercicio exercicio : exerciciosList) {
			//if(exercicio.getData().compareTo(dataInicio) >=0 && exercicio.getData().compareTo(dataFim) <= 0) {
				totalPassos += exercicio.getPassos();
				distanciaTotal += exercicio.getDistancia();
				caloriasPerdidas += exercicio.getCaloriasPerdidas();
			//}
		}	
		
		totalPassosLabel.setText(String.format("%d",totalPassos));
		
		distanciaMedia = distanciaTotal / exerciciosList.size();
		distanciaMediaLabel.setText(String.format("%.1f Km", distanciaMedia));
		
		distanciaTotalLabel.setText(String.format("%.1f Km", distanciaTotal));
		
		mediaCaloriasPerdidas = caloriasPerdidas / exerciciosList.size();
		mediaCaloriasLabel.setText(String.format("%.1f Kcal", mediaCaloriasPerdidas));
		
		totalCalorias.setText(String.format("%.1f Kcal", caloriasPerdidas));
		
    }
}
