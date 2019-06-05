package gui.adminGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Event;
import domain.Question;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class GUIEventAdm extends JFrame {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private BLFacade bl;
	private Event ev;
	private List<Question> que;

	/**
	 * Create the application.
	 */
	public GUIEventAdm(String ide) {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIMainAdm win = new GUIMainAdm("Event");
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
		ev = bl.getEventByID(ide);
		que = bl.getQuestions(ev);
		initialize();
	}

	private void setBusinessLogic(BLFacade bl2) {
		this.bl = bl2;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 320, 371);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);

		JLabel lblMovementDetails = new JLabel("EVENT DETAILS");
		lblMovementDetails.setVerticalAlignment(SwingConstants.CENTER);
		lblMovementDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovementDetails.setForeground(Color.BLACK);
		lblMovementDetails.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblMovementDetails.setBackground(Color.GRAY);

		JLabel label1 = new JLabel("Event:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label4 = new JLabel("Date:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label2 = new JLabel("Event ID:");
		label2.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label3 = new JLabel("Sport ID:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel label5 = new JLabel("Category ID:");
		label5.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnCreateQuestion = new JButton("Create question");
		btnCreateQuestion.setFont(new Font("Tahoma", Font.PLAIN, 10));

		JLabel label = new JLabel(ev.getDescription());
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblEventID = new JLabel(Integer.toString(ev.getEventID()));
		lblEventID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblSportID = new JLabel(ev.getSportID());
		lblSportID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblDate = new JLabel(dateFormat.format(ev.getEventDate()));
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblCategoryID = new JLabel(Integer.toString(ev.getCategoryID()));
		lblCategoryID.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane();

		JList<String> lstQuestions = new JList<String>();
		lstQuestions.setModel(dlm);
		dlm.removeAllElements();
		scrollPane.setViewportView(lstQuestions);

		JButton btnDeleteQuestion = new JButton("Delete question");
		btnDeleteQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblQuestions = new JLabel("Questions:");
		lblQuestions.setFont(new Font("Tahoma", Font.BOLD, 14));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
					.addGap(37))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
					.addGap(37))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(74)
							.addComponent(lblEventID, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(label4)
					.addGap(6)
					.addComponent(lblDate, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label3, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(74)
							.addComponent(lblSportID, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(13)
					.addComponent(label5)
					.addGap(8)
					.addComponent(lblCategoryID, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(btnCreateQuestion, GroupLayout.PREFERRED_SIZE, 90, Short.MAX_VALUE)
					.addGap(4)
					.addComponent(btnDeleteQuestion, GroupLayout.PREFERRED_SIZE, 90, Short.MAX_VALUE)
					.addGap(15))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
					.addGap(15))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestions)
					.addContainerGap(248, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(label1)
					.addGap(11)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label2)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblEventID))
						.addComponent(label4)
						.addComponent(lblDate))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label3)
						.addComponent(lblSportID)
						.addComponent(label5)
						.addComponent(lblCategoryID))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblQuestions)
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCreateQuestion, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDeleteQuestion, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(11))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblMovementDetails, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
					.addGap(10))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblMovementDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(8))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Do you want to delete this event?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (option == 0) {
					if (bl.deleteEvent(Integer.toString(ev.getEventID()))) {
						JOptionPane.showMessageDialog(null, "Event deleted.", " ", JOptionPane.INFORMATION_MESSAGE);
						GUIMainAdm win = new GUIMainAdm("Event");
						win.setVisible(true);
						setVisible(false);
					} else
						JOptionPane.showMessageDialog(null, "Event not deleted. An error occurred.", " Error",
								JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnCreateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUICreateQuestion win = new GUICreateQuestion(ev);
				win.setVisible(true);
			}
		});

		btnDeleteQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Question selected = bl.getQuestionFromEventAs(ev, lstQuestions.getSelectedValue());
				if (bl.removeQuestion(selected)) {
					JOptionPane.showMessageDialog(null, "Question deleted.", " Success!",
							JOptionPane.INFORMATION_MESSAGE);
					dlm.removeElementAt(lstQuestions.getSelectedIndex());
				}
			}
		});

		if (que != null)
			for (Question q : que)
				dlm.addElement(q.toString());
	}
}
