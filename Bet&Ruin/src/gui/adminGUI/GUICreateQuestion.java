package gui.adminGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import businessLogic.BLFacadeImplementation;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class GUICreateQuestion  extends JFrame{
	private JTextField txtQuestion;
	private JTextField txtMinBet;
	private BLFacadeImplementation bl;
	private Event ev;

	/**
	 * Create the application.
	 */
	public GUICreateQuestion(Event ev) {
		BLFacadeImplementation bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		this.ev = ev;
		initialize();
	}
	
	private void setBusinessLogic(BLFacadeImplementation bl2) {
		this.bl = bl2;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 322, 254);
		setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblMovementDetails = new JLabel("CREATE QUESTION");
		lblMovementDetails.setVerticalAlignment(SwingConstants.CENTER);
		lblMovementDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovementDetails.setForeground(Color.BLACK);
		lblMovementDetails.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblMovementDetails.setBackground(Color.GRAY);
		
		JLabel lbl_1 = new JLabel("Event:");
		lbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblEvent = new JLabel(ev.getDescription());
		lblEvent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtQuestion = new JTextField();
		txtQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtQuestion.setColumns(10);
		
		JLabel lbl_2 = new JLabel("Question:");
		lbl_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lbl_3 = new JLabel("Minimum bet:");
		lbl_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		txtMinBet = new JTextField();
		txtMinBet.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMinBet.setText("50");
		txtMinBet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMinBet.setColumns(10);
		
		JLabel lblRp = new JLabel("RP");
		lblRp.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnAccept = new JButton("Save");
		btnAccept.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblError = new JLabel("Format not accepted");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblError.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
							.addGap(4))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addComponent(lblEvent, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
							.addGap(4))
						.addComponent(lbl_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_2)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtQuestion, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
							.addGap(4))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lbl_3, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtMinBet, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRp, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(23)
									.addComponent(lblError, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAccept, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
							.addGap(28)
							.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
							.addGap(4)))
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEvent)
						.addComponent(lbl_1))
					.addGap(11)
					.addComponent(lbl_2)
					.addGap(1)
					.addComponent(txtQuestion, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_3)
						.addComponent(txtMinBet, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRp)
								.addComponent(lblError, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAccept, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblMovementDetails, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
					.addGap(13))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblMovementDetails))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Float minBet;
				try {
					if(!txtMinBet.getText().contains("."))
						minBet = Float.valueOf(txtMinBet.getText() + ".00");
					else
						minBet = Float.valueOf(txtMinBet.getText());
					
					bl.createQuestion(ev, txtQuestion.getText(), minBet);
					dispose();
				} catch (NumberFormatException e1) {
					txtMinBet.setBackground(Color.RED);
					lblError.setVisible(true);
				} catch (EventFinished e1) {
					e1.printStackTrace();
				} catch (QuestionAlreadyExist e1) {
					txtQuestion.setBackground(Color.RED);
					txtQuestion.setText("QUESTION ALREDY EXISTS");
				}
			}
		});
		
		txtMinBet.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblError.setVisible(false);
				lblError.setBackground(Color.WHITE);
			}
		});
	}
}
