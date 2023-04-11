package br.com.luca.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.luca.cm.modelo.Campo;
import br.com.luca.cm.modelo.CampoEvento;
import br.com.luca.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements 
CampoObservador, MouseListener{
	
	private final Color BG_PADRAO = new Color(184,184,184);
	private final Color BG_MARCAR = Color.BLACK;
	private final Color BG_EXPLODIR = new Color(189,66,68);
	private final Color TEXTO_VERDE = new Color(0,168,0);
	
	private Campo campo;
	
	public BotaoCampo(Campo campo ) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		campo.registrarObservadores(this);
	}

	@Override
	public void eventoOcorreu(Campo c, CampoEvento e) {
		switch (e) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
			
		case MARCAR:
			aplicarEstiloMarcar();
			break;
			
		case DESMARCAR:
			aplicarEstiloDesmarcar();
			break;
			
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
		
		SwingUtilities.invokeLater(() ->{
			repaint();
			validate();
		});
	}

	private void aplicarEstiloPadrao() {
		// TODO Auto-generated method stub
		setBackground(BG_PADRAO);
		setText("");
		setBorder(BorderFactory.createBevelBorder(0));
	}

	private void aplicarEstiloExplodir() {
		// TODO Auto-generated method stub
		setBackground(BG_EXPLODIR);
		setText("*");
		
	}

	private void aplicarEstiloDesmarcar() {
		// TODO Auto-generated method stub
		setBackground(BG_PADRAO);
		setText("");

	}

	private void aplicarEstiloMarcar() {
		// TODO Auto-generated method stub
		setForeground(BG_MARCAR);
		setText("X");
	}

	private void aplicarEstiloAbrir() {
		
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			setText("*");
			return;
		}
		
		switch (campo.minasNaVizinhaca()){
		case 1:
				setForeground(TEXTO_VERDE);
				break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.MAGENTA);
			break;
		case 4:
			setForeground(Color.YELLOW);
			break;
		case 5:
			setForeground(Color.PINK);
			break;
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.BLACK);
			
		}
		
		String valor = !campo.vizinhacaSegura() ?
				campo.minasNaVizinhaca() + "" : "";
		setText(valor);
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == 1) {
			campo.abrir();
		}
		else {
			campo.alternarMarcacao();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

		

}
