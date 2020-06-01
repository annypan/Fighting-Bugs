import java.awt.Graphics;

public abstract class GameObj {
	/*
	 * Current position of the object, in terms of graphical coordinates
	 * 
	 * location given by the upper left corner of the object
	 */
	private int x;
	private int y;
	
	/*
	 * The size of the object
	 * All object will be squares, so one parameter is enough
	 */
	private int width;
	
	/*
	 * the remaining Hit Points(hp, or stamina) of the object;
	 * does not apply to the bullet and the snack
	 */
	private int hp;
	
	/*
	 * the velocity of this object
	 * the number of pixels to move when move() is called
	 */
	private int vx;
	private int vy;
	
	/*
	 * maximum permissible x and y coordinates of the object's upper left corner 
	 */
	private int maxX;
	private int maxY;
	
	/**
	 * Constructor
	 */
	public GameObj(int x, int y, int width, int hp, int vx, int vy, 
			int courtWidth, int courtHeight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.hp = hp;
		this.vx = vx;
		this.vy = vy;
		this.maxX = courtWidth - width;
		this.maxY = courtHeight - width;
	}
	
	/**
	 * getters
	 */
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getVx() {
		return vx;
	}
	
	public int getVy() {
		return vy;
	}
	
	public int getMaxX() {
		return this.maxX;
	}
	
	public int getMaxY() {
		return this.maxY;
	}
	
	/**
	 * setters
	 */
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setVx(int vx) {
		this.vx = vx;
	}
	
	public void setVy(int vy) {
		this.vy = vy;
	}
	
	/**
	 * updates the internal states of the game object
	 * when the object receives the command to move in specified directions
	 * does not apply to the snack
	 * 
	 */
	public void moveUp() {
		this.y -= vy;
		clip();
	}
	
	public void moveDown() {
		this.y += vy;
		clip();
	}
	
	public void moveLeft() {
		this.x -= vx;
		clip();
	}
	
	public void moveRight() {
		this.x += vx;
		clip();
	}
	
	
	/**
     * Prevents the object from going outside of the bounds of the area designated for the object.
     * (i.e. Object cannot go outside of the active area the user defines for it).
     */ 
	void clip() {
		this.x = Math.min(Math.max(0, this.x), this.maxX);
		this.y = Math.min(Math.max(0, this.y), this.maxY);
	}
	
	/**
     * Determine whether this game object is currently intersecting another object.
     *  
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
	public boolean intersects(GameObj that) {
		return (this.getX() + this.width >= that.getX()
	            && this.getY() + this.width >= that.getY()
	            && that.getX() + that.width >= this.getX() 
	            && that.getY() + that.width >= this.getY());
	}
	
	
	/**
	 * Drawing method that tells the program how to draw a specific object
	 * should be overridden by its subclasses
	 */
	public abstract void draw(Graphics g);
	
}
