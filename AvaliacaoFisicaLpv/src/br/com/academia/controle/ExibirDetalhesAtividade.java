package br.com.academia.controle;

import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExibirDetalhesAtividade {
	@FXML
    private AnchorPane exibirDetalhesAtividadeAnchorPane;

    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() {
    	textArea.setText("");
    	textArea.setEditable(false);
    	
    	Exercicio exercicio = PesquisarAtividadeAlunoController.exercicio;
    	
    	if(exercicio instanceof ExercicioDetalhado) {
    		textArea.setText(exercicio.toStringCompleto());
    	}
    	else{
    		textArea.setText(exercicio.toStringCompleto());
    	}
    }
    
    @FXML
    void voltar() {
    	voltarTela();
    	fecharTelaAtual();
    }
    
    public void fecharTelaAtual() {
    	Stage stage = (Stage) textArea.getScene().getWindow();
    	stage.close();
    }
    
    public void voltarTela() {
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
}
