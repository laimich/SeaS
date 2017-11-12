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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LocationPage frame = new LocationPage();
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
	public LocationPage() {
		setTitle("Search Location");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(44, 22, 61, 16);
		contentPane.add(lblLocation);
		
		JLabel lblSourcewaterName = new JLabel("Source/Water Name:");
		lblSourcewaterName.setBounds(44, 58, 131, 16);
		contentPane.add(lblSourcewaterName);
		
		JLabel lblAvgRating = new JLabel("Avg Rating:");
		lblAvgRating.setBounds(44, 127, 79, 16);
		contentPane.add(lblAvgRating);
		
		JList waterbodyList = new JList();
		waterbodyList.setBounds(44, 166, 177, 70);
		contentPane.add(waterbodyList);
		
		JLabel locationName = new JLabel("Name");
		locationName.setBounds(117, 22, 116, 16);
		contentPane.add(locationName);
		
		JLabel source = new JLabel("The Source");
		source.setBounds(44, 86, 79, 16);
		contentPane.add(source);
		
		JLabel avgRating = new JLabel("Number");
		avgRating.setBounds(135, 127, 61, 16);
		contentPane.add(avgRating);
	}
}
