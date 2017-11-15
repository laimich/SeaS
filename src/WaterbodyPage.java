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

import javax.swing.JButton;

public class WaterbodyPage extends JFrame {

	private JPanel waterbodyPanel;
	private WaterModel model;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WaterbodyPage frame = new WaterbodyPage();
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
	public WaterbodyPage() {
		setTitle("Search Waterbody");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 315);
		waterbodyPanel = new JPanel();
		waterbodyPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(waterbodyPanel);
		waterbodyPanel.setLayout(null);
		
		JLabel waterbNameLabel = new JLabel("Waterbody Name:");
		waterbNameLabel.setBounds(42, 35, 120, 16);
		waterbodyPanel.add(waterbNameLabel);
		
		JLabel locLabel = new JLabel("Location:");
		locLabel.setBounds(42, 88, 61, 16);
		waterbodyPanel.add(locLabel);
		
		JLabel sourceLabel = new JLabel("Source/WaterName");
		sourceLabel.setBounds(42, 116, 120, 16);
		waterbodyPanel.add(sourceLabel);
		
		JLabel rateLabel = new JLabel("Rating: #");
		rateLabel.setBounds(42, 191, 61, 16);
		waterbodyPanel.add(rateLabel);
		
		JLabel avgLabel = new JLabel("#");
		//avgLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		avgLabel.setBounds(42, 211, 41, 28);
		waterbodyPanel.add(avgLabel);
		
		JLabel totalNumLabel = new JLabel("(#)");
		//totalNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		totalNumLabel.setBounds(60, 211, 41, 28);
		waterbodyPanel.add(totalNumLabel);
		
		JButton btnNewButton = new JButton("+");
		btnNewButton.setFont(new Font("plain", Font.PLAIN, 20));
		btnNewButton.setBounds(165, 200, 50, 40);
		btnNewButton.setBackground(null);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object input = JOptionPane.showInputDialog("Enter rating: ");
				//System.out.println("You entered: " + input);
			}
		});
		waterbodyPanel.add(btnNewButton);
		
		JLabel waterName = new JLabel("Name");
		waterName.setBounds(42, 60, 120, 16);
		waterbodyPanel.add(waterName);
		
		JLabel locationName = new JLabel("Name");
		locationName.setBounds(127, 88, 97, 16);
		waterbodyPanel.add(locationName);
		
		JLabel source = new JLabel("The Source");
		source.setBounds(42, 141, 179, 16);
		waterbodyPanel.add(source);
	}
}
