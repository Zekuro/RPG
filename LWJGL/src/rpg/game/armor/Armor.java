package rpg.game.armor;

import rpg.game.Stats;
import rpg.game.items.Item;

public abstract class Armor extends Item{

	private Type type;
	private Stats stats;
	// ?
	private int setId;
	
	public static enum Type{
		HEAD, BREAST, SHOULDERS,BELT,PANTS, SHOES, GLOVES, NECKLACE, RING, EARRING
	}
	
	public Armor(String name, Tier tier, int neededLvl, int sellPrice,
			int buyPrice, String texture) {
		super(name, tier, neededLvl, sellPrice, buyPrice, texture);
	}

}
