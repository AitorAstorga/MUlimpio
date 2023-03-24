package popbl4.modelo;

import popbl4.reservas.HoraExcepcion;

public class Reserva {
	static int id = 1;
	int idUser, habitacion, mesa;
	String fecha, horaInicio, horaFinal;
	
	public Reserva(int idHabitacion, int mesaReservada, String fecha, String horaInicio, String horaFinal,
			int idUser) throws HoraExcepcion {
		validarHora(horaInicio);
		validarHora(horaFinal);
		this.id++;
		this.habitacion = idHabitacion;
		this.mesa = mesaReservada;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFinal = horaFinal;
		this.idUser = idUser;
	}

	public int getId() {
		return id;
	}

	public int getIdUser() {
		return idUser;
	}

	public int getHabitacion() {
		return habitacion;
	}

	public int getMesa() {
		return mesa;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}
	
	public void validarHora(String hora) throws HoraExcepcion {
		String[] valores = hora.split(":");
		if(Integer.parseInt(valores[0]) >= 24 || Integer.parseInt(valores[0]) < 0) {
			throw new HoraExcepcion ("Hora no válida");
		}
		else if(Integer.parseInt(valores[0]) < 7) {
			throw new HoraExcepcion ("Establecimiento cerrado a esta hora");
		}
		else if(Integer.parseInt(valores[1]) > 60 || Integer.parseInt(valores[1]) < 0) {
			throw new HoraExcepcion ("Revise los minutos");
		}
		else if(Integer.parseInt(valores[2]) > 60 || Integer.parseInt(valores[2]) < 0) {
			throw new HoraExcepcion ("Revise los segundos");
		}
	}
}