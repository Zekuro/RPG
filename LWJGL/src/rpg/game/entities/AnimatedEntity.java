package rpg.game.entities;

import org.lwjgl.opengl.GL11;

public abstract class AnimatedEntity extends Entity{
	
	private int speed;
	private int yChange;
	private int animationSpeed;
	private int movingChangeSpeed;
	private int walkTime;
	private int standTime;
	private int movingDir;
	private int movingDirAnimation;
	private boolean stopAnimation = false;
	
	
	private int animationHorOffsetX;
	private int animationHorOffsetY;
	private int animationHorWidth;
	private int animationHorHeight;
	
	
	private int animationVertOffsetX;
	private int animationVertOffsetY;
	private int animationVertWidth;
	private int animationVertHeight;
	
	private int change = 1;
	private int delta = (int) (Math.random() * 500);

	/**
	 * Add following Vars:
	 * - movingChangeSpeed
	 * 
	 * - horAnimationHitbox
	 * - vertAnimationHitbox
	 * 
	 * @param lvl
	 * @param width
	 * @param height
	 * @param aggressive
	 */
	public AnimatedEntity(int lvl, int width, int height, boolean aggressive) {
		super(lvl, width, height, aggressive);
	}
	
	public void update(){
		super.update();
		delta++;
		
		if(!isDead()){
			move();
		}
		
	}
	
	public void move(){
		int update = delta%animationSpeed;
		
		if(stopAnimation == false){
		if(update == 0){
			change = 1;
		}else
		if(update == animationSpeed/5|| update == 4*animationSpeed/5){
			change = 2;
		}else
		if(update == 2*animationSpeed/5 || update == 3*animationSpeed/5){
			change = 3;
		}}
		
		if(delta%120 == 0){
			regenerate();
		}
		
		if(delta % movingChangeSpeed == 0){
			movingDir = (int) (Math.random() * 8);
		}
		
		if(delta % (walkTime+standTime)< walkTime){
			stopAnimation = false;
			switch(movingDir){
				case 0:
					move(0,1);
					movingDirAnimation = 8;
					break;
				case 1:
					move(0,-1);
					movingDirAnimation = 2;
					break;
				case 2:
					move(-1,0);
					movingDirAnimation = 4;
					break;
				case 3:
					move(1,0);
					movingDirAnimation = 6;
					break;
				case 4:
					move(1,1);
					// 8 od 6
					movingDirAnimation = 8;
					break;
				case 5:
					move(-1,-1);
					// 2 od 4
					movingDirAnimation = 2;
					break;
				case 6:
					move(-1,1);
					// 4 od 8
					movingDirAnimation = 8;
					break;
				case 7:
					move(1,-1);
					// 6 od 2
					movingDirAnimation = 6;
					break;
			}}else{
				stopAnimation = true;
				change = 2;
			}
		
		if(movingDirAnimation == 2 || movingDirAnimation == 8){
			if(	!hasCollisionAt(x+animationVertOffsetX, y+animationVertOffsetY) 
					&& !hasCollisionAt(x+animationVertOffsetX+animationVertWidth, y+animationVertOffsetY)
					&& !hasCollisionAt(x+animationVertOffsetX+animationVertWidth, y+animationVertOffsetY+animationVertHeight)
					&& !hasCollisionAt(x+animationVertOffsetX, y+animationVertOffsetY+animationVertHeight)){
						yChange = movingDirAnimation/2 - 1;
						colOffsetX = animationVertOffsetX;
						colOffsetY = animationVertOffsetY;
						width = animationVertWidth;
						height = animationVertHeight;
						
						// TODO animationVertImage Bounds (clickable)
						setImageBounds(15, 0, width+10, height+10);
			}
		}
		
		if(movingDirAnimation == 4 || movingDirAnimation == 6){
			if(	!hasCollisionAt(x+animationHorOffsetX, y+animationHorOffsetY) 
					&& !hasCollisionAt(x+animationHorOffsetX+animationHorWidth, y+animationHorOffsetY)
					&& !hasCollisionAt(x+animationHorOffsetX+animationHorWidth, y+animationHorOffsetY+animationHorHeight)
					&& !hasCollisionAt(x+animationHorOffsetX, y+animationHorOffsetY+animationHorHeight)){
					yChange = movingDirAnimation/2 - 1;
					colOffsetX = animationHorOffsetX;
					colOffsetY = animationHorOffsetY;
					width = animationHorWidth;
					height = animationHorHeight;
					
					// TODO animationHorImage Bounds (clickable)
					setImageBounds(0, 0, width+5, height+10);
				}
		}
	}
	
	public void render(){
		texture.bind();
		
		if(!isDead()){
			int xChange = (change-1) % 3;
			
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(xChange * 0.25f,yChange * 0.25f + 0.25f);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(xChange * 0.25f + 0.25f,yChange * 0.25f + 0.25f);
			GL11.glVertex2f(x+64, y);
			GL11.glTexCoord2f(xChange * 0.25f + 0.25f,yChange * 0.25f);
			GL11.glVertex2f(x+64, y+64);
			GL11.glTexCoord2f(xChange * 0.25f,yChange * 0.25f);
			GL11.glVertex2f(x, y+64);
		    GL11.glEnd();
		}else{
			renderItemBag();
		}
	}
	
	public void setHorAnimationBounds(int xOffset, int yOffset, int width, int height){
		this.animationHorOffsetX = xOffset;
		this.animationHorOffsetY = yOffset;
		this.animationHorWidth = width;
		this.animationHorHeight = height;
	}
	
	public void setVertAnimationBounds(int xOffset, int yOffset, int width, int height){
		this.animationVertOffsetX = xOffset;
		this.animationVertOffsetY = yOffset;
		this.animationVertWidth = width;
		this.animationVertHeight = height;
	}
	
	public void setWalkTime(int time){
		this.walkTime = time;
	}
	
	public void setStandTime(int time){
		this.standTime = time;
	}
	
	public void setAnimationSpeed(int speed){
		this.animationSpeed = speed;
	}
	
	public void setMovingChangeSpeed(int speed){
		this.movingChangeSpeed = speed;
	}

}
