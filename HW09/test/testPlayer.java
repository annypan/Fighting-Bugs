import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class testPlayer {
	
	@Test
	public void testSetUp() {
		Player p = new Player(100, 100);
		assertEquals(0, p.getX());
		assertEquals(0, p.getY());
	}
	
	@Test
	public void testMove() {
		Player p = new Player(100, 100);
		p.setVx(10);
		p.setVy(20);
		p.move();
		assertEquals(10, p.getX());
		assertEquals(20, p.getY());
	}
	
	@Test
	public void testMoveOutOfBound() {
		Player p = new Player(100, 100);
		p.setVx(110);
		p.setVy(120);
		p.move();
		assertEquals(100, p.getX());
		assertEquals(100, p.getY());
	}
	
	@Test
	public void testIntersectsOverlap() {
		Player p = new Player(100, 100);
		DumbBug bug = new DumbBug(10, 10, 100, 100);
		assertTrue(p.intersects(bug));
	}
	
	@Test
	public void testIntersectsTangent() {
		Player p = new Player(100, 100);
		DumbBug bug = new DumbBug(60, 0, 100, 100);
		assertTrue(p.intersects(bug));
	}
	
	@Test
	public void testIntersectsFalse() {
		Player p = new Player(100, 100);
		DumbBug bug = new DumbBug(70, 0, 100, 100);
		assertFalse(p.intersects(bug));
	}
	
}
