package popbl4.pantallas;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import popbl4.modelo.Estancia;
import popbl4.principal.Principal;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Font;
import javax.swing.JList;

public class MarcarLimpieza extends JFrame {
	private final static String BOTON_ACEPTAR = "aceptar";
	private final static String BOTON_CANCELAR = "cancelar";
	
	private final static Color DefaultColor = Principal.getDefaultcolor();
	private ActionListener listenerBotones;
	private ListSelectionListener listenerEstancias;
	private int estanciaSeleccionadaID;
	private JScrollPane scrollPaneEstancias;
	private JList<Estancia> estancias;
	private JLabel labelUltimaReserva;
	
	public MarcarLimpieza() {
		inicializar();
	}
	
	private JSplitPane crearPanelPrincipal() {
		JSplitPane panelPrincipal = new JSplitPane();
		panelPrincipal.setLeftComponent(crearPanelIzquierdo());
		panelPrincipal.setRightComponent(crearPanelDerecho());
		return panelPrincipal;
	}
	
	private DefaultListModel<Estancia> crearModeloEstancias() {
		List<Estancia> estancias = Principal.getDatabase().guardarEstancias();
		DefaultListModel<Estancia> listModel = new DefaultListModel<Estancia>();
		for(int i = 0; i < estancias.size(); i++) {
		    listModel.add(i, estancias.get(i));
		}
		return listModel;
	}
	
	private JScrollPane crearPanelListado() {
		scrollPaneEstancias = new JScrollPane();
		estancias = new JList<Estancia>();
		estancias.setFont(new Font("Arial", Font.PLAIN, Principal.getScreenY()/20));
		
		estancias.addListSelectionListener(listenerEstancias);
		estancias.setModel(crearModeloEstancias());
		scrollPaneEstancias.setViewportView(estancias);
		return scrollPaneEstancias;
	}
	
	private JSplitPane crearPanelIzquierdo() {
		JSplitPane splitPaneIzquierda = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneIzquierda.setBackground(DefaultColor);
		
		splitPaneIzquierda.setBottomComponent(crearPanelListado());
		
		JLabel labelReservados = new JLabel("Estancias");
		labelReservados.setFont(new Font("Rockwell", Font.BOLD, Principal.getScreenY()/12));
		labelReservados.setBackground(DefaultColor);
		splitPaneIzquierda.setTopComponent(labelReservados);
		
		return splitPaneIzquierda;
	}
	
	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 5, 5));
		panelBotones.setBackground(DefaultColor);
		
		JButton btAceptar = new JButton("Limpio");
		btAceptar.addActionListener(listenerBotones);
		btAceptar.setActionCommand(BOTON_ACEPTAR);
		
		JButton btDenegar = new JButton("No limpio");
		btDenegar.addActionListener(listenerBotones);
		btDenegar.setActionCommand(BOTON_CANCELAR);
		
		panelBotones.add(btAceptar);
		panelBotones.add(btDenegar);
		
		return panelBotones;
	}
	
	private JPanel crearPanelDerecho() {
		JPanel panelRight = new JPanel(new GridLayout(2, 2, 5, 5));
		panelRight.setBackground(DefaultColor);
		panelRight.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JPanel panelReserva = new JPanel();
		panelReserva.setBackground(DefaultColor);
		labelUltimaReserva = new JLabel();
		labelUltimaReserva.setFont(new Font("Arial", Font.PLAIN, Principal.getScreenY()/10));
		panelReserva.add(labelUltimaReserva);
		
		panelRight.add(panelReserva);
		panelRight.add(crearPanelBotones());
		
		return panelRight;
	}
	
	private void inicializar() {
		this.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		this.getContentPane().setBackground(DefaultColor);
		this.setBounds(100, 100, Principal.getScreenX(), Principal.getScreenY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		listenerBotones = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals(BOTON_ACEPTAR)) {
					System.out.println(BOTON_ACEPTAR);
					Principal.getDatabase().setEstadoLimpieza(estanciaSeleccionadaID, 100);
					actualizarLista();
				}
				else if(e.getActionCommand().equals(BOTON_CANCELAR)) {
					System.out.println(BOTON_CANCELAR);
					Principal.getDatabase().setEstadoLimpieza(estanciaSeleccionadaID, 0);
					actualizarLista();
				}
			}
		};
		listenerEstancias = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting() && estancias.getSelectedValue() != null) {
					estanciaSeleccionadaID = estancias.getSelectedValue().getEstanciaID();
					labelUltimaReserva.setText(Principal.getDatabase().getUltimaReserva(estanciaSeleccionadaID));
					System.out.println("estLimp: " + Principal.getDatabase().getEstadoLimpieza(estanciaSeleccionadaID));
				}
			}
		};
		this.setContentPane(crearPanelPrincipal());
	}
	
	private void actualizarLista() {
		estancias.setModel(crearModeloEstancias());
		scrollPaneEstancias.setViewportView(estancias);
	}
	
}
