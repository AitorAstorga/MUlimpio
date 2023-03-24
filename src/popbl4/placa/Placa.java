package popbl4.placa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.fazecast.jSerialComm.SerialPort;

import popbl4.principal.Principal;

public class Placa extends JFrame implements PropertyChangeListener {	
	final static String SELECTED_PORT = "COM6";
	final static char SENSOR_ENTRADA = '1';
	final static char SENSOR_SALIDA = '2';
	final static int TIEMPO_CRUZAR = 5000;
	
	int firstDigit, secondDigit;
	PropertyChangeSupport support;
	SerialReader reader;
	SerialPort port;
	
	Timer timer;
	boolean entrando = false;
	boolean saliendo = false;
	boolean entradaDetectada = false;
	boolean salidaDetectada = false;
	
	private int numeroPersonas;

	public Placa() throws Exception {
		super("Conexión Periférico");
		
		port = SerialPort.getCommPort(SELECTED_PORT);
		connect();
		Scanner teclado = new Scanner(System.in);
		numeroPersonas = 0;
		while(true) {
			/*System.out.print("Press 'enter' to send a byte: ");
			String test = teclado.nextLine();
			
			if(test.equals("")){
				port.writeBytes("_".getBytes(), "_".getBytes().length);
				System.out.println("Bytes written");
			}*/
		}
	}

	void connect () throws Exception {
		boolean portOpen = false;
		boolean portParameters = false;
		portOpen = port.openPort();
        portParameters = port.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        
        reader = new SerialReader(port);
        reader.addPropertyChangeListener(this);
        port.addDataListener(reader);        
        
        if(portOpen) System.out.println("Port open");
        if(portParameters) System.out.println("Port parameters set");
        System.out.println("Conexión establecida con el puerto");
	}
	
	ActionListener taskPerformer = new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
	    	System.out.println("Task Performed");
	    	entrando = false;
	    	saliendo = false;
	    	timer.stop();
	    }
	};
	
	private void iniciarTimer() {
		timer = new Timer(TIEMPO_CRUZAR, taskPerformer);
		timer.start();
		System.out.println("Timer Iniciado--------------------");
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		char caracter;
		if (e.getPropertyName().equals(SerialReader.TAG_RECIEVED)) {
			String stringCaracter = (String) e.getNewValue();
			caracter = stringCaracter.charAt(0);
			switch (caracter) {
				//default: System.out.println("Other");break;
				case SENSOR_ENTRADA: {
					//System.out.println("SENSOR_ENTRADA");
					if(saliendo) {
						if(timer.isRunning()) {
							numeroPersonas--;
							System.out.println("saliendo");
							salidaDetectada = true;
					    	saliendo = false;
						}
						timer.stop();
					}
					else if (!entrando) {
						entrando = true;
						iniciarTimer();
					}
					break;
				}
				case SENSOR_SALIDA:
					//System.out.println("SENSOR_SALIDA");
					if(entrando) {
						if(timer.isRunning()) {
							numeroPersonas++;
							try {
								if(Principal.getGestor().getHabitacionElegida().getAforo() == numeroPersonas ) {
									port.writeBytes("_".getBytes(), "_".getBytes().length);
								}
							} finally {}
							System.out.println("entrando");
							entradaDetectada = true;
							entrando = false;
						}
						timer.stop();
					}
					else if (!saliendo) {
						saliendo = true;
						iniciarTimer();
					}
					break;
			}
		}
	}
	
}
