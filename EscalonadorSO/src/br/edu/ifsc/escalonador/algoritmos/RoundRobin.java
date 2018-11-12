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

public class RoundRobin {
	public /*static final*/ LinkedList<Node> rr = new LinkedList<Node>();
	public LinkedList<Node> rrQueue = new LinkedList<Node>();
	public Node aux = new Node();
	public int quantum;
	
	
	public void lerArquivo(String endereco) {
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = " ";
	    try {
	    	
	        br = new BufferedReader(new FileReader(endereco));
	        linha = br.readLine();
	        quantum = Integer.parseInt(br.readLine());
	        while ((linha = br.readLine()) != null) {

	        	String[] dados = linha.split(divisor);
	        	System.out.println(dados[0] + " " + dados[1] + " " + dados[2]);
	        	aux = new Node();
	        	aux.setIndice(Integer.parseInt(dados[0]));
	        	aux.setTempoChegada(Integer.parseInt(dados[1]));
	        	aux.setTempoCPU(Integer.parseInt(dados[2]));
	        	aux.setPrioridade(0);
	        	aux.setTempoRestante(aux.getTempoCPU());
	        	rr.add(aux);
	        	
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
//		FileWriter arq = new FileWriter("/media/alunos/WITT/SO/rrResultados.txt");
		FileWriter arq = new FileWriter("E:\\SO\\rrResultados.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf("Quantum = " + quantum + "%n");
		gravarArq.printf("=====================================================================================================================%n");
		gravarArq.printf("|| Id do Processo || Tempo de chegada || Tempo de CPU || Tempo de Conclusão || Tempo em Sistema || Tempo de Espera ||%n");
		gravarArq.printf("=====================================================================================================================%n");
		String linhaGravar = "";
		
//		FileWriter arqGantt = new FileWriter("/media/alunos/WITT/SO/rrGantts.txt");
		FileWriter arqGantt = new FileWriter("E:\\SO\\rrGantt.txt");
		PrintWriter gravarArqGantt = new PrintWriter(arqGantt);
		String linhaGravarGantt = "";
		
		int tempo = 0;
		int cont;
		int contRR;
		Node escalonar;
		while (!rr.isEmpty()){
			escalonar = null;
			for(Node nd : rr) {
				if(!nd.getRemoveFlag()) {
					rrQueue.add(nd);
				}
			}
			if(rrQueue.isEmpty()) {
				rr.clear();
			}
			for(Node nd : rrQueue) {

				while(escalonar==null) {
					
					if(nd.getTempoChegada()<=tempo) {
						escalonar=nd;
						//System.out.println(nd.getIndice());
					}else {
						linhaGravarGantt = "|| Tempo: " + tempo  + "; Sem processo ";
						gravarArqGantt.printf(linhaGravarGantt);
						tempo++;
					}
				}
				escalonar=nd;
				if(escalonar!=null) {
					cont = 0;
					linhaGravarGantt = "|| Tempo: " + tempo  + "; Processo: " + escalonar.getIndice() + "; até tempo: ";
					gravarArqGantt.printf(linhaGravarGantt);
					while(cont < quantum && escalonar.getTempoRestante() != 0) {
						escalonar.setTempoRestante(escalonar.getTempoRestante()-1);
						cont++;
						tempo++;
					}
					linhaGravarGantt = "" + tempo + " ";
					gravarArqGantt.printf(linhaGravarGantt);
					if(escalonar.getTempoRestante()==0) {
						escalonar.setTempoConclusao(tempo);
						escalonar.setTempoSistema(escalonar.getTempoConclusao()-escalonar.getTempoChegada());
						escalonar.setTempoEspera(escalonar.getTempoSistema()-escalonar.getTempoCPU());
						
						linhaGravar = "|| " + escalonar.getIndice() + " || " + escalonar.getTempoChegada() + " || " + escalonar.getTempoCPU() + " || " + escalonar.getTempoConclusao() + " || " + escalonar.getTempoSistema() + " || " + escalonar.getTempoEspera() + " ||%n";
						gravarArq.printf(linhaGravar);
						escalonar.setRemoveFlag(true);
					}
				}
			}
			rrQueue.clear();
		}
		arq.close();
		linhaGravarGantt = "||";
		gravarArqGantt.printf(linhaGravarGantt);
		arqGantt.close();
	}
}
