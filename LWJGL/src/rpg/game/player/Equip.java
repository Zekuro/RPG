package rpg.game.player;

import rpg.game.armor.Armor;

public class Equip {

	private Armor head;
	private Armor shoulder;
	private Armor breast;
	private Armor gloves;
	private Armor belt;
	private Armor pants;
	private Armor shoes;
	
	private Armor earringLeft;
	private Armor earringRight;
	private Armor necklate;
	private Armor ringLeft;
	private Armor ringRight;
	
	private Armor[] armorList = new Armor[12];
	
	public Equip(){
		armorList[0] = head;
		armorList[1] = shoulder;
		armorList[2] = breast;
		armorList[3] = gloves;
		armorList[4] = belt;
		armorList[5] = pants;
		armorList[6] = shoes;
		
		armorList[7] = earringLeft;
		armorList[8] = earringRight;
		armorList[9] = necklate;
		armorList[10] = ringLeft;
		armorList[11] = ringRight;
	
	}
	
	public Armor getHead() {
		return armorList[0];
	}
	public void setHead(Armor head) {
		armorList[0] = head;
	}
	public Armor getShoulder() {
		return armorList[1];
	}
	public void setShoulder(Armor shoulder) {
		armorList[1] = shoulder;
	}
	public Armor getBreast() {
		return armorList[2];
	}
	public void setBreast(Armor breast) {
		armorList[2] = breast;
	}
	public Armor getGloves() {
		return armorList[3];
	}
	public void setGloves(Armor gloves) {
		armorList[3] = gloves;
	}
	public Armor getBelt() {
		return armorList[4];
	}
	public void setBelt(Armor belt) {
		armorList[4] = belt;
	}
	public Armor getPants() {
		return armorList[5];
	}
	public void setPants(Armor pants) {
		armorList [5] = pants;
	}
	public Armor getShoes() {
		return armorList[6];
	}
	public void setShoes(Armor shoes) {
		armorList[6] = shoes;
	}
	public Armor getEarringLeft() {
		return armorList[7];
	}
	public void setEarringLeft(Armor earringLeft) {
		armorList[7] = earringLeft;
	}
	public Armor getEarringRight() {
		return armorList[8];
	}
	public void setEarringRight(Armor earringRight) {
		armorList[8] = earringRight;
	}
	public Armor getNecklate() {
		return armorList[9];
	}
	public void setNecklate(Armor necklate) {
		armorList[9] = necklate;
	}
	public Armor getRingLeft() {
		return armorList[10];
	}
	public void setRingLeft(Armor ringLeft) {
		armorList[10] = ringLeft;
	}
	public Armor getRingRight() {
		return armorList[11];
	}
	public void setRingRight(Armor ringRight) {
		armorList[11] = ringRight;
	}
	
	public int getIntelligence(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getIntelligence();
		}
		
		return value;
	}
	
	public int getStrength(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getStrength();
		}
		
		return value;
	}
	
	public int getVitality(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getVitality();
		}
		
		return value;
	}
	
	public int getWisdom(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getWisdom();
		}
		
		return value;
	}
	
	public int getPDef(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getpDef();
		}
		
		return value;
	}
	
	public int getMDef(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getmDef();
		}
		
		return value;
	}
	
	public int getFireResistance(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getFireResistance();
		}
		
		return value;
	}
	
	public int getIceResistance(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getIceResistance();
		}
		
		return value;
	}
	
	public int getElectricResistance(){
		int value = 0;
		
		for (Armor armor : armorList) {
			if(armor != null) value += armor.getStats().getElectricResistance();
		}
		
		return value;
	}
}