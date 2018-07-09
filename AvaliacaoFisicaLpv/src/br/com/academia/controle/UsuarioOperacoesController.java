package br.com.academia.controle;

import java.sql.Connection;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Usuario;
import br.com.academia.modelo.dao.UsuarioDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UsuarioOperacoesController {
	Connection con = new FabricaConexao().getConexao();
	
	private UsuarioDao usuarioDao = new UsuarioDao(con);
	private ObservableList<Usuario> usuarios;
	
	@FXML
    private TableColumn<Usuario, String> nomeTableColumn;

    @FXML
    private PasswordField senhaPasswordField;

    @FXML
    private TableColumn<Usuario, Long> idTableColumn;

    @FXML
    private TextField papelTextField;

    @FXML
    private TableColumn<Usuario, String> papelTableColumn;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private AnchorPane usuarioOperacoesAnchorPane;

    @FXML
    private TextField usuarioTextField;

    @FXML
    public void initialize() {
    	limpaTextField();
    	atualizaTableView();
    }
    
    @FXML
    void obtemUsuario(MouseEvent mouseEvent) {
    	
    }
    
    @FXML
    void obterUsuario(MouseEvent mouseEvent) {
    	if(mouseEvent.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
    		Usuario usuario = tableView.getSelectionModel().getSelectedItem();
    		
    		usuarioTextField.setText(usuario.getUsuario());
    		senhaPasswordField.setText(usuario.getSenha());
    		papelTextField.setText(usuario.getPapel());
    	}
    }

    @FXML
    void alterarUsuario() {
    	Usuario usuario = new Usuario();
    	
    	usuario.setUsuario(usuarioTextField.getText());
    	usuario.setSenha(senhaPasswordField.getText());
    	usuario.setPapel(papelTextField.getText());
    	
    	Usuario user = new Usuario();
    	
    	user = usuarioDao.buscaPorNomeUsuario(usuario.getUsuario());
    		
    	if(user != null) {
    		usuario.setId(user.getId());
    		usuarioDao.alterarusuario(usuario);
    	}
    	
    	atualizaTableView();
    	
    	limpaTextField();
    }

    @FXML
    void excluirUsuario() {
    	
    	Usuario usuario = new Usuario();
    	
    	usuario.setUsuario(usuarioTextField.getText());
    	usuario.setSenha(senhaPasswordField.getText());
    	usuario.setPapel(papelTextField.getText());
    	
    	Usuario user = new Usuario();
    	
    	user = usuarioDao.buscaPorNomeUsuario(usuario.getUsuario());
    		
    	if(user != null) {
    		usuario.setId(user.getId());
    		usuarioDao.excluirUsuario(usuario);
    	}
    	
    	limpaTextField();
    	
    	atualizaTableView();
    }
    
    public void atualizaTableView() {
    	//limpaTextField();
    	idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));
    	

    	
    	papelTableColumn.setCellValueFactory(new PropertyValueFactory<>("papel"));
    	
    	this.usuarios = FXCollections.observableList(this.usuarioDao.todosUsuarios());
    	
    	tableView.setItems(usuarios);
    }
    
    public void limpaTextField() {
    	usuarioTextField.setText("");
    	senhaPasswordField.setText("");
    	papelTextField.setText("");
    }
}
