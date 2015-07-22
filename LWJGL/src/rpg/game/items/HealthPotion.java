package rpg.game.items;

import rpg.game.Game;

public class HealthPotion extends Item{

	public HealthPotion() {
		super("Lebenstrank", Tier.T1, 1, 5, 10, "res/items/healthPotion.png");
	}

	@Override
	public void use() {
		// TESTCASE
		Game.PLAYER.setHealth(Game.PLAYER.getHealth() + 20);
		remove();
	}

}
