package rpg.game.armor.boots;

import rpg.game.armor.Armor;

public class SimpleBoots extends Armor{

	public SimpleBoots(int minLvl, int maxLvl, Tier tier) {
		super("Wanderschuhe",Armor.Type.SHOES, tier, 1, 100, 200, "res/armor/shoes/A_Shoes01.png");
	
		requiredLvl = (int) (Math.random()*(maxLvl-minLvl)+minLvl);
		
		stats.setpDef(getStatValue(10));
		stats.setmDef(getStatValue(10));
	}

}
