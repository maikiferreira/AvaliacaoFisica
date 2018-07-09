package br.com.academia.controle;

import java.sql.Connection;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.dao.AlunoDao;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PesquisarAlunoController {
	private Connection con = new FabricaConexao().getConexao();
	private AlunoDao alunoDao = new AlunoDao(con);
	
	@FXML
    private TextField nomeTextField;

    @FXML
    private AnchorPane pesquisarAlunoAnchorPane;

    @FXML
    private TextArea textArea;

    @FXML
    void pesquisar() {
    	Aluno aluno = alunoDao.buscaPorNome(nomeTextField.getText());
    	
    	if(aluno != null) {
    		textArea.setText(aluno.toStringCompleto());
    	}
    }
}
