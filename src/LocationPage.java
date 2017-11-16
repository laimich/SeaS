import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;

public class LocationPage extends JFrame {

	private JPanel contentPane;
	private WaterModel model;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LocationPage frame = new LocationPage(new WaterModel());
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
	public LocationPage(WaterModel model) {
		setTitle("Search Location");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(44, 22, 61, 16);
		contentPane.add(lblLocation);
		
		JLabel locationName = new JLabel("Name");
		locationName.setBounds(117, 22, 116, 16);
		contentPane.add(locationName);
		
		JLabel lblAvgRating = new JLabel("Avg Rating:");
		lblAvgRating.setBounds(44, 52, 79, 16);
		contentPane.add(lblAvgRating);
		
		JLabel avgRating = new JLabel("#");
		avgRating.setBounds(135, 52, 61, 16);
		contentPane.add(avgRating);
		
		JList waterbodyList = new JList();
		waterbodyList.setBounds(44, 82, 177, 200);
		contentPane.add(waterbodyList);
		//include source/watername in the waterbody list
	
		setVisible(true);
	}
}
