package space;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import java.util.*;

import ship.*;


/**
 * 
 * @author Joost Verplancke
 * @version 1.0
 * TODO: comment
 */
public class World {

	public static final double LIGHTSPEED = 300000;
	
	
	public World() {
		this(1000, 1000);
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


	public void add(Ship ship) throws IllegalArgumentException {
		if(!isValidShipToAdd(ship)) throw new IllegalArgumentException();
		ships.add(ship);		
	}
	
	public void add(Asteroid asteroid) throws IllegalArgumentException {
		if(!isValidAsteroidToAdd(asteroid)) throw new IllegalArgumentException();
		asteroids.add(asteroid);
	}
	
	public static boolean isValidShipToAdd(Ship ship){
		return ship != null && ship.isAvailableToAdd();
	}
	
	public static boolean isValidAsteroidToAdd(Flying asteroid){
		return asteroid != null && asteroid.isAvailableToAdd();
	}

	
	public boolean contains(Flying asteroid){
		return asteroids.contains(asteroid);
	}
	
	public boolean contains(Ship ship){
		return ships.contains(ship);
	}
	
	public boolean contains(Bullet bullet){
		return bullets.contains(bullet);
	}
	


	public void evolve(double dt, CollisionListener collisionListener) {
		// TODO Auto-generated method stub
		
	}

	public void remove(Asteroid asteroid) {
		// TODO Auto-generated method stub
		
	}
	
	public void remove(Ship ship){
		// TODO implement
	}

	public void shoot(Ship ship, Bullet shotbullet) {
		// TODO Auto-generated method stub
		
	}


}
