package tests;

import static org.junit.Assert.*;


import org.junit.*;

import extraUtil.Util;

import ship.*;
import space.Flying;

import asteroids.*;

public class ShipTest {
	

	IShip iShipTester;
	Ship shipTester;
	
	@Before
	public void setUp(){
		shipTester = new Ship(0, 0, 0, 0, 10, 0, 600);
	}
	
	
	// Static ship methods
	

	@Test
	public void testValidAccelerationNAN(){
		assertFalse(Ship.isValidAcceleration(Double.NaN));
	}
	
	@Test
	public void testValidAccelerationNegativeAcceleration(){
		assertFalse(Ship.isValidAcceleration(-6));
	}
	
	@Test
	public void testVAlidAccelerationOK(){
		assertTrue(Ship.isValidAcceleration(25));
	}
	
	
	//We don't test exceeding the max double value because of overflow
	
	@Test
	public void testValidCoordinateNAN(){
		assertFalse(Ship.isValidCoordinate(Double.NaN));
	}
	
	@Test
	public void testValidCoordinateOK(){
		assertTrue(Ship.isValidCoordinate(5));
	}
	
	//We don't test exceeding the max double value because of overflow
	
	@Test
	public void testRadiiLimits(){
		assertTrue(Ship.isValidRadius(11));
		assertFalse(Ship.isValidRadius(6));
		assertFalse(Ship.isValidRadius(Double.NaN));
		assertTrue(Ship.isValidRadius(16.33));
		assertTrue(Ship.isValidRadius(17));
	}
	
	@Test
	public void testValidTimeIntervalNAN(){
		assertFalse(Ship.isValidTimeInterval(Double.NaN));
	}
	
	@Test
	public void testValidTimeIntervalNegative(){
		assertFalse(Ship.isValidTimeInterval(-6));
	}
	
	@Test public void testValidTimeIntervalOK(){
		assertTrue(Ship.isValidTimeInterval(5));
	}
	
	
	@Test
	public void testSetXOK(){
		shipTester.setXCoordinate(5);
		assertTrue(shipTester.getXCoordinate() == 5);
	}
	
	@Test
	public void testSetYOK(){
		shipTester.setYCoordinate(6);
		assertTrue(shipTester.getYCoordinate() == 6);
	}
	
	//Speeds totally
	
	@Test
	public void setXSpeedOK(){
		shipTester.setXSpeed(55);
		assertEquals(55, shipTester.getXSpeed(), 0);
	}
	
	@Test
	public void setYSpeedOK(){
		shipTester.setYSpeed(66);
		assertEquals(66, shipTester.getYSpeed(),0);
	}
	
	
	@Test
	public void setXSpeedNAN(){
		double before = shipTester.getXSpeed();
		shipTester.setXSpeed(Double.NaN);
		assertEquals(before, shipTester.getXSpeed(),0);
	}
	
	@Test
	public void setYSpeedNAN(){
		double before = shipTester.getYSpeed();
		shipTester.setYSpeed(Double.NaN);
		assertEquals(before, shipTester.getYSpeed(),0);
	}
	
	
	// controls
	
	@Test
	public void testTurn(){
		shipTester.setAngle(0);
		shipTester.turn(Math.PI/2);
		assertEquals(Math.PI/2, shipTester.getAngle(), Util.getEpsilon());
	}
	
	@Test
	public void testAccelerate(){
		shipTester.setAngle(Math.PI/4);
		shipTester.setXSpeed(2);
		shipTester.setYSpeed(1);
		shipTester.thrust(Math.sqrt(2));
		assertEquals(3, shipTester.getXSpeed(), Util.getEpsilon());
		assertEquals(2, shipTester.getYSpeed(), Util.getEpsilon());
	}
	
	@Test
	public void testMove(){
		shipTester.setXCoordinate(5);
		shipTester.setYCoordinate(28);
		shipTester.setXSpeed(15);
		shipTester.setYSpeed(-43);
		shipTester.move(2, null);
		assertEquals(35, shipTester.getXCoordinate(), Util.getEpsilon());
		assertEquals(-58, shipTester.getYCoordinate(), Util.getEpsilon());
	}
	
	
	@Test
	public void testDistanceToSelfIsZero() {
		assertTrue(shipTester.getDistanceBetween(shipTester) == 0);
	}
	
	@Test
	public void testSetThrusting(){
		shipTester.setThrusting(true);
		assertTrue(shipTester.isThrusterActive());
		shipTester.setThrusting(false);
		assertFalse(shipTester.isThrusterActive());		
		shipTester.toggleThrust();
		assertTrue(shipTester.isThrusterActive());
	}
	
	@Test
	public void testThrustingAngleZero(){
		Ship ship = new Ship(0, 0, 0, 0, 10, 0, 600);
		ship.thrust(6);
		assertTrue(Util.fuzzyEquals(ship.getXSpeed(), 6));
	}
	
	@Test
	public void testThrustingAngleHalfPie(){
		Ship ship = new Ship(0,0,0,0,10,Math.PI/2,600);
		ship.thrust(6);
		assertTrue(Util.fuzzyEquals(ship.getYSpeed(), 6));
	}
	
	@Test
	public void testTurning(){
		Ship ship = new Ship(0, 0, 0, 0, 10, 0, 600);
		ship.turn(0.3);
		assertTrue(Util.fuzzyEquals(ship.getAngle(), 0.3));
	}

}