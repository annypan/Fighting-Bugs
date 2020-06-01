import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class DumbBug extends GameObj{
	public static final int SIZE = 30;
    public static final int DUMB_SPEED = 10;
    public static final int DUMB_HP = 3;
    public static final String IMG_FILE = "images/DumbBug.png";
    
    private BufferedImage img;
    
    public DumbBug(int x, int y, int courtWidth, int courtHeight) {
		super(x, y, SIZE, DUMB_HP, DUMB_SPEED, DUMB_SPEED,
				courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(IMG_FILE));
			}
		} catch (IOException e) {
				System.out.println("Internal Error: " + e.getMessage());
			}
		}
    
    public void move() {
    	Random rand = new Random();
    	int randNum = rand.nextInt(4);
    	if (randNum == 0) {this.moveUp();}
    	else if (randNum == 1) {this.moveDown();}
    	else if (randNum == 2) {this.moveLeft();}
    	else {this.moveRight();}
    }
    
    @Override
    public void draw(Graphics g) {
    	g.drawImage(img, this.getX(), this.getY(), 
    			this.getWidth(), this.getWidth(), null);
    }
    
}
