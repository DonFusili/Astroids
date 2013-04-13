package space;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import extraUtil.Util;

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
		ship.setWorld(this);
		ships.add(ship);		
		recallibrate(ship);
	}
	
	public void add(Asteroid asteroid) throws IllegalArgumentException {
		if(!isValidAsteroidToAdd(asteroid)) throw new IllegalArgumentException();
		asteroid.setWorld(this);
		asteroids.add(asteroid);
		recallibrate(asteroid);
	}
	
	public static boolean isValidShipToAdd(Ship ship){
		return ship != null && ship.isAvailableToAdd();
	}
	
	public static boolean isValidAsteroidToAdd(Flying asteroid){
		return asteroid != null && asteroid.isAvailableToAdd();
	}

	
	public boolean contains(Asteroid asteroid){
		return asteroids.contains(asteroid);
	}
	
	public boolean contains(Ship ship){
		return ships.contains(ship);
	}
	
	public boolean contains(Bullet bullet){
		return bullets.contains(bullet);
	}
	

	public void remove(Asteroid asteroid) {
		if(!this.asteroids.contains(asteroid)) return;
		asteroid.setWorld(null);
		this.asteroids.remove(asteroid);
	}
	
	public void remove(Ship ship){
		if(!this.ships.contains(ship)) return;
		ship.setWorld(null);
		this.ships.remove(ship);
	}
	
	public void remove(Bullet bullet){
		if(!this.bullets.contains(bullet) || !(bullet.getWorld() == null)) return;
		this.bullets.remove(bullet);
	}

	public void shoot(Ship ship, Bullet shotbullet) throws IllegalArgumentException {
		if(!ships.contains(ship)) throw new IllegalArgumentException();
		shotbullet.setWorld(this);
		bullets.add(shotbullet);
		recallibrate(shotbullet);
	}
	
	private void recallibrate(Flying flying){
		Collision collision1 = null;
		double t = -1;
		double wouldcollidebefore = -1;
		try{
		wouldcollidebefore = collisions.get(flying).getDelay();
		} catch(NullPointerException e){
			wouldcollidebefore = Double.MAX_VALUE;
		}
		for(Ship flying2 : ships){
			if(!(flying2 == flying)){
				t = Flying.getTimeToCollision(flying, flying2);
				if(!(t == Double.POSITIVE_INFINITY) && t < wouldcollidebefore &&  t < collisions.get(flying2).getDelay()){
					wouldcollidebefore = t;
					collisionsorder.remove(collisions.get(flying));
					collisionsorder.remove(collisions.get(flying2));
					collision1 = new Collision(flying, flying2, t);
					collisions.put(flying2, collision1);
				}
			}
		}
		for(Asteroid flying2 : asteroids){
			if(!(flying2 == flying)){
				t = Flying.getTimeToCollision(flying, flying2);
				if(!(t == Double.POSITIVE_INFINITY) && t < wouldcollidebefore &&  t < collisions.get(flying2).getDelay()){
					wouldcollidebefore = t;
					collisionsorder.remove(collisions.get(flying));
					collisionsorder.remove(collisions.get(flying2));
					collision1 = new Collision(flying, flying2, t);
					collisions.put(flying2, collision1);
				}
			}
		}
		for(Bullet flying2 : bullets){
			if(!(flying2 == flying)){
				t = Flying.getTimeToCollision(flying, flying2);
				if(!(t == Double.POSITIVE_INFINITY) && t < wouldcollidebefore &&  t < collisions.get(flying2).getDelay()){
					wouldcollidebefore = t;
					collisionsorder.remove(collisions.get(flying));
					collisionsorder.remove(collisions.get(flying2));
					collision1 = new Collision(flying, flying2, t);
					collisions.put(flying2, collision1);
				}
			}
		}
		t = collideWithBoundaryTime(flying);
		if(t != Double.POSITIVE_INFINITY && t < wouldcollidebefore){
			collision1 = new Collision(flying, null, t);
		}
		if(!(collision1 == null)) { collisionsorder.add(collision1);
									collisions.put(flying, collision1);
		}
		}
	
	private double collideWithBoundaryTime(Flying flying){
		extraUtil.Vector coo = flying.getCoordinates();
		double radius = flying.getRadius();
		double xs = flying.getSpeeds().getX();
		double ys = flying.getSpeeds().getY();
		double timex = Double.POSITIVE_INFINITY;
		double timey = Double.POSITIVE_INFINITY;
		if(xs == 0 && ys == 0) return Double.POSITIVE_INFINITY;
		if(xs != 0){
			if(xs < 0) timex = ((this.getWidth()/2) + coo.getX() - radius)/xs;
			if(xs > 0) timex = ((this.getWidth()/2) - coo.getX() - radius)/xs;
		}
		if(ys !=0){
			if(ys < 0) timey = ((this.getHeight()/2) + coo.getY() - radius)/ys;
			if(ys > 0) timey = ((this.getHeight()/2) - coo.getY() -radius)/ys;
		}
		return Math.min(timex, timey);
	}
		
	
	private Map<Flying, Collision> collisions = new HashMap<Flying, Collision>();
	
	private SortedSet<Collision> collisionsorder = new ConcurrentSkipListSet<Collision>();

	
	
	public void evolve(double dt, CollisionListener collisionListener) {
		double dtt = dt;
		boolean skipcol = false;
		Collision nextCollision = null;
		double nextcollisiontime = Double.MAX_VALUE;
		Collision collisionToHandle = null;
		try{
			nextCollision = collisionsorder.first();
			nextcollisiontime = nextCollision.getDelay();
			collisionToHandle = null;
		}
		catch(NoSuchElementException e){
			skipcol = true;
		}
		while(!skipcol && (Util.fuzzyLessThanOrEqualTo(nextcollisiontime, dtt) || Util.fuzzyLessThanOrEqualTo(nextcollisiontime, 0))){
			collisionToHandle = nextCollision;
			evolvewithoutcollisions(nextcollisiontime, false);
			handleCollision(collisionToHandle);
			collisionsorder.remove(collisionToHandle);
			dtt -= nextcollisiontime;
			try{
				nextCollision = collisionsorder.first();
				nextcollisiontime = nextCollision.getDelay();
			}
			catch(NoSuchElementException e){
				skipcol = true;
			}
		}
		evolvewithoutcollisions(dtt, true);
	}
	
	private void handleCollision(Collision collision){
		Flying flying1 = collision.getFlying1();
		Flying flying2 = collision.getFlying2();
		if(flying2 == null) { flying1.collideWithBoundary(); recallibrate(flying1); return;}
		Flying.collide(flying1, flying2);
		recallibrate(flying2);
		recallibrate(flying1);
	}
	
	private void evolvewithoutcollisions(double dt, boolean thrustingenabled){
		for(Ship ship : ships){
			ship.move(dt);
		}
		for(Asteroid asteroid : asteroids){
			asteroid.move(dt);
		}
		for(Bullet bullet : bullets){
			bullet.move(dt);
		}
		if(thrustingenabled){
			for(Ship ship : ships){
				if(ship.isThrusterActive()){
					ship.thrust(ship.getAcceleration());
					recallibrate(ship);
				}
			}
		}
		for(Collision toadjust : collisionsorder){
			toadjust.shortenDelayWith(dt);
		}
		for(Collision toadjust : collisions.values()){
			toadjust.shortenDelayWith(dt);
		}
	}
	
	

}
