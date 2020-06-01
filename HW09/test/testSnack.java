import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testSnack {
	
	@Test
	public void testSetUp() {
		Snack snack = new Snack(10, 10, 100, 100);
		assertEquals(10, snack.getX());
		assertEquals(10, snack.getY());
	}
}
