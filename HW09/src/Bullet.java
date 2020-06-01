import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends GameObj{
	private final Direction dir;
	public static final int SIZE = 10;
    public static final int BULLET_SPEED = 10;
    public static final int HP = 100;
    public static final String IMG_FILEUP = "images/bulletUp.png";
    public static final String IMG_FILEDOWN = "images/bulletDown.png";
    public static final String IMG_FILELEFT = "images/bulletLeft.png";
    public static final String IMG_FILERIGHT = "images/bulletRight.png";
    private BufferedImage img;
    
    public Bullet (int x, int y, int courtWidth, int courtHeight, Direction dir) {
    	super(x, y, SIZE, HP, BULLET_SPEED, BULLET_SPEED, courtWidth, courtHeight);
    	this.dir = dir;
    	
    	if (dir == Direction.UP) {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILEUP));
    			} 
    		} catch (IOException e) {
    				System.out.println("Internal Error: " + e.getMessage());
    		}
    	}
    	else if (dir == Direction.DOWN) {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILEDOWN));
    			} 
    		} catch (IOException e) {
    				System.out.println("Internal Error: " + e.getMessage());
    		}
    	}
    	else if (dir == Direction.LEFT) {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILELEFT));
    			} 
    		} catch (IOException e) {
    				System.out.println("Internal Error: " + e.getMessage());
    		}
    	}
    	else {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILERIGHT));
    			} 
    		} catch (IOException e) {
    				System.out.println("Internal Error: " + e.getMessage());
    		}
    	}
    }
    
    @Override
    public void draw(Graphics g) {
    	g.drawImage(img, this.getX(), this.getY(), 
    			this.getWidth(), this.getWidth(), null);
    }
    
    public void move() {
    	if (this.dir == Direction.UP) {
    		this.moveUp();
    	}
    	else if (this.dir == Direction.DOWN) {
    		this.moveDown();
    	}
    	else if (this.dir == Direction.LEFT) {
    		this.moveLeft();
    	}
    	else {this.moveRight();}
    }
    
}
