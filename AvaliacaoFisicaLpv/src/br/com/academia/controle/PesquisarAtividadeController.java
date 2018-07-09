package br.com.academia.controle;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.Ritmo;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PesquisarAtividadeController {
	private Connection con = new FabricaConexao().getConexao();
	
	public static Exercicio exercicio = new Exercicio();
	
	private ExercicioDao exercicioDao = new ExercicioDao(con);
	private ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(con);
	private RitmoDao ritmoDao = new RitmoDao(con);
	private List<Ritmo> ritmos;
	
	private ObservableList<Exercicio> atividades;
	private List<Exercicio> atividadesList = new ArrayList<Exercicio>();
	
	@FXML
    private TableColumn<Exercicio, String> horaTableColumn;

    @FXML
    private TextField nomeAtividadeTextField;

    @FXML
    private TableColumn<Exercicio, String> atividadeTableColumn;

    @FXML
    private TableView<Exercicio> tableView;

    @FXML
    private TableColumn<Exercicio, String> dataTableColumn;
    
    @FXML
    private TableColumn<String, Long> idAluno;

    @FXML
    void pesquisarAtividades() {
    	ArrayList<Exercicio> atividadesvalidas = new ArrayList<Exercicio>();
    	/*exercicio = exercicioDao.buscaExercicioPorNome(nomeAtividadeTextField.getText());
    	atividades.addAll((Exercicio) exercicioDao.listarTodosExercicios());
    	atividades.addAll((Exercicio) exercicioDetalhadoDao.listarTodosExerciciosDetalhados());
    	System.out.println(atividades.size());*/
    	atividadesList.addAll(exercicioDao.listarTodosExercicios());
    	atividadesList.addAll(exercicioDetalhadoDao.listarTodosExerciciosDetalhados());
    	
    	for (int i = 0; i < atividadesList.size(); i++) {
			if(atividadesList.get(i).getNomeExercicio().equalsIgnoreCase(nomeAtividadeTextField.getText())) {
				atividadesvalidas.add(atividadesList.get(i));
			}
		}
    	
    	atualizaTableView(atividadesvalidas);
    }

    @FXML
    void obterAtividade(MouseEvent mouseEvent) {
    	if(mouseEvent.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
    		exercicio = tableView.getSelectionModel().getSelectedItem();
    		
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
    
    public void atualizaTableView(List<Exercicio> atividadesValidas) {
    	
    	idAluno.setCellValueFactory(new PropertyValueFactory<>("codigoAluno"));
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
    	
    	this.atividades = FXCollections.observableList(atividadesValidas);
    	
    	tableView.setItems(atividades);
    }
    
    public void fecharJanelaAtual() {
    	Stage stage = (Stage) tableView.getScene().getWindow();
    	stage.close();
    }
    
    public void exibirDetalhesAtividade(){
    	try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/ExibirDadosPesquisaAtividade.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
