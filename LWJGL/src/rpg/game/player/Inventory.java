package rpg.game.player;

import java.util.ArrayList;

import rpg.game.items.Item;

public class Inventory {

	private static final int space = 90;
	private static ArrayList<Item> inventory = new ArrayList();
	
	public static void add(Item i){
		for (Item item : inventory) {
			if(i.getClass() ==item.getClass() && item.canAdd()){
				item.add();
				return;
			}
		}

		if(inventory.size() < space){
			inventory.add(i);
		}else{
			System.out.println("INVENTAR IS VOLL DU SPACKN");
		}
	}
	
	public static Item get(int index){
		if(inventory.size() > 0 && index < inventory.size()) return inventory.get(index);
		return null;
	}
	
}
