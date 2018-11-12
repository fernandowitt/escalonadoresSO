package br.edu.ifsc.escalonador.algoritmos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
	public int alg;
	public void lerArquivo(String endereco){
	    BufferedReader br = null;
	    String linha = "";
	    String divisor = " ";
	    try {

	        br = new BufferedReader(new FileReader(endereco));
	        linha = br.readLine();

	        	String[] algrow = linha.split(divisor);

	            alg = Integer.parseInt(algrow[0]);

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
		switch(alg) {
			case 1:
				FirstComeFirstServed fcfs = new FirstComeFirstServed();
				fcfs.lerArquivo(endereco);
				break;
			case 2:
				ShortestJobFirst sjf = new ShortestJobFirst();
				sjf.lerArquivo(endereco);
				break;
			case 3:
				RoundRobin rr = new RoundRobin();
				rr.lerArquivo(endereco);
				break;
			case 4:
				Priority pr = new Priority();
				pr.lerArquivo(endereco);
				break;
			default:
				System.out.println("Insira um arquivo válido");
		}
	}
}
