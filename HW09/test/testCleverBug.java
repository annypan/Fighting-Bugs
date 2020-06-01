import static org.junit.Assert.assertEquals;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

public class testCleverBug {
	
	@Test
	public void testGetDist() {
		CleverBug bug = new CleverBug(10, 9, 100, 100);
		Snack s1 = new Snack(10, 10, 100, 100);
		assertEquals(1, bug.getDist(s1));
	}
	
	@Test
	public void testMoveOneStep() {
		Collection<Snack> snacks = new HashSet<>();
		Snack s1 = new Snack(10, 10, 100, 100);
		Snack s2 = new Snack(30, 30, 100, 100);
		snacks.add(s1);
		snacks.add(s2);
		CleverBug bug = new CleverBug(10, 9, 100, 100);
		bug.move(snacks);
		assertEquals(10, bug.getX());
		assertEquals(10, bug.getY());
	}
	
	@Test
	public void testMoveTwoSteps() {
		Collection<Snack> snacks = new HashSet<>();
		Snack s1 = new Snack(25, 30, 100, 100);
		snacks.add(s1);
		CleverBug bug = new CleverBug(10, 10, 100, 100);
		bug.move(snacks);
		assertEquals(10, bug.getX());
		assertEquals(11, bug.getY());
		bug.move(snacks);
		assertEquals(10, bug.getX());
		assertEquals(12, bug.getY());
	}
	
	@Test
	public void testMoveOutOfBound() {
		CleverBug bug = new CleverBug(70, 70, 100, 100);
		bug.moveDown();
		assertEquals(70, bug.getX());
		assertEquals(70, bug.getY());
	}
	
}
