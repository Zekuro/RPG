package rpg.game.items;

import rpg.game.Game;

public class ManaPotion extends Item{

	public int heal;
	
	public ManaPotion(int heal) {
		super("Manatrank", Tier.T1, 1, 5, 10, "res/items/manaPotion.png");
		this.heal = heal;
	}

	@Override
	public void use() {
		// TESTCASE
		Game.PLAYER.restoreMana(heal);
		destroy();
	}

}
