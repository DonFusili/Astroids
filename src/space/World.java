package space;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;

import ship.*;


/**
 * 
 * @author Joost Verplancke
 * @version 1.0
 */
public class World {

	public static final double LIGHTSPEED = 300000;
	
	
	public World() {
		this(100, 100);
	}
	
	public World(double width, double height){
		this.width = width;
		this.height = height;
	}
	
	@Basic
	@Immutable
	public double getWidth(){
		return width;
	}
	
	private final double width;
	
	@Basic
	@Immutable
	public double getHeight(){
		return height;
	}
	
	private final double height;
	
	public Set<Ship> getShips(){
		return new HashSet<Ship>(ships);
	}
	
	private Set<Ship> ships = new HashSet<Ship>();


	public Set<Asteroid> getAsteroids() {
		return new HashSet<Asteroid>(asteroids);
	}
	
	private Set<Asteroid> asteroids = new HashSet<Asteroid>();


	public Set<Bullet> getBullets() {
		return new HashSet<Bullet>(bullets);
	}
	
	private Set<Bullet> bullets = new HashSet<Bullet>();


	public void addShip(Ship ship) throws IllegalArgumentException {
		if(!isValidShipToAdd(ship)) throw new IllegalArgumentException();
		ships.add(ship);		
	}
	
	public static boolean isValidShipToAdd(Ship ship){
		return ship != null && ship.getWorld() == null;
	}

	public void addAsteroid(Asteroid asteroid) {
		// TODO Auto-generated method stub
		
	}

	public void removeShip(Ship ship) {
		// TODO Auto-generated method stub
		
	}

}
