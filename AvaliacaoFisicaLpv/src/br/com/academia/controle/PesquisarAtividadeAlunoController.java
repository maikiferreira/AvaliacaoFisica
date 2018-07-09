package br.com.academia.controle;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PesquisarAtividadeAlunoController {
	private Connection con = new FabricaConexao().getConexao();
	
	public static Exercicio exercicio = new Exercicio();
	
	private AlunoDao alunoDao = new AlunoDao(con);
	private ExercicioDao exercicioDao = new ExercicioDao(con);
	private ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(con);
	private RitmoDao ritmoDao = new RitmoDao(con);
	private List<Ritmo> ritmos;
	
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private ObservableList<Exercicio> atividades;
	//private ObservableList<ExercicioDetalhado> atividadesDetalhada;
	
	@FXML
    private TableColumn<Exercicio, String> horaTableColumn;

    @FXML
    private TableColumn<Exercicio, String> atividadeTableColumn;

    @FXML
    private TableView<Exercicio> atividadesTableView;

    @FXML
    private AnchorPane pesquisarAtividadeAlunoAnchorPane;

    @FXML
    private TableColumn<Exercicio, String> dataTableColumn;

    @FXML
    private ComboBox<Aluno> alunosComboBox;
	
	@FXML
	public void initialize() {
		alunos = alunoDao.todosAlunos();
		alunosComboBox.setItems(FXCollections.observableList(alunos));
	}
	
	@FXML
    void obterAtividade(MouseEvent mouseEvent) {
		if(mouseEvent.getClickCount() == 2 && atividadesTableView.getSelectionModel().getSelectedItem() != null) {
    		exercicio = atividadesTableView.getSelectionModel().getSelectedItem();
    		
    		if(exercicio instanceof ExercicioDetalhado) {
	    		ritmos = ritmoDao.pesquisaRitmo(exercicio.getId());
	    		
	    		for (Ritmo ritmo : ritmos) {
	    			((ExercicioDetalhado)exercicio).adicionarRitmo(ritmo);
				}
    		}
    		
    		exibirDetalhesAtividade();
    		fecharJanelaAtual();
    	}
    }
	
    @FXML
    void pesquisarAluno() {
    	atualizaTableView();
    }
    
    public void fecharJanelaAtual() {
    	Stage stage = (Stage) alunosComboBox.getScene().getWindow();
    	stage.close();
    }
    
    public void exibirDetalhesAtividade() {
    	try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/ExibirDetalhesAtividade.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void atualizaTableView() {
    	atividadeTableColumn .setCellValueFactory(new PropertyValueFactory<>("nomeExercicio"));
    	
    	dataTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercicio,String>, ObservableValue<String>>() {
    		
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
    	
    	horaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercicio,String>, ObservableValue<String>>() {
    		
			@Override
			public ObservableValue<String> call(CellDataFeatures<Exercicio, String> param) {
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
				Exercicio exercicio = new Exercicio();
				exercicio = param.getValue();
				
				SimpleObjectProperty<String> simpleObjectProperty = new SimpleObjectProperty<String>(sdf.format(exercicio.getTempoInicio().getTime()));
				return simpleObjectProperty;
			}
		});
    	
    	
    	this.atividades = FXCollections.observableList(this.exercicioDao.pesquisaExerciciosUsuario(alunosComboBox.getValue().getId()));
    	this.atividades.addAll(FXCollections.observableArrayList(this.exercicioDetalhadoDao.pesquisaExercicioDetalhadoUsuario(alunosComboBox.getValue().getId())));
    	
    	atividadesTableView.setItems(atividades);
    	
    }

}
