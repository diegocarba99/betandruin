package gui.guestGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Event;
import domain.EventContainer;
import domain.Result;
import gui.loginGUI.GUILogin;


@SuppressWarnings("serial")
public class GUIGuestPage extends JFrame{
	
	private static BLFacade bl;

	private static DefaultListModel<String> summaryList = new DefaultListModel<String>();
	private static JList<String> sumaryPanel = new JList<String>();
	private static JButton betButton = new JButton("Sing in to be able to BET");
		
	private static JPanel managementPanel = new JPanel();
	private static JButton userLabel = new JButton();
	private static JButton virtualWalletLabel = new JButton();
	private static JButton logOutButton = new JButton();
	private static JLabel balancePanel = new JLabel();

	
	////////////////////////////////////////////////////////////////////////////
	
	private static JTabbedPane mainTabs = new JTabbedPane();
	
	private static 	JPanel tabSoccer = new JPanel();
	private static 	JPanel tabGolf = new JPanel();
	private static 	JPanel tabBasket = new JPanel();
	private static 	JPanel tabRugby = new JPanel();
	private static 	JPanel tabMotor = new JPanel();
	
	private static  JScrollPane scrollSoccer = new JScrollPane();
	private static 	JScrollPane scrollGolf = new JScrollPane();
	private static 	JScrollPane scrollBasket = new JScrollPane();
	private static 	JScrollPane scrollRugby = new JScrollPane();
	private static 	JScrollPane scrollMotor = new JScrollPane();
	
	private static JPanel inPanelSoccer = new JPanel();
	private static JPanel inPanelGolf = new JPanel();
	private static JPanel inPanelBasket = new JPanel();
	private static JPanel inPanelRugby = new JPanel();
	private static JPanel inPanelMotor = new JPanel();
	
	private static JPanel infoPanelSoccer = new JPanel();
	private static JPanel infoPanelGolf = new JPanel();
	private static JPanel infoPanelBasket = new JPanel();
	private static JPanel infoPanelRugby = new JPanel();
	private static JPanel infoPanelMotor = new JPanel();
	
	private static JPanel eventPanelSoccer = new JPanel();
	private static JPanel eventPanelGolf = new JPanel();
	private static JPanel eventPanelBasket = new JPanel();
	private static JPanel eventPanelRugby = new JPanel();
	private static JPanel eventPanelMotor = new JPanel();
	
	private static  JLabel infoLabelSoccer = new JLabel();
	private static  JLabel infoLabelGolf = new JLabel();
	private static  JLabel infoLabelBasket = new JLabel();
	private static  JLabel infoLabelRugby = new JLabel();
	private static  JLabel infoLabelMotor = new JLabel();
	
	private static 	JComboBox<String> infoComboSoccer = new JComboBox<String>();
	private static 	JComboBox<String> infoComboGolf = new JComboBox<String>();
	private static 	JComboBox<String> infoComboBasket = new JComboBox<String>();
	private static 	JComboBox<String> infoComboRugby = new JComboBox<String>();
	private static 	JComboBox<String> infoComboMotor = new JComboBox<String>();
	
	////////////////////////////////////////////////////////////////////////////

	private static GUIGuestPage window;
	
	/**
	 * Create the application.
	 */
	private GUIGuestPage() {
		initialize();
		initializeBL();
		initializeTabWindow();
		initializeManagement();	
	}
	
	public static GUIGuestPage getInstance() {
		if( window == null ) window = new GUIGuestPage();
		return window;
	}

