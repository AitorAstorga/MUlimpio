package popbl4.pantallas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import popbl4.modelo.ListaMesas;
import popbl4.modelo.Mesa;
import popbl4.reservas.Gestor;
import popbl4.reservas.MiHilo;
import popbl4.reservas.PanelMesa;
import popbl4.reservas.RendererMesa;

public class Reservas implements ItemListener {	
	public final static int POS_X = 200;
	public final static int POS_Y = 100;
	private final static int POS = 1;
	JList<Mesa> listaMesas;
	JFrame contenedor;
	JSplitPane pVentana;
	ListaMesas modelo;
	Gestor gestor;
	PanelMesa pMesas;
	JComboBox<String> comboClases;
	RendererMesa renderer;
	MiHilo elHilo;
	char[] aCaracteres;

	public Reservas(PanelMesa pMesa, ListaMesas modelo, Gestor gestor) {
		contenedor = new JFrame("Reservas");
		this.modelo = modelo;
		this.gestor = gestor;
		this.pMesas = pMesa;
		this.listaMesas = new JList<>();
		this.renderer = new RendererMesa(gestor);
		this.gestor.setVista(contenedor);
		
		this.gestor.leerBaseDeDatos();
		this.gestor.setPrincipal(this);
		
		contenedor.setSize(925, 500);
		contenedor.setLocation(POS_X, POS_Y);
		
		contenedor.getContentPane().add(crearPanelToolBar(), BorderLayout.PAGE_START);
		
		pVentana = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, 
				crearPanelInfo(), pMesas);
		pVentana.setDividerLocation(200);
		contenedor.getContentPane().add(pVentana, BorderLayout.CENTER);
		
		this.pMesas.setLista(modelo.getMesas());
		
		elHilo = new MiHilo(gestor);
		elHilo.start();
		
		contenedor.setVisible(true); 
		contenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private Container crearPanelToolBar() {
		JPanel panel = new JPanel(new BorderLayout(0, 10));
		panel.add(crearToolBar(), BorderLayout.NORTH);
		return panel;
	}

	private Component crearPanelInfo() {
		JPanel panel = new JPanel(new BorderLayout(0, 20));
		panel.add(crearPanelDatos(), BorderLayout.CENTER);
				
		return panel;
	}
	
	private Component crearPanelDatos() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		listaMesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaMesas.setModel(modelo);
		listaMesas.setCellRenderer(renderer);
		
		panel.setViewportView(listaMesas);
		panel.setBackground(Color.white);
		panel.setOpaque(true);
		
		return panel;
	}	

	private Component crearToolBar() {
		JToolBar toolBar = new JToolBar();
		comboClases = new JComboBox<>(gestor.getClases());
		comboClases.addItemListener(this);
		comboClases.setSelectedIndex(0);
		selectInicio();
		
		JButton bAdd = new JButton(new ImageIcon("iconos/edit_add.png"));
		bAdd.setActionCommand("add");
		bAdd.addActionListener(gestor);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));	
		panel.setPreferredSize(new Dimension(0, 50));
		panel.add(comboClases);
		panel.add(bAdd);
		panel.setBackground(Color.ORANGE);
		toolBar.add(panel);	
		
		return toolBar;
	}
	
	private void selectInicio() {
		char[] aCaracteres = comboClases.getItemAt(0).toCharArray();
		gestor.leerDatosMesa(Character.getNumericValue(aCaracteres[POS]));
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getStateChange()==1) return;
		int select = comboClases.getSelectedIndex();
		aCaracteres = comboClases.getItemAt(select).toCharArray();
		dibujarMesas();
		regenerarHilo();
	}
	
	public void dibujarMesas() {
		gestor.leerDatosMesa(Character.getNumericValue(aCaracteres[POS]));
		pMesas.setLista(modelo.getMesas());
		pMesas.updateUI();
	}

	public void regenerarHilo() {
		if(elHilo != null) {
			elHilo.stop();
			MiHilo elHilo = new MiHilo(gestor);
			elHilo.start();
		}
	}
	
	public static int getPosX() {
		return POS_X;
	}

	public static int getPosY() {
		return POS_Y;
	}

	public static void main(String[] args) {
		ListaMesas modelo = new ListaMesas();
		PanelMesa pMesa = new PanelMesa();
		Gestor gestor = new Gestor(modelo);
		Reservas pantalla = new Reservas(pMesa, modelo, gestor);		
	}	
}