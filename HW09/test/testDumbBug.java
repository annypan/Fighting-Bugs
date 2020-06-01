import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class testDumbBug {
	
	@Test
	public void testMoveOutOfBound() {
		DumbBug bug = new DumbBug(70, 70, 100, 100);
		bug.moveDown();
		assertEquals(70, bug.getX());
		assertEquals(70, bug.getY());
	}
	
	@Test
	public void testRandomMove() {
		DumbBug bug = new DumbBug(30, 30, 100, 100);
		bug.move();
		assertTrue(bug.getX() == 20 || bug.getX() == 30 || bug.getX() == 40);
		assertTrue(bug.getY() == 20 || bug.getY() == 30 || bug.getY() == 40);
	}
}
