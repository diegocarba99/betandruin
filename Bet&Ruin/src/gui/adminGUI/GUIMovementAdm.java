package gui.adminGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Movement;
import domain.User;
import java.text.SimpleDateFormat;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class GUIMovementAdm extends JFrame {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Movement mov;
	private User use;

	/**
	 * Create the application.
	 */
	public GUIMovementAdm(String id) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIMainAdm win = new GUIMainAdm("Movement");
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
		mov = bl.getMovementByID(id);
		use = bl.getUserByWalletID(Integer.toString(mov.getWallet().getID()));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 352, 244);

		JLabel lblIdentification = new JLabel("Identification:");
		lblIdentification.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label1 = new JLabel("Date:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label3 = new JLabel("User:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label4 = new JLabel("VWallet ID:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label2 = new JLabel("Type:");
		label2.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblDate = new JLabel(dateFormat.format(mov.getMovDate()));
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblVW = new JLabel(Integer.toString(mov.getWallet().getID()));
		lblVW.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblUser = new JLabel(use.getUserName());
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblType = new JLabel();
		if (mov.getOption() == '-')
			lblType.setText("Withdrawal");
		else
			lblType.setText("Added funds");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel label6 = new JLabel("User ID:");
		label6.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblUserId = new JLabel(Integer.toString(use.getId()));
		lblUserId.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel label5 = new JLabel("Amount:");
		label5.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblAmount = new JLabel(Double.toString(mov.getAmount()));
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblID = new JLabel(Integer.toString(mov.getID()));
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);

		JLabel lblMovementDetails = new JLabel("MOVEMENT DETAILS");
		lblMovementDetails.setVerticalAlignment(SwingConstants.CENTER);
		lblMovementDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovementDetails.setForeground(Color.BLACK);
		lblMovementDetails.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblMovementDetails.setBackground(Color.GRAY);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblMovementDetails, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
					.addGap(9))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblMovementDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(8))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblIdentification, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(lblDate, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(label4, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(lblVW, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(66)
									.addComponent(lblType, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
								.addComponent(label2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addComponent(label5, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(lblAmount, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(lblUser, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(label6, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(lblUserId, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIdentification)
						.addComponent(lblID))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(label1))
						.addComponent(lblDate)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(label4))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblVW)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(label2))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label5))
						.addComponent(lblAmount))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(label3))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblUser))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(label6))
						.addComponent(lblUserId)))
		);
		getContentPane().setLayout(groupLayout);
	}
}
