package asteroids;

import be.kuleuven.cs.som.annotate.*;
import java.lang.Math;

/**
 * 
 * @author Deevid De Meyer and Joost Verplancke
 * @Version 1.0
 * @Invar The radius will always be valid, that is: bigger than the LOWERBOUND_RADIUS
 * 			| this.getRadius() <= LOWERBOUND_RADIUS
 * @Invar The LOWERBOUND_RADIUS will always be bigger than zero
 * 			| LOWERBOUND_RADIUS > 0
 * TODO: Andere Invars?
 * TODO: User Library? 			
 *
 */

public class Ship implements IShip {
	
	public Ship (double radius) throws IllegalArgumentException{
		if(!isValidRadius(radius)) throw new IllegalArgumentException();
		this.radius = radius;
	}
	
	

	/**
	 * @param coordinate
	 * @post If the given coordinate is valid, we make the new x coordinate equal to the given coordinate
	 * 		| if(isValidCoordinate(coordinate))
	 * 			then new.X == coordinate
	 * @throws IllegalArgumentException
	 * 		| If the given coordinate is not valid, we throw an exception for the caller to handle.
	 * 		| if(!isValidCoordinate(coordinate))
	 */
	public void setXCoordinate(double coordinate) throws IllegalArgumentException {
		if(!isValidCoordinate(coordinate)) throw new IllegalArgumentException();
		this.x = coordinate;
	}
	
	/**
	 * 
	 * @param distance
	 * @throws IllegalArgumentException
	 * @Post Changes the x coordinate by the given value 
	 * 		@Eff setXCoordinate(this.getXCoordinate() + distance)
	 */
	public void relativeXDisplacement(double distance) throws IllegalArgumentException {
		double newX = this.getXCoordinate() + distance;
		setXCoordinate(newX);
	}
	
	/**
	 * 
	 * @return The X Coordinate of this ship
	 * 			| result = this.X;
	 */
	@Basic
	public double getXCoordinate() {
		return this.x;
	}
	
	private double x;
	
	/**
	 * This method is used to adjust the Y coordinate appropriately
	 * @param coordinate
	 * @post If the given coordinate is valid, we make the new Y coordinate equal to the given coordinate
	 * 		| if(isValidCoordinate(coordinate))
	 * 			then new.Y == coordinate
	 * @throws IllegalArgumentException
	 * 		| If the given coordinate is not valid, we throw an exception for the caller to handle.
	 * 		| if(!isValidCoordinate(coordinate))
	 */
	public void setYCoordinate(double coordinate) throws IllegalArgumentException {
		if(!isValidCoordinate(coordinate)) throw new IllegalArgumentException();
		this.y = coordinate;
	}
	
	/**
	 * 
	 * @param distance
	 * @throws IllegalArgumentException
	 * @Post Changes the y coordinate by the given value 
	 * 		@Eff setYCoordinate(this.getYCoordinate() + distance)
	 */
	public void relativeYDisplacement(double distance) throws IllegalArgumentException {
		double newY = this.getYCoordinate() + distance;
		setYCoordinate(newY);
	}
	
	/**
	 * 
	 * @return The Y Coordinate of this ship
	 * 			| result = this.Y;
	 */
	@Basic
	public double getYCoordinate() {
		return this.y;
	}
	
	private double y;
	
	
	/**
	 * 
	 * @param coordinate
	 * @return If the given coordinate lies between double.NEGATIVE_INFINITY and double.POSITIVE_INFINITY (both included), we return true, otherwise we return false
	 * 			| result = !(coordinate > Double.MAX_VALUE || coordinate < Double.MIN_VALUE || coordinate == Double.NaN);
	 */
	public static boolean isValidCoordinate(double coordinate) {
		return !(coordinate > Double.MAX_VALUE || coordinate < Double.MIN_VALUE || coordinate == Double.NaN);
	}
	
	/**
	 * This method is used to set the horizontal speed appropriately. If the proposed speed is not valid, we do not change the speed.
 * @param speed
 * @post If the proposed speed is valid, we adjust the speed, otherwise, we don't do a thing.
 * 		 | if(isValidXSpeed(speed))
 * 			then (new this).getXSpeed() == speed
 * 		  else
 * 			then (new this).getXSpeed() == this.getXSpeed()
 */
	public void setXSpeed(double speed) {
	if(isValidXSpeed(speed)) this.xSpeed = speed;
	}
	
	/**
	 * This method is used to change the horizontal speed
	 * @param difference
	 * @post Changes the horizontal speed by a given value 
	 * 		@Eff setXSpeed(this.getXSpeed() + difference)
	 */
	public void changeXSpeed(double difference) {
		double newSpeed = this.getXSpeed() + difference;
		setXSpeed(newSpeed);
	}
	
	@Basic
	public double getXSpeed() {
		return this.xSpeed;
	}
	
