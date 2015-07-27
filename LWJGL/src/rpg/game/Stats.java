package rpg.game;

public class Stats {

	private int intelligence;
	private int strength;
	private int wisdom;
	private int vitality;
	
	private int pDef;
	private int mDef;
	
	private int fireResistance;
	private int iceResistance;
	private int electricResistance;
	
	public void render(int x, int y){
		
		Font.render("Int: " + intelligence, x, y);
		Font.render("Str: " + strength, x, y - 16);
		Font.render("Wis: " + wisdom, x, y - 32);
		Font.render("Vit: " + vitality, x, y - 48);
		
		Font.render("Armor: " + pDef, x, y - 80);
		Font.render("M-Res: " + mDef, x, y - 96);
		
		Font.render("eleRes: " + electricResistance, x, y - 128);
		Font.render("iceRes: " + iceResistance, x, y - 144);
		Font.render("fireRes: " + fireResistance, x, y - 160);
	}
	
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getWisdom() {
		return wisdom;
	}
	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}
	public int getVitality() {
		return vitality;
	}
	public void setVitality(int vitality) {
		this.vitality = vitality;
	}
	public int getmDef() {
		return mDef;
	}
	public void setmDef(int mDef) {
		this.mDef = mDef;
	}
	public int getpDef() {
		return pDef;
	}
	public void setpDef(int pDef) {
		this.pDef = pDef;
	}
	public int getFireResistance() {
		return fireResistance;
	}
	public void setFireResistance(int fireResistance) {
		this.fireResistance = fireResistance;
	}
	public int getIceResistance() {
		return iceResistance;
	}
	public void setIceResistance(int iceResistance) {
		this.iceResistance = iceResistance;
	}
	public int getElectricResistance() {
		return electricResistance;
	}
	public void setElectricResistance(int electricResistance) {
		this.electricResistance = electricResistance;
	}
	public int[] toArray(){
		int[] stats = new int[9];
		
		stats[0] = intelligence;
		stats[1] = strength;
		stats[2] = wisdom;
		stats[3] = vitality;
		stats[4] = pDef;
		stats[5] = mDef;
		stats[6] = fireResistance;
		stats[7] = iceResistance;
		stats[8] = electricResistance;
		
		return stats;
	}
	
	public String getStatName(int i){
		String name = null;
		
		switch(i){
		case 0: name = "int";
		break;
		case 1: name = "str";
		break;
		case 2: name = "wis";
		break;
		case 3: name = "vit";
		break;
		case 4: name = "pDef";
		break;
		case 5: name = "mDef";
		break;
		case 6: name = "fireRes";
		break;
		case 7: name = "iceRes";
		break;
		case 8: name = "eleRes";
		break;
		}
		
		return name;
	}
	
}
