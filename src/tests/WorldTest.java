package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import space.World;

public class WorldTest {

	@Test
	public void testConstructorOK(){
		World world = new World();
		assertTrue(world.getHeight() == 768 && world.getWidth() == 1366);
		world = new World(36, 17);
		assertTrue(world.getHeight() == 17 && world.getWidth() == 36);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorChuckle(){
		World world = new World(-2, 17);
	}
}
