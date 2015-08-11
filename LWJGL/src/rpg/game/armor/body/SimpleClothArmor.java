package rpg.game.armor.body;

import rpg.game.armor.Armor;

public class SimpleClothArmor extends Armor{

	public SimpleClothArmor(int minLvl, int maxLvl, Tier tier) {
		super("Einfache Stoffrüstung",Armor.Type.SHOES, tier, 1, 100, 200, "res/armor/body/A_Clothing01.png");
	
		requiredLvl = (int) (Math.random()*(maxLvl-minLvl)+minLvl);
		
		stats.setpDef(getStatValue(3));
		stats.setmDef(getStatValue(5));
	}

}
