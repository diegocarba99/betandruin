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

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

@SuppressWarnings("serial")
public class GUIUploadFunds extends JFrame {
	private JTextField txtEuros;
	private BLFacade bl;

	/**
	 * Create the application.
	 */
	public GUIUploadFunds() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				GUIWalletPanel main = new GUIWalletPanel();
				dispose();
				main.setVisible(true);
			}
		});
		BLFacade bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		initialize();
	}
	
	private void setBusinessLogic(BLFacade bl) {
		this.bl = bl;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 299, 228);
		this.setAlwaysOnTop(true);
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		this.setResizable(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 11, 271, 59);
		panel.add(panel_1);

		JLabel lblUploadFunds = new JLabel("UPLOAD FUNDS");
		lblUploadFunds.setVerticalAlignment(SwingConstants.CENTER);
		lblUploadFunds.setHorizontalAlignment(SwingConstants.CENTER);
		lblUploadFunds.setForeground(Color.BLACK);
		lblUploadFunds.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblUploadFunds.setBackground(Color.GRAY);
		lblUploadFunds.setBounds(10, 11, 253, 40);
		panel_1.add(lblUploadFunds);

		JLabel lblEnterDeEuro = new JLabel("Enter the euro amount you want to add:");
		lblEnterDeEuro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterDeEuro.setBounds(10, 85, 271, 14);
		panel.add(lblEnterDeEuro);

		txtEuros = new JTextField();
		txtEuros.setBounds(80, 111, 139, 20);
		panel.add(txtEuros);
		txtEuros.setColumns(10);

		JButton btnAccept = new JButton("ADD FUNDS");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null,
						"Do you want to add " + txtEuros.getText() + "� to your wallet? ", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (option == 0) {
					if(Integer.parseInt(txtEuros.getText()) >= 5) {
						bl.movementUpload(Double.parseDouble(txtEuros.getText()));
						JOptionPane.showMessageDialog(null, txtEuros.getText() + "� have been added.", " ", JOptionPane.INFORMATION_MESSAGE);
					} else
						JOptionPane.showMessageDialog(null, "You must add at least 5�.", "FUNDS ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAccept.setBounds(90, 163, 121, 23);
		panel.add(btnAccept);

		JLabel lblPoints = new JLabel("");
		lblPoints.setHorizontalAlignment(SwingConstants.CENTER);
		lblPoints.setBounds(80, 142, 138, 14);
		panel.add(lblPoints);

		txtEuros.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar()) || (e.getKeyChar() == '.')) {
					Double num = Double.parseDouble(txtEuros.getText()) * 10;
					String t = Double.toString(num);
					lblPoints.setText(t + " points");
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()) && !(e.getKeyChar() == '.')) {
					e.consume();
				}
			}
		});
	}
}
