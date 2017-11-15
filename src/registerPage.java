import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class RegisterPage extends JFrame {

	private JPanel registerMainPanel;
	private JTextField userNameText;
	private JTextField passwordsText;
	private JTextField ifExistUser;
	private WaterModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPage frame = new RegisterPage();
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
	public RegisterPage() {
		setTitle("SeaS Register Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		registerMainPanel = new JPanel();
		registerMainPanel.setForeground(Color.BLACK);
		registerMainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(registerMainPanel);
		registerMainPanel.setLayout(null);
		
		JLabel userNameLabel = new JLabel("User Name");
		userNameLabel.setBounds(61, 75, 81, 16);
		registerMainPanel.add(userNameLabel);
		
		JLabel passwordsLabel = new JLabel("Passwords");
		passwordsLabel.setBounds(61, 143, 81, 16);
		registerMainPanel.add(passwordsLabel);
		
		userNameText = new JTextField();
		userNameText.setBounds(214, 70, 170, 26);
		registerMainPanel.add(userNameText);
		userNameText.setColumns(10);
		
		passwordsText = new JTextField();
		passwordsText.setBounds(214, 138, 170, 26);
		registerMainPanel.add(passwordsText);
		passwordsText.setColumns(10);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.setBounds(166, 222, 117, 29);
		registerMainPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Test popup message");
				//Object input = JOptionPane.showInputDialog("Enter test");
				//System.out.println("You entered: " + input);
				System.out.println();
				
			}
			
		});
		
		ifExistUser = new JTextField();
		ifExistUser.setHorizontalAlignment(SwingConstants.CENTER);
		ifExistUser.setText("Invalid User Input");
		ifExistUser.setBounds(64, 184, 321, 26);
		registerMainPanel.add(ifExistUser);
		ifExistUser.setColumns(10);
	}

}
