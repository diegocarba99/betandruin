package domain;

import java.util.LinkedList;
import java.util.List;

public class UserSingleton {
	private int id;
	private String userName;
	private String password;
	private boolean isAdmin;
	private VWallet wallet;
	private List<Bet> userBets;
	private static UserSingleton user;
	
	private UserSingleton() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public VWallet getWallet() {
		return wallet;
	}

	public void setWallet(VWallet wallet) {
		this.wallet = wallet;
	}
	
	public List<Bet> getUserBets() {
		return userBets;
	}

	public void setUserBets(List<Bet> userBets) {
		this.userBets = userBets;
	}
	
	public void addBet(Bet b) {
		if(userBets == null) userBets = new LinkedList<Bet>();
		userBets.add(b);
	}

	public static UserSingleton getInstance() {
		if( user == null ) user = new UserSingleton();
		 return user;
	}

	public void showBets() {
		for (Bet bet : userBets) {
			System.out.println( bet.toString() );
			List<PossibleBet> pb = bet.getBets();
			for (PossibleBet possibleBet : pb) {
				System.out.println("\t" + possibleBet.toString());
			}
		}
	}
}
