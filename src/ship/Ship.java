package ship;

import asteroids.IShip;
import be.kuleuven.cs.som.annotate.*;
import java.lang.Math;
import java.util.HashSet;
import java.util.Set;

import space.*;
import extraUtil.*;


/**
 * 
 * @author Deevid De Meyer and Joost Verplancke
 * @Version 2.0
 * @Invar The LOWERBOUND_RADIUS will always be bigger than zero
 * 			| LOWERBOUND_RADIUS > 0
 * TODO: comment constructors
 */

public class Ship extends Flying implements IShip {
	
	// Constructors, all implemented defensively
	
	//TODO: comment constructors!
	

	
	/**
	 * Constructor for ship, defines the position, speed, radius, angle/orientation and mass of the ship.
	 * @param XPosition
	 * 			The starting horizontal position
	 * @param YPosition
	 * 			The starting vertical position
	 * @param XSpeed
	 * 			The starting horizontal speed
	 * @param YSpeed
	 * 			The starting vertical speed
	 * @param radius
	 * 			The radius of the ship
	 * @param angle
	 * 			The starting angle/orientation of the ship
	 * @throws IllegalArgumentException
	 * TODO
	 * 			|We throw an exception if either the radius, one of the coordinates, one of the speed
	 * 				components, the angle or the mass does not satisfy our conditions, e.g. the radius has to be a defined double
	 * 				bigger than the lower bound of the radius
	 */
	public Ship(double XPosition, double YPosition, double XSpeed, double YSpeed, double radius, double angle, double mass) throws IllegalArgumentException{
		this(XPosition, YPosition, XSpeed, YSpeed, radius, angle, null, mass);
	}

	
	/**
	 * TODO
	 * Constructor for ship, defines the position, speed, radius, angle/orientation, world and mass of the ship.
	 * @param XPosition
	 * 			The starting horizontal position
	 * @param YPosition
	 * 			The starting vertical position
	 * @param XSpeed
	 * 			The starting horizontal speed
	 * @param YSpeed
	 * 			The starting vertical speed
	 * @param radius
	 * 			The radius of the ship
	 * @param angle
	 * 			The starting angle/orientation of the ship
	 * @param world
	 * 			The game world the ship is in
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setXCoordinate(XPosition)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setYCoordinate(YPosition)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setXSpeed(Xspeed)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setYSpeed(Yspeed)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setRadius(radius)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setAngle(angle)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setWorld(world)
	 * @post The new value for the horizontal coordinate is equal to the given inital horizontal coordinate
	 * 		| setMass(mass)
	 * @post The value for thrusting is set to false
	 * 		
	 * @throws IllegalArgumentException
	 * 		|We throw an exception if either the radius, one of the coordinates or one of the speed
	 * 		components does not satisfy our conditions, e.g. the radius has to be a defined double
	 * 		bigger than the lower bound of the radius (which can be accessed through the 
	 */
	public Ship(double XPosition, double YPosition, double XSpeed, double YSpeed, double radius, double angle, World world, double mass) throws IllegalArgumentException{
		super(radius, new Vector(XPosition, YPosition), new Vector(XSpeed, YSpeed), Flyer.SHIP);		
		if(!isValidWorld(world)) throw new IllegalArgumentException();
		this.setAngle(angle);
		this.thrusting = false;
		this.mass = mass;
	}
	
	
		
//	/**
//	 * 
//	 * @param speed
//	 * @post 	If the proposed speed for MAX_SPEED is valid, we adjust the maximum speed to that value, otherwise we fall back to the speed of light.
//	 * 			|if(isValidMaxSpeed(speed))
//	 * 				then (new this).getMaxSpeed() == speed
//	 * 			 else then (new this).getMaxSpeed() == LIGHTSPEED
//	 * @post	If the change in max speed would mean that our current speed exceeds the max speed, we fall back to max speed
//	 * 			|if(this.getSpeed >= (new this).getMaxSpeed())
//	 * 				then @Eff changeToMaxSpeed()
//	 */
//	public void setMaxSpeed(double speed) {
//		if(isValidMaxSpeed(speed)) this.MAX_SPEED = speed;
//		else MAX_SPEED = LIGHTSPEED;
//		if(Util.fuzzyLessThanOrEqualTo(this.getSpeed(), this.getMaxSpeed())) this.changeToMaxSpeed();
//	}
	
	
	
	
	/**
	 * Radii for ships are not free for the choosing
	 * @param radius
	 * 			Radius of the ship
	 * @return 	whether the radius is valid, by which we mean we check if the radius is a number, isn't null and is bigger than the set static lowerbound radius.
	 * 			| result = radius != null && radius != NaN && radius >= LOWERBOUND_RADIUS
	 */
	public static boolean isValidRadius(double radius){
		return (Double)radius != null && (Double)radius != Double.NaN && radius >= LOWERBOUND_RADIUS;
	}
	
