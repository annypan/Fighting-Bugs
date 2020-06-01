import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObj{
	public static final int SIZE = 60;
    public static final int SPEED = 20;
    public static final int PLAYER_HP = 1;
    public static final String IMG_FILE = "images/player.png";
    private BufferedImage img;
    
    public Player(int courtWidth, int courtHeight) {
    	super(0, 0, SIZE, PLAYER_HP, 0, 0, courtWidth, courtHeight);
    	try {
			if (img == null) {
				img = ImageIO.read(new File(IMG_FILE));
			} 
		} catch (IOException e) {
				System.out.println("Internal Error: " + e.getMessage());
		}
    }
    
    @Override
    public void draw(Graphics g) {
    	g.drawImage(img, this.getX(), this.getY(), 
    			this.getWidth(), this.getWidth(), null);
    }
    
    public void move() {
    	this.setX(this.getX() + this.getVx());
    	this.setY(this.getY() + this.getVy());
    	this.clip();
    }

}
