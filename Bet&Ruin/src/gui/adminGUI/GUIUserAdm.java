package gui.adminGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.User;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class GUIUserAdm extends JFrame {
	private BLFacade bl;
	private User use;

	/**
	 * Create the application.
	 */
	public GUIUserAdm(String ide) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIMainAdm win = new GUIMainAdm("User");
				win.setVisible(true);
				setVisible(false);
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getFocusOwner();
			}
		});
		BLFacade bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		use = bl.getUserByID(ide).getUser();
		initialize();
	}

	private void setBusinessLogic(BLFacade bl2) {
		this.bl = bl2;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 354, 210);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);

		JLabel lblUsersDetails = new JLabel("USERS DETAILS");
		lblUsersDetails.setVerticalAlignment(SwingConstants.CENTER);
		lblUsersDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersDetails.setForeground(Color.BLACK);
		lblUsersDetails.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblUsersDetails.setBackground(Color.GRAY);
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

		JLabel label1 = new JLabel("User ID:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label3 = new JLabel("User name:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label2 = new JLabel("VWallet ID:");
		label2.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnDeleteUser = new JButton("Delete");
		btnDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel label4 = new JLabel("Administrator:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblUserID = new JLabel(Integer.toString(use.getId()));
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblUserName = new JLabel(use.getUserName());
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblAdmin = new JLabel(Boolean.toString(use.isAdmin()));
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblWallet = new JLabel(Integer.toString(use.getWallet().getID()));
		lblWallet.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnOpenWallet = new JButton("Wallet");
		btnOpenWallet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(62)
							.addComponent(lblUserID, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
						.addComponent(label1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addComponent(label3, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblUserName, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(label2, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(lblWallet, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED))
					.addComponent(label4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblAdmin, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(51)
					.addComponent(btnDeleteUser, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
					.addGap(30)
					.addComponent(btnOpenWallet, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
					.addGap(57))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblUserID, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addComponent(label1)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label3))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblUserName, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label2)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label4))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblAdmin, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblWallet, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDeleteUser, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOpenWallet, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
		);
		getContentPane().setLayout(groupLayout);

		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Do you want to delete this user?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (option == 0) {
					if (bl.deleteUser(Integer.toString(use.getId()))) {
						JOptionPane.showMessageDialog(null, "User deleted.", " ", JOptionPane.INFORMATION_MESSAGE);
						GUIMainAdm win = new GUIMainAdm("User");
						win.setVisible(true);
						setVisible(false);						
					} else
						JOptionPane.showMessageDialog(null, "User not deleted. An error occurred.", " ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnOpenWallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIVWalletAdm win = new GUIVWalletAdm(Integer.toString(use.getWallet().getID()));
				win.setVisible(true);
				setVisible(false);
			}
		});
	}
}
