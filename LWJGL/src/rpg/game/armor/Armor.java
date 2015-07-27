package rpg.game.armor;

import rpg.game.Game;
import rpg.game.Stats;
import rpg.game.items.Item;
import rpg.game.player.Inventory;

public class Armor extends Item{

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
		isArmor = true;
	}
	
	// make an extra class for each armor object to make some different scalings
	public static Armor get(ArmorObject object){
		Armor armor = null;

		switch(object){
		case simpleBoots: 
			armor = new Armor("Einfache Schuhe", Type.SHOES, Tier.T1, 1, 100, 200, "res/armor/shoes/A_Shoes01.png");
			armor.getStats().setmDef(2);
			armor.getStats().setpDef(3);
			break;
		}
		
		return armor;
	}
	
	public void equip(int inventoryIndex){
		
		switch(this.type){
		case HEAD:
			Game.PLAYER.getEquip().setHead(this);
			Inventory.remove(this);
			break;
		case SHOULDERS:
			Game.PLAYER.getEquip().setShoulder(this);
			Inventory.remove(this);
			break;
		case BREAST:
			Game.PLAYER.getEquip().setBreast(this);
			Inventory.remove(this);
			break;
		case GLOVES:
			Game.PLAYER.getEquip().setGloves(this);
			Inventory.remove(this);
			break;
		case BELT:
			Game.PLAYER.getEquip().setBelt(this);
			Inventory.remove(this);
			break;
		case PANTS:
			Game.PLAYER.getEquip().setPants(this);
			Inventory.remove(this);
			break;
		case SHOES:
			Game.PLAYER.getEquip().setShoes(this);
			Inventory.remove(this);
			break;
		}
		
		Game.PLAYER.recalculateStats();
	}
	
	public Type getType(){
		return type;
	}
	
	public Stats getStats(){
		return stats;
	}
}
