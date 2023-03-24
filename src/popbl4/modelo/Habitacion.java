package popbl4.modelo;

import java.util.ArrayList;
import java.util.List;

public class Habitacion {
	List<Mesa> mesas;
	int idHabitacion, piso, aforo;
	char letra;
	
	public Habitacion(int idHabitacion, int piso, char letra, int aforo) {
		this.idHabitacion = idHabitacion;
		this.piso = piso;
		this.aforo = aforo;
		this.letra = letra;
		this.mesas = new ArrayList<>();
	}
	
	public void addMesa(Mesa mesa) {
		this.mesas.add(mesa);
	}

	public char getLetra() {
		return letra;
	}

	public List<Mesa> getMesas() {
		return mesas;
	}

	public int getIdHabitacion() {
		return idHabitacion;
	}

	public int getPiso() {
		return piso;
	}

	public int getAforo() {
		return aforo;
	}

	public void clearLista() {
		mesas.clear();		
	}
}