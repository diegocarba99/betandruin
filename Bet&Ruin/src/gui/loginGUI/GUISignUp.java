package gui.loginGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import businessLogic.BLFacadeImplementation;

import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowFocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUISignUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtVerifyPassword;
	private BLFacadeImplementation bl;
	private boolean goodUsername, goodPassword;
	

	/**
	 * Launch the application.
	 */
	

	public void setBusinessLogic() {
		this.bl = new BLFacadeImplementation();
	}
	/**
	 * Create the frame.
	 */
	public GUISignUp() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
			}
			public void windowLostFocus(WindowEvent arg0) {
				requestFocus();
			}
		});
		setAlwaysOnTop(true);
		setBusinessLogic();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username"));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblRepeatPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RepeatPassword"));
		lblRepeatPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblUsernameVerify = new JLabel("");
		JLabel lblVerifyPassword = new JLabel("");
		
		JSpinner spnDay = new JSpinner();
		spnDay.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		
		JLabel lblBornDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Birthdate"));
		lblBornDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JComboBox<String> cboMonth = new JComboBox<String>();
		cboMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((cboMonth.getSelectedIndex()%2)==0) {
					spnDay.setModel(new SpinnerNumberModel(1,1,31,1));
				}else {
					if(cboMonth.getSelectedIndex()==1)spnDay.setModel(new SpinnerNumberModel(1,1,28,1));
					else spnDay.setModel(new SpinnerNumberModel(1,1,30,1));
				}
			}
		});
		cboMonth.setModel(new DefaultComboBoxModel<String>(new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"}));
		
		JSpinner spnYear = new JSpinner();
		spnYear.setModel(new SpinnerNumberModel(2019, 1920, 2019, 1));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnYear,"#");		
		spnYear.setEditor(editor);
		txtUsername = new JTextField(16);
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(!txtUsername.getText().trim().equals("") && !bl.existsUser(txtUsername.getText())) {
					
					ImageIcon img = new ImageIcon("resources/images/tick.png");
					lblUsernameVerify.setIcon(img);
					lblUsernameVerify.setVisible(true);
					lblUsernameVerify.setToolTipText("Username available");
					goodUsername=true;
				}else {
					ImageIcon img = new ImageIcon("resources/images/cross.png");
					lblUsernameVerify.setIcon(img);
					lblUsernameVerify.setVisible(true);
					lblUsernameVerify.setToolTipText("Username not available");
					goodUsername=false;
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				lblUsernameVerify.setVisible(false);
			}
		});
		txtUsername.setToolTipText("Maximum 16 characters.");
		txtUsername.setDocument(new JTextFieldLimit(16));
		
		txtPassword = new JPasswordField();
		
		txtVerifyPassword = new JPasswordField();
		txtVerifyPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String pass = new String(txtPassword.getPassword());
				String repeatedPass= new String(txtVerifyPassword.getPassword());
				if(!pass.trim().equals("") && repeatedPass.equals(pass)) {
					goodPassword=true;
					ImageIcon img = new ImageIcon("resources/images/tick.png");
					lblVerifyPassword.setIcon(img);
					lblVerifyPassword.setVisible(true);
					lblVerifyPassword.setToolTipText("Correct password");
				}else {
					goodPassword=false;
					ImageIcon img = new ImageIcon("resources/images/cross.png");
					lblVerifyPassword.setIcon(img);
					lblVerifyPassword.setVisible(true);
					lblVerifyPassword.setToolTipText("Passwords don't match.");
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				lblVerifyPassword.setVisible(false);
			}
		});
		
		JButton btnCreateAccount = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateAccount"));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date birthDate= df.parse(spnDay.getValue()+"/"+(cboMonth.getSelectedIndex()+1)+"/"+(int)spnYear.getValue());
					
					
					LocalDate loc = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate loc2 = LocalDate.now().minusYears(18);
					
				
					if(loc2.compareTo(loc)<1)
					{
						JOptionPane.showMessageDialog(getFocusOwner(), "You must be more than 18 years old.","Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(goodPassword && goodUsername) {
					bl.createUser(txtUsername.getText(), new String(txtPassword.getPassword()));
					JOptionPane.showMessageDialog(getFocusOwner(), "Account created!", "", JOptionPane.PLAIN_MESSAGE);
					dispose();
				}
				else JOptionPane.showMessageDialog(getFocusOwner(), "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();	
				}
		});
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(38)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblUsername, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblRepeatPassword, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblBornDate))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(40)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(txtPassword)
												.addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblUsernameVerify))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(spnDay, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
													.addGap(64)
													.addComponent(spnYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(txtVerifyPassword, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblVerifyPassword))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(86)
									.addComponent(cboMonth, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(96)
							.addComponent(btnCreateAccount)
							.addGap(18)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
					.addGap(51))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsernameVerify))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRepeatPassword)
						.addComponent(txtVerifyPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblVerifyPassword, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBornDate)
						.addComponent(cboMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spnYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spnDay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreateAccount)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	

	private static class JTextFieldLimit extends PlainDocument {
	  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	private int limit;

	  JTextFieldLimit(int limit) {
	   super();
	   this.limit = limit;
	   }

	  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
	    if (str == null) return;

	    if ((getLength() + str.length()) <= limit) {
	      super.insertString(offset, str, attr);
	    }
	  }
	}
}