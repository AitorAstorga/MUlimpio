package popbl4.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;


public class ListaMesas extends AbstractListModel<Mesa> {
	List<Mesa> mesas;
	
	public ListaMesas() {
		mesas = new ArrayList<>();
	}
	
	public void add(Mesa mesa) {
		mesas.add(mesa);
		this.fireContentsChanged(mesas, 0, mesas.size());
	}
	
	public void borrar(int index) {
		mesas.remove(index);
		this.fireContentsChanged(mesas, 0, mesas.size());
	}
	
	public List<Mesa> getMesas() {
		return mesas;
	}

	@Override
	public Mesa getElementAt(int index) {
		return mesas.get(index); 
	}

	@Override
	public int getSize() {
		return mesas.size(); 
	}

	public void clear() {
		mesas.clear();
		this.fireContentsChanged(mesas, 0, mesas.size());
	}
	
}
