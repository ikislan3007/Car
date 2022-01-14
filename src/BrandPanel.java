import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BrandPanel extends JPanel {

	static Connection conn = null;
	static PreparedStatement state = null;

	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);
	int id = -1;

	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JLabel brandsL = new JLabel("Brand");
	JLabel countryL = new JLabel("Country:");

	JTextField brandsTF = new JTextField();
	JTextField countryTF = new JTextField();

	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton resetBtn = new JButton("Reset");
	JButton editBtn = new JButton("Edit");
	JComboBox<String> searchCombo = new JComboBox<String>();
	JButton searchBtn = new JButton("Search");

	public BrandPanel() {
		this.setSize(500, 600);

		this.setLayout(new GridLayout(3, 1));

		// -------------------------------------------------------
		upPanel.setLayout(new GridLayout(5, 2));

		upPanel.add(brandsL);
		upPanel.add(brandsTF);
		upPanel.add(countryL);
		upPanel.add(countryTF);

		this.add(upPanel);

		// -------------------------------------------------------
		midPanel.add(addBtn);
		midPanel.add(deleteBtn);
		midPanel.add(editBtn);
		midPanel.add(searchBtn);
		midPanel.add(resetBtn);

		this.add(midPanel);
		addBtn.addActionListener(new AddAction());
		searchBtn.addActionListener(new SearchAction());
		deleteBtn.addActionListener(new DeleteAction());
		editBtn.addActionListener(new EditAction());
		resetBtn.addActionListener(new ResetAction());
		// downPanel

		this.add(downPanel);
		downPanel.add(scroller);
		scroller.setPreferredSize(new Dimension(450, 160));
		table.setModel(DBBrandHelper.getAllData());
		table.addMouseListener(new TableListener());

		this.setVisible(true);

	}// end constructor

	class DeleteAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBBrandHelper.getConnection();

			table.setModel(DBBrandHelper.getAllData());

		}

	}// end DeleteAction

	class ResetAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBBrandHelper.getConnection();
			String sql = "delete from brand where id=?";
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
				table.setModel(DBBrandHelper.getAllData());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class SearchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBBrandHelper.getConnection();
			String sql = "select * from brand where brands=? and country=?";
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, countryTF.getText());
				state.setString(2, brandsTF.getText());

				id = -1;
				table.setModel(new MyModel(state.executeQuery()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int row = table.getSelectedRow();
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			brandsTF.setText(table.getValueAt(row, 2).toString());
			countryTF.setText(table.getValueAt(row, 1).toString());

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void clearForm() {
		brandsTF.setText("");
		countryTF.setText("");

	}// end clearForm

	class EditAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			conn = DBBrandHelper.getConnection();
			String sql = "update brand set brands=?, country=? where id=?";

			try {
				state = conn.prepareStatement(sql);
				state.setString(1, countryTF.getText());
				state.setString(2, brandsTF.getText());
				state.setInt(3, id);
				state.execute();
				table.setModel(DBBrandHelper.getAllData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			clearForm();
		}

	}

	class AddAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			conn = DBBrandHelper.getConnection();
			String sql = "insert into brand values(null,?,?)";

			try {
				state = conn.prepareStatement(sql);
				state.setString(1, brandsTF.getText());
				state.setString(2, countryTF.getText());
				state.execute();
				table.setModel(DBBrandHelper.getAllData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			clearForm();
		}

	}// end class AddAction

}// end class MyFrame
