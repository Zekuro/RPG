package rpg.game.skills;

import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import rpg.game.Game;
import rpg.game.World;
import rpg.game.items.Item;

public class Skill extends Item{

	public static enum SkillType{
		PROJECTILE, LASER, IMPACT, AURA
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
	
	private boolean inUse = false;
	private ArrayList<SkillEffect> skillEffects = new ArrayList();
	private ArrayList<Element> elements = new ArrayList();
	
	//TODO UPDATE effectList -> renderOrder: first Impact -> Laser -> Projectile -> Aura
	public Skill(String name, Tier tier, SkillType type) {
		super(name, tier, 0, 0, 0, "/res/skills/S_Fire05.png");
		this.type = type;
		
		String texturePath = "/res/skills/";
		
		switch (type) {
		case PROJECTILE:
			texturePath += "S_Fire05.png";
			speed = 8;
			break;
		case LASER:
			texturePath += "S_Fire01.png";
			speed = 10;
			break;
		case IMPACT:
			texturePath += "S_Earth04.png";
			speed = 8;
			break;
		case AURA:
			texturePath += "S_Earth04.png";
			speed = 8;
			break;
		}
		
		setTexture(texturePath);
		isSkill = true;
		stackable = false;
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
		case PROJECTILE: 
			projectileAttack();
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
		if(delta >= Game.UPS) delta = 0;
	}
	
	private void projectileAttack(){
		
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY()+ Game.PLAYER.getCameraY());
		
		// Ice03 rotate = 45
		// Fire05 rotate = 135
		// Fire01 rotate = 120
		
		if(skillEffects.isEmpty()){
			World.effects.add(new Projectile(32,range, speed, damage,startPoint, endPoint, texturePath, 135));
		}
		
		
	}
	
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
		if(skillEffects.isEmpty()){
			
			
			for(int i = 0; i < range/speed; i++){
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
				Projectile p = new Projectile(32,range-i*speed, speed, damage/(range*speed),startPoint, endPoint, texturePath, 120);
				World.effects.add(p);
				if(p.hasCollision()) break;
			}
			
		}}else if(inUse = true){
			World.effects.add(new Projectile(32,range, speed, damage/(range*speed),startPoint, endPoint, texturePath, 120));
		}
		
	}
	
	private void impactAttack(){
		// make a circle out of projectiles
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(0, 0);
		
		if(skillEffects.isEmpty()){

			for(double i = 0; i < 2*Math.PI-0.01; i+= 0.1){
				endPoint = new Point(Game.PLAYER.getX() + (int) (Math.cos(i)*range),Game.PLAYER.getY() + (int) (Math.sin(i)*range));

				Projectile p = new Projectile(32,range, speed, damage,startPoint, endPoint, texturePath, 120);
				World.effects.add(p);
			}
			
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
		
		if(skillEffects.isEmpty()){

			for(double i = startAngle - Math.PI/4; i < startAngle + Math.PI/2; i+= iAdd){
				startPoint = new Point(Game.PLAYER.getX() + (int) (Math.cos(i)*range),Game.PLAYER.getY() + (int) (Math.sin(i)*range));
				endPoint = new Point(Game.PLAYER.getX() + (int) (Math.cos(i+iAdd)*range),Game.PLAYER.getY() + (int) (Math.sin(i+iAdd)*range));
				Projectile p = new Projectile(32,1/range, speed, damage,startPoint, endPoint, texturePath, 0);
				World.effects.add(p);
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
}
