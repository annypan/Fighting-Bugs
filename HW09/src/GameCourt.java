import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameCourt extends JPanel{
	
	// the state of the game logic
	Player player;
	
	/* the following sets store the corresponding objects that are currently
	 * on the screen
	 */
	Set<CleverBug> cleverBugSet;
	Set<DumbBug> dumbBugSet;
	Set<Snack> snackSet;
	Set<Bullet> bulletSet;
	Set<Pair> gameRecords;
	
	
	boolean playing = false; // indicates whether the game is on
	private JLabel status; // the text that displays the current status
	int score = 0; // the current score of player
	// the label to display the player's current score
	final JLabel scoreDisplayer = new JLabel("Score: " + score);
	
	// a double to keep track of the total time elapsed,
	// along with a label to display it on the screen
	double timeElapsed = 0;
	final JLabel timeDisplayer = new JLabel("Time elapsed: " + timeElapsed);
	
	// a label to display the highest scores
	final JLabel highScoreDisplayer = new JLabel("Highest Scores: " + 0);
	/*
	 * game constants
	 */
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 600;
	private static final int NUM_SNACKS = 5; // the number of snacks
	private static final String pathToOutput = "ScoreInfo/scores.txt";
	
	// round the time to 2 decimals
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	// update interval for timer, in milliseconds
	private static final int INTERVAL = 50;
	
	public GameCourt(JLabel status) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
				tick();
            }
        });
        timer.start();
        
        // record the current time
        // startTime = System.currentTimeMillis();
        setFocusable(true);
        
        // read the data from file into gameRecords
        gameRecords = new TreeSet<Pair>();
        readFromFile();
        
        addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		// the movement of the player
        		if (e.getKeyCode() == KeyEvent.VK_A) {
        			player.setVx(-Player.SPEED);
        		}
        		else if (e.getKeyCode() == KeyEvent.VK_D) {
        			player.setVx(Player.SPEED);
        		}
        		else if (e.getKeyCode() == KeyEvent.VK_W) {
        			player.setVy(-Player.SPEED);
        		}
        		else if (e.getKeyCode() == KeyEvent.VK_S) {
        			player.setVy(Player.SPEED);
        		}
        		
        		// the shooting of the bullet
        		if (e.getKeyCode() == KeyEvent.VK_J) {
        			Bullet b = new Bullet(player.getX(), player.getY(),
        					COURT_WIDTH, COURT_HEIGHT, Direction.LEFT);
        			bulletSet.add(b);
        		}
        		else if (e.getKeyCode() == KeyEvent.VK_L) {
        			Bullet b = new Bullet(player.getX(), player.getY(),
        					COURT_WIDTH, COURT_HEIGHT, Direction.RIGHT);
        			bulletSet.add(b);
        		}
        		else if (e.getKeyCode() == KeyEvent.VK_I) {
        			Bullet b = new Bullet(player.getX(), player.getY(),
        					COURT_WIDTH, COURT_HEIGHT, Direction.UP);
        			bulletSet.add(b);
        		}
        		else if (e.getKeyCode() == KeyEvent.VK_K) {
        			Bullet b = new Bullet(player.getX(), player.getY(),
        					COURT_WIDTH, COURT_HEIGHT, Direction.DOWN);
        			bulletSet.add(b);
        		}
        		
        	}
        	public void keyReleased(KeyEvent e) {
        		player.setVx(0);
        		player.setVy(0);
        	}
        });
        this.status = status;
	}
	/**
	 * reset the game to its original state
	 * first prompts the user to enter the number of bugs he wants to fight
	 * then reset the game to its initial state 
	 */
	public void reset() {
		displayHighestScore();
		timeElapsed = 0;
		score = 0;
		scoreDisplayer.setText("Score: " + score);
		timeDisplayer.setText("Time elapsed: " + 0);
		Random r = new Random();
		int numOfBugs = r.nextInt(9) + 1;
		// initialization of the player
		player = new Player(COURT_WIDTH, COURT_HEIGHT);
		dumbBugSet = new HashSet<DumbBug>();
		cleverBugSet = new HashSet<CleverBug>();
		snackSet = new HashSet<Snack>();
		bulletSet = new HashSet<Bullet>();
		
		// initialization of the bugs
		for (int i = 0; i < numOfBugs; i++) {
			Random rand = new Random();
			int randInt = rand.nextInt(2);
			int pos1 = rand.nextInt(COURT_WIDTH - 100);
			int pos2 = rand.nextInt(COURT_HEIGHT - 100);
			pos1 += 100;
			pos2 += 100;
			if (randInt == 0) {
				CleverBug bug = new CleverBug(pos1, pos2, 
						COURT_WIDTH, COURT_HEIGHT);
				cleverBugSet.add(bug);
			}
			else {
				DumbBug bug = new DumbBug(pos1, pos2,
						COURT_WIDTH, COURT_HEIGHT);
				dumbBugSet.add(bug);
			}
		}
		
		// initialization of the snacks
		for (int i = 0; i < NUM_SNACKS; i++) {
			Random rand = new Random();
			int pos1 = rand.nextInt(COURT_WIDTH - Snack.SIZE);
			int pos2 = rand.nextInt(COURT_HEIGHT - Snack.SIZE);
			Snack snack = new Snack(pos1, pos2, COURT_WIDTH, COURT_HEIGHT);
			snackSet.add(snack);
		}
		
		// set the game on
		playing = true;
		status.setText("Hurry! Go kill the bugs and get your snacks!");
		requestFocusInWindow();
	}
	
	void tick() {
		if (playing) {
			timeElapsed += (double)INTERVAL/1000;
			timeDisplayer.setText("Time elapsed: " + df.format(timeElapsed));
			// update the state of DumbBug: if it runs into a snack, it eats it
			for (DumbBug bug : dumbBugSet) {
				bug.move();
				// There could be more than one snack in one place,
				// so a set is used to store the snacks that are eaten
				Set<Snack> eaten = new HashSet<Snack>();
				for (Snack snack : snackSet) {
					if (bug.intersects(snack)) {
						bug.setHP(DumbBug.DUMB_HP);
						eaten.add(snack);
					}
				}
				if (eaten.size() > 0) {
					for (Snack snack : eaten) {
						snackSet.remove(snack);
					}
				}
			}
			
			// update the state of cleverBug: if it runs into a snack, it eats it
			for (CleverBug bug : cleverBugSet) {
				bug.move(snackSet);
				Set<Snack> eaten = new HashSet<Snack>();
				for (Snack snack : snackSet) {
					if (bug.intersects(snack)) {
						bug.setHP(DumbBug.DUMB_HP);
						eaten.add(snack);
					}
				}
				if (eaten.size() > 0) {
					for (Snack snack : eaten) {
						snackSet.remove(snack);
					}
				}
			}
			
			// update the state of bullets and bugs
			// a temporary set for storing the bullets that are going to
			// be removed from the set of bullets
			Collection<Bullet> toBeRemoved = new HashSet<Bullet>();
			for (Bullet bullet : bulletSet) {
				bullet.move();
				
				// if the bullet strikes a cleverBug
				Set<CleverBug> strikenCBug = new HashSet<CleverBug>();
				for (CleverBug cbug : cleverBugSet) {
					if (cbug.intersects(bullet)) {
						cbug.setHP(cbug.getHP() - 1);
						if (cbug.getHP() <= 0) {
							strikenCBug.add(cbug);
						}
						toBeRemoved.add(bullet);
					}
				}
				if (strikenCBug.size() > 0) {
					for (CleverBug cbug : strikenCBug) {
						cleverBugSet.remove(cbug);
						score++;
						scoreDisplayer.setText("Score: " + score);
					}
				}
				
				// if the bullet strikes a dumbBug
				Set<DumbBug> strikenDBug = new HashSet<DumbBug>();
				for (DumbBug dbug : dumbBugSet) {
					if (dbug.intersects(bullet)) {
						dbug.setHP(dbug.getHP() - 1);
						if (dbug.getHP() <= 0) {
							strikenDBug.add(dbug);
						}
						toBeRemoved.add(bullet);
					}
				}
				if (strikenDBug.size() > 0) {
					for (DumbBug dbug : strikenDBug) {
						dumbBugSet.remove(dbug);
						score++;
						//scoreDisplayer.setText("Score: " + score);
					}
				}
				
				// if the bullet goes out of the boundary 
				if (bullet.getX() <= 0 || bullet.getX() >= bullet.getMaxX()
				    || bullet.getY() <= 0 || bullet.getY() >= bullet.getMaxY()) {
						toBeRemoved.add(bullet);
				}
			}
			
			for (Bullet bullet : toBeRemoved) {
				bulletSet.remove(bullet);
			}
			
			// updates the state of the player
			player.move();
			// if he runs into a bug, he dies
			for (CleverBug cbug : cleverBugSet) {
				if (cbug.intersects(player)) {
					player.setHP(player.getHP() - 1);
				}
			}
			for (DumbBug dbug : dumbBugSet) {
				if (dbug.intersects(player)) {
					player.setHP(player.getHP() - 1);
				}
			}
			if (player.getHP() <= 0) {
				score = 0;
				scoreDisplayer.setText("Score: " + score);
				playing = false;
				status.setText("You're frightened to death! Game over."
						+ " Time spent: " + df.format(timeElapsed));
				writeIntoFile(Math.floor(timeElapsed * 100)/100);
				gameRecords.add(new Pair(score, 
						Math.floor(timeElapsed * 100)/100));
			}
			// if he runs into a snack, he collects it
			Set<Snack> eaten = new HashSet<Snack>();
			for (Snack snack : snackSet) {
				if (player.intersects(snack)) {
					eaten.add(snack);
				}
			}
			if (eaten.size() > 0) {
				for (Snack snack : eaten) {
					snackSet.remove(snack);
					score++;
					scoreDisplayer.setText("Score: " + score);
				}
			}
			
			// if there are no snack in the screen, the game ends
			if (snackSet.size() == 0) {
				playing = false;
				status.setText("You retrieved as many snacks "
						+ "as possible! Score: " + score 
						+ " Time spent: " + df.format(timeElapsed));
				writeIntoFile(Math.floor(timeElapsed * 100)/100);
				gameRecords.add(new Pair(score, 
						Math.round(Math.floor(timeElapsed * 100)/100)));
			}
			repaint();
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (CleverBug cb : cleverBugSet) {
        	cb.draw(g);
        }
        for (DumbBug db : dumbBugSet) {
        	db.draw(g);
        }
        for (Bullet b : bulletSet) {
        	b.draw(g);
        }
        for (Snack s : snackSet) {
        	s.draw(g);
        }
    }
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
	
	/***
	 * write the current score and time into the file for keeping game records
	 */
	private void writeIntoFile(double time) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pathToOutput, true));
			bw.write(((Integer) score).toString());
			bw.write(" ");
			bw.write(((Double) time).toString());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	/***
	 * read the previous records into gameRecords
	 */
	private void readFromFile() {
		RecordIterator rit = new RecordIterator(pathToOutput);
		while (rit.hasNext()) {
			String line = rit.next();
			String[] words = line.split(" ");
			if (words[0].equals("") || words[1].equals("")) {break;}
			int score = Integer.parseInt(words[0]);
			double time = Double.parseDouble(words[1]);
			Pair timeAndScore = new Pair(score, time);
			gameRecords.add(timeAndScore);
		}			
	}
	
	private void displayHighestScore() {
		if (gameRecords.size() == 0) {
			highScoreDisplayer.setText("Highest Scores: 0");
		}
		else if (gameRecords.size() == 1) {
			Pair pair0 = (Pair)gameRecords.toArray()[0];
			highScoreDisplayer.setText("Highest Scores: " 
					+ (pair0).getScore()
					+ "(" + pair0.getTime() + "s)");
		}
		
		else if (gameRecords.size() == 2) {
			Pair pair0 = (Pair)gameRecords.toArray()[0];
			Pair pair1 = (Pair)gameRecords.toArray()[1];
			highScoreDisplayer.setText("Highest Scores: "
						+ pair0.getScore() + "(" + pair0.getTime() + "s)  "
						+ pair0.getScore() + "(" + pair1.getTime() +"s)");
		}
		else {
			Pair pair0 = (Pair)gameRecords.toArray()[0];
			Pair pair1 = (Pair)gameRecords.toArray()[1];
			Pair pair2 = (Pair)gameRecords.toArray()[2];
			highScoreDisplayer.setText("Highest Scores: "
					+ pair0.getScore() + "(" + pair0.getTime() + "s)  " 
					+ pair1.getScore() + "(" + pair1.getTime() + "s)  "
					+ pair2.getScore() + "(" + pair2.getTime() + "s)");
		}
	}
}
