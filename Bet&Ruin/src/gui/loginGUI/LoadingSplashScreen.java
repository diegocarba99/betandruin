package gui.loginGUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import businessLogic.BLFacadeImplementation;

import gui.mainGUI.GUIMainPage;
import sportsdbConnection.JsonReader;
import domain.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;


import java.awt.Font;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;

public class LoadingSplashScreen extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BLFacadeImplementation bl;
	JProgressBar progressBar;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public LoadingSplashScreen() {
		this.setIconImage(new ImageIcon("resources/images/brlogo.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		setUndecorated(true);
		setBackground(new Color(25,31,38));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(25,31,38));
		setContentPane(contentPane);
		progressBar = new JProgressBar();
		 progressBar.setForeground(new Color(64, 237, 250));
		 progressBar.setUI(new BasicProgressBarUI() {
			 protected Color getSelectionBackground() { return new Color(25,31,38); }
		      protected Color getSelectionForeground() { return new Color(25,31,38); }
		 });
		 
		ImageIcon image = new ImageIcon("resources/images/loading1.gif");
		JLabel lblLoadingData = new JLabel(image);
		lblLoadingData.setText("");
		lblLoadingData.setFont(new Font("Gabriola", Font.BOLD, 34));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(progressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblLoadingData)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(lblLoadingData)
					.addPreferredGap(ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
		bl = new BLFacadeImplementation();
		
	}

	

	



	@Override
	public void run() {
		
		
		progressBar.setMinimum(0);
		
		progressBar.setStringPainted(true);
		
		BetDecider betDecider = new BetDecider();
		betDecider.run();

		////////////////////////////
		try {
			bl.openDB();
			JSONObject leaguesJson = JsonReader
					.getJsonFromURL("https://www.thesportsdb.com/api/v1/json/1/all_leagues.php");
			JSONArray array = leaguesJson.getJSONArray("leagues");
			progressBar.setMaximum(array.length());
			for (int i = 0; i < array.length(); i++) {
				bl.loadEventsFromAPI(array, i);
				progressBar.setValue(i);
			}
			bl.closeDB();

		} catch (JSONException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//////////////////////////////////////
		
		while( !betDecider.hasFinished() ) {}
		
		GUIMainPage main = GUIMainPage.getInstance();
		this.setVisible(false);
		main.setVisible(true);
		this.dispose();

	}
	
	public class BetDecider extends Thread{
		boolean finished = false;
		
		public void run(){
			finished = bl.decideBets(UserSingleton.getInstance().getId());
		} 
	   
		public boolean hasFinished() {
		   return finished;
		}
	}	
	
	
}


