import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.swing.JButton;

public class WaterbodyPage extends JFrame {

	private JPanel waterbodyPanel;
	private WaterModel model;
	
	/**
	 * Create the frame.
	 */
	public WaterbodyPage(WaterModel model) {
		setTitle("Search Waterbody");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 350);
		waterbodyPanel = new JPanel();
		waterbodyPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(waterbodyPanel);
		waterbodyPanel.setLayout(null);
		
		TreeMap<String, String> search = model.getWaterbodySearch();
		
		JLabel waterbNameLabel = new JLabel("Waterbody: ");
		waterbNameLabel.setBounds(42, 35, 120, 16);
		waterbodyPanel.add(waterbNameLabel);
		
		JLabel locLabel = new JLabel("Location: ");
		locLabel.setBounds(42, 88, 61, 16);
		waterbodyPanel.add(locLabel);
		
		JLabel originLabel = new JLabel("Origin: ");
		originLabel.setBounds(42, 146, 120, 16);
		waterbodyPanel.add(originLabel);
		
		JLabel rateLabel = new JLabel("Rating (# given): ");
		rateLabel.setBounds(42, 205, 120, 16);
		waterbodyPanel.add(rateLabel);
		
		JLabel avgLabel = new JLabel(search.get("avgRating"));
		//avgLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		avgLabel.setBounds(42, 230, 41, 28);
		waterbodyPanel.add(avgLabel);
		
		JLabel totalNumLabel = new JLabel("( " + search.get("numRating") + " )");
		//totalNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		totalNumLabel.setBounds(60, 230, 41, 28);
		waterbodyPanel.add(totalNumLabel);
		
		JButton btnNewButton = new JButton("+");
		btnNewButton.setFont(new Font("plain", Font.PLAIN, 20));
		btnNewButton.setBounds(165, 235, 50, 40);
		btnNewButton.setBackground(null);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object input = JOptionPane.showInputDialog("Enter rating: ");
				//JOptionPane.
				System.out.println("You entered: " + input);
				int numRate = (int) Integer.parseInt(input.toString());
				try {
					boolean canAddRating = model.checkCredandInputReview(numRate);
					if (!canAddRating) {
						JOptionPane.showMessageDialog(null, "Error: You don't have the credentials to leave a rating.");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("You do not have the right access to thing item!!!");
					e.printStackTrace();
				}
			}
		});
		waterbodyPanel.add(btnNewButton);
		
		JLabel waterName = new JLabel(search.get("waterbodyName"));
		waterName.setBounds(127, 60, 120, 16);
		waterbodyPanel.add(waterName);
		
		JLabel locationName = new JLabel(search.get("location"));
		locationName.setBounds(127, 113, 97, 16);
		waterbodyPanel.add(locationName);
		
		JLabel origin = new JLabel(search.get("waterName"));
		origin.setBounds(127, 170, 179, 16);
		waterbodyPanel.add(origin);
		setVisible(true);
	}
}
