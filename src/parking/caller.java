package parking;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.xml.validation.Validator;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class caller {

	private JFrame frame;
	private JTextField match;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					caller window = new caller();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public caller() {
		initialize();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/valid","root","");
		}
		catch(ClassNotFoundException ex)
		{
			
		}
		catch(SQLException ex)
		{
			
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		match = new JTextField();
		match.setBounds(55, 93, 300, 40);
		desktopPane.add(match);
		match.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter Todays Secret Code");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(45, 10, 352, 103);
		desktopPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Enter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = match.getText();
				try {
//					pst = con.prepareStatement("select * from login where username=?");
//					pst.setString(1, username);
//					boolean ans = pst.execute();
					String h = "tirthoraj";
					if(username.compareTo(h)==0)
					{
						parking.funcall();
					}else {
						JOptionPane.showMessageDialog(null,"Wrong Credentials");
					}
					
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(189, 143, 166, 34);
		desktopPane.add(btnNewButton);
	}
}
