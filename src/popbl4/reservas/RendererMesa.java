package popbl4.reservas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import popbl4.modelo.Mesa;

public class RendererMesa implements ListCellRenderer<Mesa>{
	JPanel panel;
	Gestor gestor;
	
	public RendererMesa(Gestor gestor) {
		this.gestor = gestor;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Mesa> list, Mesa mesa, int index,
			boolean isSelected,	boolean cellHasFocus) {	
		
		JPanel panel = new JPanel(new BorderLayout());
				
		JLabel text = new JLabel(String.valueOf(mesa.getId()));
		text.setBorder(null);
		text.setFont(new Font("Verdana", Font.ITALIC, 24));
		if(mesa.isEstado())text.setForeground(Color.green);
		else text.setForeground(Color.red);
		
		if(isSelected) {
			gestor.mesaSelect(mesa);
		
			text.setBackground(Color.gray);
			text.setForeground(Color.white);
			text.setOpaque(true);
		}
		
		panel.setBackground(Color.white);
		panel.add(text);
		
		return panel;
	}
}