	private double xSpeed;
	
	
	/**
	 * Before we adjust the horizontal speed (that is: the speed projected onto the x coordinate), we check if the proposed new speed in that direction is valid
	 * to do this, we check if the total speed would exceed the maximum speed of this ship.
	 * @param speed
	 * @return 
	 */
	public boolean isValidXSpeed(double speed) {
		double a = speed*speed + this.getYSpeed()*this.getYSpeed();
		return a < this.getMaxSpeed();
	}	
	
	
	/**
	 * This method is used to set the vertical speed appropriately. If the proposed speed is not valid, we do not change the speed.
	 * @param speed
	 * @post If the proposed speed is valid, we adjust the speed, otherwise, we don't do a thing.
	 * 		 | if(isValidYSpeed(speed))
	 * 			then (new this).getYSpeed() == speed
	 * 		  else
	 * 			then (new this).getYSpeed() == this.getYSpeed()
	 */
	public void setYSpeed(double speed) {
		if(isValidYSpeed(speed)) this.ySpeed = speed;
	}
	
	/**
	 * This method is used to change the vertical speed
	 * @post Changes the vertical speed by a given value
	 * 		@Eff setYSpeed(this.getYSpeed() + difference)
	 * @param difference
	 */
	public void changeYSpeed(double difference) {
		double newSpeed = this.getYSpeed() + difference;
		setYSpeed(newSpeed);
	}
	
	@Basic
	public double getYSpeed() {
		return this.ySpeed;
	}
	
	private double ySpeed;
	
	/**
	 * Before we adjust the vertical speed (that is: the speed projected onto the y coordinate), we check if the proposed new speed in that direction is valid
	 * to do this, we check if the total speed would exceed the maximum speed of this ship.
	 * @param speed
	 * @return 
	 */
	public boolean isValidYSpeed(double speed) {
		double a = speed*speed + this.getXSpeed()*this.getXSpeed();
		return a < this.getMaxSpeed();
	}		
	
	@Basic
	public double getMaxSpeed() {
		return this.MAX_SPEED;
	
	}
	
	
	/**
	 * 
	 * @param speed
	 * @post 	If the proposed speed for MAX_SPEED is valid, we adjust the maximum speed to that value, otherwise we fall back to the speed of light.
	 * 			|if(isValidMasSpeed(speed))
	 * 				then (new this).getMaxSpeed() == speed
	 * 			 else then (new this).getMaxSpeed() == LIGHTSPEED
	 */
	public void setMaxSpeed(double speed) {
		if(isValidMaxSpeed(speed)) this.MAX_SPEED = speed;
		else MAX_SPEED = LIGHTSPEED;
	}
	
	
	/**
	 * We want to be able to adapt our maximum speed, but want to check if the proposed speed is valid. Speeds must stay below the speed of light and have to be a number.
	 * @param speed
	 * @return !(speed == Double.NaN() || Math.Abs(speed) > LIGHTSPEED)
	 */
	public static boolean isValidMaxSpeed(double speed) {
		return !(speed == Double.NaN || Math.abs(speed) > LIGHTSPEED);
	}
	
	public static final double LIGHTSPEED = 300000;
	
	private double MAX_SPEED;
	
	
	/**
	 * 
	 * @param angle
	 * @Pre		The angle needs to be between 0 and 2*Pi
	 * 			0 =< angle < 2*Math.PI
	 * @Post	Sets the angle to the given value
	 * 			| new.getAngle() == angle
	 */
	public void setAngle(double angle) {
		assert angle >= 0;
		assert angle < 2 * Math.PI;
		this.angle = angle;
	}
	
	private double angle;
	
	@Basic
	public double getAngle() {
		return this.angle;
	}
	
	
	/**
	 * This method is used to turn the ship relatively to its current position.
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
	public void relativeTurn(double angle){
		assert angle > -1*Math.PI;
		assert angle <= Math.PI;
		double newAngle = this.getAngle() + angle;
		if(newAngle > 2 * Math.PI) newAngle -= 2*Math.PI;
		if(newAngle < 0) newAngle += 2*Math.PI;
		if(newAngle == 2*Math.PI) newAngle = 0;
		this.setAngle(newAngle);
	}
	
	@Basic @Immutable
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Radii for ships are not free for the chosing
	 * @param radius
	 * @return TODO:leuke commentaar, Deevid
	 * 			| result = radius >= LOWERBOUND_RADIUS
	 */
	public static boolean isValidRadius(double radius){
		return radius >= LOWERBOUND_RADIUS;
	}
	
	private final double radius;
	
	public static double LOWERBOUND_RADIUS = 10;
	
	public static void setLowerBoundRadius(double newLower) throws IllegalArgumentException{
		if(!isValidLowerRadius(newLower)) throw new IllegalArgumentException();
		LOWERBOUND_RADIUS = newLower;
	}
	
	/**
	 * checker for the lowerbound of radii of ships
	 * @param radius
	 * @return lowerbound radii of ships have to be bigger than 0
	 * 			| result = radius > 0
	 */
	public static boolean isValidLowerRadius(double radius){
		return radius > 0;
	}
}
