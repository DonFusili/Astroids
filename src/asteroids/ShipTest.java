package asteroids;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShipTest {
	
	@Test
	public void testLowerBoundRadiusInitialize(){
		assertTrue(Ship.getLowerboundRadius() == 10);
	}

	@Test
	public void testValidDoubles_HigherThanMaxValue() {
		long x = (long)Double.MAX_VALUE + 1;
		boolean test = Ship.isValidCoordinate(x);
		assertFalse(test);
	}
	
	@Test
	public void testOverlapShipWithItself(){
		Ship ship = new Ship(1, 1, 0, 0, 11, 1);
		double overlap = ship.getDistanceBetween(ship);
		boolean test = (overlap == 0);
		assert test;
	}
	
	@Test
	public void testSettingCoordinates(){
		Ship ship = new Ship(11);
		ship.setXCoordinate(22);
		ship.setYCoordinate(33);
		boolean test = ship.getXCoordinate() == 22 && ship.getYCoordinate() == 33;
		assertTrue(test);
	}

	
	@Test
	public void testChangingLowerBoundForRadius(){
		Ship.setLowerBoundRadius(11);
		boolean test = Ship.getLowerboundRadius() == 11;
		assertTrue(test);
	}

}
