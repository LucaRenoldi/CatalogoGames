package br.com.luca.cm.modelo;
import java.util.ArrayList; 
import java.util.List;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador{
	
	private final int linhas;
	

	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	public void resgistrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		observadores.stream()
		.forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linhas, int colunas) {
		
			campos.parallelStream().filter(c -> c.getLinha() == linhas && c.getColuna() == colunas)
			.findFirst()
			.ifPresent(c -> c.abrir());
	}
	
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.forEach(c-> c.setAberto(true));
	}
	
	public void alternarMarcacao(int linhas, int colunas) {
		campos.parallelStream().filter(c -> c.getLinha() == linhas && c.getColuna() == colunas)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha + 1, coluna + 1);
				campo.registrarObservadores(this);
				campos.add(campo);
				
			}
		}
	}
	
	private void associarVizinhos() {
		for (Campo campo : campos) {
			for(Campo campo2: campos) {
				campo.adicionarVizinho(campo2);
			}
		}
		
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		
		do {
			
			int aletorio = (int)(Math.random() * campos.size());
			campos.get(aletorio).minar();
			minasArmadas = campos.stream().filter(c -> c.isMinado()).count();
		} while (minasArmadas < minas);
		
	} 
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c-> c.objetivoAlcancado());
	}
	
	public void reiniciar(){
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public void eventoOcorreu(Campo c, CampoEvento e ) {
		if( e == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}
		else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	/*public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int coluna = 0; coluna < colunas; coluna++) {
			sb.append(" ");
			sb.append(coluna + 1);
			sb.append(" ");
		}
		sb.append("\n");
		int i = 0;
		for (int linha = 0; linha< linhas; linha++) {
			sb.append(linha + 1);
			sb.append(" ");
			for (int coluna = 0; coluna < colunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();		
	}*/
}
