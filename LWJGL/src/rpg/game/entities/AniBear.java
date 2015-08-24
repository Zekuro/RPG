package rpg.game.entities;

import rpg.game.armor.boots.SimpleBoots;
import rpg.game.items.Item;
import rpg.game.items.Item.ItemObject;
import rpg.game.items.Item.Tier;
import rpg.game.player.Inventory;

public class AniBear extends AnimatedEntity{

	// = animated bear, test for animated entities
	public AniBear(int lvl) {
		super(lvl, 45, 50, false);

		setTexture("res/entities/bear.png");
		setImageBounds(15, 0, width+10, height+10);
		
		setHorAnimationBounds(2, 2, 58, 40);
		setVertAnimationBounds(14, 4, 32, 45);
		
		setWalkTime(100);
		setStandTime(150);
		setAnimationSpeed(60);
		setMovingChangeSpeed(250);
		setRespawnTime(8);
		
		healthReg = 5;
		maxHealth = 100;
		health = 50;
	}
	
	public void createLoot(){
		int randomNumber = (int) (Math.random()*100);
		
		if(randomNumber < 30){
			Inventory.add(Item.get(ItemObject.smallManaPotion), loot);
		}
		
		if(randomNumber >=30 && randomNumber < 60){
			for(int i = 0; i <= (int) (Math.random()*3); i++){
				Inventory.add(Item.get(ItemObject.smallHealthPotion), loot);
			}
		}
		
		if(randomNumber >= 95){
			Inventory.add(new SimpleBoots(1, 3, Tier.T1), loot);
		}
	}
	
}
