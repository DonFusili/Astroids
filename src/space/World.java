package space;

import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import extraUtil.Util;

import ship.*;


/**
 * This is the world in which Flying objects reside. A world is a rectangle with a width and a length. These are positive numbers within the double range.
 * 
 * 
 * @author Joost Verplancke
 * @version 1.0
 * @Invar	isValidDimension(this.getHeight());
 * @Invar	isValidDimension(this.getWidth());
 * @Invar	All Flying Objects mentioned in a collision in this object are also present in this object
 * 			| for(collision : collisionsorder U collisions.values())
 * 				this.contains(collision.getFlying1()) && this.contains(collision.getFlying2())
 */
public class World {

	public static final double LIGHTSPEED = 300000;
	
	
	/**
	 * In case of doubt, you can call this constructor which makes a valid World with dimensions that conveniently fit my screen's dimensions.
	 */
	public World() {
		this(1366, 768);
	}
	
	/**
	 * The constructor for a new World in which you want to play.
	 * @param width
	 * @param height
	 * @throws IllegalArgumentException ...
	 * 			| !(isValidDimension(width) && isValidDimension(height))
	 * @post ...
	 * 			| new.getWidth() == width && new.getHeight() == height
	 */
	public World(double width, double height) throws IllegalArgumentException{
		if(!isValidDimension(width) || !isValidDimension(height)) throw new IllegalArgumentException();
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Checker for the dimensions 
	 * @param dimension
	 * @return dimension > 0
	 */
	public static boolean isValidDimension(double dimension){
		return dimension > 0;
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
	
	/**
	 * Getter for the copy of the collection of ships in this world. Keep in mind that removing ships from the returned collection does not remove them from this world
	 * @return new Set<Ship>(this.ships)
	 */
	@Basic
	public Set<Ship> getShips(){
		return new HashSet<Ship>(ships);
	}
	
	private Set<Ship> ships = new HashSet<Ship>();

	/**
	 * Getter for the copy of the collection of asteroids in this world. Keep in mind that removing asteroids from the returned collection does not remove them from this world
	 * @return new Set<Asteroid>(this.asteroids)
	 */
	@Basic
	public Set<Asteroid> getAsteroids() {
		return new HashSet<Asteroid>(asteroids);
	}
	
	private Set<Asteroid> asteroids = new HashSet<Asteroid>();

	/**
	 * Getter for the copy of the collection of bullets in this world. Keep in mind that removing bullets from the returned collection does not remove them from this world
	 * @return new Set<Bullet>(this.bullets)
	 */
	public Set<Bullet> getBullets() {
		return new HashSet<Bullet>(bullets);
	}
	
	private Set<Bullet> bullets = new HashSet<Bullet>();

	/**
	 * Add a ship to this world, overloaded with add(asteroid).
	 * @param ship
	 * @throws IllegalArgumentException ...
	 * 			| !isValidShipToAdd(ship)
	 * @post ...
	 * 			| new.contains(ship)
	 * @post ...
	 * 			| ship.getWorld() == this
	 * @effect	recalibrate(ship)
	 */
	public void add(Ship ship) throws IllegalArgumentException {
		if(!isValidShipToAdd(ship)) throw new IllegalArgumentException();
		ship.setWorld(this);
		ships.add(ship);		
		recalibrate(ship);
	}
	
	/**
	 * Add a ship to this world, overloaded with add(ship)
	 * @param asteroid
	 * @throws IllegalArgumentException ...
	 * 			| !isValidAsteroidToAdd(asteroid)
	 * @post ...
	 * 			| new.contains(asteroid)
	 * @post ...
	 * 			| asteroid.getWorld() == this
	 * @effect recalibrate(asteroid)
	 */
	public void add(Asteroid asteroid) throws IllegalArgumentException {
		if(!isValidAsteroidToAdd(asteroid)) throw new IllegalArgumentException();
		asteroid.setWorld(this);
		asteroids.add(asteroid);
		recalibrate(asteroid);
	}
	
	/**
	 * Return whether the ship is available to add to this world
	 * @param ship
	 * @return ..
	 * 			| result = (ship != null && ship.isAvailableToAdd())
	 */
	public static boolean isValidShipToAdd(Ship ship){
		return ship != null && ship.isAvailableToAdd();
	}
	
	/**
	 * Return whether the asteroid is available to add to this world
	 * @param asteroid
	 * @return ...
	 * 			| result = (asteroid != null && asteroid.isAvailableToAdd())
	 */
	public static boolean isValidAsteroidToAdd(Flying asteroid){
		return asteroid != null && asteroid.isAvailableToAdd();
	}

	/**
	 * return whether the given Flying is to be found in this world.
	 * @param flying
	 * @return ...
	 * 			| this.contains((Ship) flying) || this.contains((Bullet) flying) || this.contains((Asteroid) flying)
	 */
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
	
	/**
	 * Return whether the given Asteroid is to be found in this world.
	 * @param asteroid
	 * @return ...
	 * 			| result = this.asteroids.contains(asteroid)
	 */
	public boolean contains(Asteroid asteroid){
		return asteroids.contains(asteroid);
	}
	
	/**
	 * Return whether the given Ship is to be found in this world
	 * @param ship
	 * @return ...
	 * 			| result = this.ships.contains(asteroid)
	 */
	public boolean contains(Ship ship){
		return ships.contains(ship);
	}
	
	/**
	 * Return whether the given Bullet is to be found in this world.
	 * @param bullet
	 * @return ...
	 * 			| result = this.bullets.contains(bullet)
	 */
	public boolean contains(Bullet bullet){
		return bullets.contains(bullet);
	}
	

	/**
	 * 
	 * @param asteroid
	 * @throws IllegalArgumentException ...
	 * 			| !this.contains(asteroid)
	 * @post ...
	 * 			| !new.contains(asteroid)
	 * @post ...
	 * 			| new.collisions.get(asteroid) == null
	 * @post ...
	 * 			| (new asteroid).getWorld() == null
	 * @effect ...
	 * 			| if(this.collisions.get(asteroid).getFlying1() != asteroid && new.contains(this.collisions.get(asteroid).getFlying1())) recalibrate(this.collisions.get(asteroid).getFlying1())
	 * @effect ...
	 * 			| if(this.collisions.get(asteroid).getFlying2() != asteroid && new.contains(this.collisions.get(asteroid).getFlying2())) recalibrate(this.collisions.get(asteroid).getFlying2())
	 */
	public void remove(Asteroid asteroid) throws IllegalArgumentException{
		if(!this.asteroids.contains(asteroid)) throw new IllegalArgumentException();
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
	
	/**
	 * 
	 * @param asteroid
	 * @throws IllegalArgumentException ...
	 * 			| !this.contains(ship)
	 * @post ...
	 * 			| !new.contains(ship)
	 * @post ...
	 * 			| new.collisions.get(ship) == null
	 * @post ...
	 * 			| (new ship).getWorld() == null
	 * @effect ...
	 * 			| if(this.collisions.get(ship).getFlying1() != asteroid && new.contains(this.collisions.get(ship).getFlying1())) recalibrate(this.collisions.get(ship).getFlying1())
	 * @effect ...
	 * 			| if(this.collisions.get(ship).getFlying2() != asteroid && new.contains(this.collisions.get(ship).getFlying2())) recalibrate(this.collisions.get(ship).getFlying2())
	 */
	public void remove(Ship ship) throws IllegalArgumentException{
		if(!this.ships.contains(ship)) throw new IllegalArgumentException();
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
	
	/**
	 * 
	 * @param asteroid
	 * @throws IllegalArgumentException ...
	 * 			| !this.contains(bullet)
	 * @post ...
	 * 			| !new.contains(bullet)
	 * @post ...
	 * 			| new.collisions.get(bullet) == null
	 * @post ...
	 * 			| (new bullet).getWorld() == null
	 * @effect ...
	 * 			| if(this.collisions.get(bullet).getFlying1() != asteroid && new.contains(this.collisions.get(bullet).getFlying1())) recalibrate(this.collisions.get(bullet).getFlying1())
	 * @effect ...
	 * 			| if(this.collisions.get(bullet).getFlying2() != asteroid && new.contains(this.collisions.get(bullet).getFlying2())) recalibrate(this.collisions.get(bullet).getFlying2())
	 */
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

	
	/**
	 * 
	 * @param ship
	 * @param shotbullet
	 * @throws IllegalArgumentException ...
	 * 			| !this.contains(ship)
	 * @post ...
	 * 			| shotbullet.getWorld() == this
	 * @post ...
	 * 			| new.contains(shotbullet)
	 * @effect ...
	 * 			| recalibrate(shotbullet)
	 */
	public void shoot(Ship ship, Bullet shotbullet) throws IllegalArgumentException {
		if(!ships.contains(ship)) throw new IllegalArgumentException();
		shotbullet.setWorld(this);
		bullets.add(shotbullet);
		System.out.println("Bullet " + shotbullet.toString() + " launched");
		recalibrate(shotbullet);
	}
	

	/**
	 * Recalibrate the ship so that its collision is the first important one on the cards for himself.
	 * @param flying
	 * @throws IllegalArgumentException ...
	 * 			| !this.contains(flying)
	 * @post ...
	 * 			| for(flyingit : this.ships U this.asteroids U this.bullets) {
	 * 				flyingit == flying ||
	 * 				this.collisions.get(flying).getDelay() <= Flying.getTimeToCollision(flying, flyingit) ||
	 * 				this.collisions.get(flyingit).getDelay() <= Flying.getTimeToCollision(flying, flyingit) }
	 * @post ...
	 * 			| this.collisionsorder.contains(this.collisions.get(flying))
	 * 				
	 */
	public void recalibrate(Flying flying) throws IllegalArgumentException{
		if(!this.contains(flying)) throw new IllegalArgumentException();
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

	
	/**
	 * 
	 * @param dt
	 * @param collisionListener
	 * @effect ...
	 * 			| for(collision : this.collisionsorder){
	 * 				if(collision.getDelay() < dt) handleCollision(collision, collisionListener) }
	 * @effect ...
	 * 			| evolvewithoutcollision(dt, true/false)
	 */
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
		//System.out.println("length of bullet set: " + this.bullets.size());
		Flying flying1 = collision.getFlying1();
		Flying flying2 = collision.getFlying2();
		//System.out.println("collision between " + flying1.toString() + " and " + flying2.toString());
		listenToCollision(flying1, flying2, collisionListener);
		Flying.collide(flying1, flying2);
		collisionsorder.remove(collision);
		collisions.remove(flying1);
		collisions.remove(flying2);
		if(this.contains(flying2))recalibrate(flying2);
		if(this.contains(flying1))recalibrate(flying1);
	}
	
	private void listenToCollision(Flying flying1, Flying flying2, CollisionListener collisionListener){
		double foy = flying1.getYCoordinate();
		double fty = flying2.getYCoordinate();
		double fox = flying1.getXCoordinate();
		double ftx = flying2.getXCoordinate();
		double thales = (flying1.getRadius()) / (flying1.getRadius() + flying2.getRadius());
		double cpo = fox + thales * (ftx - fox);
		double cpt = foy + thales * (fty - foy);
		collisionListener.objectCollision(flying1, flying2, cpo, cpt);
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
