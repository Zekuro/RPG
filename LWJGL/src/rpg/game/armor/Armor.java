package rpg.game.armor;

import rpg.game.Game;
import rpg.game.Stats;
import rpg.game.items.Item;
import rpg.game.player.Inventory;

public class Armor extends Item{

	private Type type;
	protected Stats stats = new Stats();
	
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
		stackable = false;
		
	}
	
	public void equip(int inventoryIndex){

		Armor wearing = null;
		
		switch(this.type){
		case HEAD:
			wearing = Game.PLAYER.getEquip().getHead();
			Game.PLAYER.getEquip().setHead(this);
			Inventory.remove(inventoryIndex);
			break;
		case SHOULDERS:
			wearing = Game.PLAYER.getEquip().getShoulder();
			Game.PLAYER.getEquip().setShoulder(this);
			Inventory.remove(inventoryIndex);
			break;
		case BREAST:
			wearing = Game.PLAYER.getEquip().getBreast();
			Game.PLAYER.getEquip().setBreast(this);
			Inventory.remove(inventoryIndex);
			break;
		case GLOVES:
			wearing = Game.PLAYER.getEquip().getGloves();
			Game.PLAYER.getEquip().setGloves(this);
			Inventory.remove(inventoryIndex);
			break;
		case BELT:
			wearing = Game.PLAYER.getEquip().getBelt();
			Game.PLAYER.getEquip().setBelt(this);
			Inventory.remove(inventoryIndex);
			break;
		case PANTS:
			wearing = Game.PLAYER.getEquip().getPants();
			Game.PLAYER.getEquip().setPants(this);
			Inventory.remove(inventoryIndex);
			break;
		case SHOES:
			wearing = Game.PLAYER.getEquip().getShoes();
			Game.PLAYER.getEquip().setShoes(this);
			Inventory.remove(inventoryIndex);
			break;
		}
		
		if(wearing != null){
			Inventory.add(wearing);
		}
		
		Game.PLAYER.recalculateStats();
	}
	
	public int getStatValue(){
		
		double scale = 0.038 + 0.002 * getTier().ordinal();
		double c = 8 + 2* getTier().ordinal();
		
		int boni = (int) (scale*Math.pow(requiredLvl, 2)+requiredLvl+c);
		
		int higherBoni = (int) ((scale+0.002)*Math.pow(requiredLvl, 2)+requiredLvl+(c+2));
		
		boni = (int) (Math.random()*(higherBoni-boni) + boni);
		
		return boni;
	}
	
	public Type getType(){
		return type;
	}
	
	public Stats getStats(){
		return stats;
	}
}
