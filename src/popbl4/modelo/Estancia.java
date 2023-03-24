package popbl4.modelo;

public class Estancia {
	private int estanciaID;
	private int piso;
	private String numeroLetra;
	private int aforo;
	private int estadoLimpieza;
	
	public Estancia(int estanciaID, int piso, String numeroLetra, int aforo, int estadoLimpieza) {
		super();
		this.estanciaID = estanciaID;
		this.piso = piso;
		this.numeroLetra = numeroLetra;
		this.aforo = aforo;
		this.estadoLimpieza = estadoLimpieza;
	}

	public int getEstanciaID() { return estanciaID;	}

	public int getPiso() { return piso; }

	public String getNumeroLetra() { return numeroLetra; }

	public int getAforo() { return aforo; }

	public int getEstadoLimpieza() { return estadoLimpieza; }

	@Override
	public String toString() {
		return piso + "." + numeroLetra + " Limp: " + estadoLimpieza;
	}
	
}
