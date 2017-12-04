import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminViewAllReviewPage extends JFrame {

	private JPanel contentPane;
	private JTable waterbodyInfoTable;
	private WaterModel model;
	
	/* 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminViewAllReviewPage frame = new AdminViewAllReviewPage();
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
	public AdminViewAllReviewPage(WaterModel model) {
		setTitle("SeaS Admin View All Reviews");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ArrayList<String[]> info = new ArrayList<String[]>();
		try {
			info = model.getAllReviews();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] columnNames = { "UserID", "User", "Waterbody", "Date", "Rating" };
		DefaultTableModel table = new DefaultTableModel(columnNames, info.size());
		for(int x = 0; x < info.size(); x++) {
			table.insertRow(x, info.get(x));
		}
		
		JLabel viewReviewLabel = new JLabel("View All Reviews");
		viewReviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewReviewLabel.setBounds(130, 6, 151, 21);
		contentPane.add(viewReviewLabel);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(140, 225, 117, 29);
		contentPane.add(deleteButton);
		
		JScrollPane scrollInfoPane = new JScrollPane();
		scrollInfoPane.setBounds(48, 39, 314, 169);
		contentPane.add(scrollInfoPane);
		
		waterbodyInfoTable = new JTable(table);
		waterbodyInfoTable.setCellSelectionEnabled(false);
		scrollInfoPane.setViewportView(waterbodyInfoTable);
		
//		JButton btnHome = new JButton("Home");
//		btnHome.setBounds(285, 3, 81, 29);
//		contentPane.add(btnHome);
		
		setVisible(true);
	}

}
