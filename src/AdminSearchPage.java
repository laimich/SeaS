import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class AdminSearchPage extends JFrame {

	private JPanel contentPane;
	private JTable waterbodyInfoTable;

	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminSearchPage frame = new AdminSearchPage(new WaterModel());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the frame.
	 */
	public AdminSearchPage(WaterModel model, String txtEnterWaterbody) {
		setTitle("SeaS Admin Search Waterbody");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel waterbodyNameLabel = new JLabel("Waterbody: " + model.getSearchName());
		waterbodyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		waterbodyNameLabel.setBounds(130, 6, 151, 21);
		contentPane.add(waterbodyNameLabel);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(140, 225, 117, 29);
		contentPane.add(updateButton);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {
				int credential = model.getWaterCredential();
				if(credential != -1) {
					AdminUpdateWaterCredPage updatePage = new AdminUpdateWaterCredPage(model, credential);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: Invalid waterbody.");
				}
				
			}
			
		});
		
		JScrollPane scrollInfoPane = new JScrollPane();
		scrollInfoPane.setBounds(48, 39, 314, 169);
		contentPane.add(scrollInfoPane);
		
		
		ArrayList<String[]> info = new ArrayList<String[]>();
		try {
			info = model.getAdminWaterbodySearch(model.getSearchName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] columnNames = {"#", "UserID", "Rating", "Review Date"};
		DefaultTableModel table = new DefaultTableModel(columnNames, info.size());
		for(int x = 0; x < info.size(); x++) {
			table.insertRow(x, info.get(x));
		}
		waterbodyInfoTable = new JTable(table);
//		waterbodyInfoTable.setCellSelectionEnabled(false);
		scrollInfoPane.setViewportView(waterbodyInfoTable);
		
//		JButton btnHome = new JButton("Home");
//		btnHome.setBounds(285, 3, 81, 29);
//		contentPane.add(btnHome);
		setVisible(true);
	}
}
