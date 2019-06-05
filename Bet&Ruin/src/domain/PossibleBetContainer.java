package domain;

public class PossibleBetContainer {
	
	private PossibleBet possibleBet;
	private Event event;
	
	public PossibleBetContainer() {
		this.possibleBet = null;
		this.event = null;
	}
	
	public PossibleBetContainer(PossibleBet possibleBet) {
		this.possibleBet = possibleBet;
		this.event = this.possibleBet.getRelatedEvent();
	}

	public PossibleBet getPossibleBet() {
		return possibleBet;
	}

	public void setPossibleBet(PossibleBet possibleBet) {
		this.possibleBet = possibleBet;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public String toString() {
		return possibleBet.toString() + "//" + event.toString() + "\\";
	}

}
