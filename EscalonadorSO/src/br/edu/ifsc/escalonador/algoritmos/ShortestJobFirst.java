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

public class ShortestJobFirst {
	public static final LinkedList<Node> sjf = new LinkedList<Node>();
	public Node aux = new Node();
	
	
	public void lerArquivo(String endereco) {
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = " ";
	    try {
	    	
	        br = new BufferedReader(new FileReader(endereco));
	        linha = br.readLine();
	        while ((linha = br.readLine()) != null) {

	        	String[] dados = linha.split(divisor);
	        	System.out.println(dados[0] + " " + dados[1] + " " + dados[2]);
	        	aux = new Node();
	        	aux.setIndice(Integer.parseInt(dados[0]));
	        	aux.setTempoChegada(Integer.parseInt(dados[1]));
	        	aux.setTempoCPU(Integer.parseInt(dados[2]));
	        	aux.setPrioridade(0);
	        	sjf.add(aux);
	        	
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
//		FileWriter arq = new FileWriter("/media/alunos/WITT/SO/sjfResultados.txt");
		FileWriter arq = new FileWriter("E:\\SO\\sjfResultados.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf("|| Id do Processo || Tempo de chegada || Tempo de CPU || Tempo de Conclusão || Tempo em Sistema || Tempo de Espera ||%n");
		gravarArq.printf("=====================================================================================================================%n");
		String linhaGravar = "";
		
//		FileWriter arqGantt = new FileWriter("/media/alunos/WITT/SO/sjfGantts.txt");
		FileWriter arqGantt = new FileWriter("E:\\SO\\sjfGantt.txt");
		PrintWriter gravarArqGantt = new PrintWriter(arqGantt);
		String linhaGravarGantt = "";
		
		int tempo = 0;
		Node escalonar;
		while (!sjf.isEmpty()){
			escalonar = null;
			for(Node nd : sjf) {
				if(nd.getTempoChegada()<=tempo) {
					if(escalonar==null) {
						escalonar=nd;
					}else {
						if(nd.getTempoCPU()<escalonar.getTempoCPU()) {
							escalonar=nd;
						}else if(nd.getTempoCPU()==escalonar.getTempoCPU() && nd.getIndice()<escalonar.getIndice()) {
							escalonar=nd;
						}
					}
					//System.out.println(nd.getIndice());
				}
			}
			if(escalonar==null) {
				linhaGravarGantt = "|| Tempo: " + tempo  + "; Sem processo ";
				gravarArqGantt.printf(linhaGravarGantt);
				tempo++;
			}else {
				linhaGravarGantt = "|| Tempo: " + tempo  + "; Processo: " + escalonar.getIndice() + "; até tempo: ";
				gravarArqGantt.printf(linhaGravarGantt);
				tempo += escalonar.getTempoCPU();
				escalonar.setTempoConclusao(tempo);
				linhaGravarGantt = "" + escalonar.getTempoConclusao() + " ";
				gravarArqGantt.printf(linhaGravarGantt);
				escalonar.setTempoSistema(escalonar.getTempoConclusao()-escalonar.getTempoChegada());
				escalonar.setTempoEspera(escalonar.getTempoSistema()-escalonar.getTempoCPU());
				
				linhaGravar = "|| " + escalonar.getIndice() + " || " + escalonar.getTempoChegada() + " || " + escalonar.getTempoCPU() + " || " + escalonar.getTempoConclusao() + " || " + escalonar.getTempoSistema() + " || " + escalonar.getTempoEspera() + " ||%n";
				gravarArq.printf(linhaGravar);
				sjf.remove(escalonar);
			}
			
		}
		arq.close();
		linhaGravarGantt = "||";
		gravarArqGantt.printf(linhaGravarGantt);
		arqGantt.close();
	}
}