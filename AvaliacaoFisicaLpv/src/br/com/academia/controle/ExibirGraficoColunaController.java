package br.com.academia.controle;

import java.sql.Connection;
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
import br.com.academia.modelo.dao.ExercicioDao;
import br.com.academia.modelo.dao.ExercicioDetalhadoDao;
import br.com.academia.modelo.dao.RitmoDao;
import br.com.funcoes.FuncoesGrafico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ExibirGraficoColunaController {
	
	private Connection con = new FabricaConexao().getConexao();
	
	public static Exercicio exercicio = new Exercicio();
	
	private AlunoDao alunoDao = new AlunoDao(con);
	private ExercicioDao exercicioDao = new ExercicioDao(con);
	private ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(con);
	private RitmoDao ritmoDao = new RitmoDao(con);
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private ObservableList<Exercicio> atividades;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	
	@FXML
    private ComboBox<Aluno> alunosComboBox;

    @FXML
    private RadioButton duracaoExercicioRadio;

    @FXML
    private DatePicker dataFim;

    @FXML
    private RadioButton distanciaPercorridaRadio;

    @FXML
    private RadioButton caloriasPerdidasRadio;

    @FXML
    private RadioButton ritmoMedioRadio;

    @FXML
    private RadioButton velocidadeMediaRadio;

    @FXML
    private ComboBox<Exercicio> exerciciosComboBox;

    @FXML
    private BarChart<String, Number> graficoBarra;

    @FXML
    private DatePicker dataInicio;

    @FXML
    private ToggleGroup tipoGrafico;

    @FXML
    private Text alunoText;
    
    @FXML
    private RadioButton passosRadio;
    
    @FXML
    private HBox radiosHbox;

    @FXML
    private Text dataTerminoTextField;
    
    @FXML
    private Text dataInicioTextField;
    
    @FXML
    public void initialize() {
    	dataInicioTextField.setVisible(false);
    	dataTerminoTextField.setVisible(false);
    	dataInicio.setVisible(false);
    	dataFim.setVisible(false);
    	radiosHbox.setVisible(false);
    	exerciciosComboBox.setVisible(false);
    	graficoBarra.setVisible(false);
    	
    	alunos = alunoDao.todosAlunos();
		alunosComboBox.setItems(FXCollections.observableList(alunos));
		
		dataInicio.setValue(LocalDate.now());
		dataFim.setValue(LocalDate.now());
    	
    }
    
	@FXML
    void pesquisarAluno() {
    	dataInicioTextField.setVisible(true);
    	dataTerminoTextField.setVisible(true);
    	dataInicio.setVisible(true);
    	dataFim.setVisible(true);
    	radiosHbox.setVisible(true);
    	exerciciosComboBox.setVisible(true);
    	graficoBarra.setVisible(true);
    	
    	this.atividades = FXCollections.observableList(this.exercicioDao.pesquisaExerciciosUsuario(alunosComboBox.getValue().getId()));
    	this.atividades.addAll(FXCollections.observableArrayList(this.exercicioDetalhadoDao.pesquisaExercicioDetalhadoUsuario(alunosComboBox.getValue().getId())));
    	
    	for (int i = 0; i < atividades.size(); i++) {
			if(atividades.get(i) instanceof ExercicioDetalhado) {
				ArrayList<Ritmo> ritmosList = (ArrayList<Ritmo>) ritmoDao.pesquisaRitmo(atividades.get(i).getId());
				for (Ritmo ritmo2 : ritmosList) {
					((ExercicioDetalhado)atividades.get(i)).adicionarRitmo(ritmo2);
				}
			}
		}
    	
    	exerciciosComboBox.setItems(atividades);
    	exerciciosComboBox.setValue(atividades.get(0));
    	
    }
	
	@FXML
    void duracaoRadio() {
		FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
    	
    	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
    	
    	plotarGraficoBarraDuracao(graficoBarra, exerciciosList);
    }
	
	@FXML
    void distanciaRadio() {
		FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
    	
    	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
    	
    	plotarGraficoBarraDistancia(graficoBarra, exerciciosList);
    }
	
	@FXML
    void caloriasRadio() {
		FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
    	
    	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
    	
    	plotarGraficoBarraCaloriasPerdidas(graficoBarra, exerciciosList);
    }
	
	@FXML
    void passosRadio() {
		FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
    	
    	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
    	
    	plotarGraficoBarraPassos(graficoBarra, exerciciosList);
    }
	
	@FXML
    void velocidadeMediiaRadio() {
		FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
    	
    	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
    	
    	plotarGraficoBarraVelocidadeMedia(graficoBarra, exerciciosList);
    }

    @FXML
    void ritmoMedioRadio() {
    		FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
        	
        	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
        	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
        	
        	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
        	
        	plotarGraficoBarraRitmoMedio(graficoBarra, exerciciosList);
    }
	
    @FXML
    void comboBox() {
    	FuncoesGrafico funcoesAuxiliares = new FuncoesGrafico();
    	
    	Calendar datInicio = funcoesAuxiliares.pegaData(dataInicio.getValue());
    	Calendar datFim = funcoesAuxiliares.pegaData(dataFim.getValue());
    	
    	List<Exercicio> exerciciosList = funcoesAuxiliares.exerciciosUsuario(con, alunosComboBox.getValue().getNome(), datInicio, datFim);
    	
    	if(duracaoExercicioRadio.isSelected()) {
    		plotarGraficoBarraDuracao(graficoBarra, exerciciosList);
    	}
    	else {
    		if(distanciaPercorridaRadio.isSelected()) {
    			plotarGraficoBarraDistancia(graficoBarra, exerciciosList);
    		}
    		else {
    			if(caloriasPerdidasRadio.isSelected()) {
    				plotarGraficoBarraCaloriasPerdidas(graficoBarra, exerciciosList);
    			}
    			else {
    				if(passosRadio.isSelected()) {
    					plotarGraficoBarraPassos(graficoBarra, exerciciosList);
    				}
    				else {
    					if(velocidadeMediaRadio.isSelected()) {
    						plotarGraficoBarraVelocidadeMedia(graficoBarra, exerciciosList);
    					}
    					else {
    						plotarGraficoBarraRitmoMedio(graficoBarra, exerciciosList);
    					}
    				}
    			}
    		}
    		
    	}
    }
    
  
    		
    public void plotarGraficoBarraDuracao(BarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		if (exer.getNomeExercicio().equals(exerciciosComboBox.getValue().getNomeExercicio())) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()),exer.getDuracao().get(Calendar.MINUTE) + exer.getDuracao().get(Calendar.HOUR_OF_DAY) * 60));
			} 
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoBarraDistancia(BarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		if (exer.getNomeExercicio().equals(exerciciosComboBox.getValue().getNomeExercicio())) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), exer.getDistancia()));
			} 
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoBarraCaloriasPerdidas(BarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		if (exer.getNomeExercicio().equals(exerciciosComboBox.getValue().getNomeExercicio())) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), exer.getCaloriasPerdidas()));
			} 
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
    
    public void plotarGraficoBarraPassos(BarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
    		if (exer.getNomeExercicio().equals(exerciciosComboBox.getValue().getNomeExercicio())) {
    			serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), exer.getPassos()));
			}
			
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }

    public void plotarGraficoBarraVelocidadeMedia(BarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
			if(exer instanceof ExercicioDetalhado) {
				if (exer.getNomeExercicio().equals(exerciciosComboBox.getValue().getNomeExercicio())) {
					serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()), ((ExercicioDetalhado) exer).getVelocidadeMedia()));
				}
				
			}
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }

    
    public void plotarGraficoBarraRitmoMedio(BarChart<String, Number> grafico, List<Exercicio> exercicio) {
    	grafico.getData().clear();
    	
    	XYChart.Series<String, Number> serie = new XYChart.Series<>();
    	for (Exercicio exer : exercicio) {
			if(exer instanceof ExercicioDetalhado) {
				if (exer.getNomeExercicio().equals(exerciciosComboBox.getValue().getNomeExercicio())) {
					serie.getData().add(new XYChart.Data<>(sdf.format(exer.getData().getTime()),((ExercicioDetalhado) exer).getRitmoMedia().get(Calendar.MINUTE) + ((ExercicioDetalhado) exer).getRitmoMedia().get(Calendar.HOUR_OF_DAY) * 60));
				}
				
			}
		}
    	grafico.setLegendVisible(true);
    	grafico.getData().add(serie);
    }
}
