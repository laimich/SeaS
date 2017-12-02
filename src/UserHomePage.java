import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserHomePage extends JFrame {

	private JPanel homePagePanel;
	private JTextField searchArea;
	private WaterModel model;

	/**
	 * Create the frame.
	 */
	public UserHomePage(WaterModel model) {
		setTitle("User Homepage");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 300);
		homePagePanel = new JPanel();
		homePagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(homePagePanel);
		homePagePanel.setLayout(null);
		
		//get current user's info
		String[] info = model.getUserHomeInfo();
		
		searchArea = new JTextField();
		searchArea.setHorizontalAlignment(SwingConstants.CENTER);
		searchArea.setText("Enter WaterBody or Location");
		searchArea.setBounds(38, 31, 222, 26);
		homePagePanel.add(searchArea);
		searchArea.setColumns(10);
		
		JButton searchButton = new JButton("Go");
		searchButton.setBounds(272, 31, 79, 29);
		homePagePanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int searchID = model.getSearchID(searchArea.getText());
				if(searchID == 0) {
					JOptionPane.showMessageDialog(null, "Invalid waterbody or location");
				}
				else {
					if(model.getSearchType().equals("waterbody")) {
						WaterbodyPage page = new WaterbodyPage(model);
						dispose();
					}
					else if(model.getSearchType().equals("location")) {
						LocationPage page = new LocationPage(model);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "Invalid waterbody or location");
					}
				}
			}
			
		});
		
		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeLabel.setBounds(83, 77, 79, 26);
		homePagePanel.add(welcomeLabel);
		
		JLabel userNameLabel = new JLabel(info[0]);
		userNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		userNameLabel.setBounds(206, 77, 127, 26);
		homePagePanel.add(userNameLabel);
		
		JLabel credentLabel = new JLabel("Credential level: ");
		credentLabel.setHorizontalAlignment(SwingConstants.LEFT);
		credentLabel.setBounds(83, 136, 110, 16);
		homePagePanel.add(credentLabel);
		
		JLabel revLabel = new JLabel("Reviews: ");
		revLabel.setHorizontalAlignment(SwingConstants.LEFT);
		revLabel.setBounds(83, 186, 79, 16);
		homePagePanel.add(revLabel);
		
		JLabel creNumLabel = new JLabel(info[1]);
		creNumLabel.setBounds(230, 136, 81, 16);
		homePagePanel.add(creNumLabel);
		
		JLabel revNumLabel = new JLabel(info[2]);
		revNumLabel.setBounds(230, 186, 81, 16);
		homePagePanel.add(revNumLabel);
		setVisible(true);
	}
}
