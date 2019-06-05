package gui.adminGUI;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Bet;
import domain.Event;
import domain.Movement;
import domain.MovementContainer;
import domain.User;
import domain.UserContainer;
import gui.GUIChangePassword;
import gui.loginGUI.GUILogin;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GUIMainAdm extends JFrame {
	static User user = new User();
	private BLFacade bl;
	private JTextField txtSearch;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private String selected;
	private Image newimg1 = null;
	private Image newimg2 = null;
	private Image newimg3 = null;
	private Image newimg4 = null;
	private JTable table;
	private String[] betCols = new String[] { "ID", "Date", "Beted Points", "Points to Earn" };
	private String[] evCols = new String[] { "ID", "Description", "Sport", "Category" };
	private String[] usCols = new String[] { "ID", "Name", "is Admin", "Wallet ID" };
	private String[] movCols = new String[] { "ID", "Amount", "Date" };

	/**
	 * Create the application.
	 */
	public GUIMainAdm(String elem) {
		try {
			Image gear = ImageIO.read(new File("resources/images/gear.png"));
			newimg1 = gear.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
			Image pass = ImageIO.read(new File("resources/images/password.png"));
			newimg2 = pass.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
			Image logout = ImageIO.read(new File("resources/images/LogOut.png"));
			newimg3 = logout.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			Image exit = ImageIO.read(new File("resources/images/exit.png"));
			newimg4 = exit.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.selected = elem;
		BLFacade bl = new BLFacadeImplementation();
		this.setBusinessLogic(bl);
		initialize();
	}

	private void setBusinessLogic(BLFacade bl2) {
		this.bl = bl2;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 593, 420);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		setResizable(false);

		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 32, 567, 59);
		panel.add(panel_1);

		JLabel lblAdministrativia = new JLabel("ADMINISTRATION");
		lblAdministrativia.setVerticalAlignment(SwingConstants.CENTER);
		lblAdministrativia.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministrativia.setForeground(Color.BLACK);
		lblAdministrativia.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 32));
		lblAdministrativia.setBackground(Color.GRAY);
		lblAdministrativia.setBounds(139, 11, 297, 40);
		panel_1.add(lblAdministrativia);

		JComboBox<String> cmbElement = new JComboBox<String>();
		cmbElement.addItem("User");
		cmbElement.addItem("Movement");
		cmbElement.addItem("Bet");
		cmbElement.addItem("Event");
		cmbElement.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbElement.setBounds(10, 120, 131, 25);
		panel.add(cmbElement);

		JComboBox<String> cmbElemChoice = new JComboBox<String>();
		cmbElemChoice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbElemChoice.setBounds(151, 120, 131, 25);
		panel.add(cmbElemChoice);

		JComboBox<String> cmbOrder = new JComboBox<String>();
		cmbOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbOrder.setBounds(446, 120, 131, 25);
		panel.add(cmbOrder);

		JLabel lblElement = new JLabel("Element");
		lblElement.setLabelFor(cmbElement);
		lblElement.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElement.setBounds(10, 102, 72, 18);
		panel.add(lblElement);

		JLabel lblOrderBy = new JLabel("Order by");
		lblOrderBy.setLabelFor(cmbOrder);
		lblOrderBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOrderBy.setBounds(446, 102, 72, 18);
		panel.add(lblOrderBy);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearch.setBounds(151, 102, 81, 18);
		panel.add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.setBounds(292, 120, 144, 25);
		panel.add(txtSearch);
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSearch.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 597, 21);
		panel.add(menuBar);

		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		mnNewMenu.setIcon(new ImageIcon(newimg1));

		JMenuItem mntmChangePassword = new JMenuItem("Change password");
		mntmChangePassword.setIcon(new ImageIcon(newimg2));
		mnNewMenu.add(mntmChangePassword);

		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.setIcon(new ImageIcon(newimg3));
		mnNewMenu.add(mntmLogOut);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(newimg4));
		mnNewMenu.add(mntmExit);

		JPanel rdPanel = new JPanel();
		rdPanel.setBounds(292, 120, 144, 25);
		panel.add(rdPanel);
		rdPanel.setLayout(null);

		ButtonGroup group = new ButtonGroup();
		JRadioButton rdbtnTrue = new JRadioButton("True");
		rdbtnTrue.setSelected(true);
		rdbtnTrue.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnTrue.setBounds(12, 0, 62, 23);
		group.add(rdbtnTrue);
		rdPanel.add(rdbtnTrue);

		JRadioButton rdbtnFalse = new JRadioButton("False");
		rdbtnFalse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnFalse.setBounds(76, 0, 62, 23);
		group.add(rdbtnFalse);
		rdPanel.add(rdbtnFalse);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 156, 567, 225);
		panel.add(scrollPane_1);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tableModel);
		scrollPane_1.setViewportView(table);

		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtSearch.getText() != null) {
					String element = cmbElement.getSelectedItem().toString();
					String where = cmbElemChoice.getSelectedItem().toString();
					String like = txtSearch.getText();

					switch (element) {
					case "User":
						List<UserContainer> UList = null;
						UList = bl.getUsersLike(where, like);
						
						tableModel.setDataVector(null, usCols);
						tableModel.setColumnCount(usCols.length);
						table.getColumnModel().getColumn(0).setPreferredWidth(25);
						table.getColumnModel().getColumn(1).setPreferredWidth(268);
						table.getColumnModel().getColumn(2).setPreferredWidth(30);
						table.getColumnModel().getColumn(3).setPreferredWidth(15);
						
						for (UserContainer uco : UList) {
							User u = uco.getUser();
							Vector<Object> row = new Vector<Object>();
							row.add(u.getId());
							row.add(u.getUserName());
							row.add(u.isAdmin());
							row.add(u.getWallet().getID());
							tableModel.addRow(row);
						}
						break;
					case "Movement":
						List<MovementContainer> MList = null;
						MList = bl.getMovementsLike(where, like);
						
						tableModel.setDataVector(null, movCols);
						tableModel.setColumnCount(movCols.length);
						for (MovementContainer moco : MList){
							Movement m = moco.getMovement();
							Vector<Object> row = new Vector<Object>();
							row.add(m.getID());
							row.add(m.getAmount());
							row.add(m.getMovDate());
							tableModel.addRow(row);
						}
						break;
					case "Event":
						List<Event> EList = null;
						EList = bl.getEventsLike(where, like);
						
						tableModel.setDataVector(null, evCols);
						tableModel.setColumnCount(evCols.length);
						table.getColumnModel().getColumn(0).setPreferredWidth(25);
						table.getColumnModel().getColumn(1).setPreferredWidth(268);
						table.getColumnModel().getColumn(2).setPreferredWidth(30);
						table.getColumnModel().getColumn(3).setPreferredWidth(15);

						for (Event ev : EList){
							Vector<Object> row = new Vector<Object>();
							row.add(ev.getEventID());
							row.add(ev.getDescription());
							row.add(ev.getSportID());
							row.add(ev.getCategoryID());
							tableModel.addRow(row);
						}
						break;
					case "Bet":
						List<Bet> BList = null;
						BList = bl.getBetsLike(where, like);
						
						tableModel.setDataVector(null, betCols);
						tableModel.setColumnCount(betCols.length);
						
						for (Bet b : BList){
							Vector<Object> row = new Vector<Object>();
							row.add(b.getBetId());
							row.add(b.getDueDate());
							row.add(b.getMoneyBetted());
							row.add(b.getEarnings());
							tableModel.addRow(row);
						}
						break;
					default:
						break;
					}
				}
			}
		});

		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (txtSearch.getText() != null) {
						String element = cmbElement.getSelectedItem().toString();
						String where = cmbElemChoice.getSelectedItem().toString();
						String like = txtSearch.getText();

						switch (element) {
						case "User":
							List<UserContainer> UList = null;
							UList = bl.getUsersLike(where, like);
							if (UList != null) {
								tableModel.setDataVector(null, usCols);
								tableModel.setColumnCount(usCols.length);
								table.getColumnModel().getColumn(0).setPreferredWidth(25);
								table.getColumnModel().getColumn(1).setPreferredWidth(268);
								table.getColumnModel().getColumn(2).setPreferredWidth(30);
								table.getColumnModel().getColumn(3).setPreferredWidth(15);
								
								for (UserContainer uc : UList) {
									User u = uc.getUser();
									Vector<Object> row = new Vector<Object>();
									row.add(u.getId());
									row.add(u.getUserName());
									row.add(u.isAdmin());
									row.add(u.getWallet().getID());
									tableModel.addRow(row);
								}
							}
							break;
						case "Movement":
							List<MovementContainer> MList = null;
							MList = bl.getMovementsLike(where, like);
							if (MList != null) {
								tableModel.setDataVector(null, movCols);
								tableModel.setColumnCount(movCols.length);
								
								for (MovementContainer moco : MList) {
									Movement m = moco.getMovement();
									Vector<Object> row = new Vector<Object>();
									row.add(m.getID());
									row.add(m.getAmount());
									row.add(m.getMovDate());
									tableModel.addRow(row);
								}
							}
							break;
						case "Event":
							List<Event> EList = null;
							EList = bl.getEventsLike(where, like);
							if (EList != null) {
								tableModel.setDataVector(null, evCols);
								tableModel.setColumnCount(evCols.length);
								table.getColumnModel().getColumn(0).setPreferredWidth(25);
								table.getColumnModel().getColumn(1).setPreferredWidth(268);
								table.getColumnModel().getColumn(2).setPreferredWidth(30);
								table.getColumnModel().getColumn(3).setPreferredWidth(15);

								for (Event ev : EList) {
									Vector<Object> row = new Vector<Object>();
									row.add(ev.getEventID());
									row.add(ev.getDescription());
									row.add(ev.getSportID());
									row.add(ev.getCategoryID());
									tableModel.addRow(row);
								}
							}
							break;
						case "Bet":
							List<Bet> BList = null;
							BList = bl.getBetsLike(where, like);
							if (BList != null) {
								tableModel.setDataVector(null, betCols);
								tableModel.setColumnCount(betCols.length);
								
								for (Bet b : BList) {
									Vector<Object> row = new Vector<Object>();
									row.add(b.getBetId());
									row.add(b.getDueDate());
									row.add(b.getMoneyBetted());
									row.add(b.getEarnings());
									tableModel.addRow(row);
								}
							}
							break;
						default:
							break;
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (txtSearch.getText() != null) {
					switch (cmbElemChoice.getSelectedItem().toString()) {
					case "vwallet.id":
						isInteger(e);
						break;
					case "amount":
						isDouble(e);
						break;
					case "eventID":
						isInteger(e);
						break;
					case "description":
						isString(e);
						break;
					case "sportID":
						isString(e);
						break;
					case "categoryID":
						isInteger(e);
						break;
					case "betId":
						isInteger(e);
						break;
					case "id":
						isInteger(e);
						break;
					case "userName":
						isString(e);
						break;
					default:
						break;
					}
				}
			}

			public void isDouble(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()) && !(e.getKeyChar() == '.')) {
					e.consume();
				}
			}

			public void isString(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}

			public void isInteger(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});

		cmbElement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (cmbElement.getSelectedItem().toString()) {
				case "User":
					cmbElemChoice.removeAllItems();
					cmbElemChoice.addItem("id");
					cmbElemChoice.addItem("userName");
					cmbElemChoice.addItem("isAdmin");

					cmbOrder.removeAllItems();
					cmbOrder.addItem("id");
					cmbOrder.addItem("userName");
					cmbOrder.addItem("isAdmin");

					updateJList("User", false);
					break;
				case "Movement":
					cmbElemChoice.removeAllItems();
					cmbElemChoice.addItem("vwallet.id");
					cmbElemChoice.addItem("amount");

					cmbOrder.removeAllItems();
					cmbOrder.addItem("vwallet.id");
					cmbOrder.addItem("movDate");
					cmbOrder.addItem("amount");

					updateJList("Movement", false);
					break;
				case "Event":
					cmbElemChoice.removeAllItems();
					cmbElemChoice.addItem("eventID");
					cmbElemChoice.addItem("description");
					cmbElemChoice.addItem("sportID");
					cmbElemChoice.addItem("categoryID");

					cmbOrder.removeAllItems();
					cmbOrder.addItem("eventID");
					cmbOrder.addItem("description");
					cmbOrder.addItem("eventDate");
					cmbOrder.addItem("sportID");
					cmbOrder.addItem("categoryID");

					updateJList("Event", false);
					break;
				case "Bet":
					cmbElemChoice.removeAllItems();
					cmbElemChoice.addItem("betId");

					cmbOrder.removeAllItems();
					cmbOrder.addItem("betId");
					cmbOrder.addItem("earnings");
					cmbOrder.addItem("dueDate");
					cmbOrder.addItem("moneyBetted");

					updateJList("Bet", false);
					break;
				default:
					break;
				}
			}

			private void updateJList(String element, boolean choice) {
				if (choice) {

				} else {
					switch (element) {
					case "User":
						List<UserContainer> UList = new ArrayList<UserContainer>();
						UList = bl.getUserList(null);

						tableModel.setDataVector(null, usCols);
						tableModel.setColumnCount(usCols.length);
						table.getColumnModel().getColumn(0).setPreferredWidth(25);
						table.getColumnModel().getColumn(1).setPreferredWidth(268);
						table.getColumnModel().getColumn(2).setPreferredWidth(30);
						table.getColumnModel().getColumn(3).setPreferredWidth(15);
						
						for (UserContainer uco : UList) {
							User u = uco.getUser();
							Vector<Object> row = new Vector<Object>();
							row.add(u.getId());
							row.add(u.getUserName());
							row.add(u.isAdmin());
							row.add(u.getWallet().getID());
							tableModel.addRow(row);
						}
						break;
					case "Movement":
						List<Movement> MList = null;
						MList = bl.getMovementList(null);

						tableModel.setDataVector(null, movCols);
						tableModel.setColumnCount(movCols.length);

						for (Movement m : MList) {
							Vector<Object> row = new Vector<Object>();
							row.add(m.getID());
							row.add(m.getAmount());
							row.add(m.getMovDate());
							tableModel.addRow(row);
						}
						break;
					case "Event":
						List<Event> EList = null;
						EList = (ArrayList<Event>) bl.getEventList(null);

						tableModel.setDataVector(null, evCols);
						tableModel.setColumnCount(evCols.length);
						table.getColumnModel().getColumn(0).setPreferredWidth(25);
						table.getColumnModel().getColumn(1).setPreferredWidth(268);
						table.getColumnModel().getColumn(2).setPreferredWidth(30);
						table.getColumnModel().getColumn(3).setPreferredWidth(15);

						for (Event e : EList) {
							Vector<Object> row = new Vector<Object>();
							row.add(e.getEventID());
							row.add(e.getDescription());
							row.add(e.getSportID());
							row.add(e.getCategoryID());
							tableModel.addRow(row);
						}
						break;
					case "Bet":
						List<Bet> BList = null;
						BList = bl.getBetList(null);

						tableModel.setDataVector(null, betCols);
						tableModel.setColumnCount(betCols.length);

						for (Bet b : BList) {
							Vector<Object> row = new Vector<Object>();
							row.add(b.getBetId());
							row.add(b.getDueDate());
							row.add(b.getMoneyBetted());
							row.add(b.getEarnings());
							tableModel.addRow(row);
						}
						break;
					default:
						break;
					}
				}
			}
		});
		cmbElement.setSelectedItem(selected);

		rdbtnTrue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.setDataVector(null, usCols);
				tableModel.setColumnCount(usCols.length);
				table.getColumnModel().getColumn(0).setPreferredWidth(25);
				table.getColumnModel().getColumn(1).setPreferredWidth(268);
				table.getColumnModel().getColumn(2).setPreferredWidth(30);
				table.getColumnModel().getColumn(3).setPreferredWidth(15);
				
				List<UserContainer> UList = bl.getUsersLike("isAdmin", "true");
				for (UserContainer uCon : UList) {
					User u = uCon.getUser();
					Vector<Object> row = new Vector<Object>();
					row.add(u.getId());
					row.add(u.getUserName());
					row.add(u.isAdmin());
					row.add(u.getWallet().getID());
					tableModel.addRow(row);
				}
			}
		});
		
		rdbtnFalse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.setDataVector(null, usCols);
				tableModel.setColumnCount(usCols.length);
				table.getColumnModel().getColumn(0).setPreferredWidth(25);
				table.getColumnModel().getColumn(1).setPreferredWidth(268);
				table.getColumnModel().getColumn(2).setPreferredWidth(30);
				table.getColumnModel().getColumn(3).setPreferredWidth(15);
				
				List<UserContainer> UList = bl.getUsersLike("isAdmin", "false");
				for (UserContainer uco : UList) {
					User u = uco.getUser();
					Vector<Object> row = new Vector<Object>();
					row.add(u.getId());
					row.add(u.getUserName());
					row.add(u.isAdmin());
					row.add(u.getWallet().getID());
					tableModel.addRow(row);
				}
			}
		});

		cmbElemChoice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (cmbElemChoice.getSelectedItem() != null) {
					if (cmbElemChoice.getSelectedItem().toString().equals("isAdmin")) {
						txtSearch.setVisible(false);
						txtSearch.setEnabled(false);
						rdPanel.setVisible(true);
						rdPanel.setEnabled(true);
					} else {
						rdPanel.setVisible(false);
						rdPanel.setEnabled(false);
						txtSearch.setVisible(true);
						txtSearch.setEnabled(true);
					}
				}
			}
		});

		cmbOrder.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					String element = cmbElement.getSelectedItem().toString();
					String choice = cmbOrder.getSelectedItem().toString();

					switch (element) {
					case "User":
						List<UserContainer> UList = new ArrayList<UserContainer>();
						UList = bl.getUserList(choice);

						tableModel.setDataVector(null, usCols);
						tableModel.setColumnCount(usCols.length);
						table.getColumnModel().getColumn(0).setPreferredWidth(25);
						table.getColumnModel().getColumn(1).setPreferredWidth(268);
						table.getColumnModel().getColumn(2).setPreferredWidth(30);
						table.getColumnModel().getColumn(3).setPreferredWidth(15);
						
						for (UserContainer uco : UList) {
							User u = uco.getUser();
							Vector<Object> row = new Vector<Object>();
							row.add(u.getId());
							row.add(u.getUserName());
							row.add(u.isAdmin());
							row.add(u.getWallet().getID());
							tableModel.addRow(row);
						}
						break;
					case "Movement":
						List<Movement> MList = null;
						MList = bl.getMovementList(choice);

						tableModel.setDataVector(null, movCols);
						tableModel.setColumnCount(movCols.length);

						for (Movement m : MList) {
							Vector<Object> row = new Vector<Object>();
							row.add(m.getID());
							row.add(m.getAmount());
							row.add(m.getMovDate());
							tableModel.addRow(row);
						}
						break;
					case "Event":
						List<Event> EList = null;
						EList = (ArrayList<Event>) bl.getEventList(choice);

						tableModel.setDataVector(null, evCols);
						tableModel.setColumnCount(evCols.length);
						table.getColumnModel().getColumn(0).setPreferredWidth(25);
						table.getColumnModel().getColumn(1).setPreferredWidth(268);
						table.getColumnModel().getColumn(2).setPreferredWidth(30);
						table.getColumnModel().getColumn(3).setPreferredWidth(15);

						for (Event e : EList) {
							Vector<Object> row = new Vector<Object>();
							row.add(e.getEventID());
							row.add(e.getDescription());
							row.add(e.getSportID());
							row.add(e.getCategoryID());
							tableModel.addRow(row);
						}
						break;
					case "Bet":
						List<Bet> BList = null;
						BList = bl.getBetList(choice);

						tableModel.setDataVector(null, betCols);
						tableModel.setColumnCount(betCols.length);

						for (Bet b : BList) {
							Vector<Object> row = new Vector<Object>();
							row.add(b.getBetId());
							row.add(b.getDueDate());
							row.add(b.getMoneyBetted());
							row.add(b.getEarnings());
							tableModel.addRow(row);
						}
						break;
					default:
						break;
					}
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (cmbElement.getSelectedItem() != null && table.getValueAt(table.getSelectedRow(), 0) != null) {
						String eleem = cmbElement.getSelectedItem().toString();
						String iden = table.getValueAt(table.getSelectedRow(), 0).toString();

						switch (eleem) {
						case "Movement":
							GUIMovementAdm MovWin = new GUIMovementAdm(iden);
							setVisible(false);
							MovWin.setVisible(true);
							break;
						case "User":
							GUIUserAdm UsWin = new GUIUserAdm(iden);
							setVisible(false);
							UsWin.setVisible(true);
							break;
						case "Bet":
							GUIBetAdm BetWin = new GUIBetAdm(iden);
							setVisible(false);
							BetWin.setVisible(true);
							break;
						case "Event":
							GUIEventAdm EvWin = new GUIEventAdm(iden);
							setVisible(false);
							EvWin.setVisible(true);
							break;
						default:
							break;
						}
					}
				}
			}
		});

		////// MENU BAR
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(GUIMainAdm.this, "Are you sure you want to Log Out? ", "Log Out",
						JOptionPane.YES_NO_OPTION);
				if (r == JOptionPane.YES_OPTION) {
					GUILogin win = new GUILogin();
					win.setVisible(true);
					dispose();
				}
			}
		});

		mntmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIChangePassword win = new GUIChangePassword();
				win.setVisible(true);
			}
		});

		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
	}
}
