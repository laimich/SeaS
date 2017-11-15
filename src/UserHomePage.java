import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;

public class UserHomePage extends JFrame {

	private JPanel homePagePanel;
	private JTextField seachArea;
	private WaterModel model;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserHomePage frame = new UserHomePage();
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
	public UserHomePage() {
		setTitle("User Homepage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 300);
		homePagePanel = new JPanel();
		homePagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(homePagePanel);
		homePagePanel.setLayout(null);
		
		seachArea = new JTextField();
		seachArea.setHorizontalAlignment(SwingConstants.CENTER);
		seachArea.setText("Enter WaterBody or Location");
		seachArea.setBounds(38, 31, 222, 26);
		homePagePanel.add(seachArea);
		seachArea.setColumns(10);
		
		JButton searchButton = new JButton("Go");
		searchButton.setBounds(272, 31, 79, 29);
		homePagePanel.add(searchButton);
		
		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeLabel.setBounds(83, 77, 79, 26);
		homePagePanel.add(welcomeLabel);
		
		JLabel userNameLabel = new JLabel("UserName");
		userNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		userNameLabel.setBounds(206, 77, 127, 26);
		homePagePanel.add(userNameLabel);
		
		JLabel credentLabel = new JLabel("Credentials:");
		credentLabel.setHorizontalAlignment(SwingConstants.LEFT);
		credentLabel.setBounds(83, 136, 79, 16);
		homePagePanel.add(credentLabel);
		
		JLabel revLabel = new JLabel("Reviews: ");
		revLabel.setHorizontalAlignment(SwingConstants.LEFT);
		revLabel.setBounds(83, 186, 79, 16);
		homePagePanel.add(revLabel);
		
		JLabel creNumLabel = new JLabel("XXXX");
		creNumLabel.setBounds(230, 136, 81, 16);
		homePagePanel.add(creNumLabel);
		
		JLabel revNumLabel = new JLabel("XXXX");
		revNumLabel.setBounds(230, 186, 81, 16);
		homePagePanel.add(revNumLabel);
	}
}
