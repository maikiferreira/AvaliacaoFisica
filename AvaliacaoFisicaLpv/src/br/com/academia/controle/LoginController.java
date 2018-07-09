package br.com.academia.controle;

import java.sql.Connection;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Usuario;
import br.com.academia.modelo.dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
	Connection con = new FabricaConexao().getConexao();
	public static Usuario user = new Usuario();
	
	@FXML
    private PasswordField senhaPasswordField;

    @FXML
    private Label mensagemlabel;

    @FXML
    private AnchorPane fundoLogin;

    @FXML
    private Button logarButton;

    @FXML
    private TextField loginTextField;

    @FXML
    public void initialize() {
    	mensagemlabel.setText("");
    }
    
    @FXML
    public void logar() {
    	UsuarioDao usuarioDao = new UsuarioDao(con);
    	
   		Usuario usuario = usuarioDao.buscaPorNomeUsuario(loginTextField.getText());
    	
    	
    	mensagemlabel.setText("");
    	
		if(usuario != null) {
    		if(usuario.getSenha().equals(senhaPasswordField.getText())) {
    			user = usuario;
    			abrirMenu();
    			fecharLogin();
    		}
    		else {
    			mensagemlabel.setText("Senha Incorreta!");
    		}
    	}
    	else {
    		mensagemlabel.setText("Usuario não encontrado!");
    	}
	}
    
    public void abrirMenu() {
    	try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void fecharLogin() {
    	Stage stage = (Stage) logarButton.getScene().getWindow();
    	stage.close();
    }

}
