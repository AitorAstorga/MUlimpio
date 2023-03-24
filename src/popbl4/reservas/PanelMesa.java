package popbl4.reservas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import popbl4.modelo.Mesa;

public class PanelMesa extends JPanel implements ActionListener{
	final static int ANCHO = 100000;
	final static int LARGO = 100000;
	List<Mesa> lista;
	
	public void setLista(List<Mesa> lista) {
		this.lista = lista;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D gr = (Graphics2D) g;
		gr.setColor(Color.white);
		gr.fillRect(0,  0, ANCHO, LARGO);
		
		if(lista != null) {
			for(Mesa e : lista) {
				e.dibujar(gr);
			}
		}
	}

	public List<Mesa> getLista() {
		return lista;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}