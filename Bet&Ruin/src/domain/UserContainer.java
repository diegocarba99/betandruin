package domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserContainer {
	private User user;
	private VWallet wallet;
	private List<Bet> bets;
	
	public UserContainer() {
		user = null;
		wallet = null;
		bets = null;
	}
	
	public UserContainer(User u) {
		user = u;
		wallet = u.getWallet();
		bets = wallet.getBets();
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User u) {
		this.user = u;
	}
	
	public VWallet getVWallet() {
		return this.wallet;
	}
	
	public void setVWallet(VWallet w) {
		this.wallet = w;
	}
	
	public List<Bet> getBets() {
		return this.bets;
	}
	
	public void setBets(List<Bet> betList) {
		this.bets = betList;
	}
	
	public String toString() {
		String est = "User: " + this.user + " / " + "VWallet: " + this.wallet + " / Bets: ";
		for(Bet b : bets)
			est += ", " + b;
		return est;
	}
}