	/**
	 * Set the apropiatte Bussines Logic for the app
	 */
	private void initializeBL() {
		
		bl = new BLFacadeImplementation();
	}
		
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setResizable(false);
		this.setTitle("Bet & Ruin");
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		this.setBounds(100, 100, 1244, 774);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
	}

	/**
	 * Initialize the contents of the User Management zone
	 */
	private void initializeManagement() {
		
		managementPanel.setBounds(842, 11, 386, 81);
		this.getContentPane().add(managementPanel);
		managementPanel.setLayout(null);
		
		try {
			userLabel.setMargin(new Insets(0, 0, 0, 0));
			userLabel.setBorder(null);
			Image img = ImageIO.read( new File("resources/images/User.png" ) );
			Image newimg = img.getScaledInstance( 60, 60,  java.awt.Image.SCALE_SMOOTH ) ; 
			userLabel.setBackground(SystemColor.control);
			userLabel.setIcon(new ImageIcon(newimg));
		} catch (IOException e1) {
			userLabel.setMargin(new Insets(0, 0, 0, 0));
			userLabel.setBorder(null);
			userLabel.setBackground(SystemColor.control);
		}
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setBorder(null);
		userLabel.setBackground(SystemColor.menu);
		userLabel.setBounds(248, 11, 59, 59);
		managementPanel.add(userLabel);
		
		try {
			virtualWalletLabel.setMargin(new Insets(0, 0, 0, 0));
			virtualWalletLabel.setBorder(null);
			Image img = ImageIO.read( new File("resources/images/VW.png" ) );
			Image newimg = img.getScaledInstance( 70, 70,  java.awt.Image.SCALE_SMOOTH ) ; 
			virtualWalletLabel.setBackground(SystemColor.menu);
			virtualWalletLabel.setIcon(new ImageIcon(newimg));
		} catch (IOException e1) {
			virtualWalletLabel.setMargin(new Insets(0, 0, 0, 0));
			virtualWalletLabel.setBorder(null);
			virtualWalletLabel.setBackground(SystemColor.menu);
		}
		virtualWalletLabel.setBorderPainted(false);
		virtualWalletLabel.setHorizontalAlignment(SwingConstants.CENTER);
		virtualWalletLabel.setBounds(179, 11, 59, 59);
		managementPanel.add(virtualWalletLabel);
		
		balancePanel.setFont(new Font("Tahoma", Font.BOLD, 13));
		balancePanel.setHorizontalAlignment(SwingConstants.CENTER);
		balancePanel.setText( "Your RP");
		balancePanel.setBounds(10, 11, 159, 59);
		managementPanel.add(balancePanel);
		
		try {
			logOutButton.setMargin(new Insets(0, 0, 0, 0));
			logOutButton.setBorder(null);
			Image img = ImageIO.read( new File("resources/images/LogOut.png" ) );
			Image newimg = img.getScaledInstance( 70, 70,  java.awt.Image.SCALE_SMOOTH ) ; 
			logOutButton.setBackground(SystemColor.menu);
			logOutButton.setIcon(new ImageIcon(newimg));
		} catch (IOException e1) {
			logOutButton.setMargin(new Insets(0, 0, 0, 0));
			logOutButton.setBorder(null);
			logOutButton.setBackground(SystemColor.menu);
		}
		logOutButton.setBorderPainted(false);
		
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(GUIGuestPage.this, "Are you sure you want to exit guest mode? ", "Exit Guest Mode", JOptionPane.YES_NO_OPTION);
				if ( r == JOptionPane.YES_OPTION ) {
					GUILogin suPanel = new GUILogin();
					suPanel.setVisible(true);
					dispose();
				}
				
			}
		});
		logOutButton.setHorizontalAlignment(SwingConstants.CENTER);
		logOutButton.setBorder(null);
		logOutButton.setBackground(SystemColor.menu);
		logOutButton.setBounds(317, 11, 59, 59);
		managementPanel.add(logOutButton);
		
		JLabel lblBetsSummary = new JLabel("BETS SUMMARY");
		lblBetsSummary.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetsSummary.setFont(new Font("Tahoma", Font.BOLD, 38));
		lblBetsSummary.setBounds(842, 103, 386, 36);
		getContentPane().add(lblBetsSummary);

		sumaryPanel.setLayout(new BoxLayout(sumaryPanel, BoxLayout.Y_AXIS));
		sumaryPanel.setBackground(Color.WHITE);
		sumaryPanel.setModel(summaryList);
		
		JPanel listContainer = new JPanel();
		listContainer.setBounds(842, 150, 386, 528);
		getContentPane().add(listContainer);
		GroupLayout gl_listContainer = new GroupLayout(listContainer);
		gl_listContainer.setHorizontalGroup(
			gl_listContainer.createParallelGroup(Alignment.LEADING)
				.addComponent(sumaryPanel, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
		);
		gl_listContainer.setVerticalGroup(
			gl_listContainer.createParallelGroup(Alignment.LEADING)
				.addComponent(sumaryPanel, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
		);
		listContainer.setLayout(gl_listContainer);
		
			
		
		
		betButton.setBounds(842, 687, 386, 47);
		getContentPane().add(betButton);
		betButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(GUIGuestPage.this, "Proceed to create an account? ", "Exit Guest Mode", JOptionPane.YES_NO_OPTION);
				if ( r == JOptionPane.YES_OPTION ) {
					GUILogin suPanel = new GUILogin();
					suPanel.setVisible(true);
					dispose();
				}			
			}
		});
	}



	/**
	 * Initalize the contents of the Events zone
	 */
	private void initializeTabWindow() {

		  /* ------------------------------------------------------------------------------ */	
		 /* ------------------------------ Football tab ---------------------------------- */
		/* ------------------------------------------------------------------------------ */

		              /* ------ Set up layouts for the components ------ */
		tabSoccer.setLayout(new BoxLayout(tabSoccer, SwingConstants.VERTICAL));
		infoPanelSoccer.setLayout(new BoxLayout(infoPanelSoccer, SwingConstants.HORIZONTAL) );
		inPanelSoccer.setLayout(new BoxLayout(inPanelSoccer, SwingConstants.VERTICAL));
		eventPanelSoccer.setLayout(new BoxLayout(eventPanelSoccer, SwingConstants.VERTICAL));
		scrollSoccer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollSoccer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
                     /* ------ Provide info and data to components ------ */
		infoComboSoccer.setModel(new DefaultComboBoxModel<String>( bl.getCategoriesArray("Soccer") ));
		infoLabelSoccer.setText( (String) infoComboSoccer.getSelectedItem());
		
			             /* ------ Setup components listeners ------ */
		infoComboSoccer.addActionListener(new ActionListener() {
		@Override
			public void actionPerformed(ActionEvent e) {
		        infoLabelSoccer.setText( (String)infoComboSoccer.getSelectedItem() );
		        setBinaryDraw(eventPanelSoccer, (String)infoComboSoccer.getSelectedItem());
			}
		});
		
                         /* ------ Setup components hierarchy ------ */
		setBinaryDraw(eventPanelSoccer, (String)infoComboSoccer.getSelectedItem() );
		inPanelSoccer.add(eventPanelSoccer);
		scrollSoccer.setViewportView(inPanelSoccer);
		infoPanelSoccer.add(infoLabelSoccer);
		infoPanelSoccer.add(Box.createHorizontalStrut(40));
		infoPanelSoccer.add(infoComboSoccer);
		infoPanelSoccer.setMaximumSize(new Dimension(500, 50));
		tabSoccer.add(infoPanelSoccer);
		tabSoccer.add(scrollSoccer);
		mainTabs.add("Soccer", tabSoccer);
		
		
		
		
		  /* -------------------------------------------------------------------------------- */
		 /* ------------------------------ Basketball tab ---------------------------------- */
		/* -------------------------------------------------------------------------------- */

        		/* ------ Set up layouts for the components ------ */
		tabBasket.setLayout(new BoxLayout(tabBasket, SwingConstants.VERTICAL));
		infoPanelBasket.setLayout(new BoxLayout(infoPanelBasket, SwingConstants.HORIZONTAL));
		inPanelBasket.setLayout(new BoxLayout(inPanelBasket, SwingConstants.VERTICAL));
		eventPanelBasket.setLayout(new BoxLayout(eventPanelBasket, SwingConstants.VERTICAL));
		scrollSoccer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollSoccer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				/* ------ Provide info and data to components ------ */
		infoComboBasket.setModel(new DefaultComboBoxModel<String>(bl.getCategoriesArray("Basketball")));
		infoLabelBasket.setText((String) infoComboBasket.getSelectedItem());

					/* ------ Setup components listeners ------ */
		infoComboBasket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoLabelBasket.setText((String) infoComboBasket.getSelectedItem());
				setBinaryDraw(eventPanelBasket, (String) infoComboBasket.getSelectedItem());
			}
		});

					/* ------ Setup components hierarchy ------ */
		setBinaryDraw(eventPanelBasket, (String) infoComboBasket.getSelectedItem());
		inPanelBasket.add(eventPanelBasket);
		scrollBasket.setViewportView(inPanelBasket);
		infoPanelBasket.add(infoLabelBasket);
		infoPanelBasket.add(Box.createHorizontalStrut(40));
		infoPanelBasket.add(infoComboBasket);
		infoPanelBasket.setMaximumSize(new Dimension(500, 50));
		tabBasket.add(infoPanelBasket);
		tabBasket.add(scrollBasket);
		mainTabs.add("Basketball", tabBasket);

	
		
		
		  /* ------------------------------------------------------------------------------ */
		 /* ------------------------------ Rugby tab ---------------------------------- */
		/* ------------------------------------------------------------------------------ */

        			/* ------ Set up layouts for the components ------ */
		tabRugby.setLayout(new BoxLayout(tabRugby, SwingConstants.VERTICAL));
		infoPanelRugby.setLayout(new BoxLayout(infoPanelRugby, SwingConstants.HORIZONTAL));
		inPanelRugby.setLayout(new BoxLayout(inPanelRugby, SwingConstants.VERTICAL));
		eventPanelRugby.setLayout(new BoxLayout(eventPanelRugby, SwingConstants.VERTICAL));
		scrollRugby.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollRugby.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

					/* ------ Provide info and data to components ------ */
		infoComboRugby.setModel(new DefaultComboBoxModel<String>(bl.getCategoriesArray("Rugby")));
		infoLabelRugby.setText((String) infoComboRugby.getSelectedItem());

						/* ------ Setup components listeners ------ */
		infoComboRugby.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoLabelRugby.setText((String) infoComboRugby.getSelectedItem());
				setBinaryDraw(eventPanelRugby, (String) infoComboRugby.getSelectedItem());
			}
		});

						/* ------ Setup components hierarchy ------ */
		setBinaryDraw(eventPanelRugby, (String) infoComboRugby.getSelectedItem());
		inPanelRugby.add(eventPanelRugby);
		scrollRugby.setViewportView(inPanelRugby);
		infoPanelRugby.add(infoLabelRugby);
		infoPanelRugby.add(Box.createHorizontalStrut(40));
		infoPanelRugby.add(infoComboRugby);
		infoPanelRugby.setMaximumSize(new Dimension(500, 50));
		tabRugby.add(infoPanelRugby);
		tabRugby.add(scrollRugby);
		mainTabs.add("Rugby", tabRugby);

		
		
		
		  /* ---------------------------------------------------------------------------- */
		 /* ------------------------------ Golf tab ---------------------------------- */
		/* ---------------------------------------------------------------------------- */

						/* ------ Set up layouts for the components ------ */
		tabGolf.setLayout(new BoxLayout(tabGolf, SwingConstants.VERTICAL));
		infoPanelGolf.setLayout(new BoxLayout(infoPanelGolf, SwingConstants.HORIZONTAL));
		inPanelGolf.setLayout(new BoxLayout(inPanelGolf, SwingConstants.VERTICAL));
		eventPanelGolf.setLayout(new BoxLayout(eventPanelGolf, SwingConstants.VERTICAL));
		scrollGolf.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollGolf.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

						/* ------ Provide info and data to components ------ */
		infoComboGolf.setModel(new DefaultComboBoxModel<String>(bl.getCategoriesArray("Golf")));
		infoLabelGolf.setText((String) infoComboGolf.getSelectedItem());

							/* ------ Setup components listeners ------ */
		infoComboGolf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoLabelGolf.setText((String) infoComboGolf.getSelectedItem());
				setMultiple(eventPanelGolf, (String) infoComboGolf.getSelectedItem());
			}
		});

							/* ------ Setup components hierarchy ------ */
		setMultiple(eventPanelGolf, (String) infoComboGolf.getSelectedItem());
		inPanelGolf.add(eventPanelGolf);
		scrollGolf.setViewportView(inPanelGolf);
		infoPanelGolf.add(infoLabelGolf);
		infoPanelGolf.add(Box.createHorizontalStrut(40));
		infoPanelGolf.add(infoComboGolf);
		infoPanelGolf.setMaximumSize(new Dimension(500, 50));
		tabGolf.add(infoPanelGolf);
		tabGolf.add(scrollGolf);
		mainTabs.add("Golf", tabGolf);
		
		
		
		
		  /* -------------------------------------------------------------------------------- */
		 /* ------------------------------ Motorsport tab ---------------------------------- */
		/* -------------------------------------------------------------------------------- */

						/* ------ Set up layouts for the components ------ */
		tabMotor.setLayout(new BoxLayout(tabMotor, SwingConstants.VERTICAL));
		infoPanelMotor.setLayout(new BoxLayout(infoPanelMotor, SwingConstants.HORIZONTAL));
		inPanelMotor.setLayout(new BoxLayout(inPanelMotor, SwingConstants.VERTICAL));
		eventPanelMotor.setLayout(new BoxLayout(eventPanelMotor, SwingConstants.VERTICAL));
		scrollMotor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMotor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

						/* ------ Provide info and data to components ------ */
		infoComboMotor.setModel(new DefaultComboBoxModel<String>(bl.getCategoriesArray("Motorsport")));
		infoLabelMotor.setText((String) infoComboMotor.getSelectedItem());

							/* ------ Setup components listeners ------ */
		infoComboMotor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoLabelMotor.setText((String) infoComboMotor.getSelectedItem());
				setMultiple(eventPanelMotor, (String) infoComboMotor.getSelectedItem());
			}
		});

							/* ------ Setup components hierarchy ------ */
		setMultiple(eventPanelMotor, (String) infoComboMotor.getSelectedItem());
		inPanelMotor.add(eventPanelMotor);
		scrollMotor.setViewportView(inPanelMotor);
		infoPanelMotor.add(infoLabelMotor);
		infoPanelMotor.add(Box.createHorizontalStrut(40));
		infoPanelMotor.add(infoComboMotor);
		infoPanelMotor.setMaximumSize(new Dimension(500, 50));
		tabMotor.add(infoPanelMotor);
		tabMotor.add(scrollMotor);
		mainTabs.add("Motorsport", tabMotor);

		
		
		
		  /* -------------------------------------------------------------------------------- */
		 /* ---------------------------------- Main tab ------------------------------------ */
		/* -------------------------------------------------------------------------------- */
		
		mainTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainTabs.setBounds(10, 11, 822, 723);
		this.getContentPane().add(mainTabs);
		mainTabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				JTabbedPane pane = (JTabbedPane) e.getSource();
				int selection = pane.getSelectedIndex();
				
				switch (selection) {
					case 0:	
						setBinaryDraw(eventPanelSoccer, (String) infoComboSoccer.getSelectedItem());
						break;
					
					case 1:
						setBinaryDraw(eventPanelBasket, (String) infoComboBasket.getSelectedItem());
						break;
						
					case 2:
						setBinaryDraw(eventPanelRugby, (String) infoComboRugby.getSelectedItem());
						break;
						
					case 3:
						setMultiple(eventPanelGolf, (String) infoComboGolf.getSelectedItem());
						break;
						
					case 4:
						setMultiple(eventPanelMotor, (String) infoComboMotor.getSelectedItem());
						break;	
					default: System.out.println("Change listener trigerred. Unknown tab selectd");
						break;
				}
			}
		});
	}
	
	private void setBinaryDraw(JPanel panel, String categoryID) {
		List<EventContainer> events = bl.getEventsByCategory(categoryID);
		panel.removeAll();
		panel.updateUI();
		
		if( events == null || events.isEmpty() ) {
			JPanel failure = new JPanel();
			failure.add(new JLabel("No events found for the " + categoryID + " category"));
			panel.add( failure );
			
		}else {
			for(EventContainer e : events) {
				Event ev = e.getEvent();
				JLabel nameAndDate = new JLabel();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(ev.getEventDate());
				String name = ev.getDescription();
				nameAndDate.setText("[" + date + "] " + name + ": ");
				nameAndDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
				
				JPanel buttonGrid = new JPanel();
				buttonGrid.setLayout(new BoxLayout(buttonGrid, BoxLayout.X_AXIS));
				JButton b1 = new JButton(" 1 ");
				b1.setMaximumSize(new Dimension(50, 30));
				JButton bx = new JButton(" X ");
				bx.setMaximumSize(new Dimension(50, 30));
				JButton b2 = new JButton(" 2 ");
				b2.setMaximumSize(new Dimension(50, 30));
				
				buttonGrid.add(b1);
				buttonGrid.add(Box.createHorizontalStrut(20));
				buttonGrid.add(bx);
				buttonGrid.add(Box.createHorizontalStrut(20));
				buttonGrid.add(b2);
				
				JPanel eventInfo = new JPanel();
				eventInfo.setLayout(new GridLayout(1, 2));
				eventInfo.add(nameAndDate);
				eventInfo.add(buttonGrid);
				panel.add(Box.createHorizontalStrut(50));
				panel.add(eventInfo);	
			}
		}
		panel.updateUI();
	}
	
	
	
	private void setMultiple(JPanel panel, String categoryID) {
		List<EventContainer> events = bl.getEventsByCategory(categoryID);
		panel.removeAll();
		panel.updateUI();
		
		if( events == null || events.isEmpty() ) {
			panel.add(new JLabel("No events found for the " + categoryID + " category"));
		}else {
			for(EventContainer e : events) {
				Event ev = e.getEvent();
				JLabel nameAndDate = new JLabel();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(ev.getEventDate());
				String name = ev.getDescription();
				nameAndDate.setText("[" + date + "] " + name + ": ");
				nameAndDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
				
				JPanel eventInfo = new JPanel();
				eventInfo.setLayout(new BorderLayout());
				
				eventInfo.add(nameAndDate, BorderLayout.WEST);
				
				panel.add(eventInfo);
				
				JPanel buttonGrid = new JPanel();
				buttonGrid.setLayout(new GridLayout(4, 5));
				for(Result r : ev.getPosibleResults()) {
					JButton b = new JButton(r.getResult());	
					buttonGrid.add(b);		
				}
				panel.add(buttonGrid);
				panel.add(new JSeparator());
			}
		}
		panel.updateUI();
	}
}

