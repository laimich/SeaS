import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AdminUpdateWaterCredPage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUpdateWaterCredPage frame = new AdminUpdateWaterCredPage(new WaterModel(), 1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	

	/**
	 * Create the frame.
	 */
	public AdminUpdateWaterCredPage(WaterModel model, int oldCredential) {
		setTitle("Update water credential");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String waterbodyName = model.getSearchName();
		JLabel lblWaterbodyName = new JLabel(waterbodyName);
		lblWaterbodyName.setBounds(119, 10, 196, 32);
//		lblWaterbodyName.setFont(new Font("SimSun", Font.PLAIN, 28));
		lblWaterbodyName.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWaterbodyName);
		
		JLabel lblCurrentCredential = new JLabel("Current Credential:");
//		lblCurrentCredential.setFont(new Font("SimSun", Font.PLAIN, 16));
		lblCurrentCredential.setBounds(52, 87, 160, 32);
		contentPane.add(lblCurrentCredential);
		
		JLabel lblNewCredential = new JLabel("New Credential:");
//		lblNewCredential.setFont(new Font("SimSun", Font.PLAIN, 16));
		lblNewCredential.setBounds(50, 129, 162, 26);
		contentPane.add(lblNewCredential);
		
		
		JLabel label = new JLabel(oldCredential + "");
//		label.setFont(new Font("SimSun", Font.PLAIN, 16));
		label.setBounds(222, 87, 66, 32);
		contentPane.add(label);
		
		textField = new JTextField();
//		textField.setFont(new Font("SimSun", Font.PLAIN, 16));
		textField.setText("# 1-5");
		textField.setBounds(218, 128, 70, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
//		btnUpdate.setFont(new Font("SimSun", Font.PLAIN, 16));
		btnUpdate.setBounds(77, 197, 115, 32);
		contentPane.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {
				int newCredential = Integer.parseInt(textField.getText());
				model.updateWaterbodyCredential(newCredential);
				AdminSearchPage page = new AdminSearchPage(model);
				dispose();
			}
			
		});
		
		JButton btnCancel = new JButton("Cancel");
//		btnCancel.setFont(new Font("SimSun", Font.PLAIN, 16));
		btnCancel.setBounds(244, 197, 115, 32);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {
				AdminSearchPage page = new AdminSearchPage(model);
				dispose();
			}
			
		});
		setVisible(true);
	}
}
