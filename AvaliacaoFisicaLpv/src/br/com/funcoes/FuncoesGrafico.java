package br.com.funcoes;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import br.com.academia.modelo.Aluno;
import br.com.academia.modelo.Exercicio;
import br.com.academia.modelo.ExercicioDetalhado;
import br.com.academia.modelo.Ritmo;
import br.com.academia.modelo.dao.AlunoDao;
import br.com.academia.modelo.dao.ExercicioDao;
import br.com.academia.modelo.dao.ExercicioDetalhadoDao;
import br.com.academia.modelo.dao.RitmoDao;

public class FuncoesGrafico {

		 
		/**
		 * Utiliza o nome de um cliente para pesquisar todos os seus exercicios.
		 * 
		 * @param conexao variavel que contem a conexao com o banco de dados.
		 * @param nome do cliente que vai ser utilizado para pesquisar os exercicios.
		 * @return uma lista com todos os exercicios do cliente.
		 */
	    public List<Exercicio> pesquisarExerciciosNomeUsuario(Connection conexao, String nome) {
	    	Aluno aluno = new Aluno();
	    	AlunoDao alunoDao = new AlunoDao(conexao);
	    	ExercicioDao exercicioDao = new ExercicioDao(conexao);
	    	ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(conexao);
	    	
	    	//verifica se o usuario esta cadastrado no banco de dados
	    	aluno = alunoDao.buscaPorNome(nome);
	    	
	    	//Se usuario != null é porque ele é valido
	    	if(aluno != null) {
	    		List<Ritmo> ritmosList = new ArrayList<Ritmo>();
	    		RitmoDao ritmoDao = new RitmoDao(conexao);
	    		List<Exercicio> exerciciosList = new ArrayList<Exercicio>();
	    		
	    		//Recebe os exercicios simples e detalhado do usuario.
	    		exerciciosList.addAll(exercicioDao.pesquisaExerciciosUsuario(aluno.getId()));
	    		exerciciosList.addAll(exercicioDetalhadoDao.pesquisaExercicioDetalhadoUsuario(aluno.getId()));
	    		
	    		// Adiciona os ritmos ao exercicio detalhado
	    		for (Exercicio exercicio : exerciciosList) {
					if(exercicio instanceof ExercicioDetalhado) {
						ritmosList.addAll(ritmoDao.pesquisaRitmo(exercicio.getId()));
						((ExercicioDetalhado)exercicio).setRitmos(ritmosList);
					}
				}
	    		
	    		//Ordena os exercicios da lista pela data
	    		exerciciosList.sort(new Comparator<Exercicio>() {
					@Override
					public int compare(Exercicio o1, Exercicio o2) {
						return o1.getData().compareTo(o2.getData());
					}
				} );
	    	
			return exerciciosList;
	    	}
	    	return null;
	    }
	    
	    /**
	     * Recebe uma data LocalDate e converte para Calendar
	     * 
	     * @param dataDatePicker contem a data q está armazenada no DatePicker.
	     * @return data com a data convertida
	     */
	    public Calendar pegaData(LocalDate dataDatePicker) {
			LocalDate ld = dataDatePicker;
			Calendar data = Calendar.getInstance();
			data.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
			
	    	return data;
	    }
	    
	    /**
	     * Pesquisa um determinado exercicio no meio de todos os exercicios de um determinado usuario.
	     * 
	     * @param conexao contao a conexão para o banco de dados.
	     * @param exerciciosList é um List com todos os exercicios do cliente.
	     * @param dados é um vetor com nome , data, duração e id do exercicio que será pesquisado.
	     * @return Exercicio pesquisado pelo usuario.
	     */
	    public Exercicio pesquisarExercicio(Connection conexao, List<Exercicio> exerciciosList, String[] dados){
			Exercicio exercicio = new Exercicio();
			RitmoDao ritmoDao = new RitmoDao(conexao);
			List<Ritmo> ritmosList = new ArrayList<Ritmo>();

			//Percorre a lista de exercicios
			for (int i = 0; i < exerciciosList.size(); i++) {
				
				//Verifica se o nome do exercicio da lista possui o mesmo nome que o exercicio que o usuario está procurando.
				if(exerciciosList.get(i).getNomeExercicio().equalsIgnoreCase(dados[0])) {
					
					//Verifica se o id do exercicio da lista possui o mesmo id que o exercicio que o usuario está procurando.
					if(exerciciosList.get(i).getId() == Integer.parseInt(dados[3])) {
						
						//Verifica se o exercicio é um ExercicioDetalhado
						if(exerciciosList.get(i) instanceof ExercicioDetalhado) {
							
							//Armazena os ritmos do exercicioDetalhado em uma lista de ritmos auxiliar
							ritmosList.addAll(ritmoDao.pesquisaRitmo(exerciciosList.get(i).getId()));
						}
						
						exercicio = exerciciosList.get(i);

						// Se o exercicio fo um ExercicioDetalhado ele recebera os seus ritmos armazenados na lista de rimtos auxiliar.
						if(exercicio instanceof ExercicioDetalhado) {
							((ExercicioDetalhado)exercicio).setRitmos(ritmosList);
						}
						return exercicio;
					}
				}
			}
	    	return null;
		}
	    
	    
	    /**
	     * 
	     * @param conexao possui a conexao para o banco de dadoos.
	     * @param exerciciosList é a lista de exercicios do usuari.o
	     * @param dados é um vetor com nome , data, duração e id do exercicio que será pesquisado..
	     * @return exercicios que é uma lista de exercicios do usario pesquisado.
	     */
	    public List<Exercicio> pesquisarExercicios(Connection conexao,List<Exercicio> exerciciosList, String[] dados){
			Exercicio exercicio = new Exercicio();
			RitmoDao ritmoDao = new RitmoDao(conexao);
			List<Ritmo> ritmosList = new ArrayList<Ritmo>();
			List<Exercicio> exercicios = new ArrayList<Exercicio>();
			
			//Percorre a lista de exercicios do usuario
			for (int i = 0; i < exerciciosList.size(); i++) {
				
				//Verifica se o nome do exercicio da lista possui o mesmo nome que o exercicio que o usuario está procurando.
				if(exerciciosList.get(i).getNomeExercicio().equalsIgnoreCase(dados[0])) {
						if(exerciciosList.get(i) instanceof ExercicioDetalhado) {
							ritmosList.addAll(ritmoDao.pesquisaRitmo(exerciciosList.get(i).getId()));
						}
						exercicio = exerciciosList.get(i);
						
						if(exercicio instanceof ExercicioDetalhado) {
							((ExercicioDetalhado)exercicio).setRitmos(ritmosList);
						}
						exercicios.add(exercicio);
				}
			}
			return exercicios;
		}
	    
