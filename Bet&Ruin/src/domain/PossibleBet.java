package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class PossibleBet {
	@XmlID
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int possibleBetId;
	private String description;
	private String winner;
	private double multiplier;
	@XmlIDREF
	private Event relatedEvent;
	
	public PossibleBet(String description, String winner, Double multiplier, Event relatedEvent) {
		super();
		this.description = description;
		this.winner = winner;
		this.multiplier = multiplier;
		this.relatedEvent = relatedEvent;
	}
	
	public PossibleBet() {
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public Double getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(Double multiplier) {
		this.multiplier = multiplier;
	}
	public Event getRelatedEvent() {
		return relatedEvent;
	}
	public void setRelatedEvent(Event relatedEvent) {
		this.relatedEvent = relatedEvent;
	}
	
	@Override
	public String toString() {
		return description + "[" + winner + " - " + String.format("%.2f", multiplier) + "]";
	}
	
}
