import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testBullet {
	
	@Test
	public void testSetUp() {
		Bullet b = new Bullet(10, 10, 100, 100, Direction.DOWN);
		assertEquals(10, b.getX());
		assertEquals(10, b.getY());
	}
	
	@Test
	public void testMove() {
		Bullet b = new Bullet(10, 10, 100, 100, Direction.DOWN);
		b.move();
		assertEquals(10, b.getX());
		assertEquals(20, b.getY());
		b.move();
		assertEquals(10, b.getX());
		assertEquals(30, b.getY());
	}
	
	@Test
	public void testMoveOutOfBound() {
		Bullet b = new Bullet(10, 90, 100, 100, Direction.DOWN);
		b.move();
		assertEquals(10, b.getX());
		assertEquals(90, b.getY());
	}
}
