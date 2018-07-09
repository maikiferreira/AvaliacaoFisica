package br.com.academia.controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import br.com.academia.jdbc.FabricaConexao;
import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.Usuario;
import br.com.academia.modelo.dao.AlunoDao;
import br.com.academia.modelo.dao.ArquivoTexto;
import br.com.academia.modelo.dao.ExercicioDao;
import br.com.academia.modelo.dao.ExercicioDetalhadoDao;
import br.com.academia.modelo.dao.RitmoDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MenuController {
	Connection conexao = new FabricaConexao().getConexao();
	
	@FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuBar munuBar;

    @FXML
    private AnchorPane fundoMenu;
    
    @FXML
    private Menu operacoesMenu;

    @FXML
    public void initialize() {
    	Usuario usuario = LoginController.user;
    	if(usuario.getPapel().equals("admin")) {
    		operacoesMenu.setVisible(true);
    	}
    	else {
    		operacoesMenu.setVisible(false);
    	}
    }
    
    @FXML
    void pesquisarAtividade() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/PesquisarAtividade.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void graficoDetalhado() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/ExibirGraficoDetalhado.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void exibirGraficoLinha() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/ExibirGraficoLinha.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void exibirGraficoColuna() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/ExibirGraficoColuna.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void relatorioPorAtividadeTempo() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/RelatorioAtividadeTempo.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void pesquisarAtividadeAluno() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/PesquisarAtividadeAluno.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void pesquisarAluno() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/PesquisarAluno.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void usuarioOperacoes() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/UsuarioOperacoes.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void exercicioOperacoes() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/ExercicioOperacoes.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void alunoOperacoes() {
    	try {
			AnchorPane anchorPaneA = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/AlunoOperacoes.fxml"));
			anchorPane.getChildren().setAll(anchorPaneA);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

    @FXML
    void sair() {
    	abrirLogin();
    	fecharMenu();
    }
    
    public void abrirLogin() {
    	try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void fecharMenu() {
    	Stage stage = (Stage) fundoMenu.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void importarArquivos() {
    	importarArquivo();
    }
    
    /**
     * Obtém um caminho de um diretório, e se for um caminho valido ele importa os caminhos dos diretorios.
     */
    public void importarArquivo() {
    	File path = obterDiretorio();
    	//textArea.setText("");
    	
    	if(path != null) {
    		importarArquivos(path);
    	}
    }
    
	/**
	 * Abre uma janela de seleção de diretorio do sistema operacional.
	 * Se o diretorio default não existir ele vai tentar criar.
	 * 
	 * @return retorna um <code>File</code> com o caminho do diretorio especificado
	 */
    public File obterDiretorio() {
    	DirectoryChooser dc = new DirectoryChooser();
    	
    	File diretorio = new File("./Arquivos");
    	if(!diretorio.exists()) {
    		diretorio.mkdir();
    	}
    	
    	dc.setInitialDirectory(diretorio);
    	
    	File path = dc.showDialog(fundoMenu.getScene().getWindow());
    	
    	return path;
    }
    
    /**
     * Recebe um caminho <code>path</code> e armazena em uma lista todos os arquivos e diretorios encotrados nesse caminho.
     * Percorre a lista e verifica se é um arquivo ou diretorio, se for um arquivo será feito uma chamada recursiva ao metodo abrirArquivo
     * passando um <code>file</code> que é caminho do arquivo que será aberto. Se for um diretorio será feita uma chamada recursivao ao metodo
     * importarArquivos passando um <code>file</code> que é o caminho do diretorio.
     * @param path é a referencia para o diretorio que se deseja importar.
     */
    public void importarArquivos(File path) {
    	File listaArquivos[] = path.listFiles();
    	
    	for (File file : listaArquivos) {
			if(file.isFile()) {
				abrirArquivo(file);
			}
			else {
				importarArquivos(file);
			}
		}
	}
    
    

    /**
     * Recebe um arquivo <code>file</code> como parametro. Abre o arquivo e logo depois invoca o metodo lerArquivoTexto passando como parametro um file.getPath
     * que é o caminho do arquivo que será aberto. E logo depois o metodo arquivoTexto.fechar e invocado para concluir a leitura do arquivo. 
     * @param file é o caminho do arquivo que será aberto
     */
    public void abrirArquivo(File file) {
		ArquivoTexto arquivoTexto = new ArquivoTexto();
		try {
			arquivoTexto.abrir(file.getPath());
			
			lerArquivoTexto(arquivoTexto);
			
			arquivoTexto.fechar();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		}catch (IOException e) {
			// TODO: handle exception
		}
	}

    /**
     * Lê um arquivo texto recebendo o arquivoTexto como parametro;
     * Para recerber o conteudo do arquivo um array de string e inicializado e recebe o retorno do metodo linhasArquivo, 
     * esse método retorna um array de string onde cada posição do array é uma linha do arquivo.
     * Como existe dois tipos de arquivo verifica-se o numéro de linhas total do arquivo, se for maior que 18 o arquivo é do exercicio detalhado, 
     * se for menor que 18 o arquivo é do exercicio simples.
     * Apósa leitura  do arquivo texto os objetos usuario irá conter os dados do usuario e o objeto exercicio irá conter os dados do exercicio.
     * Entao e invocado o metodo adicionarUsuarioNoBancoDeDados(usuario) passando o objeto usuario como parammetro.
     * Depois verifica se o exercicio e uma instancia de exercicioDetalhado ou não, se for o metodo 
     * adicionarExercicioDetalhadoNoBancoDeDados((ExercicioDetalhado)exercicio, usuario) é invocado para armazenar os dados do exercicio no banco de dados
     * passando como parametro os objetos exercicio e usuario se não for verifica se o exercicio e uma instancia de exercicio  
     * se for o metodo adicionarExercicioNoBancodeDados(exercicio, usuario) é invocado para adicionar os dados do exercicio no 
     * banco de dados passando como parametros o exercicio e o usuario.
     * 
     * 
     * @param arquivoTexto é o arquivo texto que se deseja ler.
     */
    public void lerArquivoTexto(ArquivoTexto arquivoTexto) {
		String linhas[] = arquivoTexto.linhasArquivos();
		//String dados = arquivoTexto.ler();
		
		Aluno aluno = new Aluno();
		Exercicio exercicio;
		Ritmo ritmo;
		
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formataHoraMinuto = new SimpleDateFormat("HH:mm");
		SimpleDateFormat formataHoraMinutoSegundo = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formataMinutoSegundo = new SimpleDateFormat("mm:ss");
		if(linhas.length > 20) {
			exercicio = new ExercicioDetalhado();
		}
		else {
			exercicio = new Exercicio();
		}
		
		for (String linha : linhas) {
			if(linha.matches(".+:.+")) {
				if(linha.contains("Exercício")) {
					exercicio.setNomeExercicio(linha.split(":")[1].trim());
				}
				else {
					if(linha.contains("Nome")) {
						aluno.setNome(linha.split(":")[1].trim());
					}
					else {
						if(linha.contains("Sexo")) {
							aluno.setSexo(linha.split(":")[1]);
						}
						else {
							if(linha.contains("Altura")) {
								aluno.setAltura(Integer.parseInt(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace(",", "").trim()));
							}
							else {
								if(linha.contains("Peso")) {
									aluno.setPeso(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace(",", ".").trim()));
								}
								else {
									if(linha.contains("Data de nascimento")) {
										
										Calendar cal = Calendar.getInstance();
										try {
											cal.setTime(formataData.parse(linha.split(":")[1]));
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										aluno.setDataNascimento(cal);
									}
									else {
										if(linha.contains("E-mail")) {
											aluno.setEmail(linha.split(":")[1]);
										}
										else {
											if(linha.contains("Cpf")) {
												aluno.setCpf(linha.split(":")[1]);
											}
											else {
												if(linha.contains("Whatsapp")) {
													aluno.setWhatsapp(linha.split(":")[1]);
												}
												else {
													if(linha.contains("Data")) {
														Calendar cal = Calendar.getInstance();
														try {
															cal.setTime(formataData.parse(linha.split(":")[1]));
														} catch (ParseException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														exercicio.setData(cal);
													}
													else {
														if(linha.contains("Tempo")) {
															Calendar cal = Calendar.getInstance();
															Calendar calx = Calendar.getInstance();
															
															//String tempos = linha.split(":")[1];
															try {
																cal.setTime(formataHoraMinuto.parse(linha.split("-")[0].substring(linha.split("-")[0].indexOf(":")+1, linha.split("-")[0].length()).trim()));
																calx.setTime(formataHoraMinuto.parse(linha.split("-")[1].trim()));
															} catch (ParseException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
															exercicio.setTempoInicio(cal);
															exercicio.setTempoFinal(calx);
														}
														else {
															if(linha.contains("Duração")) {
																Calendar cal = Calendar.getInstance();
																
																try {
																	cal.setTime(formataHoraMinutoSegundo.parse(linha.substring(linha.indexOf(":" )+1)));
																} catch (ParseException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																}
																exercicio.setDuracao(cal);
															}
															else {
																if(linha.contains("Distância")) {
																	exercicio.setDistancia(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace(",",".").trim()));
																}
																else {
																	if(linha.contains("Calorias perdidas")) {
																		exercicio.setCaloriasPerdidas(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" "))));
																	}
																	else {
																		if(linha.contains("Passos")) {
																			exercicio.setPassos(Integer.parseInt(linha.split(":")[1].replace(".","").trim()));
																		}
																		else {
																			if(exercicio instanceof ExercicioDetalhado) {
																				if(linha.contains("Velocidade média")) {
																					((ExercicioDetalhado) exercicio).setVelocidadeMedia(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace(",",".").trim()));
																				}
																				else {
																					if(linha.contains("Velocidade máxima")) {
																						((ExercicioDetalhado) exercicio).setVelocidadeMaxima(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace(",",".").trim()));
																					}
																					else {
																						if(linha.contains("Ritmo médio")) {
																							Calendar cal = Calendar.getInstance();
																							
																							try {
																								cal.setTime(formataMinutoSegundo.parse(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace("\'", ":").replace("\"", "").trim()));
																							} catch (ParseException e) {
																								// TODO Auto-generated catch block
																								e.printStackTrace();
																							}
																							
																							((ExercicioDetalhado) exercicio).setRitmoMedia(cal);
																						}
																						else {
																							if(linha.contains("Ritmo máximo")) {
																								Calendar cal = Calendar.getInstance();
																								
																								try {
																									cal.setTime(formataMinutoSegundo.parse(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" ")).replace("\'", ":").replace("\"", "").trim()));
																								} catch (ParseException e) {
																									// TODO Auto-generated catch block
																									e.printStackTrace();
																								}
																								((ExercicioDetalhado) exercicio).setRitmoMaximo(cal);
																							}
																							else {
																								if(linha.contains("Menor elevação")) {
																									((ExercicioDetalhado) exercicio).setMenorElevacao(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" "))));
																								}
																								else {
																									if(linha.contains("Maior elevação")) {
																										((ExercicioDetalhado) exercicio).setMaiorElevacao(Float.parseFloat(linha.split(":")[1].substring(0, linha.split(":")[1].lastIndexOf(" "))));
																									}
																									else {
																										if(linha.matches("[0-9].+[0-9]{2}.[0-9]{2}.")) {
																											ritmo = new Ritmo();
																											
																											String rit[] = linha.split(":");
																											
																											if(rit[0].contains(",")) {
																												ritmo.setDistancia(Float.parseFloat(linha.split(" ")[0].replace(",", ".").trim()));
																											}
																											else {
																												ritmo.setDistancia(Float.parseFloat(linha.split(" ")[0]));
																											}
																											
																											Calendar cal = Calendar.getInstance();
																											
																											try {
																												cal.setTime(formataMinutoSegundo.parse(linha.split(":")[1].replace("’", ":").replace("”", "").trim()));
																											} catch (ParseException e) {
																												// TODO Auto-generated catch block
																												e.printStackTrace();
																											}
																											ritmo.setRitmo(cal);
																											((ExercicioDetalhado) exercicio).adicionarRitmo(ritmo);
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
																
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		}
		adicionarUsuarioNoBancoDeDados(aluno);
		
		if(exercicio instanceof ExercicioDetalhado) {
			adicionarExercicioDetalhadoNoBancoDeDados((ExercicioDetalhado)exercicio, aluno);
			
			
		}
		else {
			if(exercicio instanceof Exercicio) {
				adicionarExercicioNoBancodeDados(exercicio, aluno);
			}
		}
	}
	
    /**
     * Adiciona um objeto ritmo no banco de dados recebendo como parametros os objetos exercicio e o usuario.
     * Uma ArrayList listaDeRitmos é criado para receber os ritmos que esta contido dentro do objeto exercicio, com isso
     * verifica se o usuario esta cadastrado no banco de dados invocando o metodo usuarioDao.buscaPorEmail(usuario.getEmail() passando como parametro o 
     * email do usuario, se retornar null é porque o usuario não está cadastrado, se estiver um objeto Usuario é retornado.  
     * Depois verifica se o objeto exercicio já esta cadastrado no banco de dados. Esta verificação e feita através do metodo
     * exercicioDetalhadoDao.pesquisar(idUsuario, exercicio) que passa um long que e o  id do usuario e o um objeto exercicio, se o objeto exer
     * receber null é porque o exercicio não está cadastrado no banco de dados, se for diferente de null vai percorrer a listaDeRitmos e adicionando
     * cada ritmo no banco de dados atraves do metodo ritmoDao.insere(listaDeRitmos.get(i), exer.getId()) que passa o objeto ritmo e o id do exercicio
     * ao qual o ritmo pertence..
     * 
     * @param exercicio é o objeto quem contem os ritmos que serao salvos no banco de dados. 
     * @param usuario é o objeto que ao qual o objeto exercicio pertence.
     */
    public void adicionarRitmosNoBancoDEDados(ExercicioDetalhado exercicio, Aluno aluno) {
		RitmoDao ritmoDao  = new RitmoDao(conexao);
		AlunoDao alunoDao = new AlunoDao(conexao);
		ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(conexao);
		
		List<Ritmo> listaDeRitmos = exercicio.listaDeRitmos();
		
		Aluno alu = alunoDao.buscaPorEmail(aluno.getEmail());
		
		if(alu != null) {
			long idUsuario = alu.getId();
			
			Exercicio exer = exercicioDetalhadoDao.pesquisar(idUsuario, exercicio);
			
			
			if(exer != null) {
				for (int i = 0; i < listaDeRitmos.size(); i++) {
					ritmoDao.insere(listaDeRitmos.get(i), exer.getId());
				}
			}
		}
	}
	
    /**
     * Para adicionar um exercicio detalhado no banco de dados primeiro o objeto user recebe o retorno do metodo 
     * usuarioDetalhadodao.buscaPorEmail(usuario.getEmail()) que passa como parametro o email do usuario, esse metodo serve para pegar 
     * o id do usuario no banco de dados, se receber null é porque  o usuario não está cadastrado no banco de dados.
     * Se o user for diferente de null o idUsuario vai receber o  id do usuario passado como parametro.
     * Entao verifica se o exercicio detalhado está cadastrado na tabela de exercicioDetalhado do banco de dados atraves do metodo   
     * exercicioDetalhadoDao.pesquisaExercicioNaTabelaExercicioDetalhado(idUsuario, exercicio.getData(), exercicio.getTempoInicio(), exercicio.getTempoFinal()
     * que recebe null se não estiver cadastrado, e depois verifica se o exer que verificou se o exercicio ja existia na tabela de exercicio 
     * do banco de dados atraves do metodo exercicioDao.pesquisar(idUsuario, exercicio) que retorna null se não existir.
     * Se o exercicio não estiver cadastrado em nenhuma das duas tabelas do banco de dados(tabela exercicio e tabela exercicio_detalhado) o metodo
     * exercicioDetalhadoDao.insere(exercicio, idUsuario) é invocado passando como parametros o objeto exercicio e o idUsuario para inserir o 
     * exercicio detalhado no banco de dados.
     * @param exercicio é o obejeto que será  salvo no banco de dados.
     * @param usuario é o obejeto que será  salvo no banco de dados.
     */
    public void adicionarExercicioDetalhadoNoBancoDeDados(ExercicioDetalhado exercicio, Aluno aluno) {
		ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(conexao);
		ExercicioDao exercicioDao = new ExercicioDao(conexao);
		AlunoDao alunoDao = new AlunoDao(conexao);
		
		Aluno alu = alunoDao.buscaPorEmail(aluno.getEmail());
		
		if(alu != null) {
			long idAluno = alu.getId();
			ExercicioDetalhado exer =  exercicioDetalhadoDao.pesquisar(idAluno, exercicio);
			
			if(exer == null) {
				if(exercicioDao.pesquisaExercicioDetalhadoNaTabelaExercicio(idAluno, exercicio.getData(), exercicio.getTempoInicio(), exercicio.getTempoFinal()) == null) {
					exercicioDetalhadoDao.insere(exercicio, idAluno);
					adicionarRitmosNoBancoDEDados((ExercicioDetalhado)exercicio, aluno);
				}
			}
		}
	}
	
    /**
     * Para adicionar um exercicio no banco de dados primeiro o objeto user recebe o retorno do metodousuariodao.buscaPorEmail(usuario.getEmail()
     * que passa como parametro o email do usuario, esse metodo serve para pegar o id do usuario no banco de dados, se receber null é porque  o usuario não esta
     * cadastrado no banco de dados.
     * Se o user for diferente de null o idUsuario vai receber o  id do usuario passado como parametro.
     * Entao verifica se o exercicio esta cadastrado na tabela de exercicioDetalhado do banco de dados atraves do metodo   
     * exercicioDetalhadoDao.pesquisaExercicioNaTabelaExercicioDetalhado(idUsuario, exercicio.getData(), exercicio.getTempoInicio(), exercicio.getTempoFinal()
     * que recebe null se não estiver cadastrado, e depois verifica se o exer que verificou se o exercicio ja existia na tabela de exercicio 
     * do banco de dados atraves do metodo exercicioDao.pesquisar(idUsuario, exercicio) que retorna null se não existir.
     * Se o exercicio não estiver cadastrado em nenhuma das duas tabelas do banco de dados(tabela exercicio e tabela exercicio_detalhado) o mmetodo
     * exercicioDao.insere(exercicio, idUsuario) é invocado passando como parametros o objeto exercicio e o idUsuario para inserir o exercicio 
     * no banco de dados.
     * 
     * @param exercicio é o obejeto que será  salvo no banco de dados.
     * @param usuario é o obejeto que será  salvo no banco de dados.
     */
    public void adicionarExercicioNoBancodeDados(Exercicio exercicio, Aluno aluno) {
		ExercicioDao exercicioDao = new ExercicioDao(conexao);
		ExercicioDetalhadoDao exercicioDetalhadoDao  = new ExercicioDetalhadoDao(conexao);
		AlunoDao alunoDao = new AlunoDao(conexao);
		
		Aluno alu = alunoDao.buscaPorEmail(aluno.getEmail());
		if(alu != null) {
			long idAluno = alu.getId();
			
			
			Exercicio exer = exercicioDao.pesquisar(idAluno, exercicio);
			
			

			if(exercicioDetalhadoDao.pesquisaExercicioNaTabelaExercicioDetalhado(idAluno, exercicio.getData(), exercicio.getTempoInicio(), exercicio.getTempoFinal()) == null) {
				if(exer == null) {
					exercicioDao.insere(exercicio, idAluno);
				}
			}
		}
	}
	
    
    public void adicionarUsuarioNoBancoDeDados(Aluno aluno) {
    	AlunoDao alunoDao = new AlunoDao(conexao);
		
		if(alunoDao.buscaPorEmail(aluno.getEmail()) == null) {
			alunoDao.insere(aluno);
		}
	}
}
