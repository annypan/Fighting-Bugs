import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Snack extends GameObj{
	public static final int SIZE = 40;
    public static final int SPEED = 0;
    public static final int HP = 1;
    public static final String IMG_FILE1 = "images/mooncake.png";
    public static final String IMG_FILE2 = "images/cake.png";
    public static final String IMG_FILE3 = "images/cookie.png";
    private BufferedImage img;
    
    public Snack(int x, int y, int courtWidth, int courtHeight) {
    	super(x, y, SIZE, HP, SPEED, SPEED, 
				courtWidth, courtHeight);
    	
    	Random rand = new Random();
    	int randNum = rand.nextInt(3);
    	if (randNum == 0) {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILE1));
    			} 
    		} catch (IOException e) {
    				System.out.println("Internal Error: " + e.getMessage());
    		}
    	}
    	else if (randNum == 1) {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILE2));
    			} 
    		} catch (IOException e) {
    				System.out.println("Internal Error: " + e.getMessage());
    		}
    	}
    	else {
    		try {
    			if (img == null) {
    				img = ImageIO.read(new File(IMG_FILE3));
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
    
}

