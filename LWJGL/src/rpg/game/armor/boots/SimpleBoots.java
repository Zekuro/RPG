package rpg.game.armor.boots;

import rpg.game.armor.Armor;

public class SimpleBoots extends Armor{

	public SimpleBoots(int minLvl, int maxLvl) {
		super("Wanderschuhe",Armor.Type.SHOES, Tier.T1, 1, 100, 200, "res/armor/shoes/A_Shoes01.png");
	
		requiredLvl = (int) (Math.random()*(maxLvl-minLvl)+minLvl);
		
		// TODO SCALING IS INCORRECT
		stats.setpDef((int) (2 + requiredLvl*0.3));
		stats.setmDef((int) (2 + requiredLvl*0.2));
	}

}
