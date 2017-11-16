



import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class LogInPage extends JFrame {

	private JPanel logInMainPanel;
	private JTextField userNameText;
	private JTextField passwordText;
	private WaterModel model;


//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LogInPage frame = new LogInPage();
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
	public LogInPage(WaterModel model) {
		setTitle("SeaS Log In Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		logInMainPanel = new JPanel();
		logInMainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(logInMainPanel);
		logInMainPanel.setLayout(null);
		
		userNameText = new JTextField();
		userNameText.setBounds(215, 55, 158, 26);
		logInMainPanel.add(userNameText);
		userNameText.setColumns(10);
		
		passwordText = new JTextField();
		passwordText.setBounds(215, 132, 158, 26);
		logInMainPanel.add(passwordText);
		passwordText.setColumns(10);
		
		JLabel userNameLabel = new JLabel("User Name");
		userNameLabel.setBounds(93, 60, 74, 16);
		logInMainPanel.add(userNameLabel);
		
		JLabel passwordLabel = new JLabel("Passwords");
		passwordLabel.setBounds(93, 137, 74, 16);
		logInMainPanel.add(passwordLabel);
		
		JButton logInButton = new JButton("Log In");
		logInButton.setBounds(256, 213, 117, 29);
		logInMainPanel.add(logInButton);
		logInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = userNameText.getText().trim();
				String password = passwordText.getText().trim();
				if(username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid username/password combination");
					return;
				}
				if(model.isAccountAvailable(username)) {
					model.createAccount(username, password);
					LogInPage login = new LogInPage(model);
					dispose();
				}
				//check if login successfull, set current user
				
				//depend type of user, send to user or admin homepage
				//dispose();
			}
			
		});
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(80, 213, 117, 29);
		logInMainPanel.add(registerButton);
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RegisterPage reg = new RegisterPage(model);
				dispose();
			}
			
		});
		
		setVisible(true);
	}
}
