import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AdminViewAllReviewPage extends JFrame {

	private JPanel contentPane;
	private JTable waterbodyInfoTable;
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public AdminViewAllReviewPage() {
		setTitle("SeaS Admin View All Reviews");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 281);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel viewReviewLabel = new JLabel("View All Reviews");
		viewReviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewReviewLabel.setBounds(130, 6, 151, 21);
		contentPane.add(viewReviewLabel);
		
		JButton updateButton = new JButton("Update");
		updateButton.setBounds(70, 225, 117, 29);
		contentPane.add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(211, 225, 117, 29);
		contentPane.add(deleteButton);
		
		JScrollPane scrollInfoPane = new JScrollPane();
		scrollInfoPane.setBounds(48, 39, 314, 169);
		contentPane.add(scrollInfoPane);
		
		waterbodyInfoTable = new JTable();
		scrollInfoPane.setViewportView(waterbodyInfoTable);
		
		JButton btnHome = new JButton("Home");
		btnHome.setBounds(285, 3, 81, 29);
		contentPane.add(btnHome);
	}

}