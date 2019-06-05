package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Bet {

	@XmlID
	@Id
	private int betId;
	
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<PossibleBet> bets;
	private Double earnings;
	private Date dueDate;
	private Double moneyBetted;
	private int status;

	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String stat;
		if (this.status == 1){
			stat = "WON";
		}else if(this.status == 0) {
			stat = "LOST";
		}else {
			stat = "PENDING";
		}
		
		return betId + " Date:" + dateFormat.format(dueDate) + " Betted:" + moneyBetted + "RP  To earn:" + String.format("%.2f", (earnings * moneyBetted)) + "RP - status = " + stat;
	}

	public Bet(List<PossibleBet> betsToProccess) {
		this.betId = new Random().nextInt(10000);
		this.bets = betsToProccess;
		this.earnings = calculateEarnings(betsToProccess);
		this.setDueDate(calculateDueDate());
		this.status = -1;
	}

	public void setMoneyBetted(Double money) {
		this.moneyBetted = money;
	}

	private double calculateEarnings(List<PossibleBet> betsToProccess) {
		Double resul = 1.0;
		for (PossibleBet pb : betsToProccess) {
			resul = resul * pb.getMultiplier();
		}

		return Double.parseDouble(String.format("%.2f", resul).replace(',', '.'));
		
	}

	private Date calculateDueDate() {
		Date resul = new Date(0);
		for (PossibleBet pb : bets) {
			if (pb.getRelatedEvent().getEventDate().after(resul))
				resul = pb.getRelatedEvent().getEventDate();

		}
		return resul;
	}

	public String earnings() {
		return earnings.toString();
	}

	public Integer getBetId() {
		return betId;
	}

	public void setBetId(Integer betId) {
		this.betId = betId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public double getMoneyBetted() {
		return moneyBetted;
	}

	public void setMoneyBetted(double moneyBetted) {
		this.moneyBetted = moneyBetted;
	}

	public List<PossibleBet> getBets() {
		return bets;
	}

	public void setBets(List<PossibleBet> bets) {
		this.bets = bets;
	}
	
	public Double getEarnings() {
		return earnings;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
