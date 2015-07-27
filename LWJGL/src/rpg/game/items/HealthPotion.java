package rpg.game.items;

import rpg.game.Game;

public class HealthPotion extends Item{

	public int heal;
	
	public HealthPotion(String name, int heal, String texture) {
		super(name, Tier.T1, 1, 5, 10, texture);
		this.heal = heal;
	}

	@Override
	public void use() {
		// TESTCASE
		Game.PLAYER.restoreHealth(heal);
		destroy();
	}

}
