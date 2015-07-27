package rpg.game.armor.boots;

import rpg.game.armor.Armor;

public class SimpleBoots extends Armor{

	public SimpleBoots(int minLvl, int maxLvl) {
		super("Wanderschuhe",Armor.Type.SHOES, Tier.T1, 1, 100, 200, "res/armor/shoes/A_Shoes01.png");
	
		requiredLvl = (int) (Math.random()*(maxLvl-minLvl)+minLvl);
		
		// TODO SCALING IS INCORRECT... this example is not scaling good, rethink pls
		// EXAMPLE: BASE + TIER * (LVL * SCALE  + (NUMBER BETWEEN X & Y))
		/*
		 * EXAMPLE:
		 * 	BASE = 10		SCALE = 0.3		X = 4		Y = 9
		 * 
		 * 	LVL 10 T1 : 
		 * 		MIN: 10 + 1 * (10 * 0.3 + 4) = 17
		 * 		MAX: 10 + 1 * (10 * 0.3 + 9) = 22
		 * 
		 * 	LVL 10 T2:
		 * 		MIN: 10 + 2 * (10 * 0.3 + 4) = 24
		 * 		MAX: 10 + 2 * (10 * 0.3 + 9) = 34
		 * 
		 * 	LVL 10 T3:
		 * 		MIN: 10 + 3 * (10 * 0.3 + 4) = 31
		 * 		MAX: 10 + 3 * (10 * 0.3 + 9) = 46
		 * 
		 * 	LVL 10 T4:
		 * 		MIN: 10 + 4 * (10 * 0.3 + 4) = 38
		 * 		MAX: 10 + 4 * (10 * 0.3 + 9) = 58
		 * 	
		 * 	LVL 10 T5:
		 * 		MIN: 10 + 5 * (10 * 0.3 + 4) = 45
		 * 		MAX: 10 + 5 * (10 * 0.3 + 9) = 70 
		 * 
		 * LVL 80 T5:
		 * 		MIN: 80 + 5 * (80 * 0.3 + 4) = 220;
		 * 		MAX: 80 + 5 * (80 * 0.3 + 9) = 245
		 * 		
		 */
		stats.setpDef((int) (2 + requiredLvl*0.5));
		stats.setmDef((int) (2 + requiredLvl*0.3));
	}

}
