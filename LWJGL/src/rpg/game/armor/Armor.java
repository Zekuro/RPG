package rpg.game.armor;

import rpg.game.Stats;
import rpg.game.items.Item;

public abstract class Armor extends Item{

	private Type type;
	private Stats stats = new Stats();
	
	public static enum Type{
		HEAD, BREAST, SHOULDERS,BELT,PANTS, SHOES, GLOVES, NECKLACE, RING, EARRING
	}
	
	public static enum ArmorObject{
		simpleBoots
	}
	
	public Armor(String name, Type type, Tier tier, int requiredLvl, int sellPrice,
			int buyPrice, String texture) {
		super(name, tier, requiredLvl, sellPrice, buyPrice, texture);
		this.type = type;
	}
	
	public static Armor get(ArmorObject object){
		Armor armor = null;

		switch(object){
		// TODO
		case simpleBoots: armor = null;
		break;
		}
		
		return armor;
	}
	
	public Stats getStats(){
		return stats;
	}
}
