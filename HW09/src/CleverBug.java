import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

public class CleverBug extends GameObj{
	public static final int SIZE = 30;
    public static final int SPEED = 1;
    public static final int CLEVER_HP = 3;
    public static final String IMG_FILE = "images/CleverBug.png";
    private BufferedImage img;
    
    public CleverBug(int x, int y, int courtWidth, int courtHeight) {
    	super(x, y, SIZE, CLEVER_HP, SPEED, SPEED, courtWidth, courtHeight);
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
    
    /**
     * a cleverbug always takes the shortest route to the snack
     * that is closest to it
     * need to interact with the collection of Snack in GameCourt
     */
    public void move(Collection<Snack> snacks) {
    	int minDist = GameCourt.COURT_WIDTH + GameCourt.COURT_HEIGHT;
    	Snack tgtSnack = null;
    	for (Snack snack : snacks) {
    		int dist = this.getDist(snack);
    		if (dist < minDist) {
    			tgtSnack = snack;
    			minDist = dist;
    		}
    	}
    	if (tgtSnack != null) {
    		int xdist = tgtSnack.getX() - this.getX();
    		int ydist = tgtSnack.getY() - this.getY();
    		if (Math.abs(xdist) > Math.abs(ydist)) {
    			if (xdist < 0) {this.moveLeft();}
    			else {this.moveRight();}
    		}
    		else {
    			if (ydist < 0) {this.moveUp();}
    			else {this.moveDown();}
    		}
    	}
    }
    
    /**
     * 
     * @param snack
     * @return the distance between the bug and the snack
     */
    int getDist(Snack snack) {
    	return Math.abs(this.getX() - snack.getX())
    			+ Math.abs(this.getY() - snack.getY());
    }
    
}
