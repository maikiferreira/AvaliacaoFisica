package br.com.academia.controle;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.dao.AlunoDao;
import br.com.academia.modelo.dao.ExercicioDao;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ExercicioOperacoesController {
	
	private Connection con = new FabricaConexao().getConexao();
	
	private AlunoDao alunoDao = new AlunoDao(con);
	private ExercicioDao exercicioDao = new ExercicioDao(con);
	private ObservableList<Exercicio> exercicios;
	
	@FXML
    private TableColumn<Exercicio, Long> idAlunoTableColumn;

    @FXML
    private TableColumn<Exercicio, String> nomeTableColumn;

    @FXML
    private TableColumn<Exercicio, Integer> passosTableColumn;

    @FXML
    private TableColumn<Exercicio, Long> idTableColumn;

    @FXML
    private TableColumn<Exercicio, String> horaTerminoTableColumn;

    @FXML
    private TableColumn<Exercicio, String> duracaoTableColumn;

    @FXML
    private TableColumn<Exercicio, String> dataExercicioTableColumn;

    @FXML
    private TableColumn<Exercicio, Float> caloriasPerdidasTableColumn;

    @FXML
    private TableView<Exercicio> exercicioTableView;

    @FXML
    private AnchorPane exercicoOpcoesAnchorPane;

    @FXML
    private TableColumn<Exercicio, String> horaInicioTableColumn;

    @FXML
    private TableColumn<Calendar, Float> distanciaTableColumn;
    
    @FXML
    private ComboBox<Aluno> alunosComboBox;

    @FXML
    private TextField dataTextField;

    @FXML
    private TextField caloriasPerdidas;

    @FXML
    private TextField duracaoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField passosTextField;

    @FXML
    private TextField horaInicioTextField;

    @FXML
    private TextField horaTerminoTextField;

    @FXML
    private TextField distanciaTextField;

    @FXML
    public void initialize() {
    	alunosComboBox.setItems(FXCollections.observableArrayList(alunoDao.todosAlunos()));
    	//atualizaTableView();
    }
    
    @FXML
    void alterar() {
    	Exercicio exercicio = new Exercicio();
    	
    	exercicio.setNomeExercicio(nomeTextField.getText());
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Calendar dataExercicio = Calendar.getInstance();
    	
    	try {
    		dataExercicio.setTime(sdf.parse(dataTextField.getText()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	exercicio.setData(dataExercicio);
    	
    	SimpleDateFormat formataHoraSemSegundo = new SimpleDateFormat("HH:mm");
    	Calendar horaInicial = Calendar.getInstance();
    	try {
			horaInicial.setTime(formataHoraSemSegundo.parse(horaInicioTextField.getText()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	exercicio.setTempoInicio(horaInicial);
    	
    	Calendar horaFinal = Calendar.getInstance();
    	try {
			horaFinal.setTime(formataHoraSemSegundo.parse(horaTerminoTextField.getText()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	exercicio.setTempoFinal(horaFinal);
    	
    	SimpleDateFormat formataHoraComSegundo = new SimpleDateFormat("HH:mm:ss");
    	
    	Calendar duracao = Calendar.getInstance();
    	try {
			duracao.setTime(formataHoraComSegundo.parse(duracaoTextField.getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	exercicio.setDuracao(duracao);
    	
    	exercicio.setDistancia(Float.parseFloat(distanciaTextField.getText().replaceAll(",", ".")));
    	exercicio.setCaloriasPerdidas(Float.parseFloat(caloriasPerdidas.getText().replaceAll(",", ".")));
    	exercicio.setPassos(Integer.parseInt(passosTextField.getText()));
    	
    	Aluno aluno = alunosComboBox.getValue();
    	Exercicio exe = new Exercicio();
    	exe.setCodigoAluno(aluno.getId());
    	exe.setData(dataExercicio);
    	exe.setTempoInicio(horaInicial);
    	exe.setTempoFinal(horaFinal);
    	Exercicio exer = exercicioDao.pesquisar(aluno.getId(), exe);
    	
    	
    	if(exer != null) {
    		exercicio.setId(exe.getId());
    		exercicioDao.alterar(exercicio);
    	}
    	
    	atualizaTableView(aluno);
    	
    	limpaTextField();
    }

    @FXML
    void excluir() {
    	if(exercicioTableView.getSelectionModel().getSelectedItem() != null) {
    		Exercicio exercicio = exercicioTableView.getSelectionModel().getSelectedItem();
    		
    		exercicioDao.remove(exercicio);
    		Aluno aluno = alunosComboBox.getValue();
    		
    		atualizaTableView(aluno);
    		
    		limpaTextField();
    		
    	}
    }

    public void atualizaTableView(Aluno aluno) {
    	idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nomeExercicio"));
    	
    	dataExercicioTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercicio,String>, ObservableValue<String>>() {
    		
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exercicio, String> param) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Exercicio exercicio = new Exercicio();
				exercicio = param.getValue();
				
				SimpleObjectProperty<String> simpleObjectProperty = new SimpleObjectProperty<String>(sdf.format(exercicio.getData().getTime()));
				return simpleObjectProperty;
			}
		});
    	//dataExercicioTableColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
    	
    	horaInicioTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercicio,String>, ObservableValue<String>>() {
    		
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exercicio, String> param) {
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
				Exercicio exercicio = new Exercicio();
				exercicio = param.getValue();
				
				SimpleObjectProperty<String> simpleObjectProperty = new SimpleObjectProperty<String>(sdf.format(exercicio.getTempoInicio().getTime()));
				return simpleObjectProperty;
			}
		});
    	//horaInicioTableColumn.setCellValueFactory(new PropertyValueFactory<>("tempoInicio"));
    	
    	horaTerminoTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercicio,String>, ObservableValue<String>>() {
    		
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exercicio, String> param) {
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
				Exercicio exercicio = new Exercicio();
				exercicio = param.getValue();
				
				SimpleObjectProperty<String> simpleObjectProperty = new SimpleObjectProperty<String>(sdf.format(exercicio.getTempoFinal().getTime()));
				return simpleObjectProperty;
			}
		});
    	//horaTerminoTableColumn.setCellValueFactory(new PropertyValueFactory<>("tempoFinal"));
    	
    	
    	
    	duracaoTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercicio,String>, ObservableValue<String>>() {
    		
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exercicio, String> param) {
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				Exercicio exercicio = new Exercicio();
				exercicio = param.getValue();
				
				SimpleObjectProperty<String> simpleObjectProperty = new SimpleObjectProperty<String>(sdf.format(exercicio.getDuracao().getTime()));
				return simpleObjectProperty;
			}
		});
    	//duracaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("duracao"));
    	distanciaTableColumn.setCellValueFactory(new PropertyValueFactory<>("distancia"));
    	caloriasPerdidasTableColumn.setCellValueFactory(new PropertyValueFactory<>("caloriasPerdidas"));
    	passosTableColumn.setCellValueFactory(new PropertyValueFactory<>("passos"));
    	idAlunoTableColumn.setCellValueFactory(new PropertyValueFactory<>("codigoAluno"));
    	
    	this.exercicios = FXCollections.observableList(this.exercicioDao.pesquisaExerciciosUsuario(aluno.getId()));
    	
    	exercicioTableView.setItems(exercicios);
    	
    }
    
    @FXML
    void onActionAlunosComboBox() {
    	Aluno aluno = alunosComboBox.getValue();
    	atualizaTableView(aluno);
    }
    
    @FXML
    void obtemExercicio(MouseEvent mouseEvent) {
    	if(mouseEvent.getClickCount() == 2 && exercicioTableView.getSelectionModel().getSelectedItem() != null) {
    		Exercicio exercicio = exercicioTableView.getSelectionModel().getSelectedItem();
    		
    		nomeTextField.setText(exercicio.getNomeExercicio());
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    		dataTextField.setText(String.format("%s", sdf.format(exercicio.getData().getTime())));
    		
    		SimpleDateFormat formataHoraSemSegundo = new SimpleDateFormat("HH:mm");
    		horaInicioTextField.setText(String.format("%s", formataHoraSemSegundo.format(exercicio.getTempoInicio().getTime())));
    		horaTerminoTextField.setText(String.format("%s", formataHoraSemSegundo.format(exercicio.getTempoFinal().getTime())));
    		
    		SimpleDateFormat formataHoraComSegundo = new SimpleDateFormat("HH:mm:ss");
    		duracaoTextField.setText(String.format("%s", formataHoraComSegundo.format(exercicio.getDuracao().getTime())));
    		
    		distanciaTextField.setText(String.format("%f",exercicio.getDistancia()));
    		caloriasPerdidas.setText(String.format("%f", exercicio.getCaloriasPerdidas()));
    		passosTextField.setText(String.format("%d", exercicio.getPassos()));
    	}
    }
    
    public void limpaTextField() {
    	nomeTextField.setText("");
		dataTextField.setText("");
		horaInicioTextField.setText("");
		horaTerminoTextField.setText("");
		duracaoTextField.setText("");
		distanciaTextField.setText("");
		caloriasPerdidas.setText("");
		passosTextField.setText("");
    }
}
