package popbl4.placa;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialReader implements SerialPortDataListener {
	public final static String TAG_RECIEVED = "received";
	private SerialPort port;
	PropertyChangeSupport support;
	
	public SerialReader(SerialPort port) {
		this.port = port;
		this.support = new PropertyChangeSupport(this);
	}
	
	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; 
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
	    byte[] newData = new byte[port.bytesAvailable()];
	    port.readBytes(newData, newData.length);
	    
	    support.firePropertyChange(TAG_RECIEVED, null, new String(newData));	
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
}
