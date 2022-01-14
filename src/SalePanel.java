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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SalePanel extends JPanel {
	static Connection conn = null;
	static PreparedStatement state = null;

	JTable carTable = new JTable();
	JScrollPane carTableScroller = new JScrollPane(carTable);

	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);
	int id = -1;

	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JLabel dateL = new JLabel("Date");
	JTextField dateTF = new JTextField();

	JLabel fNameL = new JLabel("First Name");
	JTextField fNameTF = new JTextField();

	JLabel lNameL = new JLabel("Last Name");
	JTextField lNameTF = new JTextField();

	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton resetBtn = new JButton("Reset");
	JButton editBtn = new JButton("Edit");
	JComboBox<String> searchCombo = new JComboBox<String>();
	JButton searchBtn = new JButton("Search");

	public SalePanel() {

		this.add(upPanel);
		upPanel.add(carTableScroller);
		carTable.setModel(DBBrandHelper.getAllCarData());
		carTableScroller.setPreferredSize(new Dimension(450, 160));
		// -------------------------------------------------------
		midPanel.setLayout(new GridLayout(2, 3));
		this.setSize(500, 600);
		midPanel.add(dateL);
		midPanel.add(dateTF);
		midPanel.add(fNameL);
		midPanel.add(fNameTF);
		midPanel.add(lNameL);
		midPanel.add(lNameTF);

		// -------------------------------------------------------
		midPanel.add(addBtn);
		midPanel.add(deleteBtn);
		midPanel.add(editBtn);
		midPanel.add(searchBtn);
		midPanel.add(resetBtn);

		this.add(midPanel);
		addBtn.addActionListener(new AddAction());
		deleteBtn.addActionListener(new DeleteAction());
		editBtn.addActionListener(new EditAction());
		resetBtn.addActionListener(new ResetAction());
		searchBtn.addActionListener(new SearchAction());
		// downPanel

		this.add(downPanel);
		downPanel.add(scroller);
		scroller.setPreferredSize(new Dimension(450, 160));
		table.setModel(DBBrandHelper.getAllSalesData());
		table.addMouseListener(new TableListener());

		this.setVisible(true);

	}// end constructor

	class DeleteAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBBrandHelper.getConnection();
			String sql = "delete from car where id=?";
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
				table.setModel(DBBrandHelper.getAllCarData());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}// end DeleteAction

	class ResetAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBBrandHelper.getConnection();

			table.setModel(DBBrandHelper.getAllCarData());

		}

	}

	class SearchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBBrandHelper.getConnection();
			String sql = "select * from car where model=? and caryear=?";
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, fNameTF.getText());

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
			dateTF.setText(table.getValueAt(row, 1).toString());
			fNameTF.setText(table.getValueAt(row, 2).toString());
			lNameTF.setText(table.getValueAt(row, 4).toString());
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
		dateTF.setText("");
		fNameTF.setText("");
		lNameTF.setText("");

	}// end clearForm

	class EditAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			conn = DBBrandHelper.getConnection();
			String sql = "update car set brand_id=?, model=?, caryear=?, price=?, comment=? where id=?";

			try {
				state = conn.prepareStatement(sql);
				state.setString(1, dateTF.getText());
				state.setString(2, fNameTF.getText());
				state.setString(4, lNameTF.getText());
				state.setInt(6, id);

				state.execute();
				table.setModel(DBBrandHelper.getAllCarData());
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
			String sql = "insert into car values(null,?,?,?,?,?)";

			try {
				state = conn.prepareStatement(sql);
				state.setString(1, dateTF.getText());
				state.setString(2, fNameTF.getText());
				state.setString(3, lNameTF.getText());
				state.execute();
				table.setModel(DBBrandHelper.getAllCarData());
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
}
