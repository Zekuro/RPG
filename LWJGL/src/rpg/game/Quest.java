package rpg.game;

import rpg.game.entities.Entity;
import rpg.game.items.Item;

public class Quest {
	
	// REQUIRED
	private int lvl;
	private int exp;
	private String title;
	private String description;
	
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
	public Quest(int lvl, Entity mob, int quantity, String title, String description){
		initRequiredParameter(lvl, title, description);
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
	public Quest(int lvl, Item questItem, int quantity, String title, String description){
		initRequiredParameter(lvl, title, description);
		this.questItem = questItem;
		collectingQuest = true;
	}
	
	/**
	 * 	For Tutorial-Quests or Quests when you just talk with the npc
	 * @param lvl
	 * @param title
	 * @param description
	 */
	public Quest(int lvl, String title, String description){
		initRequiredParameter(lvl, title, description);
	}
	
	private void initRequiredParameter(int lvl, String title, String description){
		this.lvl = lvl;
		this.title = title;
		this.description = description;
		exp = (int) (Math.pow(lvl, 2) + lvl + 25);
	}
}
