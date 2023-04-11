package Visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame {
	
	public Calculadora() {
		organizarLayout();
	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(232, 322);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void organizarLayout() {
		setLayout(new BorderLayout());
		
		Display display = new Display();
		display.setPreferredSize(new Dimension(233,47));
		add(display,BorderLayout.NORTH);
		setSize(WIDTH, HEIGHT);
		
		Teclado teclado = new Teclado();
		add(teclado, BorderLayout.CENTER);
	
		
		
		
	}


	public static void main(String[] args) {
		new Calculadora();
	}
}
