package gui.walletGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacadeImplementation;
import domain.Movement;
import domain.User;
import gui.mainGUI.GUIMainPage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class GUIWalletPanel extends JFrame {
	private BLFacadeImplementation bl;
	private boolean b;

	/**
	 * Create the application.
	 */
	public GUIWalletPanel() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(!b) {
					GUIMainPage mp = GUIMainPage.getInstance();
					mp.setVisible(true);
					dispose();
				}
			}
		});
		BLFacadeImplementation bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		initialize();
		b = false;
	}

	private void setBusinessLogic(BLFacadeImplementation bl) {
		this.bl = bl;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 402, 547);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());

		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setForeground(Color.WHITE);

		JLabel lblVirtualWallet = new JLabel("VIRTUAL WALLET");
		lblVirtualWallet.setVerticalAlignment(SwingConstants.CENTER);
		lblVirtualWallet.setHorizontalAlignment(SwingConstants.CENTER);
		lblVirtualWallet.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblVirtualWallet.setForeground(Color.BLACK);
		lblVirtualWallet.setBackground(Color.GRAY);

		JButton btnUploadFunds = new JButton("UPLOAD FUNDS");
		btnUploadFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIUploadFunds main = new GUIUploadFunds();	
				b = true;
				dispose();
				main.setVisible(true);
			}
		});
		btnUploadFunds.setFont(new Font("Tahoma", Font.BOLD, 17));

		JLabel lblLastMovements = new JLabel("LAST MOVEMENTS:");
		lblLastMovements.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JButton btnWithdrawFunds = new JButton("WITHDRAW FUNDS");
		btnWithdrawFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIWithdrawalFunds main = new GUIWithdrawalFunds();	
				b = true;
				dispose();
				main.setVisible(true);
			}
		});
		btnWithdrawFunds.setFont(new Font("Tahoma", Font.BOLD, 17));

		JLabel label1 = new JLabel("BALANCE:");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel label2 = new JLabel("RUIN POINTS: ");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblBalance = new JLabel(bl.getBalance());
		lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblPoints = new JLabel(bl.getPoints());
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 17));

		DefaultListModel<String> txtMovements = new DefaultListModel<>();
		JList<String> list = new JList<>(txtMovements);
		txtMovements.removeAllElements();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(82)
									.addComponent(lblBalance, GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE))
								.addComponent(label1, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label2, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(106)
									.addComponent(lblPoints, GroupLayout.PREFERRED_SIZE, 82, Short.MAX_VALUE))))
						.addComponent(btnUploadFunds, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
						.addComponent(lblLastMovements, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
						.addComponent(btnWithdrawFunds, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(lblBalance))
						.addComponent(label1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(label2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblPoints, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(btnUploadFunds, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblLastMovements, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(list, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
					.addGap(16)
					.addComponent(btnWithdrawFunds, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(67))
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(58)
					.addComponent(lblVirtualWallet, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(48))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(11)
					.addComponent(lblVirtualWallet))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

		ArrayList<Movement> lastMov = bl.getLastMovements();
		if (lastMov != null) {
			for (Movement m : lastMov)
				txtMovements.addElement(m.toString());
		}
	}
}
