import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame implements ChangeListener {

	static int selectedTab = 0;
	static int currentTab = 0;
	
	CarPanel carPanel = new CarPanel();
	BrandPanel brandPanel = new BrandPanel();
	SalePanel salePanel = new SalePanel();
	JTabbedPane tab = new JTabbedPane();
	
	public MainFrame() {
		this.setSize(500, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tab.add(carPanel,"Configure Car");
		tab.add(brandPanel, "Configure Brand");
		tab.add(salePanel, "Sales");
		tab.setSize(500,600);
		this.add(tab);
		this.setVisible(true);
		
	}
	
	public void stateChanged(ChangeEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        selectedTab = tabbedPane.getSelectedIndex();
        currentTab = selectedTab;
        
    }

}
