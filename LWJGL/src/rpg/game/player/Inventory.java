package rpg.game.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Sound;
import rpg.game.armor.Armor;
import rpg.game.items.Item;
import rpg.game.ui.ArmorInfo;
import rpg.game.ui.ItemInfo;

public class Inventory {

	private static final int space = 90;
	public static int dragIndex = -1;
	private static String itemName = null;
	private static Armor hoveredArmor = null;
	private static Item hoveredItem= null;
	
	private static Item[] inventory = new Item[space];
	
	public static void add(Item i){
		int firstFreeSlot = -1;
		int j = 0;
		
		for (Item item : inventory) {
			if(item != null){
			if(i.isStackable() && i.getClass() == item.getClass() && item.canAdd()){
				item.add();
				return;
			}}
		}
		
		for (Item item : inventory) {
			if( item == null){
					firstFreeSlot = j;
					break;
			}
			j++;
		}

		if(firstFreeSlot >= 0 && firstFreeSlot < space){
			inventory[firstFreeSlot] = i;
		}else{
			System.out.println("INVENTAR IS VOLL DU SPACKN");
		}
	}
	
	public static void add(Item i, ArrayList<Item> inventory){
		
		for (Item item : inventory) {
			if(item.isStackable() && i.getClass() ==item.getClass() && item.canAdd()){
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
	
	public static void remove(Item i){
		boolean foundItem = false;
		int j = 0;
		int index = 0;
		
		int stacks = 1000;
		
		// get item with lowest stacks
		for (Item item : inventory) {
			if(item != null){
			if(i.getClass() == item.getClass() && item.getStacks() < stacks){
				stacks = item.getStacks();
				index = j;
			}}
			j++;
		}
		
		
		Item item = inventory[index];

		if(item != null){
			if(item.getStacks() > 1){
				item.remove();
			}else if(item.getStacks() == 1){
				inventory[index] = null;
			}
		}
	}
	
	public static void remove(int index){
		inventory[index] = null;
	}
	
	public static Item get(int index){
		if(index >= 0 && index < 90) return inventory[index];
		return null;
	}
	
	// FIXME always same size (even high resolution)
	public static void render(int x, int y){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40-2, y + Game.SCREEN_HEIGHT/2-5*36-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40+2, y + Game.SCREEN_HEIGHT/2-5*36-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40+2, y + Game.SCREEN_HEIGHT/2+5*36+2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40-2, y + Game.SCREEN_HEIGHT/2+5*36+2);
		GL11.glEnd();
		
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40, y + Game.SCREEN_HEIGHT/2-5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40, y + Game.SCREEN_HEIGHT/2-5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40, y + Game.SCREEN_HEIGHT/2+5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40, y + Game.SCREEN_HEIGHT/2+5*36);
		GL11.glEnd();
		
		int size = 32;
		int index = 0; 
		
		for (int j = 0; j <= 8; j++){
		for(int i = 0; i <=9 ; i++){
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size/2, y + Game.SCREEN_HEIGHT/2+5*36 - 25 - j*4 - j*size);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size*3/2, y + Game.SCREEN_HEIGHT/2+5*36 - 25 - j*4 - j*size);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size*3/2, y + Game.SCREEN_HEIGHT/2+5*36-25- j*4 -j*size - size);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size/2, y + Game.SCREEN_HEIGHT/2+5*36-25- j*4 - j*size - size);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 1, 1);
			
			int itemX = x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size/2;
			int itemY = y + Game.SCREEN_HEIGHT/2+5*36 - 25 - j*4 - j*size - size;
			
			if(inventory[index] != null){
				Item item = inventory[index];
				item.render(x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size/2, y + Game.SCREEN_HEIGHT/2+5*36 - 25 - j*4 - j*size-size);
				
				String stacks = Integer.toString(inventory[index].getStacks());
				if(inventory[index].isStackable()) Font.render(stacks,x + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size/2 + 32 - 8*stacks.length(), y + Game.SCREEN_HEIGHT/2+5*36 - 25 - j*4 - j*size-size);
			}
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			index++;
		}}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		if(hoveredItem != null) ItemInfo.render(hoveredItem);
		if(hoveredArmor != null) ArmorInfo.render(hoveredArmor);
		
		String msg = "- I N V E N T A R -";
		Font.render(msg,x + Game.SCREEN_WIDTH/2 - msg.length()*4, y + Game.SCREEN_HEIGHT/2+5*36-16);
		
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		int size = 32;
		int k = 0; 
		int index = -1;
		
		for (int j = 0; j <= 8; j++){
		for(int i = 0; i <=9 ; i++){
			
			int itemX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2-5*40 + i*4 + i*size + size/2;
			int itemY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2+5*36 - 25 - j*4 - j*size - size;

			if(	Game.PLAYER.getCameraX()+mouseX > itemX
				&&Game.PLAYER.getCameraX()+mouseX < itemX + size
				&&Game.PLAYER.getCameraY()+mouseY > itemY
				&&Game.PLAYER.getCameraY()+mouseY < itemY + size){
				index = k;
				break;
			}
			
			k++;
		}}
		
		// MouseButton: -1 = nothing, 0 = left, 1 = right
		if(index >= 0 && inventory[index] != null){
			Item item = inventory[index];
			if(button == 0){
				
				if(Keyboard.getEventKeyState()){
					
					int[] keys = {Keyboard.KEY_1,Keyboard.KEY_2,Keyboard.KEY_3,Keyboard.KEY_4,Keyboard.KEY_5,Keyboard.KEY_6,Keyboard.KEY_7,Keyboard.KEY_8,Keyboard.KEY_9};
					
					for(int i=0; i < keys.length; i++){
						
						if(Keyboard.getEventKey() == keys[i]){
							ActionBar.actionBar[i] = inventory[index];
							Sound.item.playAsSoundEffect(1, 0.1f, false);
							break;
						}
						
					}
					
					return;
				}

				Game.UI.clickedInventorySlot = true;
				// DRAG N DROP
				if(dragIndex < 0){
					Game.UI.setDragItem(item);
					dragIndex = index;
				}else{
					Sound.item.playAsSoundEffect(1, 0.1f, false);
					inventory[dragIndex] = item;
					inventory[index] = Game.UI.getDragItem();
					Game.UI.setDragItem(null);
					dragIndex = -1;
				}
			
			
			}else if(button == 1){

			if(item.isArmor() && Game.PLAYER.getLvl() >= item.getRequiredLvl()){
				Armor armor = (Armor) item;
				armor.equip(index);
			}
				
			}else if(button == -1){
				if(item.isArmor()){
					hoveredArmor = (Armor) item;
				}else{
					hoveredItem = item;
				}
			}
		}else{
			if(button == 0){
				// DRAG ONTO FREE SPACE
				if(index != -1 && dragIndex != -1 && inventory[index] == null){
					Sound.item.playAsSoundEffect(1, 0.1f, false);
					inventory[dragIndex] = null;
					inventory[index] = Game.UI.getDragItem();
					Game.UI.setDragItem(null);
					dragIndex = -1;
				}else{
					Game.UI.clickedInventorySlot= false;
				}
			}
			hoveredArmor = null;
			hoveredItem = null;
		}
		
	}
	
	public static Item[] getInventory(){
		return inventory;
	}
	
	public static boolean hasSpace(){
		
		for (Item item : inventory) {
			if(item == null) return true;
		}
		
		return false;
	}
	
}
