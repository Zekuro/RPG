package rpg.game;

public class LevelColor {

	private int a;
	private int r;
	private int g;
	private int b;
	
	public LevelColor(int a, int r, int g, int b){
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public LevelColor(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public boolean hasColors(int a,int r, int g, int b){
		if(this.a == a && this.r == r && this.g == g && this.b == b){
			return true;
		}
		return false;
	}
	
	public boolean hasColors(int r, int g, int b){
		if(this.r == r && this.g == g && this.b == b){
			return true;
		}
		return false;
	}
	
	public boolean matchesColor(LevelColor c){
		if(a == c.getAplha() && r == c.getRed() && g == c.getGreen() && b == c.getBlue()){
			return true;
		}
			return false;
	}
	
	public int getAplha(){
		return a;
	}
	
	public int getRed(){
		return r;
	}
	
	public int getGreen(){
		return g;
	}
	
	public int getBlue(){
		return b;
	}
	
	public boolean hasRed(int r){
		return this.r == r;
	}
	
	public boolean hasGreen(int g){
		return this.g == g;
	}
	
	public boolean hasBlue(int b){
		return this.b == b;
	}
}
