package popbl4.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import popbl4.modelo.Estancia;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private final static String dbName = "GESTIONLIMPIEZA";
    private boolean conexionEstablecida = false;
    
    public String getDbName() { return dbName; }
    public boolean isConexionEstablecida() { return conexionEstablecida; }
    
    public void readTable(String database, String table, String column) {
    	try {
			resultSet = statement.executeQuery("SELECT" + column + " FROM " + database + "." + table);
		} catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Estancia> guardarEstancias() {
    	List<Estancia> estancias = new ArrayList<>();
    	readTable("GESTIONLIMPIEZA", "ESTANCIAS", "*");
		try {
			while (resultSet.next()) {
				int estanciaID = resultSet.getInt(1);
				int piso = resultSet.getInt(2);
				String numeroLetra = resultSet.getString(3).toString();
				int aforo = resultSet.getInt(4);
				int estadoLimpieza = resultSet.getInt(5);
				Estancia estancia = new Estancia(estanciaID, piso, numeroLetra, aforo, estadoLimpieza);
		   	 	estancias.add(estancia);
			}
		} catch (SQLException e) { e.printStackTrace(); }
    	return estancias;
    }
    
    public void loginDataBase(String user, String password) {
    	// This will load the MySQL driver, each DB has its own driver
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) { e1.printStackTrace(); }
        // Setup the connection with the DB
        try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/GESTIONLIMPIEZA?"
			                + "user=" + user + "&password=" + password);
			conexionEstablecida = true;
		} catch (SQLException e) {
			System.out.println("Usuario o password invalida");
		}
        // Statements allow to issue SQL queries to the database
        try {
			statement = connect.createStatement();
		} catch (SQLException e) { e.printStackTrace(); }
    }
    
    public int getEstadoLimpieza(int estanciaID) {
    	int estadoLimpieza = -1;
    	try {
    		resultSet = statement.executeQuery("SELECT EstadoLimpieza FROM estancias WHERE EstanciaID = " + estanciaID);
    		while(resultSet.next()) {
    			estadoLimpieza = resultSet.getInt(1);
    		}
    	} catch (SQLException e) { e.printStackTrace(); }
    	return estadoLimpieza;
    }
    
    public void setEstadoLimpieza(int estanciaID, int porcentaje) {
    	try {
			statement.execute("UPDATE ESTANCIAS SET EstadoLimpieza = " + porcentaje + " WHERE EstanciaID = " + estanciaID);
		} catch (SQLException e) { e.printStackTrace(); }
    }
    
    public String getUltimaReserva(int estancia) {
    	String ultimaReserva = null;
    	try {
			resultSet = statement.executeQuery("CALL ultima_reserva(" + estancia + ");");
			while(resultSet.next()) {
    			ultimaReserva = resultSet.getDate(1).toString() + " " + resultSet.getTime(1).toString();
    		}
		} catch (SQLException e) { e.printStackTrace(); }
    	return ultimaReserva;
    }
    
    public void crearUsuario(String user, String password) throws SQLException {
    	loginDataBase("root", "");
    	statement.execute("CREATE USER " + user + " IDENTIFIED BY '" + password + "';");
    	statement.execute("GRANT SELECT " + "ON " + dbName + ".* TO " + user);
    }
    
    public List<String> guardarDatos() {
    	List<String> lista = new ArrayList<>();
    	
    	int columnCount = 0;
		try {
			columnCount = resultSet.getMetaData().getColumnCount();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	
		try {
			while (resultSet.next()) {
				if(columnCount == 4){
					lista.add(resultSet.getString(1) + "$" +
							resultSet.getString(2) + "$" + 
							resultSet.getString(3) + "$" + 
							resultSet.getString(4));
				}	
				else if(columnCount == 5) {
					lista.add(resultSet.getString(1) + "$" +
							resultSet.getString(2) + "$" + 
							resultSet.getString(3) + "$" + 
							resultSet.getString(4) + "$" + 
							resultSet.getString(5));
				}	
				else {
					lista.add(resultSet.getString(1) + "$" +
							resultSet.getString(2) + "$" + 
							resultSet.getString(3) + "$" + 
							resultSet.getString(4) + "$" + 
							resultSet.getString(5) + "$" + 
							resultSet.getString(6) + "$" + 
							resultSet.getString(7));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
    }
    
    public void readTableWhere(String database, String table, String column, String busqueda, int where) throws Exception {
    	resultSet = statement.executeQuery("SELECT" + column + " FROM " + database + "." + table + " WHERE " + busqueda + "=" + where);
    }
    
    public void doUpdate(int estado, int mesaID, int habitacionID) throws SQLException {
    	statement.execute("update " + dbName + ".mesas set reservada = " + estado + " where mesaID = " + mesaID + " AND estanciaID = " + habitacionID +";");
	}
    
    public List<String> leerReservas(int habitacionElegida) throws Exception {
    	readTableWhere("GESTIONLIMPIEZA", "RESERVAS", "*", "EstanciaID", habitacionElegida);
    	return guardarDatos();
    }
    
    public int getUser() {
		//cambiar para devolver id del usuario en uso
		return 1;
	}
    
    public void crearReserva(int usuarioID, int estanciaID, int mesaID, String fecha, String tiempoInicio, String tiempoFinal) throws SQLException {
    	statement.execute("insert into " + dbName + ".reservas (UsuarioID, EstanciaID, MesaID, Fecha, TiempoInicio, TiempoFinal) values ("  
    	+ usuarioID +", " + estanciaID + ", " + mesaID + ", " + fecha + ", " + 
    			 tiempoInicio + ", " + tiempoFinal + ");");	
    }
    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connect != null) connect.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

}