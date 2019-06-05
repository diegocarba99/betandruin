package domain;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Movement {
	@Id
	@XmlID
	@GeneratedValue
	int id;
	@XmlIDREF
	VWallet vwallet = null;
	Date movDate = null;
	double amount = 0;
	char option = 0; // - or +
	
	public Movement(VWallet wallet, Date movDate, Double amount, char option) {
		this.vwallet = wallet;
		this.movDate = movDate;
		this.amount = amount;
		this.option = option;
	}
	
	public Date getMovDate() {
		return movDate;
	}

	public double getAmount() {
		return amount;
	}

	public char getOption() {
		return option;
	}
	
	public int getID() {
		return this.id;
	}
	
	public VWallet getWallet() {
		return this.vwallet;
	}
	
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return this.getID() + " Amount:" + this.getOption() + this.getAmount() + " Date:" + dateFormat.format(this.movDate);
	}
}