	public static final double LOWERBOUND_RADIUS = 10;

	// Acceleration and Mass
	
	/**
	 * 	The getter for thrusting.
	 * @return thrusting
	 */
	public boolean isThrusterActive(){
		return thrusting;
	}
	
	/**
	 * 	The setter for thrusting.
	 * @param thrusting
	 * 			Boolean defining if the ship is thrusting or not
	 * @post sets thrusting to the given value
	 * 		| new.thrusting = thrusting
	 */
	public void setThrusting(boolean thrusting){
		this.thrusting = thrusting;
	}
	
	private boolean thrusting;
	
	
	// Controlling the ship
	
	
	/**
	 * This method is used to accelerate in the current direction the ship is facing. This method is implemented 
	 * totally. We check the interval and possible speeds a few times and depending on our findings, we 
	 * discard the changes or apply them according to the given parameters.
	 * This method is implemented totally.
	 * @param interval
	 * @Post if the acceleration isn't valid, nothing happens
	 * 			| if(!isValidAcceleration(acceleration))
	 * 				then
	 * @Post if the acceleration is valid but the new speed would exceed the maximum allowable speed,
	 * 			the speed in both the horizontal and vertical direction get adjusted so the ship moves at the maximum allowable speed
	 * 			| if( (this.getXSpeed() + usedAcceleration * Math.cos(this.getAngle()))² 
	 * 					+ (this.getYSpeed() + usedAcceleration * Math.sin(this.getAngle()))² > getMaxSpeed())
	 * 					then 
	 * 						 @Eff setXSpeed(getMaxSpeed() * Math.cos(this.getAngle()))
	 * 						 @Eff setYSpeed(getMaxSpeed() * Math.sin(this.getAngle()))
	 * @Post if both the acceleration and the would-be speed are viable, we accelerate in the direction the ship is currently facing
	 * 				| else
	 * 					@Eff this.setXSpeed(this.getXSpeed() + usedAcceleration * Math.cos(this.getAngle()))
	 * 					@Eff this.setYSpeed(this.getYSpeed() + usedAcceleration * Math.sin(this.getAngle()))
	 */
	public void thrust(double acceleration){
//		//If our acceleration is not acceptable, we don't have to do a thing.
//		if(!isValidAcceleration(acceleration)) return;
//		double newXSpeed = this.getXSpeed() + acceleration * Math.cos(this.getAngle());
//		double newYSpeed = this.getYSpeed() + acceleration * Math.sin(this.getAngle());
//		// If the acceleration would make us move faster than the max allowable speed, we switch to the max speed in the direction the ship is facing at the moment.
//		if(Util.fuzzyLessThanOrEqualTo(getMaxSpeed() * getMaxSpeed(), newXSpeed * newXSpeed + newYSpeed * newYSpeed)){
//			newXSpeed = getMaxSpeed() * Math.cos(this.getAngle());
//			newYSpeed = getMaxSpeed() * Math.sin(this.getAngle());
//		}
//		/**
//		 * circumvent the fact that our setter checks whether we exceed the maximum allowable speed
//		 * by setting the y speed to zero first
//		 */
//		this.setYSpeed(0);
//		this.setXSpeed(newXSpeed);
//		this.setYSpeed(newYSpeed);
		if(!isValidAcceleration(acceleration)){ return;}
		try {
		this.setSpeeds(Vector.add(this.getSpeeds(), new Vector(this.getAngle(), acceleration, Double.MAX_VALUE)));
		}
		catch(IllegalArgumentException e){
			return;
		}
		
	}

	
	/**
	 * This method checks whether a given acceleration is acceptable for use in this class. Accelerations
	 * must be numbers representable in doubles and have to be bigger than zero.
	 * @param acceleration
	 * @return whether the given acceleration is a number bigger than zero
	 * 			| result = !(Double.isNaN(acceleration)) && !(acceleration < 0)
	 */
	public static boolean isValidAcceleration(double acceleration){
		return !(acceleration < 0);
	}
	
