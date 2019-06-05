package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MovementContainer {
	private Movement movement;
	private VWallet wallet;

	public MovementContainer() {
		movement = null;
		wallet = null;
	}
	
	public MovementContainer(Movement mov) {
		movement = mov;
		wallet = mov.getWallet();
	}
	
	public Movement getMovement() {
		return this.movement;
	}
	
	public void setMovement(Movement mov) {
		this.movement = mov;
	}

	public VWallet getWallet() {
		return this.wallet;
	}

	public void setWallet(VWallet wallet) {
		this.wallet = wallet;
	}
	
	public String toString() {
		return "VWallet: " + wallet + " / " + "Movement: " + movement;
	}
}
