package rpg.game.skills;

import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import rpg.game.Game;
import rpg.game.World;
import rpg.game.items.Item;

public class Skill extends Item{

	public static enum SkillType{
		IMPACT, AURA, LASER, PROJECTILE
	}
	
	public static enum SkillEffect{
		DOUBLE, SPREAD, RANGE, SIZE
	}
	
	public static enum Element{
		FIRE, ICE, ELECTICITY, SHIELD
	}
	
	private SkillType type;
	
	// FIXME CHANGE ME!!!
	private int requiredMana = 0;
	private int cooldown;
	
	private int range = 160;
	private int speed;
	private int damage;
	private int fireDamage;
	private int iceDamage;
	private int electricDamage;
	private int delta = 0;
	private int layer = 0;
	private int effectTextureRotation = 0;
	private int time = 0;
	private int timer = 0;
	
	
	
	private boolean inUse = false;
	
	private String effectTexture;
	private Point previousEndPoint;
	private ArrayList<SkillEffect> skillEffects = new ArrayList();
	private ArrayList<Element> elements = new ArrayList();
	
	//TODO UPDATE effectList -> renderOrder: first Impact -> Laser -> Projectile -> Aura
	// define rotation to the effectTexture!
	public Skill(String name, Tier tier, SkillType type) {
		super(name, tier, 0, 0, 0, "/res/skills/S_Fire05.png");
		this.type = type;
		
		String texturePath = "/res/skills/";
		
		switch (type) {
		case PROJECTILE:
			effectTexture = texturePath + "S_Fire05.png";
			texturePath += "S_Fire05.png";
			damage = 10;
			speed = 8;
			break;
		case LASER:
			effectTexture = texturePath + "S_Fire01.png";
			texturePath += "S_Fire01.png";
			speed = 10;
			damage = 1;
			break;
		case IMPACT:
			effectTexture = texturePath + "S_Earth04.png";
			texturePath += "S_Earth04.png";
			speed = 8;
			damage = 1;
			break;
		case AURA:
			effectTexture = texturePath + "S_Earth04.png";
			texturePath += "S_Buff05.png";
			speed = 8;
			break;
		}
		layer = type.ordinal();
		setTexture(texturePath);
		setEffectTextureRotation();
		isSkill = true;
		stackable = false;
		
		// TODO THESE ARE TESTCASES PLEASE REMOVE AFTER TEST!
		switch (type) {
		case PROJECTILE:
			addEffect(SkillEffect.DOUBLE);
			break;
		case LASER:
			addEffect(SkillEffect.DOUBLE);
			break;
		case IMPACT:
			addEffect(SkillEffect.DOUBLE);
			break;
		case AURA:
			break;
		}
		
	}
	
	// change icon to same element
	public void addElement(Element e){
		for (Element element: elements) {
			if(element== e) return;
		}
		elements.add(e);
	}
	
	public void addEffect(SkillEffect e){
		for (SkillEffect effect : skillEffects) {
			if(effect == e) return;
		}
			skillEffects.add(e);
			
			switch(skillEffects.size()){
			case 1: tier = Tier.T1;
				break;
			case 2: tier = Tier.T2;
				break;
			case 3: tier = Tier.T3;
				break;
			case 4: tier = Tier.T4;
				break;
			case 5: tier = Tier.T5;
				break;
			}
	}
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	public ArrayList<SkillEffect> getSkillEffects(){
		return skillEffects;
	}
	
	public void use(){
		// use skill by type
		// add effects & element
		if(Game.PLAYER.reduceMana(requiredMana)){
		
		switch(type){
		case PROJECTILE: projectileAttack();
			break;
		case LASER: laserAttack();
			break;
		case IMPACT: impactAttack();
			break;
		case AURA: auraAttack();
			break;
		}
		
		}
		delta++;
		time++;
		if(delta >= Game.UPS) delta = 0;
	}
	
	private void projectileAttack(){
		
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY()+ Game.PLAYER.getCameraY());
		
