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
	public void setUp() {
		shipTester = new Ship(11);
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
	
	//static method movingTowardsEachOther tested at Overlap paragraph
	
	
	//Constructors
	
	@Test
	public void basicConstructorOK(){
		Flying ship = new Ship(17);
		assertTrue(ship.getRadius() == 17);
		assertTrue(ship.getXCoordinate() == 0);
		assertTrue(ship.getYCoordinate() == 0);
		assertTrue(ship.getXSpeed() == 0);
		assertTrue(ship.getYSpeed() == 0);
		assertTrue(ship.getAngle() == 0);
	}
	
	@Test
	public void totalConstructorOK(){
		Flying ship = new Ship(25, -20, -6, 14, 33, 5);
		assertTrue(ship.getRadius() == 33);
		assertTrue(ship.getXCoordinate() == 25);
		assertTrue(ship.getYCoordinate() == -20);
		assertTrue(ship.getAngle() == 5);
		assertTrue(ship.getXSpeed() == -6);
		assertTrue(ship.getYSpeed() == 14);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBasicConstructorTooSmallRadius(){
		@SuppressWarnings("unused")
		Flying ship = new Ship(5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTotalConstructorIllegalX(){
		@SuppressWarnings("unused")
		Flying ship = new Ship(Double.NaN, 0, 0, 0, 11, 0);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testBasicConstructorNegativeRadius(){
		@SuppressWarnings("unused")
		Flying ship = new Ship(-16);
	}
	
	//Setters
	//Coordinates: defensively
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetXNAN(){
		Flying ship = new Ship(11);
		ship.setXCoordinate(Double.NaN);
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
	public void setXSpeedOverflow(){
		double before = shipTester.getXSpeed();
		shipTester.setYSpeed(2);
		shipTester.setXSpeed(Ship.getMaxSpeed());
		assertEquals(before, shipTester.getXSpeed(),0);
	}
	
	@Test
	public void setYSpeedOverflow(){
		double before = shipTester.getYSpeed();
		shipTester.setXSpeed(2);
		shipTester.setYSpeed(Ship.getMaxSpeed());
		assertEquals(before, shipTester.getYSpeed(),0);
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
		shipTester.move(2);
		assertEquals(35, shipTester.getXCoordinate(), Util.getEpsilon());
		assertEquals(-58, shipTester.getYCoordinate(), Util.getEpsilon());
	}
	
	//Overlaps and collisions, tests for Ship
	
	@Test
	public void testMovingTowardsEachOther(){
		Flying ship1 = new Ship(20, 0, -2, 0, 10, Math.PI);
		Flying ship2 = new Ship(-20, 0, 2, 0, 10, 0);
		assertTrue(Ship.movingTowardsEachOther(ship1, ship2));
	}
	
	@Test
	public void testNotMovingTowardsEachOther(){
		Flying ship1 = new Ship(20, 0, 2, 0, 10, Math.PI);
		Flying ship2 = new Ship(-20, 0, -2, 0, 10, 0);
		assertFalse(Ship.movingTowardsEachOther(ship1, ship2));
	}
	
	@Test
	public void testMovingTowardsEachOtherChasing(){
		Flying ship1 = new Ship(20, 0, -2, 0, 10, Math.PI);
		Flying ship2 = new Ship(-20, 0, -1, 0, 10, 0);
		assertTrue(Ship.movingTowardsEachOther(ship1, ship2));
	}
	
	@Test
	public void testNotMovingTowardsEachOtherChasing(){
		Flying ship1 = new Ship(20, 0, 2, 0, 10, Math.PI);
		Flying ship2 = new Ship(-20, 0, 1, 0, 10, 0);
		assertFalse(Ship.movingTowardsEachOther(ship1, ship2));
	}
	
	@Test
	public void testDistanceToSelfIsZero() {
		assertTrue(shipTester.getDistanceBetween(shipTester) == 0);
	}
	
	@Test
	public void testDistanceToOtherShipPositiveDistance(){
		Flying ship1 = new Ship(20, 0, 2, 0, 10, Math.PI);
		Flying ship2 = new Ship(-20, 0, -2, 0, 10, 0);
		assertTrue(Ship.getDistanceBetween(ship1, ship2) == 20);
	}
	
	@Test
	public void testDistanceToOtherShipNegativeDistance(){
		Flying ship1 = new Ship(20, 0, 2, 0, 21, Math.PI);
		Flying ship2 = new Ship(-20, 0, -2, 0, 20, 0);
		assertTrue(Ship.getDistanceBetween(ship1, ship2) < 0);
	}
	
	@Test
	public void testDistanceToOtherShipTouching(){
		Flying ship1 = new Ship(20, 0, 2, 0, 20, Math.PI);
		Flying ship2 = new Ship(-20, 0, -2, 0, 20, 0);
		assertTrue(Ship.getDistanceBetween(ship1, ship2) == 0);
	}
	
	@Test
	public void testTimeToCollisionOK(){
		Flying ship1 = new Ship(20, 0, -2, 0, 10, Math.PI);
		Flying ship2 = new Ship(-20, 0, 2, 0, 10, 0);
		double time = ship1.getTimeToCollision(ship2);
		assertFalse(time == Double.POSITIVE_INFINITY);
		assertEquals(5, time, Util.getEpsilon());
	}
	
	@Test
	public void testCollisionPointXAxis(){
		Flying ship1 = new Ship(20, 0, -2, 0, 10, 0);
		Flying ship2 = new Ship(-20, 0, 2, 0, 10, 0);
		double[] pos = ship1.getCollisionPosition(ship2);
		assertEquals(0, pos[1], 0);
		assertEquals(0, pos[0], 0);
	}
	
	@Test
	public void testCollisionYAxis(){
		Flying ship1 = new Ship(0, 20, 0, -2, 10, 0);
		Flying ship2 = new Ship(0, -20, 0, 2, 10, 0);
		double[] pos = ship1.getCollisionPosition(ship2);
		assertEquals(0, pos[1], 0);
		assertEquals(0, pos[0], 0);
	}
	
	@Test
	public void testCollisionKissAndRide(){
		Flying ship1 = new Ship(7, 5, 2, 0, 10, 0);
		Flying ship2 = new Ship(27, 25, -2, 0, 10, 0);
		double[] pos = Ship.getCollisionPoint(ship1, ship2);
		assertEquals(15, pos[1], 0);
		assertEquals(17, pos[0], 0);
	}
	

}