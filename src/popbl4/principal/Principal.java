package popbl4.principal;

import java.awt.Color;
import java.awt.EventQueue;

import popbl4.modelo.ListaMesas;
import popbl4.mysql.MySQLAccess;
import popbl4.pantallas.Login;
import popbl4.placa.Placa;
import popbl4.reservas.Gestor;

public class Principal {
	private final static Color DefaultColor = new Color(255, 158, 0);
	private final static int screenX = 1045;
	private final static int screenY = 665;
	private static MySQLAccess database;
	private static ListaMesas modelo = new ListaMesas();
	private static Gestor gestor = new Gestor(modelo);
		
	public static Color getDefaultcolor() { return DefaultColor; }
	public static int getScreenX() { return screenX; }
	public static int getScreenY() { return screenY; }
	public static MySQLAccess getDatabase() { return database; }
	public static ListaMesas getModelo() { return modelo; }
	public static Gestor getGestor() { return gestor; }

	public static void main(String[] args) {
		/*try {
			Placa programa = new Placa();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		database = new MySQLAccess();
		Login login = new Login();
		login.setVisible(true);
		
		System.out.println(database.isConexionEstablecida());
				
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				
			}
		});*/
	}

}
