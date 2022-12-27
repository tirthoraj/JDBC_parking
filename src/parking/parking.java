package parking;

import java.awt.EventQueue;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
public class parking {

	private JFrame frame;
	private JTextField vno;
	private JTextField vtype;
	private JTextField price;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTextField searchVno;

	/**
	 * Launch the application.
	 */
	public static void funcall() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					parking window = new parking();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public parking() {
		initialize();
		connect();
		table_load();
	}

	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable disp;
	private JTextField result;
	public void connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/parkingdb","root","");
		}
		catch(ClassNotFoundException ex)
		{
			
		}
		catch(SQLException ex)
		{
			
		}
	}
	
	public void table_load()
	{
		try {
			pst = con.prepareStatement("select id AS Transaction_ID,vno AS Vehicle, vtype AS Wheels,price AS Amount from parking");
			rs = pst.executeQuery();
			disp.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e){
			e.printStackTrace();
			
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 914, 505);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Parking System");
		lblNewLabel.setFont(new Font("STZhongsong", Font.BOLD, 20));
		lblNewLabel.setBounds(363, -42, 165, 113);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Register", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 44, 458, 276);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Vehicle No");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 25));
		lblNewLabel_1.setBounds(10, 42, 190, 36);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.BOLD, 25));
		lblNewLabel_1_1_1.setBounds(10, 171, 80, 29);
		panel.add(lblNewLabel_1_1_1);
		
		vno = new JTextField();
		vno.setBounds(175, 44, 222, 34);
		panel.add(vno);
		vno.setColumns(10);
		
		vtype = new JTextField();
		vtype.setColumns(10);
		vtype.setBounds(175, 131, 222, 34);
		panel.add(vtype);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(175, 175, 222, 34);
		panel.add(price);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String vehno,vehtype; int vehprice;
				
				vehno = vno.getText();
				vehtype = vtype.getText();
				vehprice = Integer.valueOf(vehtype) * 20;
				
				try {
					pst = con.prepareStatement("insert into parking(vno,vtype,price)values(?,?,?)");
					pst.setString(1, vehno);
					pst.setString(2, vehtype);
					pst.setInt(3, vehprice);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Added");
					table_load();
					vno.setText("");
					vtype.setText("");
					price.setText("");
					vno.requestFocus();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 25));
		btnNewButton.setBounds(36, 219, 113, 47);
		panel.add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Verdana", Font.BOLD, 25));
		btnExit.setBounds(159, 219, 122, 47);
		panel.add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vno.setText("");
				vtype.setText("");
				price.setText("");
				vno.requestFocus();
			}
		});
		btnClear.setFont(new Font("Verdana", Font.BOLD, 25));
		btnClear.setBounds(291, 219, 106, 47);
		panel.add(btnClear);
		
		JLabel lblNewLabel_1_1 = new JLabel("Wheels");
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(10, 125, 113, 36);
		panel.add(lblNewLabel_1_1);
		
		JButton validate = new JButton("Validate");
		validate.setFont(new Font("Verdana", Font.BOLD, 20));
		validate.setForeground(Color.BLACK);
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String num = vno.getText();
			       String regex = "^[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}$";
			       Pattern pattern = Pattern.compile(regex); 
			       Matcher matcher = pattern.matcher(num); 
			       if(matcher.matches())
			       {
			    	   result.setText("Valid");
			       }else {
			    	   result.setText("In-Valid");
			       }
			}
		});
		validate.setBounds(272, 85, 125, 36);
		panel.add(validate);
		
		result = new JTextField();
		result.setBounds(175, 88, 87, 33);
		panel.add(result);
		result.setColumns(10);
		
		table = new JTable();
		table.setBounds(527, 202, 52, -34);
		frame.getContentPane().add(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 340, 458, 95);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Vehicle No");
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 23));
		panel_1.add(lblNewLabel_2);
		
		searchVno = new JTextField();
		searchVno.setFont(new Font("Tahoma", Font.PLAIN, 15));
		searchVno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = searchVno.getText();
					pst = con.prepareStatement("select vno,vtype,price from parking where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true)
					{
						String vehno = rs.getString(1);
						String vehtype = rs.getString(2);
						String vprice = rs.getString(3);
						
						vno.setText(vehno);
						vtype.setText(vehtype);
						price.setText(vprice);
							
					}else
					{
						vno.setText("");
						vtype.setText("");
						price.setText("");
					}
				}
				catch (SQLException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		searchVno.setColumns(10);
		panel_1.add(searchVno);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String vehno,vehtype,vehprice,bid;
				
				vehno = vno.getText();
				vehtype = vtype.getText();
				vehprice = price.getText();
				bid = searchVno.getText();

				
				try {
					pst = con.prepareStatement("update parking set vno = ?,vtype = ?,price = ? where id = ?");
					pst.setString(1, vehno);
					pst.setString(2, vehtype);
					pst.setString(3, vehprice);
					pst.setString(4,bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Updated");
					table_load();
					vno.setText("");
					vtype.setText("");
					price.setText("");
					vno.requestFocus();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Verdana", Font.BOLD, 25));
		btnUpdate.setBounds(498, 340, 194, 95);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bid;
				
				bid = searchVno.getText();

				
				try {
					pst = con.prepareStatement("delete from parking where id = ?");
					pst.setString(1,bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Deleted");
					table_load();
					vno.setText("");
					vtype.setText("");
					price.setText("");
					vno.requestFocus();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		btnDelete.setFont(new Font("Verdana", Font.BOLD, 25));
		btnDelete.setBounds(702, 340, 188, 95);
		frame.getContentPane().add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(498, 43, 392, 276);
		frame.getContentPane().add(scrollPane);
		
		disp = new JTable();
		scrollPane.setViewportView(disp);
	}
}
