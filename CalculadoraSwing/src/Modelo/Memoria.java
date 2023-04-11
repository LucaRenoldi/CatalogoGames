package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	private enum TipoComando{
		ZERAR, SOMA, MULTIPLICACAO, DIVISAO, SUBTRACAO, IGUAL, NUMERO, VIRGULA, TROCAR_SINAL,PORCENTAGEM;
		
	};
	
	private final static Memoria instancia = new Memoria();
	private List<MemoriaObservador> observadores = new ArrayList<>(); 
	
	private TipoComando ultimaOperacao = null;
	private boolean temPorcentagem = false;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";
	
	private Memoria(){
		
	}
	
	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0": textoAtual;
	}
	
	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		if(tipoComando == null) {
			return;
		}
		else if(tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}
		else if (tipoComando == TipoComando.NUMERO 
				|| tipoComando == TipoComando.VIRGULA) {

			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		}
		else if(tipoComando == TipoComando.TROCAR_SINAL) {
			substituir = true;
			double numeroAtual = Double.parseDouble(textoAtual);
			numeroAtual = numeroAtual * -1;
			textoAtual=  Double.toString(numeroAtual).replace(".0", "");
		}
		else if (tipoComando == TipoComando.PORCENTAGEM){
			substituir = true;
			temPorcentagem = true;
			textoAtual = obterResultadoOperacao(temPorcentagem);
			textoBuffer = textoAtual;
			temPorcentagem = false;
			ultimaOperacao = tipoComando;
		}
		else {
			substituir = true;
			textoAtual = obterResultadoOperacao(temPorcentagem);
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
		
		observadores.forEach(observador -> observador.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao(boolean temPorcentagem) {
		
		
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL){
			return textoAtual;
		}
		
		double resultado = 0;
		double numeroBuffer =  Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual =  Double.parseDouble(textoAtual.replace(",", "."));
		
		if(temPorcentagem == true) {
			if(ultimaOperacao == TipoComando.SOMA){
				resultado = numeroBuffer + (numeroAtual * numeroBuffer)/100;
			}
			else if(ultimaOperacao == TipoComando.SUBTRACAO){
				resultado = numeroBuffer - (numeroAtual * numeroBuffer)/100;
			}
			else if(ultimaOperacao == TipoComando.MULTIPLICACAO){
				resultado = numeroBuffer * (numeroAtual * numeroBuffer)/100;
			}
			else if(ultimaOperacao == TipoComando.DIVISAO){
				resultado = numeroBuffer / (numeroAtual * numeroBuffer)/100;
			}
		}
		else if(temPorcentagem == false && ultimaOperacao == TipoComando.PORCENTAGEM) {
			return textoAtual;
		}
		else {
			if(ultimaOperacao == TipoComando.SOMA){
				resultado = numeroBuffer + numeroAtual;
			}
			else if(ultimaOperacao == TipoComando.SUBTRACAO){
				resultado = numeroBuffer - numeroAtual;
			}
			else if(ultimaOperacao == TipoComando.MULTIPLICACAO){
				resultado = numeroBuffer * numeroAtual;
			}
			else if(ultimaOperacao == TipoComando.DIVISAO){
				resultado = numeroBuffer / numeroAtual;
			}
		}
		
		String resultadoString= Double.toString(resultado).replace(".", ",");	
		boolean isInteiro = resultadoString.endsWith(",0");
		
		
		return isInteiro ? resultadoString.replace(",0", "") : resultadoString;
	}

	private TipoComando detectarTipoComando(String texto) {
		
		if(texto.isEmpty() && texto == "0") {
			return null;
		}
		
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			//quando não for numero...
			if("AC".equalsIgnoreCase(texto)){
				return TipoComando.ZERAR;
			}
			else if ("÷".equals(texto)) {
				return TipoComando.DIVISAO;
			}
			else if ("-".equals(texto)) {
				return TipoComando.SUBTRACAO;
			}
			else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			}
			else if ("x".equals(texto)) {
				return TipoComando.MULTIPLICACAO;
			}
			else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			}
			else if (",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
			else if("+/-".equals(texto)){
					return TipoComando.TROCAR_SINAL;
			}
			else if("%".equals(texto)){
					return TipoComando.PORCENTAGEM;			
			}
		}
		
		return null;
	}
}
