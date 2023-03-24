package popbl4.reservas;

import java.util.List;

public class MiHilo extends Thread {
	Gestor gestor;
	
	public MiHilo(Gestor gestor) {
		this.gestor = gestor;
	}
	
	public void run() {
		List<String> lista = gestor.leerReservas();
		while(true) {
			gestor.comparar(lista);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}