package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ship.Ship;
import space.Flying;

public class GeneralTests {

	@Test
	public void testGetClassShip() {
		Ship ship = new Ship(11);
		assertTrue(ship.getClass() == Ship.class);
	}
	

}
