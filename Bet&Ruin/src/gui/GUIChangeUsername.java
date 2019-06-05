package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import businessLogic.BLFacadeImplementation;
import domain.UserSingleton;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUIChangeUsername extends JFrame {
	static UserSingleton us = UserSingleton.getInstance();
	private BLFacadeImplementation bl;
	private JTextField txtCurrent;
	private JTextField txtNew;
	private JPasswordField pwdPass;

	/**
	 * Create the application.
	 */
	public GUIChangeUsername() {
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
		this.setBounds(100, 100, 329, 221);
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

		JLabel lblCurrentPaswword = new JLabel("Current user name:");
		lblCurrentPaswword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentPaswword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPaswword.setBounds(10, 63, 156, 17);
		panel.add(lblCurrentPaswword);

		JLabel lblNewPassword = new JLabel("New username:");
		lblNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewPassword.setBounds(10, 95, 156, 17);
		panel.add(lblNewPassword);

		JLabel lblRepeatNewPassword = new JLabel("Password:");
		lblRepeatNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepeatNewPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRepeatNewPassword.setBounds(10, 126, 158, 17);
		panel.add(lblRepeatNewPassword);

		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(20, 157, 134, 23);
		panel.add(btnSave);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(169, 157, 134, 23);
		panel.add(btnCancel);
		
		txtCurrent = new JTextField();
		txtCurrent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCurrent.setBounds(169, 63, 142, 20);
		panel.add(txtCurrent);
		txtCurrent.setColumns(10);
		
		txtNew = new JTextField();
		txtNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNew.setColumns(10);
		txtNew.setBounds(169, 95, 142, 20);
		panel.add(txtNew);
		
		pwdPass = new JPasswordField();
		pwdPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pwdPass.setBounds(169, 126, 144, 17);
		panel.add(pwdPass);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bl.checkCredentials(us.getUserName(), new String(pwdPass.getPassword())) && (us.getUserName().equals(txtCurrent.getText()))) {
						if(bl.changeUserName(txtCurrent.getText(), txtNew.getText())) {
							JOptionPane.showMessageDialog(null, "Username changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else
							JOptionPane.showMessageDialog(null, "Usename in use.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Password not correct or not current user.", "Error", JOptionPane.ERROR_MESSAGE);
					txtCurrent.setText(null);
					txtNew.setText(null);
					pwdPass.setText(null);
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
