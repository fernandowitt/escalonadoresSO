package br.edu.ifsc.escalonador.mainpackage;

public class Node {
	private int indice;
	private int tempoCPU;
	private int tempoChegada;
	private int prioridade;
	private int tempoEspera;
	private int tempoSistema;
	private int tempoConclusao;
	private boolean removeFlag;
	private int tempoRestante;
	
	public Node(int indice, int tempoCPU, int tempoChegada, int prioridade) {
		super();
		this.indice = indice;
		this.tempoCPU = tempoCPU;
		this.tempoChegada = tempoChegada;
		this.prioridade = prioridade;
		this.removeFlag = false;
		this.tempoRestante = tempoCPU;
	}
	
	public Node(int indice, int tempoCPU, int tempoChegada) {
		super();
		this.indice = indice;
		this.tempoCPU = tempoCPU;
		this.tempoChegada = tempoChegada;
		this.prioridade = 0;
		this.removeFlag = false;
		this.tempoRestante = tempoCPU;
	}
	
	public Node() {
	}
	
	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getTempoCPU() {
		return tempoCPU;
	}

	public void setTempoCPU(int tempoCPU) {
		this.tempoCPU = tempoCPU;
	}

	public int getTempoChegada() {
		return tempoChegada;
	}

	public void setTempoChegada(int tempoChegada) {
		this.tempoChegada = tempoChegada;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public int getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	public int getTempoSistema() {
		return tempoSistema;
	}

	public void setTempoSistema(int tempoSistema) {
		this.tempoSistema = tempoSistema;
	}

	public int getTempoConclusao() {
		return tempoConclusao;
	}

	public void setTempoConclusao(int tempoConclusao) {
		this.tempoConclusao = tempoConclusao;
	}
	
	public boolean getRemoveFlag() {
		return removeFlag;
	}
	
	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}

	public int getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(int tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	
	
}
