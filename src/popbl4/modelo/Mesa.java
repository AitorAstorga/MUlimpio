package popbl4.modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Mesa {
	final static int ANCHO_MESA = 125;
	final static int LARGO_MESA = 70;
	final static int SEPARADOR = 110;
	final static int MARGEN = 50;
	int id, posX, posY, tamaño, habitacion;
	boolean estado;
	
	public Mesa(int id, boolean estado, int habitacion, int posX, int posY) {
		this.id = id;
		this.estado = estado;
		this.habitacion = habitacion;
		this.posX = posX;
		this.posY = posY;
		this.tamaño = 100;
	}
	
	public int getHabitacion() {
		return habitacion;
	}

	public int getId() {
		return id;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean isEstado() {
		return estado;
	}
	
	public int getTamaño() {
		return tamaño;
	}
	
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void dibujar(Graphics g) {
		Graphics2D gr = (Graphics2D) g;
		
		if(isEstado())gr.setColor(Color.GREEN); 
		else gr.setColor(Color.RED);
		gr.drawRect((int)Math.round(MARGEN + ANCHO_MESA*getPosY() + SEPARADOR*getPosY()), (int)Math.round(MARGEN + MARGEN*getPosX() + LARGO_MESA*getPosX()), 
				ANCHO_MESA, LARGO_MESA);
		gr.fillRect((int)Math.round(MARGEN + ANCHO_MESA*getPosY() + SEPARADOR*getPosY()), (int)Math.round(MARGEN + MARGEN*getPosX() + LARGO_MESA*getPosX()), 
				ANCHO_MESA, LARGO_MESA);
	}
	@Override
	public String toString() {
		return String.valueOf(getId());
	}	
}