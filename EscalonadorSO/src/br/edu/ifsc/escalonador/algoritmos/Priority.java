package br.edu.ifsc.escalonador.algoritmos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import br.edu.ifsc.escalonador.mainpackage.Node;

public class Priority {
	public /*static final*/ LinkedList<Node> pr = new LinkedList<Node>();
	public LinkedList<Node> prQueue = new LinkedList<Node>();
	public Node aux = new Node();
	public int high;
	public int low;
	
	
	public void lerArquivo(String endereco) {
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = " ";
	    String[] prioridades;
	    try {
	    	
	        br = new BufferedReader(new FileReader(endereco));
	        linha = br.readLine();
	        linha = br.readLine();
	        prioridades = linha.split(divisor);
	        high = Integer.parseInt(prioridades[0]);
	        low = Integer.parseInt(prioridades[1]);
	        while ((linha = br.readLine()) != null) {

	        	String[] dados = linha.split(divisor);
	        	System.out.println(dados[0] + " " + dados[1] + " " + dados[2] + " " + dados[3]);
	        	aux = new Node();
	        	aux.setIndice(Integer.parseInt(dados[0]));
	        	aux.setTempoChegada(Integer.parseInt(dados[1]));
	        	aux.setTempoCPU(Integer.parseInt(dados[2]));
	        	aux.setPrioridade(Integer.parseInt(dados[3]));
	        	aux.setTempoRestante(aux.getTempoCPU());
	        	pr.add(aux);
	        	
	        }
	        executa();

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public void executa() throws IOException {
//		FileWriter arq = new FileWriter("/media/alunos/WITT/SO/prResultados.txt");
		FileWriter arq = new FileWriter("E:\\SO\\prResultados.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf("Prioridade máxima = " + high + " || Prioridade mínima = " + low + "%n");
		gravarArq.printf("===================================================================================================================================%n");
		gravarArq.printf("|| Id do Processo || Tempo de chegada || Tempo de CPU || Tempo de Conclusão || Tempo em Sistema || Tempo de Espera || Prioridade ||%n");
		gravarArq.printf("===================================================================================================================================%n");
		String linhaGravar = "";
		
//		FileWriter arqGantt = new FileWriter("/media/alunos/WITT/SO/prGantts.txt");
		FileWriter arqGantt = new FileWriter("E:\\SO\\prGantt.txt");
		PrintWriter gravarArqGantt = new PrintWriter(arqGantt);
		String linhaGravarGantt = "";
		
		int tempo = 0;
		int cont;
		int contPR;
		Node escalonar;
		while (!pr.isEmpty()){
			escalonar = null;
			for(Node nd : pr) {
				if(!nd.getRemoveFlag()) {
					prQueue.add(nd);
				}
			}
			if(prQueue.isEmpty()) {
				pr.clear();
			}
			for(Node nd : prQueue) {
				if(high<=low) {
					if(nd.getTempoChegada()<=tempo) {
						if(escalonar==null) {
							escalonar=nd;
						}else {
							if(nd.getPrioridade()<escalonar.getPrioridade()) {
								escalonar=nd;
							}else if(nd.getPrioridade()==escalonar.getPrioridade() && nd.getIndice()<escalonar.getIndice()) {
								escalonar=nd;
							}
						}
						//System.out.println(nd.getIndice());
					}
				}else if(high>low) {
					if(nd.getTempoChegada()<=tempo) {
						if(escalonar==null) {
							escalonar=nd;
						}else {
							if(nd.getPrioridade()>escalonar.getPrioridade()) {
								escalonar=nd;
							}else if(nd.getPrioridade()==escalonar.getPrioridade() && nd.getIndice()<escalonar.getIndice()) {
								escalonar=nd;
							}
						}
						//System.out.println(nd.getIndice());
					}
				}
			}
			if(escalonar!=null) {
				linhaGravarGantt = "|| Tempo: " + tempo  + "; Processo: " + escalonar.getIndice() + "; até tempo: ";
				gravarArqGantt.printf(linhaGravarGantt);
				escalonar.setTempoRestante(escalonar.getTempoRestante()-1);
				tempo++;
				linhaGravarGantt = "" + tempo + " ";
				gravarArqGantt.printf(linhaGravarGantt);
				if(escalonar.getTempoRestante()==0) {
					escalonar.setTempoConclusao(tempo);
					escalonar.setTempoSistema(escalonar.getTempoConclusao()-escalonar.getTempoChegada());
					escalonar.setTempoEspera(escalonar.getTempoSistema()-escalonar.getTempoCPU());
					
					linhaGravar = "|| " + escalonar.getIndice() + " || " + escalonar.getTempoChegada() + " || " + escalonar.getTempoCPU() + " || " + escalonar.getTempoConclusao() + " || " + escalonar.getTempoSistema() + " || " + escalonar.getTempoEspera() + " || " + escalonar.getPrioridade() + " ||%n";
					gravarArq.printf(linhaGravar);
					escalonar.setRemoveFlag(true);
				}
			}else {
				linhaGravarGantt = "|| Tempo: " + tempo  + "; Sem processo ";
				gravarArqGantt.printf(linhaGravarGantt);
				tempo++;
			}
			prQueue.clear();
		}
		arq.close();
		linhaGravarGantt = "||";
		gravarArqGantt.printf(linhaGravarGantt);
		arqGantt.close();
	}
}
