package br.com.academia.modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;


	/**
	 * Fornece v�rios m�todos para manipular um arquivo texto em disco.
	 * <p>
	 * Como � um arquivo texto somente uma opera��o pode ser realizada por vez: escrita ou leitura.
	 * </p>
	 * <p> 
	 * Se for leitura o arquivo deve ser aberto usando o m�todo <code>abrir</code> e ap�s a leitura 
	 * deve ser fechado usando o m�todo <code>fechar</code>. 
	 * </p>
	 * <p>
	 * Se for escrita o arquivo deve ser criado usando o m�todo <code>criar</code> e ap�s a escrita deve ser fechado usando 
	 * o m�todo <code>fechar</code>.
	 * </p>
	 *   
	 * @author Maiki Ferreira
	 * 
	 * @version 0.1
	 */
public class ArquivoTexto {
	private Scanner inputScanner; // O conte�do do arquivo texto ser� lido usando um objeto Scanner.
	private FileInputStream fileInputStream; // Representa o arquivo texto como um arquivo de bytes. 
	private Formatter fileOutputFormatter; // O conte�do do arquivo texto ser� escrito usando um objeto Formatter.
	  
	/** 
	 * Abre um arquivo texto armazenado em disco somente para leitura. A escrita n�o � permitida.
	 * 
	 * @param nomeArquivo nome do arquivo a ser aberto.
	 * 
	 * @throws FileNotFoundException se o nome do arquivo n�o for encontrado.
	 */
	  public void abrir(String nomeArquivo) throws FileNotFoundException {
		  // Abre um arquivo de bytes para realizar a entrada de dados. 
		  fileInputStream = new FileInputStream(nomeArquivo);
		        
		  // O arquivo ser� lido como um arquivo de texto puro usando a classe java.util.Scanner. 
		  inputScanner = new Scanner(fileInputStream);
	  } 

	  /** 
	   * Cria um arquivo texto em disco. Se o arquivo j� existe o seu conte�do ser� apagado. O arquivo criado �
	   * s� para escrita. A leitura n�o � peermitida.
	   * 
	   * @param nomeArquivo nome do arquivo a ser criado.
	   * 
	   * @throws FileNotFoundException se o nome do arquivo n�o for encontrado.
	   */
	  public void criar(String nomeArquivo) throws FileNotFoundException {
		  // Cria um arquivo texto em disco.
		  fileOutputFormatter = new Formatter(nomeArquivo);
	  } 

	  /** 
	   * Escreve no arquivo texto o conte�do do objeto <code>String</code> armazenado no
       * par�metro conteudo. 
	   * 
	   * @param conteudo conte�do a ser escrito no arquivo texto.
	   */
	  public void escrever(String conteudo) {
		  // Escreve o conte�do no arquivo texto.
		  fileOutputFormatter.format("%s\n", conteudo);
	  } 
	  
	  /** 
	   * L� o conte�do completo do arquivo texto.
	   * 
	   * As exce��es disparadas pelo m�todo <code>ler</code> tem o seu ponto de disparo nos 
	   * m�todos <code>hasNextLine()</code> e <code>nextLine()</code> 
	   * da classe <code>java.util.Scanner</code>. Lembre que o arquivo texto � lido usando 
	   * um objeto Scanner. Estas exce��es s�o geradas pelos m�todos da classe Scanner. 
	   * 
	   * @return um <code>String</code> com o conte�do lido do arquivo texto.
	   *
	   * @throws IllegalStateException ocorre se o arquivo estiver fechado.  
	   */
	  public String ler() throws IllegalStateException {
		  String conteudo = "";
		  
			// Lê o conteúdo completo do arquivo.
		  while (inputScanner.hasNextLine()) 
			  conteudo += inputScanner.nextLine() + "\n";
		  
		  return conteudo; 
	  } 
	  
	  public String[] linhasArquivos() throws IllegalStateException{
		  ArrayList<String> linhas = new ArrayList<>();
		  
		// Lê o conteúdo completo do arquivo.
		  while (inputScanner.hasNextLine()) 
			  linhas.add(inputScanner.nextLine());
		  
		  return linhas.toArray(new String[0]);
	  }
	  
	  /**
	   * Fecha os arquivos que foram criados para manipula��o do arquivo texto.
	   * 
	   * @throws IOException se ocorrer algum erro de E/S ao tentar fechar o arquivo.
	   */
	  public void fechar() throws IOException {
		  if (fileInputStream != null) fileInputStream.close();
		  if (inputScanner != null) inputScanner.close();
		  if (fileOutputFormatter != null) fileOutputFormatter.close();
	  } // fecharArquivo()
} // class ArquivoTexto