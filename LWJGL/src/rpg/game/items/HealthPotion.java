package rpg.game.items;

import rpg.game.Game;

public class HealthPotion extends Item{

	public int heal;
	
	public HealthPotion(int heal) {
		super("Lebenstrank", Tier.T1, 1, 5, 10, "res/items/P_Red02.png");
		this.heal = heal;
	}

	@Override
	public void use() {
		// TESTCASE
		Game.PLAYER.restoreHealth(heal);
		destroy();
	}

}
