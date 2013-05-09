package space;

import java.util.Random;

import be.kuleuven.cs.som.annotate.*;

import ship.Bullet;
import ship.Ship;

import extraUtil.*;


/**
 * Asteroids are spheres flying around that have an awkward way of dying.
 * @version 1.0
 * @author Joost Verplancke
 * @Invar ...
 * 			| this.getFlyerType == Flyer.ASTEROID
 */
public class Asteroid extends Flying {

	/**
	 * Constructor for asteroid, coordinates and speeds are entered in vector format
	 * @param radius
	 * @param coordinates
	 * @param speeds
	 * @effect 
	 * 		| super(radius, coordinates, speeds, Flyer.ASTEROID)
	 * @effect
	 * 		| this.random = null
	 */
	public Asteroid(double radius, Vector coordinates, Vector speeds){
		super(radius, coordinates, speeds, Flyer.ASTEROID);
		this.random = null;
	}
	
	/**
	 * Constructor for asteroid,x- and y-components of coordinate and speed are entered seperately 
	 * @param radius
	 * @param xpos
	 * @param ypos
	 * @param xspeed
	 * @param yspeed
	 * @effect 
	 * 		| super(radius, Vector(xpos, ypos), Vector(xspeed, yspeed), Flyer.ASTEROID)
	 * @effect
	 * 		| this.random = null
	 */
	public Asteroid(double radius, double xpos, double ypos, double xspeed, double yspeed){
		super(radius, new Vector(xpos, ypos), new Vector(xspeed, yspeed), Flyer.ASTEROID);
		this.random = null;
	}
	
	/**
	 * Constructor for asteroid,x- and y-components of coordinate and speed are entered seperately and a random element is used 
	 * @param radius
	 * @param xpos
	 * @param ypos
	 * @param xspeed
	 * @param yspeed
	 * @param random
	 * @effect 
	 * 		| super(radius, Vector(xpos, ypos), Vector(xspeed, yspeed), Flyer.ASTEROID)
	 * @effect
	 * 		| this.random = random
	 */
	public Asteroid(double radius, double xpos, double ypos, double xspeed, double yspeed, Random random){
		this(radius, xpos, ypos, xspeed, yspeed);
		this.random = random;
	}
	
	/**
	 * Constructor for asteroid, coordinates and speeds are entered in vector format and a random element is used
	 * @param radius
	 * @param coordinates
	 * @param speeds
	 * @effect 
	 * 		| super(radius, coordinates, speeds, Flyer.ASTEROID)
	 * @effect
	 * 		| this.random = random
	 */
	public Asteroid(double radius, Vector coordinates, Vector speeds, Random random){
		super(radius, coordinates, speeds, Flyer.ASTEROID);
		this.random = random;
	}
	
	private Random random;
	
	/**
	 * Returns the mass of the asteroid
	 * @return this.getSphericalMass()
	 */
	public double getMass() {
		return this.getSphericalMass();
	}
	
	/**
	 * Returns the mass density of the asteroid
	 * @return RHO1;
	 */
	@Override
	public double getMassDensity() {
		return RHO1;
	}

	/**
	 * Adds the asteroid to the world
	 * @param world
	 * 			Defines which world the asteroid is added to
	 * @post this.getWorld() == world
	 * @throws IllegalStateException
	 * 			The asteroid is already assigned to a world
	 * 		| !this.isAvailableToAdd()
	 */
	@Override
	public void addToWorld(World world) throws IllegalStateException, IllegalArgumentException{
		if(! this.isAvailableToAdd()) throw new IllegalStateException();
		world.add(this);
	}

	/**
	 * Asteroids are not destroyed by ships, nothing happens
	 */
	@Override
	protected void collideWithShip(Ship ship) {
		//Nothing happens.
		return;
	}

	/**
	 * Handles a collision with a bullet by killing the asteroid
	 * @param bullet
	 * @effect this.die()
	 */
	@Override
	protected void collideWithBullet(Bullet bullet) {
		this.die();
	}

	/**
	 * Handles a collision with an asteroid by killing the asteroid
	 * @param asteroid
	 * 		The asteroid to check collision with
	 * @effect this.die()
	 */
	@Override
	protected void collideWithAsteroid(Asteroid asteroid) {
		this.die();
	}

	/**
	 * Terminates an asteroid by removing it from the world
	 * @effect this.getWorld().remove(this)
	 */
	@Override
	protected void terminate() {
		this.getWorld().remove(this);
	}

	/**
	 * Large asteroids are not removed from the game, but are split into two smaller asteroids.
	 * @effect ...
	 * 			| coordinates = Vector.add(this.getCoordinates(), new Vector(angle, this.getRadius()/2, this.getRadius()))
	 * 			  coordinates2 = Vector.add(this.getCoordinates(), new Vector(angle2, this.getRadius()/2, this.getRadius()))
	 * @effect ...
	 * 			| angle = this.getRandom().getDouble() * Pi
	 * 			  speeds = new Vector(angle, speed, World.LIGHTSPEED)
	 * 			  speeds2 = new Vector(angle + Pi, speed, World.LIGHTSPEED)
	 * @effect ...
	 * 			| asteroid1 = new Asteroid(this.getRadius()/2, coordinates, speeds, this.getRandom())
	 * @effect ...
	 * 			| asteroid2 = new Asteroid(this.getRadius()/2, coordinates2, speeds2, this.getRandom())
	 * @effect ...
	 * 			| if(this.getRadius()>30) this.getWorld().add(asteroid1) && this.getWorld().add(asteroid2)
	 * @effect ...
	 * 			| this.die()
	 * 			
	 */
	@Override
	public void die() {
		if(this.getRadius() >= 30){
			Asteroid kid1 = null;
			Asteroid kid2 = null;
			double speed = 1.5*this.getSpeed();
			double angle = 0;
			double angle2 = Math.PI;
			try{
				angle = this.getRandom().nextDouble()*2*Math.PI;
				angle2 = Math.PI - angle;
			}
			catch(NullPointerException e){ System.out.println("not randomized");}
			if(angle2 < 0) angle2 += 2*Math.PI;
			Vector coordinates = Vector.add(this.getCoordinates(), new Vector(angle, this.getRadius()/2, this.getRadius()));
			Vector coordinates2 = Vector.add(this.getCoordinates(), new Vector(angle2, this.getRadius()/2, this.getRadius()));
			Vector speeds = null;
			Vector speeds2 = null;
			try{
				speeds = new Vector(angle, speed, World.LIGHTSPEED);
				speeds2 = new Vector(angle2, speed, World.LIGHTSPEED);
			}
			catch(IllegalArgumentException e){
				speeds = new Vector(angle, World.LIGHTSPEED, World.LIGHTSPEED);
				speeds2 = new Vector(angle2, World.LIGHTSPEED, World.LIGHTSPEED);
			}
			kid1 = new Asteroid(this.getRadius()/2, coordinates, speeds, this.getRandom());
			kid2 = new Asteroid(this.getRadius()/2, coordinates2, speeds2, this.getRandom());
			this.getWorld().add(kid1);
			this.getWorld().add(kid2);
		}
		this.terminate();
	}
	
	@Basic
	public Random getRandom(){
		return this.random;
	}

}