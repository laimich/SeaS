import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;

public class LocationPage extends JFrame {

	private JPanel contentPane;


//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LocationPage frame = new LocationPage(new WaterModel());
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
	public LocationPage(WaterModel model) {
		setTitle("Search Location");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//get location and its average rating
		String loc = model.getSearchName();
		String avg = model.getLocationAvgRating();
		
		//get waterbodies and their origin name for the location
		ArrayList<String[]> waterbodies = new ArrayList<String[]>();
		waterbodies = model.getLocationWaterbodies();
		
		//set up list to display the waterbodies and their origin
		DefaultListModel waterItems = new DefaultListModel();
		DefaultListModel originItems = new DefaultListModel();
		waterItems.addElement("Origin Name");
		waterItems.addElement("---------------------------------");
		originItems.addElement("Waterbody Name");
		originItems.addElement("---------------------------------");
		for(int x = 0; x < waterbodies.size(); x++) {
			String[] entry = waterbodies.get(x);
			waterItems.addElement(entry[0]);
			originItems.addElement(entry[1]);
		}
		
		JLabel lblLocation = new JLabel("Location: ");
		lblLocation.setBounds(44, 22, 61, 16);
		contentPane.add(lblLocation);
		
		JLabel locationName = new JLabel(loc);
		locationName.setBounds(117, 22, 116, 16);
		contentPane.add(locationName);
		
		JLabel lblAvgRating = new JLabel("Avg Rating:");
		lblAvgRating.setBounds(44, 52, 79, 16);
		contentPane.add(lblAvgRating);
		
		JLabel avgRating = new JLabel(avg);
		avgRating.setBounds(135, 52, 61, 16);
		contentPane.add(avgRating);
		
		JList waterbodyList = new JList(waterItems);
		waterbodyList.setBounds(44, 82, 140, 200);
		contentPane.add(waterbodyList);
		
		JList originList = new JList(originItems);
		originList.setBounds(190, 82, 140, 200);
		contentPane.add(originList);
	
		setVisible(true);
	}
}