	    /**
	     * Pesquisa os exercicios de um usuario filtrando pelas datas de inicio e de fim
	     * 
	     * @param conexao possui a conexão para o banco de dados.
	     * @param nome é o nome do exercicio que está sendo pesquisado..
	     * @param dataInicio é a data inicial para verificaçao se o exercicio é valido.
	     * @param dataFim é a data final para verificação se o exercicio é valido.
	     * @return exerciciosValidos é uma lista de exercicios validos ou NULL se não possuir nenhum exercicio.
	     */
		public List<Exercicio> exerciciosUsuario(Connection conexao, String nome, Calendar dataInicio, Calendar dataFim){
			Aluno aluno = new Aluno();
	    	AlunoDao alunoDao = new AlunoDao(conexao);
	    	ExercicioDao exercicioDao = new ExercicioDao(conexao);
	    	ExercicioDetalhadoDao exercicioDetalhadoDao = new ExercicioDetalhadoDao(conexao);
	    	
	    	Calendar dataDeInicio = dataInicio;
	  		Calendar dataDeFim = dataFim;
	  		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
	  		
	  		try {
	  			try {
					dataDeInicio.setTime(formataData.parse(formataData.format(dataDeInicio.getTime())));
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	  			try {
					dataDeFim.setTime(formataData.parse(formataData.format(dataDeFim.getTime())));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  		} catch (ParseException e) {
	  			e.printStackTrace();
	  		}
	        	
	        	//usuario recebe null se não for encontado no banco de dados e um objeto Usuario se for encontrado.
	        	aluno = alunoDao.buscaPorNome(nome);
	        	
	        	//Se o usuario for diferente de null recupera seus exercicios armazenados no banco de dados.
	        	if(aluno != null) {
	        		List<Ritmo> ritmosList = new ArrayList<Ritmo>();
	        		RitmoDao ritmoDao = new RitmoDao(conexao);
	        		List<Exercicio> exerciciosList = new ArrayList<Exercicio>();
	        		List<Exercicio> exerciciosValidos = new ArrayList<Exercicio>();
	        		
	        		exerciciosList.addAll(exercicioDao.pesquisaExerciciosUsuario(aluno.getId()));
	        		exerciciosList.addAll(exercicioDetalhadoDao.pesquisaExercicioDetalhadoUsuario(aluno.getId()));
	        		
	        		for (Exercicio exercicio : exerciciosList) {
	    				if(exercicio instanceof ExercicioDetalhado) {
	    					ritmosList.addAll(ritmoDao.pesquisaRitmo(exercicio.getId())); 
	    				}
	    			}
	        		
	        		for (Exercicio exercicio : exerciciosList) {
	        			if(exercicio.getData().compareTo(dataDeInicio) >=0 && exercicio.getData().compareTo(dataDeFim) <= 0) {
	        	      		
	        	      		if(exercicio instanceof ExercicioDetalhado) {
	        	      			ritmosList.addAll(ritmoDao.pesquisaRitmo(exercicio.getId())); 
	        	      			((ExercicioDetalhado)exercicio).setRitmos(ritmosList);
	        	      		}
	        	      		exerciciosValidos.add(exercicio);
	            		}
	  			}
	        		
	        		exerciciosValidos.sort(new Comparator<Exercicio>() {
	    				@Override
	    				public int compare(Exercicio o1, Exercicio o2) {
	    					return o1.getData().compareTo(o2.getData());
	    				}
	    			} );
	    		return exerciciosValidos;
	        	}                    
	        	return null;
		}
}

