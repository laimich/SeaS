


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

public class AdminHomePage extends JFrame {

	private JPanel AdminMainPanel;
	private JTextField txtEnterWaterbody;

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
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int searchID = model.getSearchID(txtEnterWaterbody.getText());
				if(searchID == 0) {
					JOptionPane.showMessageDialog(null, "Error: Invalid waterbody.");
				}
				else {
					if(model.getSearchType().equals("waterbody")) {
						AdminSearchPage page = new AdminSearchPage(model);
						dispose();
					}
					else if(model.getSearchType().equals("location")) {
						//only allow search for waterbody
						JOptionPane.showMessageDialog(null, "Error: Invalid waterbody.");
					}
				}
			}
			
		});
		
		JLabel lblWalcomeAdmin = new JLabel("Welcome Admin!");
		lblWalcomeAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblWalcomeAdmin.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblWalcomeAdmin.setBounds(131, 88, 141, 26);
		AdminMainPanel.add(lblWalcomeAdmin);
		
		JButton btnNewButton = new JButton("View All Reviews");
		btnNewButton.setBounds(65, 142, 273, 29);
		AdminMainPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AdminViewAllReviewPage page = new AdminViewAllReviewPage(model);
				dispose();
			}
			
		});
		
		JButton btnAddWaterbody = new JButton("Add Waterbody");
		btnAddWaterbody.setBounds(65, 194, 273, 29);
		AdminMainPanel.add(btnAddWaterbody);
		btnAddWaterbody.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
			{
				//get input for new waterbody, waterbody name, origin name, min credentials
				Object inputWaterbody = JOptionPane.showInputDialog("Enter name for new waterbody: ");
				Object inputOrigin = JOptionPane.showInputDialog("Enter name for existing origin: ");
				Object inputMinCred = JOptionPane.showInputDialog("Enter credentials for the waterbody (1-5): ");
				
				int minCred = 0;
				boolean originExist = model.doesOriginExist(inputOrigin.toString());
				
				//check that all inputs are valid
				if(inputWaterbody.toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Error: Invalid waterbody name.");
				}
				else if(!originExist) {
					JOptionPane.showMessageDialog(null, "Error: Origin name does not exist.");
				}
				else if(inputMinCred.toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Error: Invalid number for min credentials.");
					try {
						minCred = Integer.parseInt(inputMinCred.toString());
					}
					catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Error: Credentials must be a number.");
					}
					if(minCred < 1 || minCred > 5) {
						JOptionPane.showMessageDialog(null, "Error: Invalid number for min credentials.");
					}
				}
				else {
					model.addWaterbody(inputWaterbody.toString(), inputOrigin.toString(), minCred);
					JOptionPane.showMessageDialog(null, "Successfully added waterbody!");
				}
			}			
		});

		
		setVisible(true);
	}

}
