



import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class logInPage extends JFrame {

	private JPanel logInMainPanel;
	private JTextField userNameText;
	private JTextField passwordText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logInPage frame = new logInPage();
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
	public logInPage() {
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
		
		JButton logInButton = new JButton("LOG IN");
		logInButton.setBounds(256, 213, 117, 29);
		logInMainPanel.add(logInButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(80, 213, 117, 29);
		logInMainPanel.add(registerButton);
	}
}
