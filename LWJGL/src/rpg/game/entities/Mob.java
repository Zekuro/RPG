package rpg.game.entities;

import rpg.game.objects.GameObject;

public class Mob extends GameObject{

	private int lvl;
	private final int id;
	
	private int health;
	private int mana;
	private int exp;
	
	// private ArrayList<Item> loot;
	
	public Mob(int id, int lvl) {
		super(true, false, 32, 32, "res/background/water.png");
		this.id = id;
		this.lvl = lvl;
	}
	

}
