package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import space.*;

import ship.*;

public class WorldTest {

	@Test
	public void testConstructorOK(){
		World world = new World();
		assertTrue(world.getHeight() == 768 && world.getWidth() == 1366);
		world = new World(36, 17);
		assertTrue(world.getHeight() == 17 && world.getWidth() == 36);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorIllegal(){
		@SuppressWarnings("unused")
		World world = new World(-2, 17);
	}
	
	@Test
	public void testValidDimension(){
		assertTrue(World.isValidDimension(7));
		assertFalse(World.isValidDimension(-6));
	}
	
	@Test
	public void testAddShip(){
		Ship ship = new Ship(0, 0, 0, 0, 11, 0, 600);
		World world = new World();
		world.add(ship);
		assertTrue(world.contains(ship));
	}
	
	@Test
	public void testAddAsteroid(){
		Asteroid asteroid = new Asteroid(11, 0, 0, 0, 0);
		World world = new World();
		world.add(asteroid);
		assertTrue(world.contains(asteroid));
	}
	
	@Test
	public void testRemoveAsteroid(){
		Asteroid asteroid = new Asteroid(11, 0, 0, 0, 0);
		World world = new World();
		world.add(asteroid);
		world.remove(asteroid);
		assertFalse(world.contains(asteroid));
	}
	
	@Test
	public void testRemoveShip(){
		Ship ship = new Ship(0, 0, 0, 0, 11, 0, 600);
		World world = new World();
		world.add(ship);
		world.remove(ship);
		assertFalse(world.contains(ship));
	}
	
	@Test
	public void testShootBullet(){
		Ship ship = new Ship(0, 0, 0, 0, 11, 0, 600);
		World world = new World();
		world.add(ship);
		ship.fireBullet();
		assertFalse(world.getBullets().isEmpty());
	}
	
	@Test
	public void testRecalibrateAndEvolve(){
		fail("Don't know how to test");
	}
}
