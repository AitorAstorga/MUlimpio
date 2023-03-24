package popbl4.reservas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.InputMismatchException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import popbl4.modelo.Reserva;

public class DialogoReserva extends JDialog implements ActionListener{
	JTextField inicio, fin, descripcion;
	Reserva newReserva;
	JPanel panelDatos;
	String fecha;
	int habitacionID, mesaID, userID;
	JFrame vista;

	public DialogoReserva(JFrame ventana, String titulo, boolean modal, String fecha, int mesaID, int userID, int habitacionID) {
		super(ventana, titulo, modal);
		this.vista = ventana;
		setDatos(fecha, mesaID, userID, habitacionID);
		this.setSize(600, 400);
		this.setLocation(300, 200);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void setDatos(String fecha, int mesaID, int userID, int habitacionID) {
		this.fecha = fecha;
		this.mesaID = mesaID;
		this.userID = userID;
		this.habitacionID = habitacionID;
	}

	private Container crearPanelVentana() {
		JPanel panelVentana =  new JPanel(new BorderLayout(20, 0));
		panelVentana.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	
		panelVentana.add(crearPanelDatosReserva(), BorderLayout.CENTER);
		panelVentana.add(crearPanelDatos(), BorderLayout.WEST);
		
		return panelVentana;
	}

	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 0, 5));

		JLabel lStr = new JLabel("Fecha");
		lStr.setFont(new Font("Verdana", Font.BOLD, 18));
		JLabel lFecha = new JLabel(this.fecha);
		lFecha.setFont(new Font("Verdana", Font.BOLD, 18));
		
		panel.add(lStr);
		panel.add(lFecha);
		
		JPanel panelP = new JPanel(new BorderLayout());
		panelP.add(panel, BorderLayout.NORTH);
		
		return panelP;
	}

	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
		
		JButton bOk = new JButton("Aceptar");
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);
		
		JButton bCancel = new JButton("Cancelar");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);
		
		panel.add(bOk);
		panel.add(bCancel);
		return panel;
	}

	private Component crearPanelDatosReserva() {
		panelDatos = new JPanel(new GridLayout(5, 1, 0, 10));
		inicio  = new JTextField(30);
		fin = new JTextField(30);
		descripcion = new JTextField(100);
		
		panelDatos.add(crearTextField(inicio, "Hora Inicio:"));
		panelDatos.add(crearTextField(fin, "Hora Final:"));
		panelDatos.add(crearTextField(descripcion, "Descripcion:"));
		
		panelDatos.add(crearPanelBotones());
		
		return panelDatos;
	}
	
	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(), titulo));
		
		panel.add(text);
		return panel;
	}

	public Reserva getNewReserva() {
		return newReserva;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")) {
			try {
				newReserva = new Reserva(this.habitacionID, this.mesaID, fecha, inicio.getText(), 
						fin.getText(), this.userID);
				dispose(); 
			}catch (InputMismatchException i) {
				 JOptionPane.showMessageDialog(vista, "El formato es hh:mm:ss");	
			}catch (HoraExcepcion i) {
				JOptionPane.showMessageDialog(vista, i.getMessage() + "\nEl formato es hh:mm:ss");
			}
		}else dispose();
	}
}