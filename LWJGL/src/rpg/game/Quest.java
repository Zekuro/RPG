package rpg.game;

import rpg.game.entities.Entity;
import rpg.game.items.Item;

public class Quest {
	
	// REQUIRED
	private int lvl;
	private int exp;
	private int gold;
	private String title;
	private String description;
	private Item[] reward;
	
	// MIGHT BE NULL
	private Item questItem;
	private Entity mob;
	
	private boolean killQuest = false;
	private boolean collectingQuest = false;
	
	// TODO: quests with multiple mob-types or items
	
	/**
	 * For kill-Quests, but only 1 mob-type
	 * @param lvl
	 * @param mob
	 * @param quantity
	 * @param title
	 * @param description
	 */
	public Quest(int lvl, Item[] reward, Entity mob, int quantity, String title, String description){
		initRequiredParameter(lvl, reward, title, description);
		this.mob = mob;
	}
	
	/**
	 * 	For Quests where you need specific items
	 * @param lvl
	 * @param questItem
	 * @param quantity
	 * @param title
	 * @param description
	 */
	public Quest(int lvl, Item[] reward, Item questItem, int quantity, String title, String description){
		initRequiredParameter(lvl, reward, title, description);
		this.questItem = questItem;
		collectingQuest = true;
	}
	
	/**
	 * 	For Tutorial-Quests or Quests when you just talk with the npc
	 * @param lvl
	 * @param title
	 * @param description
	 */
	public Quest(int lvl, Item[] reward, String title, String description){
		initRequiredParameter(lvl, reward, title, description);
	}
	
	private void initRequiredParameter(int lvl, Item[] reward, String title, String description){
		this.lvl = lvl;
		this.title = title;
		this.description = description;
		this.reward = reward;
		exp = (int) (Math.pow(lvl, 2) + lvl + 25);
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getExp(){
		return exp;
	}
	
	public int getLvl(){
		return lvl;
	}
	
	public Item[] getReward(){
		return reward;
	}
}
