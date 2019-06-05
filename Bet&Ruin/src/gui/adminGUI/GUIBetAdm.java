package gui.adminGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Bet;
import domain.Event;
import domain.User;
import domain.VWallet;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class GUIBetAdm extends JFrame {
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Bet bet;
	private VWallet wa;
	private User us;
	private List<Event> ev;

	/**
	 * Create the application.
	 */
	public GUIBetAdm(String iden) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIMainAdm win = new GUIMainAdm("Bet");
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
		bet = bl.getBetByID(iden);
		wa = bl.specialGetWalletByBetID(Integer.toString(bet.getBetId()));
		us = bl.getUserByWalletID(Integer.toString(wa.getID()));
		//ev = bl.getEventByBet(Integer.toString(bet.getBetId()));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 354, 311);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JList<String> lstEvents = new JList<String>();
		lstEvents.setModel(dlm);
		dlm.removeAllElements();
		scrollPane.setViewportView(lstEvents);

		JLabel lblUsersDetails = new JLabel("BET DETAILS");
		lblUsersDetails.setVerticalAlignment(SwingConstants.CENTER);
		lblUsersDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersDetails.setForeground(Color.BLACK);
		lblUsersDetails.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblUsersDetails.setBackground(Color.GRAY);

		JLabel label1 = new JLabel("Bet ID:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label_1 = new JLabel("User ID:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label2 = new JLabel("VWallet ID:");
		label2.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label3 = new JLabel("Earnings:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label4 = new JLabel("Betted amount:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label5 = new JLabel("Date:");
		label5.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel label6 = new JLabel("Events betted:");
		label6.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblBetID = new JLabel(Integer.toString(bet.getBetId()));
		lblBetID.setHorizontalAlignment(SwingConstants.LEFT);
		lblBetID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblUserID = new JLabel(Integer.toString(us.getId()));
		lblUserID.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblWalletID = new JLabel(Integer.toString(wa.getID()));
		lblWalletID.setHorizontalAlignment(SwingConstants.LEFT);
		lblWalletID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblEarnings = new JLabel(String.format("%.2f", Double.parseDouble(bet.earnings())));
		lblEarnings.setHorizontalAlignment(SwingConstants.LEFT);
		lblEarnings.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblBetAmount = new JLabel(Double.toString(bet.getMoneyBetted()));
		lblBetAmount.setHorizontalAlignment(SwingConstants.LEFT);
		lblBetAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblDate = new JLabel(dateFormat.format(bet.getDueDate()));
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
							.addGap(2))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(51)
									.addComponent(lblBetID, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
								.addComponent(label1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(lblEarnings, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
							.addGap(2))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(lblUserID, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label4, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblBetAmount, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(label6, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWalletID, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblDate, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
					.addGap(12))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBetID)
						.addComponent(label1)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblEarnings)
							.addComponent(label3)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(label_1))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label4)
								.addComponent(lblBetAmount)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(lblUserID)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label2)
						.addComponent(label5)
						.addComponent(lblDate)
						.addComponent(lblWalletID))
					.addGap(12)
					.addComponent(label6)
					.addGap(3)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
					.addGap(11))
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
		
		for(Event e: ev) {
			dlm.addElement(e.getDescription());
		}
	}
}
