package rpg.game.entities;

public class AniBear extends AnimatedEntity{

	// = animated bear, test for animated entities
	public AniBear(int lvl) {
		super(lvl, 45, 50, false);

		setTexture("res/entities/bear.png");
		setImageBounds(15, 0, width+10, height+10);
		
		setHorAnimationBounds(2, 2, 58, 40);
		setVertAnimationBounds(14, 4, 32, 45);
		
		setWalkTime(100);
		setStandTime(150);
		setAnimationSpeed(60);
		setMovingChangeSpeed(250);
		setRespawnTime(8);
		
		healthReg = 5;
		maxHealth = 100;
		health = 50;
	}

}
