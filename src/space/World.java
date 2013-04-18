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
		recalibrate(ship);
	}
	
	public void add(Asteroid asteroid) throws IllegalArgumentException {
		if(!isValidAsteroidToAdd(asteroid)) throw new IllegalArgumentException();
		asteroid.setWorld(this);
		asteroids.add(asteroid);
		recalibrate(asteroid);
	}
	
	public static boolean isValidShipToAdd(Ship ship){
		return ship != null && ship.isAvailableToAdd();
	}
	
	public static boolean isValidAsteroidToAdd(Flying asteroid){
		return asteroid != null && asteroid.isAvailableToAdd();
	}

	public boolean contains(Flying flying){
		boolean contains = false;
		switch(flying.getFlyertype()){
			case BULLET : contains =  this.contains((Bullet)flying); break;
			case SHIP : contains =  this.contains((Ship)flying); break;
			case ASTEROID : contains = this.contains((Asteroid)flying); break;
			default : assert false;
		}
		return contains;
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
		if(!(collisions.get(asteroid) == null)) {
			Collision collision = collisions.get(asteroid);
			collisionsorder.remove(collisions.get(collision.getFlying1()));
			collisionsorder.remove(collisions.get(collision.getFlying2()));
			collisions.remove(collision.getFlying1());
			collisions.remove(collision.getFlying2());
			if(this.contains(collision.getFlying2())){
				recalibrate(collision.getFlying2());
			}
			if(this.contains(collision.getFlying1())){
				recalibrate(collision.getFlying1());
			}
			return;
		}
	}
	
	public void remove(Ship ship){
		if(!this.ships.contains(ship)) return;
		ship.setWorld(null);
		this.ships.remove(ship);
		if(!(collisions.get(ship) == null)) {
			Collision collision = collisions.get(ship);
			collisionsorder.remove(collisions.get(collision.getFlying1()));
			collisionsorder.remove(collisions.get(collision.getFlying2()));
			collisions.remove(collision.getFlying1());
			collisions.remove(collision.getFlying2());
			if(this.contains(collision.getFlying2())){
				recalibrate(collision.getFlying2());
			}
			if(this.contains(collision.getFlying1())){
				recalibrate(collision.getFlying1());
			}
			return;
		}
	}
	
	public void remove(Bullet bullet) throws IllegalArgumentException{
		if(!this.bullets.contains(bullet)) throw new IllegalArgumentException();
		System.out.println("Bullet " + bullet.toString() + " deleted");
		this.bullets.remove(bullet);
		bullet.setWorld(null);
		if(!(collisions.get(bullet) == null)) {
			Collision collision = collisions.get(bullet);
			collisionsorder.remove(collisions.get(collision.getFlying1()));
			collisionsorder.remove(collisions.get(collision.getFlying2()));
			if(collision == collisions.get(collision.getFlying1())) collisions.remove(collision.getFlying1());
			if(collision == collisions.get(collision.getFlying2())) collisions.remove(collision.getFlying2());
			if(collision.getFlying2() != bullet && this.contains(collision.getFlying2())){
				recalibrate(collision.getFlying2());
			}
			if(collision.getFlying1() != bullet && this.contains(collision.getFlying1())){
				recalibrate(collision.getFlying1());
			}
			return;
		}
	}

	public void shoot(Ship ship, Bullet shotbullet) throws IllegalArgumentException {
		if(!ships.contains(ship)) throw new IllegalArgumentException();
		shotbullet.setWorld(this);
		bullets.add(shotbullet);
		System.out.println("Bullet " + shotbullet.toString() + " launched");
		recalibrate(shotbullet);
	}
	
	public void recalibrate(Flying flying){
		Collision collision1 = null;
		double t = -1;
		double wouldcollidebefore = -1;
		if(!(collisions.get(flying) == null)){
			wouldcollidebefore = collisions.get(flying).getDelay();
		} 
		else{
			wouldcollidebefore = Double.MAX_VALUE;
		}
		for(Ship flying2 : ships){
			if(!(flying2 == flying)){
				t = Flying.getTimeToCollision(flying, flying2);
				if(!(t == Double.POSITIVE_INFINITY) && t < wouldcollidebefore &&  (collisions.get(flying2) == null || t < collisions.get(flying2).getDelay())){
					wouldcollidebefore = t;
					if(!(collisions.get(flying) == null)) {collisionsorder.remove(collisions.get(flying)); }
					if(!(collisions.get(flying2) == null)) {collisionsorder.remove(collisions.get(flying2)); }
					collision1 = new Collision(flying, flying2, t);
					collisions.put(flying2, collision1);
				}
			}
		}
		for(Asteroid flying2 : asteroids){
			if(!(flying2 == flying)){
				t = Flying.getTimeToCollision(flying, flying2);
				if(!(t == Double.POSITIVE_INFINITY) && t < wouldcollidebefore &&  (collisions.get(flying2) == null || t < collisions.get(flying2).getDelay())){
					wouldcollidebefore = t;
					if(!(collisions.get(flying) == null)) {collisionsorder.remove(collisions.get(flying)); }
					if(!(collisions.get(flying2) == null)) {collisionsorder.remove(collisions.get(flying2)); }
					collision1 = new Collision(flying, flying2, t);
					collisions.put(flying2, collision1);
				}
			}
		}
		for(Bullet flying2 : bullets){
			if(!(flying2 == flying)){
				t = Flying.getTimeToCollision(flying, flying2);
				if(!(t == Double.POSITIVE_INFINITY) && t < wouldcollidebefore &&  (collisions.get(flying2) == null || t < collisions.get(flying2).getDelay())){
					wouldcollidebefore = t;
					if(!(collisions.get(flying) == null)) {collisionsorder.remove(collisions.get(flying)); }
					if(!(collisions.get(flying2) == null)) {collisionsorder.remove(collisions.get(flying2)); }
					collision1 = new Collision(flying, flying2, t);
					collisions.put(flying2, collision1);
				}
			}
		}
		if(!(collision1 == null)) { collisionsorder.add(collision1);
									collisions.put(flying, collision1);
		}
		}
	
	private Map<Flying, Collision> collisions = new HashMap<Flying, Collision>();
	
	private SortedSet<Collision> collisionsorder = new ConcurrentSkipListSet<Collision>();

	
	
	public void evolve(double dt, CollisionListener collisionListener) {
		double dtt = dt;
		boolean skipcol = false;
		Collision nextCollision = null;
		double nextcollisiontime = Double.MAX_VALUE;
		Collision collisionToHandle = null;
		if(!collisionsorder.isEmpty()){
			nextCollision = collisionsorder.first();
			nextcollisiontime = nextCollision.getDelay();
		}
		else{
			skipcol = true;
		}
		while(!(skipcol) && (Util.fuzzyLessThanOrEqualTo(nextcollisiontime, dtt) || Util.fuzzyLessThanOrEqualTo(nextcollisiontime, 0))){
			// System.out.println("nextcollisiontime " + nextcollisiontime + " dtt " + dtt);
			collisionToHandle = nextCollision;
			evolvewithoutcollisions(nextcollisiontime, collisionListener, false);
			handleCollision(collisionToHandle, collisionListener);
			dtt -= nextcollisiontime;
			if(!collisionsorder.isEmpty()){
				nextCollision = collisionsorder.first();
				nextcollisiontime = nextCollision.getDelay();
			}
			else{
				skipcol = true;
			}
		}
		// if(!collisionsorder.isEmpty()) System.out.println("nextcollisiontimeafterwhile " + collisionsorder.first().getDelay() + " nextline shouldbe: " + (collisionsorder.first().getDelay() - dtt));
		evolvewithoutcollisions(dtt, collisionListener, true);
	}
	
	private void handleCollision(Collision collision, CollisionListener collisionListener){
		System.out.println("length of bullet set: " + this.bullets.size());
		Flying flying1 = collision.getFlying1();
		Flying flying2 = collision.getFlying2();
		System.out.println("collision between " + flying1.toString() + " and " + flying2.toString());
		Flying.collide(flying1, flying2);
		collisionsorder.remove(collision);
		collisions.remove(flying1);
		collisions.remove(flying2);
		if(this.contains(flying2))recalibrate(flying2);
		if(this.contains(flying1))recalibrate(flying1);
	}
	
	private void evolvewithoutcollisions(double dt, CollisionListener collisionListener, boolean thrustingenabled){
		double dtt = dt;
		if(dtt < 0) dtt = 0;
			for(Ship ship : ships){
				ship.move(dtt, collisionListener);
			}
		for(Asteroid asteroid : asteroids){
			asteroid.move(dtt, collisionListener);
		}
		try{
			for(Bullet bullet : bullets){
				bullet.move(dtt, collisionListener);
			}
		}
		catch(ConcurrentModificationException e){}
		if(thrustingenabled){
			for(Ship ship : ships){
				if(ship.isThrusterActive()){
					ship.thrust(ship.getAcceleration());
					recalibrate(ship);
				}
			}
		}
		for(Collision toadjust : collisionsorder){
			toadjust.shortenDelayWith(dtt);
		}
	}
	
	

}
