package domain;

public enum Sports {
	
	FUT		("Football",0), 
	BLM		("Handball", 1), 
	BSK		("BasketBall", 2), 
	TNS		("Tennis", 3), 
	MTP		("Motorsport", 4);
	
	private final String name;
	private final int number;
	
	
	Sports (String name, int number) {
		this.name = name;
		this.number = number;
	}


	public String getName() {
		return name;
	}


	public int getNumber() {
		return number;
	}

	
	

}
