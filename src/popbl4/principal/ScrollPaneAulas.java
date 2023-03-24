package popbl4.principal;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;

public class ScrollPaneAulas extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ScrollPaneAulas() {
		setLayout(new MigLayout("", "[450px]", "[300px]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0,grow");
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		scrollPane.setRowHeaderView(panel);
		
		JList list = new JList();
		add(list, "cell 0 0,alignx left,aligny top");

	}

}
