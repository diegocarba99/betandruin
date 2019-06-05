package gui.walletGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacadeImplementation;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class GUIWithdrawalFunds extends JFrame {

	private JTextField txtEuros;
	private JTextField txtPoints;
	private BLFacadeImplementation bl;

	/**
	 * Create the application.
	 */
	public GUIWithdrawalFunds() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				GUIWalletPanel main = new GUIWalletPanel();
				dispose();
				main.setVisible(true);
			}
		});
		BLFacadeImplementation bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		initialize();
	}

	private void setBusinessLogic(BLFacadeImplementation bl) {
		this.bl = bl;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 373, 254);
		this.setAlwaysOnTop(true);
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());

		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.LIGHT_GRAY);

		JLabel lblWithdrawalFunds = new JLabel("WITHDRAW FUNDS");
		lblWithdrawalFunds.setVerticalAlignment(SwingConstants.CENTER);
		lblWithdrawalFunds.setHorizontalAlignment(SwingConstants.CENTER);
		lblWithdrawalFunds.setForeground(Color.BLACK);
		lblWithdrawalFunds.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblWithdrawalFunds.setBackground(Color.GRAY);

		JLabel lblAmountToWithdraw = new JLabel("Available amount:");
		lblAmountToWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtEuros = new JTextField();
		txtEuros.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar()) || (e.getKeyChar() == '.')) {
					String t = Double.toString((Double.parseDouble(txtEuros.getText()) * 10));
					txtPoints.setText(t);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()) && !(e.getKeyChar() == '.')) {
					e.consume();
				}
			}
		});
		txtEuros.setColumns(10);

		txtPoints = new JTextField();
		txtPoints.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar())) {
					String t = Double.toString((Double.parseDouble(txtPoints.getText()) / 10));
					txtEuros.setText(t);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()) && !(e.getKeyChar() == '.')) {
					e.consume();
				}
			}
		});
		txtPoints.setColumns(10);

		JLabel lblEuros = new JLabel("Euros:");

		JLabel lblBrpoints = new JLabel("BRPoints:");

		JLabel lblAvailable = new JLabel(bl.getBalance() + "€" + " | " + bl.getPoints() + " points");
		lblAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAvailable.setHorizontalAlignment(SwingConstants.LEFT);

		JButton btnWithdraw = new JButton("WITHDRAW");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Double.parseDouble(txtEuros.getText()) <= Double.parseDouble(bl.getBalance())) {
					int option = JOptionPane.showConfirmDialog(null, "Do you want to withdraw " + txtEuros.getText()
							+ "€ [" + txtPoints.getText() + " points]? ", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (option == 0) {
						bl.movementWithdraw(Double.parseDouble(txtEuros.getText()));
						JOptionPane.showMessageDialog(null, txtEuros.getText() + "€ have been withdrawed.", " ",
								JOptionPane.INFORMATION_MESSAGE);
						lblAvailable.setText(bl.getBalance() + "€" + " | " + bl.getPoints() + " points");
						txtEuros.setText("");
						txtPoints.setText("");
					}
				} else
					JOptionPane.showMessageDialog(null, "You don't have enough funds.", "FUNDS ERROR", JOptionPane.ERROR_MESSAGE);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblAmountToWithdraw, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAvailable, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(123)
					.addComponent(btnWithdraw, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
					.addGap(121))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(58)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblEuros, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(txtEuros, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBrpoints, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(58)
								.addComponent(txtPoints, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))))
					.addGap(87))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAmountToWithdraw, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAvailable))
					.addGap(9)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblEuros))
						.addComponent(txtEuros, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblBrpoints))
						.addComponent(txtPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnWithdraw, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblWithdrawalFunds, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
					.addGap(10))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(11)
					.addComponent(lblWithdrawalFunds))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
	}
}
