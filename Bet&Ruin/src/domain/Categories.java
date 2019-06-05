package domain;

public enum Categories {
	
	LIG		("La Liga",5), 
	SEG		("Segunda",6), 
	FCL		("Champions League",7), 
	FEL		("Europa League",8), 
	
	NBA		("NBA",9), 
	ACB		("ACB",10),
	EUR		("Euroliga",11), 
	
	ASO		("ASOBALL",12), 
	BCH		("HandBall Champions League",13), 
	
	ATP 	("ATP Tournaments",14), 
	
	F1		("Formula 1",15),
	MGP		("MotoGP",16); 
	
	private final String name;
	private final int number;
	
	Categories (String name, int number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}
	
	public static String[] getFootball() {
		String[] resul = {LIG.getName(), SEG.getName(), FCL.getName(), FEL.getName()};
		return resul;
	}
	
	public static String[] getHandball() {
		String[] resul = {ASO.getName(), BCH.getName()};
		return resul;
	}
	
	public static String[] getBasketBall() {
		String[] resul = {NBA.getName(), ACB.getName(), EUR.getName()};
		return resul;
	}
	
	public static String[] getTennis() {
		String[] resul = {ATP.getName()};
		return resul;
	}
	
	public static String[] getMotorsport() {
		String[] resul = {F1.getName(), MGP.getName()};
		return resul;
	}
	

	

}
