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
 * @Invar The radius will always be valid, that is: bigger than the LOWERBOUND_RADIUS
 * 			| isValidRadius(this.getRadius())
 * @Invar The LOWERBOUND_RADIUS will always be bigger than zero
 * 			| LOWERBOUND_RADIUS > 0
 * @Invar The coordinates will always be valid
 * 			| isValidCoordinate(this.getXCoordinate()) && isValidCoordinate(this.getYCoordinate())
 * @Invar The total speed will always be lower than or equal to the max allowable speed at that moment
 * 			| this.getSpeed() < this.getMaxSpeed()
 * TODO: Other Change invars for better compatibility
 * TODO: comment constructors
 */

public class Ship extends Flying implements IShip {
	
	// Constructors, all implemented defensively
	
	//TODO: comment constructors!
	
	/**
	 * The basic constructor of the ship, the position, speed and direction are undefined,
	 * only the radius is implemented.
	 * @param radius
	 * @throws IllegalArgumentException
	 * 			The given radius must be valid.
	 * 			|!isValidRadius(radius)
	 */
	public Ship (double radius) throws IllegalArgumentException{
		this(0, 0, 0, 0, radius, 0, 1);
	}
	
	/**
	 * 
	 * @throws IllegalArgumentException
	 */
	public Ship() throws IllegalArgumentException{
		this(1);
	}
	
	public Ship(double XPosition, double YPosition, double XSpeed, double YSpeed, double radius, double angle, double mass) throws IllegalArgumentException{
		this(XPosition, YPosition, XSpeed, YSpeed, radius, angle, null, mass);
	}
	
	public Ship(double XPosition, double YPosition, double XSpeed, double YSpeed, double radius, double angle){
		this(XPosition, YPosition, XSpeed, YSpeed, radius, angle, null, 1);
	}
	
	
	/**
	 * 
	 * @param XPosition
	 * @param YPosition
	 * @param XSpeed
	 * @param YSpeed
	 * @param radius
	 * @param angle
	 * @throws IllegalArgumentException
	 * 			|We throw an exception if either the radius, one of the coordinates or one of the speed
	 * 				components does not satisfy our conditions, e.g. the radius has to be a defined double
	 * 				bigger than the lower bound of the radius (which can be accessed through the 
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
		
		if(!isValidAcceleration(acceleration)) return;
		try {
		this.setSpeeds(Vector.add(this.getSpeeds(), new Vector(this.getAngle(), acceleration, World.LIGHTSPEED)));
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
	 * This method is used to toggle whether the 
	 */
	public void toggleThrust(){
		setThrusting(!this.thrusting);
	}
	
	public double getThrustForce(){
		return engineThrustForce;
	}
	
	private final double engineThrustForce = STANDARD_THRUSTER_THRUSTFORCE;
	
	public final static double STANDARD_THRUSTER_THRUSTFORCE = 1.1e8;
	
	public double getAcceleration() {
		return this.getThrustForce() / this.getMass();
	}
		
	/**
	 * TODO: comment
	 */
	public void fireBullet() {
		Bullet shotbullet = new Bullet(this, 3, 250);
		this.getWorld().shoot(this, shotbullet);
		aliveBullets.add(shotbullet);
	}
	
	private Set<Bullet> aliveBullets = new HashSet<Bullet>();
	
	public void bulletDies(Bullet bullet) throws IllegalArgumentException{
		if (!aliveBullets.contains(bullet)) throw new IllegalArgumentException();
		aliveBullets.remove(bullet);
	}
	
	@Override
	public double getMassDensity() {
		return 0;
	}

	@Override
	public void addToWorld(World world) {
		if(! this.isAvailableToAdd()) throw new IllegalStateException();
		world.add(this);
	}

	@Override
	@Immutable
	@Basic
	public double getMass() {
		return this.mass;
	}
	
	private final double mass;
	
	


	@Override
	protected void collideWithShip(Ship ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void collideWithBullet(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void collideWithAsteroid(Asteroid asteroid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void collideWithBoundary() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void terminate() {
		// TODO Auto-generated method stub
		
	}

	
	
}

