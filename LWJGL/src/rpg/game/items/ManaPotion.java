package rpg.game.items;

import rpg.game.Game;

public class ManaPotion extends Item{

	public int heal;
	
	public ManaPotion(String name,int heal, String texture) {
		super(name, Tier.T1, 1, 5, 10, texture);
		this.heal = heal;
		setDescription("Stellt " + heal + " Mana her.");
	}

	@Override
	public void use() {
		// TESTCASE
		Game.PLAYER.restoreMana(heal);
		destroy();
	}

}
