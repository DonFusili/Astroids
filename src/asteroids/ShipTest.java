package asteroids;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShipTest {

	@Test
	public void testValidDoubles_HigherThanMaxValue() {
		long x = (long)Double.MAX_VALUE + 1;
		boolean test = Ship.isValidCoordinate(x);
		assertFalse(test);
	}

}
