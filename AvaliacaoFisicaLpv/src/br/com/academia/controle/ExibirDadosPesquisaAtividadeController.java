package br.com.academia.controle;

import java.sql.Connection;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.dao.AlunoDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExibirDadosPesquisaAtividadeController {
	private Connection con = new FabricaConexao().getConexao();
	private AlunoDao alunoDao = new AlunoDao(con);
    @FXML
    private TextArea textArea;
    
    @FXML
    public void initialize() {
    	textArea.setText("");
    	textArea.setEditable(false);
    	
    	Exercicio exercicio = PesquisarAtividadeController.exercicio;
    	Aluno aluno = new Aluno();
    	
    	aluno = alunoDao.buscaPorId(exercicio.getCodigoAluno());
    	textArea.appendText(aluno.toStringCompleto() + "\n\n");
    	
    	if(exercicio instanceof ExercicioDetalhado) {
    		textArea.appendText(exercicio.toStringCompleto());
    	}
    	else {
    		textArea.appendText(exercicio.toStringCompleto());
    	}
    }
    
    @FXML
    void voltar() {
    	try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/Menu.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    	
    	fecharJanelaAtual();
    }
    
    public void fecharJanelaAtual() {
    	Stage stage = (Stage) textArea.getScene().getWindow();
    	stage.close();
    }
}