		if(inUse == false){
			if(skillEffects.isEmpty() || hasEffect(SkillEffect.DOUBLE)){
				World.addEffect(new Projectile(32,range, speed, damage,startPoint, endPoint, effectTexture, effectTextureRotation), layer);
				if(!hasEffect(SkillEffect.DOUBLE)) inUse = !inUse;
				if(hasEffect(SkillEffect.DOUBLE)) timer = time + 5;
				previousEndPoint = endPoint;
			}
		}else if(inUse == true){
			if(hasEffect(SkillEffect.DOUBLE) && time == timer){
				World.addEffect(new Projectile(32,range, speed, damage,startPoint, previousEndPoint, effectTexture, effectTextureRotation), layer);
				inUse = !inUse;
				timer = 0;
			}
		}
		
		
	}
	
	// TODO: please make some extra methods e.g.: laser(startPoint, endPoint)
	// this will make it easier to add effects
	private void laserAttack(){
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY()+ Game.PLAYER.getCameraY());
		double m = (endPoint.getY() - startPoint.getY())/(endPoint.getX() - startPoint.getX());
		
		
		// Manacost per second
//		if(delta == 0){
//			requiredMana = 2;
//			if(Game.PLAYER.getMana() >= requiredMana){
//				Game.PLAYER.reduceMana(requiredMana);
//			}else{
//				do not attack
//			}
//		}
		
		if(inUse == false){
			if(skillEffects.isEmpty())	laserAttack(startPoint, endPoint);
			if(hasEffect(SkillEffect.DOUBLE)){
				
				laserAttack(startPoint, endPoint);
				// TODO Parallel line
				//laserAttack(startPoint, new Point((int) endPoint.getX(), (int) endPoint.getY() + 100));
			}
		}else if(inUse = true){
			
				startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
				endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY()+ Game.PLAYER.getCameraY());

				int offset = 3;
				
				int xAdd = xAdd = (int) (offset*speed*Math.cos(Math.atan(m)));
				int yAdd = yAdd = (int) (offset*speed*Math.sin(Math.atan(m)));
				if(endPoint.getX() > startPoint.getX()){
					startPoint.setLocation(startPoint.getX()+xAdd, startPoint.getY()+yAdd);
					endPoint.setLocation(endPoint.getX()+xAdd, endPoint.getY()+yAdd);
				}else if(endPoint.getX() < startPoint.getX()){
					startPoint.setLocation(startPoint.getX()-xAdd, startPoint.getY()-yAdd);
					endPoint.setLocation(endPoint.getX()-xAdd, endPoint.getY()-yAdd);
				}
				if(endPoint.getX() == startPoint.getX()){
					
					if(endPoint.getY() > startPoint.getY()){
						yAdd = offset*speed;
					}else{
						yAdd = offset*speed;
					}
					
				}
				Projectile p = new Projectile(32,range-speed*offset, speed, damage,startPoint, endPoint, effectTexture, effectTextureRotation);
				World.addEffect(p, layer);
			
			//World.addEffect(new Projectile(32,range, speed, damage,startPoint, endPoint, effectTexture, effectTextureRotation),layer);
		
			// TODO Parallel line
			//if(hasEffect(SkillEffect.DOUBLE)) World.addEffect(new Projectile(32,range, speed, damage/(range*speed),startPoint, new Point((int) endPoint.getX(), (int) endPoint.getY() + 100), effectTexture, effectTextureRotation),layer);;
		}
		
	}
	
	private void laserAttack(Point startPoint, Point endPoint) {
		double m = (endPoint.getY() - startPoint.getY())/(endPoint.getX() - startPoint.getX());
		
		for(int i = 3; i < range/speed; i++){
			startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
			endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY()+ Game.PLAYER.getCameraY());

			int xAdd = 0;
			int yAdd = 0;
			
			xAdd = (int) (i*speed*Math.cos(Math.atan(m)));
			yAdd = (int) (i*speed*Math.sin(Math.atan(m)));
			if(endPoint.getX() > startPoint.getX()){
				startPoint.setLocation(startPoint.getX()+xAdd, startPoint.getY()+yAdd);
				endPoint.setLocation(endPoint.getX()+xAdd, endPoint.getY()+yAdd);
			}else if(endPoint.getX() < startPoint.getX()){
				startPoint.setLocation(startPoint.getX()-xAdd, startPoint.getY()-yAdd);
				endPoint.setLocation(endPoint.getX()-xAdd, endPoint.getY()-yAdd);
			}
			if(endPoint.getX() == startPoint.getX()){
				
				if(endPoint.getY() > startPoint.getY()){
					yAdd = i*speed;
				}else{
					yAdd = i*speed;
				}
				
			}
			Projectile p = new Projectile(32,range-i*speed, speed, damage,startPoint, endPoint, effectTexture, effectTextureRotation);
			World.addEffect(p, layer);
			if(p.hasCollision()) break;
		}
	}

	private void impactAttack(){
		// make a circle out of projectiles
				
		if(inUse == false){
			impact(range, speed);
			if(skillEffects.isEmpty()) inUse = !inUse;
			if(hasEffect(SkillEffect.DOUBLE)) timer = time + 5;
		} else if(inUse == true){
			if(hasEffect(SkillEffect.DOUBLE) && time == timer){
				impact(range, speed);
				inUse = !inUse;
			}
			
		}
	}
	
	private void impact(int range2, int speed2) {
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(0, 0);

		
		for(double i = 0; i < 2*Math.PI-0.01; i+= 0.1){
			endPoint = new Point(Game.PLAYER.getX() + (int) (Math.cos(i)*range),Game.PLAYER.getY() + (int) (Math.sin(i)*range));

			Projectile p = new Projectile(32,range, speed, damage,startPoint, endPoint, effectTexture, effectTextureRotation);
			World.addEffect(p,layer);
		}
	}

	private void auraAttack(){
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY() + Game.PLAYER.getCameraY());
		double m = (endPoint.getY() - startPoint.getY())/(endPoint.getX() - startPoint.getX());
		double startAngle;
		double iAdd = 0.2;
		range = 50;
		
		if(endPoint.getX() > startPoint.getX()){
			startAngle = Math.atan(m);
		}else{
			startAngle = Math.atan(m) + Math.PI;
		}
		
		if(endPoint.getX() == startPoint.getX()){
			if(endPoint.getY() > startPoint.getY()){
				startAngle = Math.PI/2;
			}else if(endPoint.getY() < startPoint.getY()){
				startAngle = -Math.PI/2;
			}
		}
		
		
		if(skillEffects.isEmpty()){

			for(double i = startAngle - Math.PI/4; i < startAngle + Math.PI/4; i+= iAdd){
				startPoint = new Point(Game.PLAYER.getX() + (int) (Math.cos(i)*range),Game.PLAYER.getY() + (int) (Math.sin(i)*range));
				endPoint = new Point(Game.PLAYER.getX(),Game.PLAYER.getY());
				Projectile p = new Projectile(32,1/range, speed, damage,startPoint, endPoint, effectTexture, effectTextureRotation + 180);
				World.addEffect(p,layer);
			}
			
		}
	}
	
	public boolean isInUse(){
		return inUse;
	}
	
	public void setInUse(boolean use){
		inUse = use;
	}
	
	public SkillType getType(){
		return type;
	}
	
	private void setEffectTextureRotation(){
		if(effectTexture.contains("Ice03")) effectTextureRotation = 45;
		if(effectTexture.contains("Fire05")) effectTextureRotation = 135;
		if(effectTexture.contains("Fire01")) effectTextureRotation = 120;
		if(effectTexture.contains("Earth04")) effectTextureRotation = 90;
	}
	
	private boolean hasEffect(SkillEffect effect){
		
		for (SkillEffect e: skillEffects) {
			if(e == effect) return true;
		}
		
		return false;
	}
}
