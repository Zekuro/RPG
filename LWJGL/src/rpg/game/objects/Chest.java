package rpg.game.objects;

import java.util.ArrayList;

import rpg.game.Game;
import rpg.game.items.HealthPotion;
import rpg.game.items.Item;
import rpg.game.items.ManaPotion;
import rpg.game.player.Inventory;


public class Chest extends GameObject{

	private ArrayList<Item> loot = new ArrayList();
	
	private boolean open = false;
	
	public Chest() {
		super(true,false, 32, 32, "res/objects/chest.png");
		Inventory.add(new ManaPotion(20), loot);
		Inventory.add(new ManaPotion(20), loot);
		Inventory.add(new HealthPotion(20), loot);
		Inventory.add(new HealthPotion(20), loot);
	}
	
	public void use(){
		if(open){
			if(!loot.isEmpty()){
				loot();
			}else{
			open = false;
			setTexture("res/objects/chest.png");
			Game.UI.renderLootDialog = false;
			}
		}else{
			open = true;
			setTexture("res/objects/emptyChest.png");
			loot();
		}
	}
	

	public void loot(){
		if(loot.size() > 0){
			Game.UI.showLootDialog(loot);
		}
	}
	
	public boolean isOpen(){
		return open;
	}
	
}
