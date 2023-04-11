package br.com.luca.cm.modelo;
import java.util.ArrayList;
import java.util.List;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean minado;
	private boolean aberto;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>(); 
	private List<CampoObservador> observadores = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.coluna = coluna;
		this.linha = linha;
	}
	
	public void registrarObservadores(CampoObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(CampoEvento e) {
		observadores.stream()
		.forEach(o -> o.eventoOcorreu(this, e));
	}
	
	boolean adicionarVizinho(Campo candidatoVizinho) {
		
		boolean linhaDiferente = linha != candidatoVizinho.linha;
		boolean colunaDiferente = coluna != candidatoVizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(this.linha - candidatoVizinho.linha);
		int deltaColuna = Math.abs(this.coluna - candidatoVizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(candidatoVizinho);
			return true;
		} 
		else if(deltaGeral == 2 && diagonal){
			vizinhos.add(candidatoVizinho);
			return true;
		} 
		else {
			return false;			
		}
		
	}
	
	public void alternarMarcacao(){
		if(!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} 
			else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}		
	}
	
	public boolean abrir() {
		if(!aberto && !marcado) {
			if(minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);
			notificarObservadores(CampoEvento.ABRIR);
			if(vizinhacaSegura()){
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		} 
		else {
			return false;
		}
	}
	
	public boolean vizinhacaSegura(){
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isMarcado(){
		return marcado;
	}
	
	void minar(){
		minado = true;
		
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	void setAberto(boolean aberto){
		this.aberto = aberto;
		notificarObservadores(CampoEvento.ABRIR);
	}
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && !aberto;
		
		return desvendado || protegido;
	}
	
	public int minasNaVizinhaca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
		
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
	
	/*public String toString() {
		if(marcado == true) {
			return "x";
		}
		else if(aberto && minado) {
			return "*";
		}
		else if(aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());			
		}
		else if(aberto) {
			return " ";
		}
		else {
			return "?";
		}
	}*/
	
	
}
