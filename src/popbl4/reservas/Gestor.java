package popbl4.reservas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import popbl4.modelo.Habitacion;
import popbl4.modelo.ListaMesas;
import popbl4.modelo.Mesa;
import popbl4.modelo.Reserva;
import popbl4.pantallas.Reservas;
import popbl4.principal.Principal;

public class Gestor implements ActionListener {
	private final static String OPCION_BUSQUEDA = "ESTANCIAID";
	private final static String ALL = "*";
	private final static String dbTable_H = "ESTANCIAS";
	private final static String dbTable_M = "MESAS";
	private final static String pass = "";
	
	Reservas principal;
	ListaMesas modelo;
	List<Habitacion> habitaciones;
	JFrame vista;
	Habitacion habitacionElegida;
	Mesa mesaElegida;
	
	
	
	public Habitacion getHabitacionElegida() {
		return habitacionElegida;
	}

	public Gestor(ListaMesas modelo) {
		this.modelo = modelo;
		this.habitaciones = new ArrayList<>();
	}
	
	public void setPrincipal(Reservas principal) {
		this.principal = principal;
	}
	
	public void setVista(JFrame vista) {
		this.vista = vista;
	}
	
	public void leerBaseDeDatos() {
		try {
			Principal.getDatabase().loginDataBase("root", pass);
			Principal.getDatabase().readTable(Principal.getDatabase().getDbName(), this.dbTable_H, ALL);
			crearDato(Principal.getDatabase().guardarDatos(), dbTable_H);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void leerDatosMesa(int habitacion) {
		try {
			for(int i=0;i<habitaciones.size();i++) {
				if(habitaciones.get(i).getIdHabitacion() == habitacion) {
					setHabitacion(habitaciones.get(i));
					habitaciones.get(i).clearLista();
					Principal.getDatabase().readTableWhere(Principal.getDatabase().getDbName(), this.dbTable_M, ALL, OPCION_BUSQUEDA, habitacion);
					crearDato(Principal.getDatabase().guardarDatos(), dbTable_M);
					guardarModelo();
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setHabitacion(Habitacion habitacion) {
		this.habitacionElegida = habitacion;
	}

	private void guardarModelo() {
		modelo.clear();
		List<Mesa> lista = habitacionElegida.getMesas();
		
		for(int i=0;i<lista.size();i++) {
			modelo.add(lista.get(i));
		}
	}

	private void crearDato(List<String> list, String tabla) {
		boolean estado;
		
		for(int i=0;i<list.size();i++) {
			String valores [] = list.get(i).split("[$]");
			
			switch(tabla) {
				case dbTable_M:{
					if(Integer.parseInt(valores[1]) == 0)estado = true;
					else estado = false;
					
					habitacionElegida.addMesa(new Mesa(Integer.parseInt(valores[0]),
							estado,
							Integer.parseInt(valores[2]),
							Integer.parseInt(valores[3]),
							Integer.parseInt(valores[4])));
					break;
				}
				case dbTable_H:{
					habitaciones.add(new Habitacion(Integer.parseInt(valores[0]),
							Integer.parseInt(valores[1]),
							valores[2].charAt(0),
							Integer.parseInt(valores[3])));
					break;
				}
			}	
		}	
	}

	public String[] getClases() {
		String[] datosArray = new String[habitaciones.size()];
		
		for(int i=0;i<habitaciones.size();i++) {
			datosArray[i] = String.valueOf(habitaciones.get(i).getPiso()) + String.valueOf(habitaciones.get(i).getIdHabitacion()) + 
					String.valueOf(habitaciones.get(i).getLetra());
		}
		
		Arrays.sort(datosArray);
		
		return datosArray.clone();
	}
	
	public void mesaSelect(Mesa mesa) {
		this.mesaElegida = mesa;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command) {
		case "add": {
			if(mesaElegida != null) {
				if(mesaElegida.isEstado()) {
					DialogoReserva dialogo = new DialogoReserva(vista, "Reserva", true,
					
					LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getDayOfMonth(),
					mesaElegida.getId(), Principal.getDatabase().getUser(), habitacionElegida.getIdHabitacion());
					
					Reserva reserva = dialogo.getNewReserva();
					if(reserva != null) crearReserva(reserva);	
				}
				
				else JOptionPane.showMessageDialog(vista, "Mesa reservada por otro usuario, espere hasta que se acabe la reserva");
				break;
				}else JOptionPane.showMessageDialog(vista, "Elija una mesa");
			}					
		}
	}

	private void crearReserva(Reserva reserva) {
		try {
			Principal.getDatabase().crearReserva(reserva.getIdUser(), reserva.getHabitacion(), reserva.getMesa(), 
					"'" + reserva.getFecha() + "'", "'" + reserva.getHoraInicio() + "'", "'" + reserva.getHoraFinal() + "'");
			
			principal.regenerarHilo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> leerReservas() {
		try {
			return (Principal.getDatabase().leerReservas(habitacionElegida.getIdHabitacion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public void comparar(List<String> lista) {
		
		String month = null, minute = null, seg = null;
		
		if(LocalDateTime.now().getMonthValue() < 10) {
			month = "0" + LocalDateTime.now().getMonthValue();
		}
		else month = String.valueOf(LocalDateTime.now().getMonthValue());
		
		if(LocalDateTime.now().getMinute() < 10) {
			minute = "0" + LocalDateTime.now().getMinute();
		}
		else minute = String.valueOf(LocalDateTime.now().getMinute());
		
		if(LocalDateTime.now().getSecond() < 10) {
			seg = "0" + LocalDateTime.now().getSecond();
		}
		else seg = String.valueOf(LocalDateTime.now().getSecond());
		
		String fechaHoy = LocalDateTime.now().getYear() + "-" + month + "-" + LocalDateTime.now().getDayOfMonth();
		
		String horaActual = LocalDateTime.now().getHour() + ":" + minute + ":" + seg;
		
		List<Mesa> listaMesas = habitacionElegida.getMesas();

		try {			
			for(int i=0;i<lista.size();i++) {	
				
				String[] valores = separar(lista.get(i));
				
				int mesaReservada = Integer.parseInt(valores[3]);
				String fecha = valores[4];
				String tInicio = valores[5];
				String tFinal = valores[6];
				
				for(int j=0;j<listaMesas.size();j++) {
					if(listaMesas != null) {						
						if(mesaReservada == listaMesas.get(j).getId()){
							if(fechaHoy.equals(fecha)) {
								
								String[] horaActualSeparada = separarHora(horaActual);
								String[] horaInicialSeparada = separarHora(tInicio);	
								String[] horaFinalSeparada = separarHora(tFinal);
																
								if((horaActualSeparada[0].equals(horaInicialSeparada[0])
										&& horaActualSeparada[1].equals(horaInicialSeparada[1])) && 
										horaActualSeparada[2].equals(horaInicialSeparada[2])
									||
									(horaActualSeparada[0].equals(horaFinalSeparada[0])
											&& horaActualSeparada[1].equals(horaFinalSeparada[1])) && 
											horaActualSeparada[2].equals(horaFinalSeparada[2])) {
									
									listaMesas.get(j).setEstado(!listaMesas.get(j).isEstado());									
									
									Principal.getDatabase().doUpdate((listaMesas.get(j).isEstado())?0:1, 
											mesaReservada, 
											habitacionElegida.getIdHabitacion());
									
									principal.dibujarMesas();
								}	
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private String[] separarHora(String hora) {
		String valores [] = null;
		valores = hora.split("[:]");
		return valores;
	}

	private String[] separar(String string) {
		String valores [] = null;
		valores = string.split("[$]");
		return valores;
	}
}