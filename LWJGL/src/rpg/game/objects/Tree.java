package rpg.game.objects;

public class Tree extends GameObject{

	public Tree() {
		super(true, false, 10, 40, "res/objects/tree.png");
		setCollisionOffset(64, 5);
	}

}
