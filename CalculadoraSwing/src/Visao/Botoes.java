package Visao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Botoes extends JButton {
	
	public Botoes(String texto, Color cor){
		setText(texto);
		setBackground(cor);
		setFont(new Font("courier", Font.PLAIN, 16));
		setOpaque(true);
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
