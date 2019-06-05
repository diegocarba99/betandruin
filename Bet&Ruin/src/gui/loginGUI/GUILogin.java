package gui.loginGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacadeImplementation;
import domain.User;
import domain.UserSingleton;
import gui.adminGUI.GUIMainAdm;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUILogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BackgroundJPanel contentPane;
	private JTextField txtUser;
	private BLFacadeImplementation bl;
	private JPasswordField txtPassword;
	private User savedUser;
	private boolean saved;
	JButton btnLogin;
	JButton btnSignUp;
	JLabel lblUser;
	JLabel lblPassword;
	JLabel lblRememberMe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUILogin frame = new GUILogin();
					frame.setVisible(true);
					BLFacadeImplementation bl = new BLFacadeImplementation();
					frame.setBusinessLogic(bl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	private void setBusinessLogic(BLFacadeImplementation bl) {
		this.bl = bl;

	}

	/**
	 * Create the frame.
	 */
	public GUILogin() {
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		setBusinessLogic(new BLFacadeImplementation());
		if( !bl.existsAdmin() ) bl.createAdmin();
		setResizable(false);
		setTitle("Bet&Ruin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		savedUser = bl.getSavedUser();
		setBounds(100, 100, 875, 502);
		try {
			contentPane = new BackgroundJPanel(ImageIO.read(new File("resources/images/background/bg5.jpg")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		btnLogin.setFont(new Font("Ink Free", Font.BOLD, 23));
		JCheckBox chkRememberMe = new JCheckBox("");
		chkRememberMe.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (saved || bl.checkCredentials(txtUser.getText(), new String(txtPassword.getPassword()))) {
					if (chkRememberMe.isSelected()) {
						if ((savedUser != null
								&& !savedUser.getUserName().toLowerCase().equals(txtUser.getText().toLowerCase()))
								|| savedUser == null)
							bl.updateSavedUser(savedUser, txtUser.getText(), true);

					} else if (savedUser != null && !chkRememberMe.isSelected()) {
						bl.updateSavedUser(savedUser, txtUser.getText(), false);
					}

					bl.createUserSingleton(txtUser.getText());
					UserSingleton user = UserSingleton.getInstance();
						
					if (user.isAdmin()) {
						GUIMainAdm main = new GUIMainAdm("User");
						main.setVisible(true);
					} else {
						LoadingSplashScreen main = new LoadingSplashScreen();
						main.setVisible(true);
						//main.run();
						new Thread(main).start();
					}

					GUILogin.this.setVisible(false);
					GUILogin.this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect user or password.", "Wrong credentials",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnSignUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignUp"));
		btnSignUp.setFont(new Font("Ink Free", Font.BOLD, 23));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUISignUp signUp = new GUISignUp();
				signUp.setVisible(true);
			}
		});

		txtUser = new JTextField();
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				saved = false;
			}
		});
		txtUser.setColumns(10);
		txtPassword = new JPasswordField();
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				saved = false;
			}
		});
		Action action = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	btnLogin.doClick();
		    }
		};
		txtPassword.addActionListener(action);
		txtUser.addActionListener(action);
		if (savedUser != null) {
			saved = true;
			txtUser.setText(savedUser.getUserName());
			txtPassword.setText("XXXXXXXXXXX");
			chkRememberMe.setSelected(true);
		}

		lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 20));

		lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));

		lblRememberMe = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RememberMe"));
		lblRememberMe.setFont(new Font("Corbel Light", Font.PLAIN, 23));
		lblRememberMe.setForeground(Color.BLACK);
		
		JComboBox<String> cboLanguage = new JComboBox<String>();
		cboLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loc =getLocale().toLanguageTag();
				switch(cboLanguage.getSelectedIndex())
				{
				case 0: //English
					Locale.setDefault(new Locale("en"));
					break;
				case 1: //Spanish
					Locale.setDefault(new Locale("es"));
					break;
				
				case 2: //Basque
					Locale.setDefault(new Locale("eus"));
					break;
				default:
					return;
				}
				renameComponents();
//				setVisible(false);
//				GUILogin login = new GUILogin();
//				login.setVisible(true);
//				dispose();
			}

			
		});
		cboLanguage.setRenderer(new LanguageComboboxRenderer());
		cboLanguage.setModel(new DefaultComboBoxModel<String>(new String[] {ResourceBundle.getBundle("Etiquetas").getString("English"),
				ResourceBundle.getBundle("Etiquetas").getString("Spanish"),
				ResourceBundle.getBundle("Etiquetas").getString("Basque")}));
		cboLanguage.setToolTipText("Select the language.");
		cboLanguage.setName("Select language");
		

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(215, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblUser)
							.addGap(117)
							.addComponent(txtUser, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPassword)
							.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(chkRememberMe, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblRememberMe))
								.addComponent(txtPassword, 210, 210, 210))))
					.addGap(264))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(257, Short.MAX_VALUE)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSignUp)
					.addGap(248))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(744, Short.MAX_VALUE)
					.addComponent(cboLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(cboLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(74)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUser)
						.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(chkRememberMe)
						.addComponent(lblRememberMe))
					.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSignUp))
					.addGap(23))
		);
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] {btnLogin, btnSignUp});
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] {txtUser, txtPassword});
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnLogin, btnSignUp});
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] {txtUser, txtPassword});
		
		contentPane.setLayout(gl_contentPane);
	}
	
	private void renameComponents() {
		this.btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		this.btnSignUp.setText(ResourceBundle.getBundle("Etiquetas").getString("SignUp"));
		this.lblPassword.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		this.lblRememberMe.setText(ResourceBundle.getBundle("Etiquetas").getString("RememberMe"));
		this.lblUser.setText(ResourceBundle.getBundle("Etiquetas").getString("User"));


		
	}
}
