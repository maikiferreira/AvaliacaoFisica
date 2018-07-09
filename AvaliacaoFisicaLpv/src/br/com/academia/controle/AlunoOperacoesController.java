package br.com.academia.controle;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.dao.AlunoDao;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class AlunoOperacoesController {
		Connection con = new FabricaConexao().getConexao();
		
		private AlunoDao alunoDao = new AlunoDao(con);
		private ObservableList<Aluno> alunos;
		
		@FXML
	    private SplitPane splitPane;
		
	 	@FXML
	    private TableColumn<Aluno, String> nomeTableColumn;

	    @FXML
	    private TableColumn<Aluno, String> dataNascimentoTableColumn;

	    @FXML
	    private TableView<Aluno> alunoTableView;

	    @FXML
	    private TableColumn<Aluno, Long> idTableColumn;

	    @FXML
	    private TableColumn<Aluno, String> emailTableColumn;

	    @FXML
	    private TableColumn<Aluno, String> cpfTableColumn;

	    @FXML
	    private TableColumn<Aluno, String> sexoTableColumn;

	    @FXML
	    private TableColumn<Aluno, Integer> alturaTableColumn;

	    @FXML
	    private TableColumn<Aluno, Float> pesoTableColumn;

	    @FXML
	    private TableColumn<Aluno, String> whatsappTableColumn;

	    @FXML
	    private TextField emailTextField;

	    @FXML
	    private Button excluirButton;

	    @FXML
	    private TextField cpfTextField;

	    @FXML
	    private TextField pesoTextField;

	    @FXML
	    private TextField dataNascimentoTextField;

	    @FXML
	    private TextField whatsappTextField;

	    @FXML
	    private Button alterarButton;

	    @FXML
	    private TextField nomeTextField;

	    @FXML
	    private TextField sexoTextField;

	    @FXML
	    private TextField alturaTextField;
	    
	    @FXML
	    private Label mensagemLabel;

	    @FXML
	    public void initialize() {
	    	atualizaTableView();
	    	
	    	/*Usuario usuario = LoginController.user;
	    	if(usuario.getPapel().equals("admin")) {
	    		splitPane.setVisible(true);
	    		mensagemLabel.setVisible(false);
	    	}
	    	else {
	    		splitPane.setVisible(false);
	    		mensagemLabel.setVisible(true);
	    		mensagemLabel.setText("Usuario não possui permissão para alterar os dados!");
	    	}*/
	    }
	    
	    
	    @FXML
	    void alterar() {
	    	Aluno aluno = new Aluno();
	    	
	    	aluno.setNome(nomeTextField.getText());
	    	aluno.setSexo(sexoTextField.getText());
	    	aluno.setAltura(Integer.parseInt(alturaTextField.getText()));
	    	aluno.setPeso(Float.parseFloat(pesoTextField.getText().replace(",", ".")));
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	Calendar calendar = Calendar.getInstance();
	    	try {
				calendar.setTime(sdf.parse(dataNascimentoTextField.getText()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	aluno.setDataNascimento(calendar);
	    	aluno.setEmail(emailTextField.getText());
	    	aluno.setCpf(cpfTextField.getText());
	    	aluno.setWhatsapp(whatsappTextField.getText());
	    	
	    	Aluno alu = new Aluno();
	    	
	    	alu = alunoDao.buscaPorEmail(aluno.getEmail());
	    		
	    	if(alu != null) {
	    		aluno.setId(alu.getId());
	    		alunoDao.alterarAluno(aluno);
	    	}
	    	
	    	atualizaTableView();
	    	
	    	limpaTextField();
	    }

	    @FXML
	    public void excluir() {
	    	
	    	Aluno aluno = new Aluno();
	    	
	    	aluno.setNome(nomeTextField.getText());
	    	aluno.setSexo(sexoTextField.getText());
	    	aluno.setAltura(Integer.parseInt(alturaTextField.getText()));
	    	aluno.setPeso(Float.parseFloat(pesoTextField.getText().replace(",", ".")));
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	Calendar calendar = Calendar.getInstance();
	    	try {
				calendar.setTime(sdf.parse(dataNascimentoTextField.getText()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	aluno.setDataNascimento(calendar);
	    	aluno.setEmail(emailTextField.getText());
	    	aluno.setCpf(cpfTextField.getText());
	    	aluno.setWhatsapp(whatsappTextField.getText());
	    	
	    	Aluno alu = new Aluno();
	    	
	    	alu = alunoDao.buscaPorEmail(aluno.getEmail());
	    		
	    	if(alu != null) {
	    		aluno.setId(alu.getId());
	    		alunoDao.excluirAluno(aluno);
	    	}
	    	
	    	limpaTextField();
	    	
	    	atualizaTableView();
	    }

	    @FXML
	    void obtemAluno(MouseEvent mouseEvent) {
	    	if(mouseEvent.getClickCount() == 2 && alunoTableView.getSelectionModel().getSelectedItem() != null) {
	    		Aluno aluno = alunoTableView.getSelectionModel().getSelectedItem();
	    		//System.out.println(aluno.getNome());
	    		
	    		nomeTextField.setText(aluno.getNome());
	    		sexoTextField.setText(aluno.getSexo());
	    		alturaTextField.setText(String.format("%d", aluno.getAltura()));
	    		pesoTextField.setText(String.format("%f", aluno.getPeso()));
	    		
	    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    		
	    		
	    		dataNascimentoTextField.setText(String.format("%s", sdf.format(aluno.getDataNascimento().getTime())));
	    		
	    		
	    		emailTextField.setText(aluno.getEmail());
	    		cpfTextField.setText(aluno.getCpf());
	    		whatsappTextField.setText(aluno.getWhatsapp());
	    	}
	    }
	    
	    public void atualizaTableView() {
	    	idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
	    	
	    	dataNascimentoTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Aluno,String>, ObservableValue<String>>() {
	    		
				@Override
				public ObservableValue<String> call(CellDataFeatures<Aluno, String> param) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Aluno aluno = new Aluno();
					aluno= param.getValue();
					
					SimpleObjectProperty<String> simpleObjectProperty = new SimpleObjectProperty<String>(sdf.format(aluno.getDataNascimento().getTime()));
					return simpleObjectProperty;
				}
			});
	    	//dataNascimentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
	    	
	    	emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
	    	cpfTableColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	    	sexoTableColumn.setCellValueFactory(new PropertyValueFactory<>("sexo"));
	    	alturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("altura"));
	    	pesoTableColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
	    	whatsappTableColumn.setCellValueFactory(new PropertyValueFactory<>("whatsapp"));
	    	
	    	this.alunos = FXCollections.observableList(this.alunoDao.todosAlunos());
	    	
	    	alunoTableView.setItems(alunos);
	    }
	    
	    public void limpaTextField() {
	    	nomeTextField.setText("");
    		sexoTextField.setText("");
    		alturaTextField.setText("");
    		pesoTextField.setText("");
    		dataNascimentoTextField.setText("");
    		emailTextField.setText("");
    		cpfTextField.setText("");
    		whatsappTextField.setText("");
	    }
}
