package gui.mainGUI;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.UserSingleton;
import gui.GUIChangePassword;
import gui.GUIChangeUsername;

@SuppressWarnings("serial")
public class GUIUserManagement extends JFrame {

	private JPanel contentPane;
	private static BLFacade bl;
	private UserSingleton user = UserSingleton.getInstance();
	private DefaultListModel<String> dlm = new DefaultListModel<String>();

	/**
	 * Create the frame.
	 */
	public GUIUserManagement() {
		initializeBL();
		initializeList();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIMainPage win = GUIMainPage.getInstance();
				win.setVisible(true);
				dispose();
			}
		});
		
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		setBounds(100, 100, 472, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("EditorPane.border"));
		panel.setBackground(SystemColor.controlHighlight);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(9, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JButton btnChangePassword = new JButton("Change password");
		panel_3.add(btnChangePassword);
		btnChangePassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIChangePassword win = new GUIChangePassword();
				win.setVisible(true);
			}
		});
		
		JButton btnChangeUsername = new JButton("Change username");
		panel_3.add(btnChangeUsername);
		btnChangeUsername.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIChangeUsername win = new GUIChangeUsername();
				win.setVisible(true);
			}
		});
		
		
		JList<String> lstDisplay = new JList<String>();
		lstDisplay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lstDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstDisplay.setBounds(10, 135, 567, 255);
		lstDisplay.setModel(dlm);
		
		JScrollPane scrollPane = new JScrollPane(lstDisplay);
		panel_2.add(scrollPane);
		
		JLabel VWLbl = new JLabel("Virtual Wallet: " + user.getWallet().getPoints() + " RP");
		VWLbl.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panel_1.add(VWLbl);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel userIconLbl = new JLabel();
		userIconLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(userIconLbl);
		try {
			userIconLbl.setBorder(null);
			Image img = ImageIO.read( new File("resources/images/User.png" ) );
			Image newimg = img.getScaledInstance( 60, 60,  java.awt.Image.SCALE_SMOOTH ) ; 
			userIconLbl.setBackground(SystemColor.control);
			userIconLbl.setIcon(new ImageIcon(newimg));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JLabel usernameLbl = new JLabel(user.getUserName());
		usernameLbl.setFont(new Font("Tahoma", Font.PLAIN, 22));
		usernameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(usernameLbl);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * Set the apropiatte Bussines Logic for the app
	 */
	private void initializeBL() {
		bl = new BLFacadeImplementation();
	}
	
	private void initializeList() {
		List<String> list = bl.getUserBets();
		if( list != null) for (String string : list) dlm.addElement(string);
	}
}