	/**
	 * This method is used to turn the ship relatively to its current position.
	 * This method is implemented nominally
	 * @param angle
	 * 			Angle the ship has to turn relative to it's current orientation
	 * @Pre The angle has to be between -Pi and Pi
	 * 		| -Math.PI < angle =< Math.PI
	 * @post The new angle gets normalized to a value between 0 and 2*Pi and then adjusted
	 * 		| if((this.getAngle() + angle) < 0
	 * 			then (new this).getAngle() == (this.getAngle() + angle + 2 * Math.PI)
	 * 		|if((this.getAngle() + angle == 2*Math.PI) || this.getAngle() + angle == 0)
	 * 				then (new this).getAngle() == 0
	 * 		|if((this.getAngle() + angle > 2*Math.PI))
	 * 			then (new this).getAngle() == (this.getAngle() + angle - 2*Math.PI)
	 * 		|if((this.getAngle() + angle) < 2*Math.Pi && (this.getAngle() + angle) > 0)
	 * 			then (new this.getAngle() == (this.getAngle() + angle)
	 * 				
	 */
	public void turn(double angle){
		assert Util.fuzzyLessThanOrEqualTo(-1*Math.PI, angle);
		assert !Util.fuzzyEquals(-1*Math.PI, angle);
		assert Util.fuzzyLessThanOrEqualTo(angle, Math.PI);
		double newAngle = this.getAngle() + angle;
		if(Util.fuzzyEquals(newAngle,2*Math.PI) || Util.fuzzyEquals(newAngle, 0)) newAngle = 0;
		// Due to the preconditions, we only have to adjust the angle by 2*Pi at the most.
		if(Util.fuzzyLessThanOrEqualTo(2*Math.PI, newAngle)) newAngle -= 2*Math.PI;
		if(newAngle < 0) newAngle += 2*Math.PI;
		this.setAngle(newAngle);
				
	}
	
	/**
	 * This method is used to toggle whether the ship is thrusting or not
	 * @post if the ship was thrusting it will no longer thrust, and vice versa
	 * 		| new.thrusting != this.thrusting
	 */
	public void toggleThrust(){
		setThrusting(!this.thrusting);
	}
	
	/**
	 * 	Returns the engine thrust force.
	 * @return thrusting
	 */
	public double getThrustForce(){
		return engineThrustForce;
	}
	
	private final double engineThrustForce = WORKABLE_THRUSTFORCE;
	
	public final static double STANDARD_THRUSTER_THRUSTFORCE = 1.1e8;
	public final static double WORKABLE_THRUSTFORCE = 9000000000000000.0;
	
	/**
	 * 	Method for returning the acceleration, calculated by dividing the thrust force with the mass of the ship 
	 * @return this.getThrustForce() / this.getMass()
	 */
	public double getAcceleration() {
		return this.getThrustForce() / this.getMass();
	}
		
	/**
	 * Fires a bullet by calling the bullet constructor and by adding it to the world and list of shot bullets
	 * @effect Creates a bullet
	 * 		| Bullet(this, 3, 250)
	 * @effect Adds the bullet to the world of the ship
	 * 		| TODO
	 * @post Adds the bullet to the hashset of alive bullets
	 * 		| aliveBullets.contains(shotbullet)
	 * 
	 */
	public void fireBullet() {
		Bullet shotbullet = new Bullet(this, 3, 250);
		this.getWorld().shoot(this, shotbullet);
	}
	
	/**
	 * Unneeded method?
	 * TODO
	 */
	@Override
	public double getMassDensity() {
		return 0;
	}

	/**
	 * Adds this ship to a game world
	 * @param world
	 * 			Defines which world the ship is added to
	 * @post this.getWorld() == world
	 * @throws IllegalStateException
	 * 			The ship is already assigned to a world
	 * 		| !this.isAvailableToAdd()
	 */
	@Override
	public void addToWorld(World world) {
		if(! this.isAvailableToAdd()) throw new IllegalStateException();
		world.add(this);
	}

	/**
	 * Getter for the mass of a ship
	 * @return mass
	 */
	@Override
	@Immutable
	@Basic
	public double getMass() {
		return this.mass;
	}
	
	private final double mass;
	
	

	/**
	 * Checks if another ship is colliding with this one
	 * @param ship
	 * 		The other ship to check collision with
	 * TODO
	 */
	@Override
	protected void collideWithShip(Ship ship) {
		this.bounceOf(ship);
	}
	
	/**
	 * Checks if a given bullet is colliding with the ship
	 * @param bullet
	 * 		The bullet to check collision with
	 * TODO
	 */
	@Override
	protected void collideWithBullet(Bullet bullet) {
		if(bullet.getShooter() !=this){
			System.out.println("collided with bullet, not one of own bullets");
			this.die();
		}
		else{
			System.out.println("collided with bullet, one of own bullets");
		}
	}
	
	/**
	 * Checks if a given asteroid is colliding with the ship
	 * @param asteroid
	 * 		The asteroid to check collision with
	 * TODO
	 */
	@Override
	protected void collideWithAsteroid(Asteroid asteroid) {
		this.die();
	}

	@Override
	protected void terminate() {
		// remove all links to world
		this.getWorld().remove(this);
	}

	/**
	 * Let the ship die by terminating it
	 * @effect this.terminate()
	 */
	@Override
	public void die() {
		this.terminate();
		this.dead = true;
	}

	
	
}
