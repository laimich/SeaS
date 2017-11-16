import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminHomePage extends JFrame {

	private JPanel AdminMainPanel;
	private JTextField txtEnterWaterbody;
	private WaterModel model;


//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminHomePage frame = new AdminHomePage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public AdminHomePage(WaterModel model) {
		setTitle("Admin Homepage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 404, 300);
		AdminMainPanel = new JPanel();
		AdminMainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(AdminMainPanel);
		AdminMainPanel.setLayout(null);
		
		txtEnterWaterbody = new JTextField();
		txtEnterWaterbody.setText("Enter Waterbody");
		txtEnterWaterbody.setBounds(26, 34, 224, 26);
		AdminMainPanel.add(txtEnterWaterbody);
		txtEnterWaterbody.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(258, 34, 117, 29);
		AdminMainPanel.add(btnSearch);
		
		JLabel lblWalcomeAdmin = new JLabel("Walcome Admin!");
		lblWalcomeAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblWalcomeAdmin.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblWalcomeAdmin.setBounds(131, 88, 141, 26);
		AdminMainPanel.add(lblWalcomeAdmin);
		
		JButton btnNewButton = new JButton("View All Resources");
		btnNewButton.setBounds(65, 142, 273, 29);
		AdminMainPanel.add(btnNewButton);
		
		JButton btnAddWaterbody = new JButton("Add Waterbody");
		btnAddWaterbody.setBounds(65, 194, 273, 29);
		AdminMainPanel.add(btnAddWaterbody);
		setVisible(true);
	}

}
