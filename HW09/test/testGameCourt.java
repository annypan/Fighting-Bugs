import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import org.junit.Test;

public class testGameCourt {
	/**
	 * makes sure that reset() is working correctly:
	 * after calling reset(), everything will be returned to its original state
	 */
	@Test
	public void testReset() {
		// set up the court
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		court.reset();
		assertTrue(court.cleverBugSet.size() <= 9);
		assertTrue(court.dumbBugSet.size() <= 9);
		assertEquals(5, court.snackSet.size());
		assertEquals(0, court.bulletSet.size());
		assertTrue(court.playing);	
	}
	
	
	/**
	 * makes sure that cleverBug is moving correctly
	 * put one snack in front of it, and update the game, check to see
	 * whether it has eaten the snack; then update again and check
	 * to see if it's moving towards another snack
	 */
	@Test
	public void testCleverBugMove() {
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		
		// create a CleverBug in the gamecourt
		CleverBug bug = new CleverBug(100, 100, 600, 600);
		Set<CleverBug> set = new HashSet<CleverBug>();
		set.add(bug);
		
		HashSet<Snack> snacks = new HashSet<>();
		// snack 1 is one movement away from the bug
		Snack snack1 = new Snack(100, 59, 600, 600);
		// snack 2 will cause the bug to move towards its left
		Snack snack2 = new Snack(20, 99, 600, 600);
		snacks.add(snack1);
		snacks.add(snack2);
		
		court.cleverBugSet = set;
		court.snackSet = snacks;
		court.bulletSet = new HashSet<Bullet>();
		court.dumbBugSet = new HashSet<DumbBug>();
		court.playing = true;
		court.player = new Player(600, 600);
		
		court.tick();
		assertEquals(99, bug.getY());
		assertEquals(1, court.snackSet.size());
		
		court.tick();
		assertEquals(99, bug.getX());
	}
	
	/**
	 * test whether the bullet behaves correctly if it hits a clever bug
	 */
	@Test
	public void testBulletMove() {
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		
		CleverBug cbug = new CleverBug(40, 40, 100, 100);
		Set<CleverBug> cbugSet = new HashSet<CleverBug>();
		cbugSet.add(cbug);
		court.cleverBugSet = cbugSet;
		
		// add a snack to control the direction in which the clever bug moves
		// in this case, it's moving to the right
		Snack snack = new Snack(80, 40, 100, 100);
		Set<Snack> snacks = new HashSet<Snack>();
		snacks.add(snack);
		court.snackSet = snacks;
		
		
		Bullet bullet = new Bullet(25, 40, 100, 100, Direction.RIGHT);
		Set<Bullet> bulletSet = new HashSet<Bullet>();
		bulletSet.add(bullet);
		court.bulletSet = bulletSet;
		
		court.dumbBugSet = new HashSet<DumbBug>();
		court.playing = true;
		court.player = new Player(100, 100);
		
		court.tick();
		assertEquals(0, bulletSet.size());
		assertEquals(1, cbugSet.size());
		assertEquals(2, cbug.getHP());
	}
	
	@Test
	public void testBulletMoveOutOfBounds() {
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		Bullet bullet = new Bullet(80, 80, 100, 100, Direction.DOWN);
		Set<Bullet> bulletSet = new HashSet<Bullet>();
		bulletSet.add(bullet);
		
		court.bulletSet = bulletSet;
		court.snackSet = new HashSet<Snack>();
		court.dumbBugSet = new HashSet<DumbBug>();
		court.cleverBugSet = new HashSet<CleverBug>();
		court.playing = true;
		court.player = new Player(100, 100);
		
		court.tick();
		assertEquals(0, bulletSet.size());
	}
	
	@Test
	public void testPlayerHitByBug() {
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		
		CleverBug cbug = new CleverBug(0, 0, 100, 100);
		Set<CleverBug> cbugSet = new HashSet<CleverBug>();
		cbugSet.add(cbug);
		court.cleverBugSet = cbugSet;

		Player player = new Player(100, 100);
		
		court.bulletSet = new HashSet<Bullet>();
		court.snackSet = new HashSet<Snack>();
		court.dumbBugSet = new HashSet<DumbBug>();
		court.playing = true;
		court.player = player;
		
		court.tick();
		assertFalse(court.playing);
		assertEquals(0, player.getHP());
	}
	
	@Test
	public void testPlayerPicksUpSnack() {
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		
		Player player = new Player(100, 100);
		court.player = player;
		
		Snack snack = new Snack(0, 0, 100, 100);
		Set<Snack> snacks = new HashSet<Snack>();
		snacks.add(snack);
		court.snackSet = snacks;
		
		court.bulletSet = new HashSet<Bullet>();
		court.dumbBugSet = new HashSet<DumbBug>();
		court.playing = true;
		court.cleverBugSet = new HashSet<CleverBug>();
		court.score = 0;
		
		court.tick();
		assertFalse(court.playing);
		assertEquals(0, snacks.size());
		assertEquals(1, court.score);
	}
	
	@Test
	public void testCleverBugEatsSnack() {
		final JLabel status = new JLabel("Running...");
		final GameCourt court = new GameCourt(status);
		
		CleverBug cbug = new CleverBug(40, 40, 100, 100);
		Set<CleverBug> cbugSet = new HashSet<CleverBug>();
		cbugSet.add(cbug);
		court.cleverBugSet = cbugSet;
		
		Snack snack = new Snack(40, 40, 100, 100);
		Set<Snack> snacks = new HashSet<Snack>();
		snacks.add(snack);
		court.snackSet = snacks;
		
		court.bulletSet = new HashSet<Bullet>();
		court.dumbBugSet = new HashSet<DumbBug>();
		court.playing = true;
		court.player = new Player(100, 100);
		
		court.tick();
		assertFalse(court.playing);
		assertEquals(0, snacks.size());
	}
		
}
