package gui.adminGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.User;
import domain.VWallet;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class GUIVWalletAdm extends JFrame{
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private VWallet vw;
	private User us;
	
	/**
	 * Create the application.
	 */
	public GUIVWalletAdm(String ide) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIUserAdm win = new GUIUserAdm( Integer.toString( us.getId() ) );
				win.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getFocusOwner();
			}
		});
		BLFacade bl = new BLFacadeImplementation();
		vw = bl.getWalletByID(ide);
		us = bl.getUserByWalletID(ide);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 354, 210);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);

		JLabel lblUsersDetails = new JLabel("VWALLET DETAILS");
		lblUsersDetails.setVerticalAlignment(SwingConstants.CENTER);
		lblUsersDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersDetails.setForeground(Color.BLACK);
		lblUsersDetails.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblUsersDetails.setBackground(Color.GRAY);
		
		JLabel label1 = new JLabel("VWallet ID:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label2 = new JLabel("User ID:");
		label2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label4 = new JLabel("Funds:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblBrPoints = new JLabel("B&R points:");
		lblBrPoints.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label3 = new JLabel("User name:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label6 = new JLabel("Creation date:");
		label6.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblWalletID = new JLabel(Integer.toString(vw.getID()));
		lblWalletID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblFunds = new JLabel(Double.toString(vw.getBalance())+"�");
		lblFunds.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblUserID = new JLabel(Integer.toString(us.getId()));
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		JLabel lblDate = new JLabel(dateFormat.format(vw.getDate()));
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblPoints = new JLabel(Double.toString(vw.getPoints()));
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblUserName = new JLabel(us.getUserName());
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
					.addGap(12))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(label1)
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(143)
							.addComponent(lblDate, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(42)
							.addComponent(label6, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWalletID, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
							.addGap(177)))
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label4, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(209)
							.addComponent(lblPoints, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(125)
							.addComponent(lblBrPoints, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addComponent(lblFunds, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
							.addGap(177)))
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(125)
							.addComponent(label3))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(61)
							.addComponent(lblUserID, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
							.addGap(74))
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(lblUserName, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
					.addGap(12))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label1)
						.addComponent(lblDate)
						.addComponent(label6)
						.addComponent(lblWalletID))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label4)
						.addComponent(lblPoints)
						.addComponent(lblBrPoints)
						.addComponent(lblFunds))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label3)
						.addComponent(lblUserID)
						.addComponent(label2)
						.addComponent(lblUserName)))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblUsersDetails, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
					.addGap(9))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblUsersDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(8))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
}
