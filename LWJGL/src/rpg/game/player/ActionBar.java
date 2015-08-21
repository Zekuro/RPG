package rpg.game.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.items.Item;
import rpg.game.skills.Skill;

public class ActionBar {

	public static final int ACTIONSLOTS = 9;
	
	private static String itemName = null;
	private static boolean renderItemName = false;
	
	private static int dragIndex = -1;
	public static Item[] actionBar = new Item[9];
	
	public static void render(){
		
		for(int i = 0; i < ACTIONSLOTS; i++){	
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
			GL11.glColor3f(0f, 0f, 0f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - 2 - ACTIONSLOTS*17 + i*2 + i*32, Game.PLAYER.getCameraY());
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 + 2 - ACTIONSLOTS*17 + i*2 + i*32 + 32, Game.PLAYER.getCameraY());
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 + 2 - ACTIONSLOTS*17 + i*2 + i*32 + 32, Game.PLAYER.getCameraY() + 32 + 4);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - 2 - ACTIONSLOTS*17 + i*2 + i*32, Game.PLAYER.getCameraY() + 32 + 4);
			GL11.glEnd();
			
			GL11.glColor3f(0.6f, 0.6f, 0.6f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32, Game.PLAYER.getCameraY() + 2);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32 + 32, Game.PLAYER.getCameraY() + 2);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32 + 32, Game.PLAYER.getCameraY() + 32 + 2);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32, Game.PLAYER.getCameraY() + 32 + 2);
			GL11.glEnd();
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 1, 1);
			
			if(actionBar[i] != null){
				actionBar[i].render(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32, Game.PLAYER.getCameraY() + 2);
			
				int stacks = 0;
				for (Item item : Inventory.getInventory()) {
					if(item != null && item.getClass() == actionBar[i].getClass()){
						stacks += item.getStacks();
					}
				}

				if(actionBar[i].isStackable()){
				String msg = Integer.toString(stacks);
				Font.render(msg,Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32 + 32 - 8*msg.length(), Game.PLAYER.getCameraY() + 2);
				}
			}
			
			
			if(renderItemName){
				Font.render(itemName, Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY() + Game.PLAYER.getCameraY() + 10);
			}
			
		}
		
	}
	
	public static void update(int button, int mouseX, int mouseY){
		
		int size = 32;
		int k = 0; 
		int index = -1;
		
		for(int i = 0; i < ACTIONSLOTS; i++){	
			
			int itemX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*17 + i*2 + i*32;
			int itemY = Game.PLAYER.getCameraY() + 2;
			
			if(	Game.PLAYER.getCameraX()+mouseX > itemX
				&&Game.PLAYER.getCameraX()+mouseX < itemX + size
				&&Game.PLAYER.getCameraY()+mouseY > itemY
				&&Game.PLAYER.getCameraY()+mouseY < itemY + size){
				index = k;
				break;
			}
			
			k++;
		}
		
		// MouseButton: -1 = nothing, 0 = left, 1 = right
		if(index >= 0 && actionBar[index] != null){
			Item item = actionBar[index];
			if(button == 0){
				Game.UI.clickedActioBarSlot = true;

				// DRAG N DROP
				if(dragIndex < 0){
					Game.UI.setDragItem(item);
					dragIndex = index;
				}else{
					actionBar[dragIndex] = item;
					actionBar[index] = Game.UI.getDragItem();
					Game.UI.setDragItem(null);
					dragIndex = -1;
				}
			
			
			}else if(button == 1){
				if(Game.isPaused() == false){
					item.use();
				}
			}else if(button == -1){
				renderItemName = true;
				itemName = item.getName();
			}
		}else{
			if(button == 0){
				
				// DRAG ONTO FREE SPACE
				if(index != -1 && Inventory.dragIndex != -1 && actionBar[index] == null){
					actionBar[index] = Game.UI.getDragItem();
					Game.UI.setDragItem(null);
					Inventory.dragIndex = -1;
				}else if(index != -1 && dragIndex != -1 && actionBar[index] == null){
					actionBar[dragIndex] = null;
					actionBar[index] = Game.UI.getDragItem();
					Game.UI.setDragItem(null);
					dragIndex = -1;
				}else{
					if(dragIndex >= 0){
						actionBar[dragIndex] = null;
						dragIndex = -1;
					}
					
					Game.UI.clickedActioBarSlot= false;
				}
			}
			renderItemName = false;
		}
		
	}
	
	public static void respondToActionKeys(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_1) && actionBar[0] != null){
			actionBar[0].use();
			if(actionBar[0].getClass() == Skill.class ){
				Skill skill = (Skill) actionBar[0];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_2) && actionBar[1] != null){
			actionBar[1].use();
			if(actionBar[1].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[1];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_3) && actionBar[2] != null){
			actionBar[2].use();
			if(actionBar[2].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[2];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_4) && actionBar[3] != null){
			actionBar[3].use();
			if(actionBar[3].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[3];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_5) && actionBar[4] != null){
			actionBar[4].use();
			if(actionBar[4].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[4];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_6) && actionBar[5] != null){
			actionBar[5].use();
			if(actionBar[5].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[5];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_7) && actionBar[6] != null){
			actionBar[6].use();
			if(actionBar[6].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[6];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_8) && actionBar[7] != null){
			actionBar[7].use();
			if(actionBar[7].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[7];
				useSkill(skill);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_9) && actionBar[8] != null){
			actionBar[8].use();
			if(actionBar[8].getClass() == Skill.class){
				Skill skill = (Skill) actionBar[8];
				useSkill(skill);
			}
		}
		
	}
	
	// TODO maybe clean me up
	private static void useSkill(Skill skill){
		skill.setInUse(!skill.isInUse());
	}
	
	public static Item[] getActionBar(){
		return actionBar;
	}
}
