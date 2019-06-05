package gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import businessLogic.BLFacadeImplementation;
import domain.User;
import domain.UserSingleton;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUIChangePassword extends JDialog{
	private BLFacadeImplementation bl;
	private JPasswordField pswCurrent;
	private JPasswordField pswNew;
	private JPasswordField pswNew2;
	static UserSingleton us = UserSingleton.getInstance();

	/**
	 * Create the application.
	 */
	public GUIChangePassword() {
		BLFacadeImplementation bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		initialize();
	}
	
	private void setBusinessLogic(BLFacadeImplementation bl2) {
		this.bl = bl2;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 336, 228);
		setResizable(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 11, 301, 41);
		panel.add(panel_1);

		JLabel lblAdministrativia = new JLabel("CHANGE PASSWORD");
		lblAdministrativia.setVerticalAlignment(SwingConstants.CENTER);
		lblAdministrativia.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministrativia.setForeground(Color.BLACK);
		lblAdministrativia.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblAdministrativia.setBackground(Color.GRAY);
		lblAdministrativia.setBounds(0, 0, 301, 40);
		panel_1.add(lblAdministrativia);
		
		JLabel lblCurrentPaswword = new JLabel("Current password:");
		lblCurrentPaswword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentPaswword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPaswword.setBounds(10, 63, 156, 17);
		panel.add(lblCurrentPaswword);
		
		pswCurrent = new JPasswordField();
		pswCurrent.setBounds(169, 63, 142, 20);
		panel.add(pswCurrent);
		
		JLabel lblNewPassword = new JLabel("New password:");
		lblNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewPassword.setBounds(10, 95, 156, 17);
		panel.add(lblNewPassword);
		
		JLabel lblRepeatNewPassword = new JLabel("Repeat new password:");
		lblRepeatNewPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRepeatNewPassword.setBounds(10, 126, 158, 17);
		panel.add(lblRepeatNewPassword);
		
		pswNew = new JPasswordField();
		pswNew.setBounds(169, 95, 142, 20);
		panel.add(pswNew);
		
		pswNew2 = new JPasswordField();
		pswNew2.setBounds(169, 126, 142, 20);
		panel.add(pswNew2);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(20, 157, 134, 23);
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(169, 157, 134, 23);
		panel.add(btnCancel);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bl.checkCredentials(us.getUserName(), new String(pswCurrent.getPassword()))) {
					if(new String(pswNew.getPassword()).equals(new String(pswNew2.getPassword()))) {
						if(bl.changePassword(us.getUserName(), new String(pswNew.getPassword()))) {
							JOptionPane.showMessageDialog(null, "Password changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else
							JOptionPane.showMessageDialog(null, "An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "New password fields don't match.", "Error", JOptionPane.ERROR_MESSAGE);
						pswNew.setText(null);
						pswNew2.setText(null);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Current password is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
					pswCurrent.setText(null);
					pswNew.setText(null);
					pswNew2.setText(null);
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
