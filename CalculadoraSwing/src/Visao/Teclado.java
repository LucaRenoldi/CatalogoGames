package Visao;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Modelo.Memoria;

@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener{
	
	private final Color COR_CINZA_ESCURO = new Color(68,69,68);
	private final Color COR_CINZA_CLARO = new Color(97,100,99);
	private final Color COR_LARANJA = new Color(242, 163, 60);
	
	public Teclado(){
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		//Primeira linha do teclado da calculadora
		adicionarBotao("AC", COR_CINZA_ESCURO, constraints, 1, 0, 0);
		adicionarBotao("+/-", COR_CINZA_ESCURO, constraints, 1, 1, 0);
		adicionarBotao("%", COR_CINZA_ESCURO, constraints, 1, 2, 0);
		adicionarBotao("÷", COR_LARANJA, constraints, 1, 3, 0);
		
		//Segunda linha do teclado da calculadora
		adicionarBotao("7", COR_CINZA_CLARO, constraints, 1, 0, 1);
		adicionarBotao("8", COR_CINZA_CLARO, constraints, 1, 1, 1);
		adicionarBotao("9", COR_CINZA_CLARO, constraints, 1, 2, 1);
		adicionarBotao("x", COR_LARANJA, constraints, 1, 3, 1);
		
		//Terceira linha do teclado da calculadora
		adicionarBotao("4", COR_CINZA_CLARO, constraints, 1, 0, 2);
		adicionarBotao("5", COR_CINZA_CLARO, constraints, 1, 1, 2);
		adicionarBotao("6", COR_CINZA_CLARO, constraints, 1, 2, 2);
		adicionarBotao("-", COR_LARANJA, constraints, 1, 3, 2);
		
		//Quarta linha do teclado da calculadora
		adicionarBotao("1", COR_CINZA_CLARO, constraints, 1, 0, 3);
		adicionarBotao("2", COR_CINZA_CLARO, constraints, 1, 1 ,3);
		adicionarBotao("3", COR_CINZA_CLARO, constraints, 1, 2, 3);
		adicionarBotao("+", COR_LARANJA, constraints, 1, 3, 3);
		
		//Quinta linha do teclado da calculadora
		adicionarBotao("0", COR_CINZA_CLARO, constraints, 2, 0, 4);
		adicionarBotao(",", COR_CINZA_CLARO, constraints, 1, 2, 4);
		adicionarBotao("=", COR_LARANJA, constraints, 1, 3 , 4);
		
		
	}

	private void adicionarBotao(String texto, Color cor,
			GridBagConstraints constraints, int gridwidth,int x, int y) {
		constraints.gridwidth = gridwidth;
		constraints.gridx = x;
		constraints.gridy = y;
		Botoes botao = new Botoes(texto, cor);
		botao.addActionListener(this);
		add(botao,constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton botao = (JButton) e.getSource();
			Memoria.getInstancia().processarComando(botao.getText());
			
		} 
	}
	
	
}
