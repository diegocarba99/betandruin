package domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class VWallet {

	@Id
	@XmlID
	@GeneratedValue
	int id;
	
	@XmlIDREF
	@OneToOne
	User mem = new User();
	double moneyAmount = 0;
	double points = 0;
	Date date = null;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	List<Bet> bets;
	
	public void addBet(Bet b) {
		if( bets == null) bets = new LinkedList<Bet>();
		bets.add(b);
	}

	public List<Bet> getBets() {
		return bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public VWallet() {
		date = new Date();
		moneyAmount = 0;
	}

	public void setUser(User user) {
		mem = user;
	}
	
	public void setBalance(double quantity) {
		this.moneyAmount = quantity;
	}

	public int getID() {
		return id;
	}

	public double getBalance() {
		return moneyAmount;
	}

	public double getPoints() {
		return moneyAmount * 10;
	}

	public Date getDate() {
		return this.date;
	}
	
	public double addFounds(double amount) {
		try {
			moneyAmount += amount;
			return moneyAmount;
		} catch (Exception e) {
			System.out.println(e);
			return moneyAmount;
		}
	}

	public double withdrawFounds(double amount) {
		try {
			moneyAmount -= amount;
			return moneyAmount;
		} catch (Exception e) {
			System.out.println(e);
			return moneyAmount;
		}
	}

	
}
