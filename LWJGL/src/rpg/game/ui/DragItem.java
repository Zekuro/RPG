package rpg.game.ui;

import rpg.game.items.Item;

public class DragItem {

	private Item item;
	private int inventoryIndex;
	private int actionBarIndex;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getInventoryIndex() {
		return inventoryIndex;
	}
	public void setInventoryIndex(int inventoryIndex) {
		this.inventoryIndex = inventoryIndex;
	}
	public int getActionBarIndex() {
		return actionBarIndex;
	}
	public void setActionBarIndex(int actionBarIndex) {
		this.actionBarIndex = actionBarIndex;
	}
	
}
