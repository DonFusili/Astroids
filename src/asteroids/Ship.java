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
	
	/**
	 * The basic constructor of the ship, the position, speed and direction are undefined,
	 * only the radius is implemented.
	 * @param radius
	 * @throws IllegalArgumentException
	 * 			The given radius must be valid.
	 * 			|!isValidRadius(radius)
	 */
	public Ship (double radius) throws IllegalArgumentException{
		if(!isValidRadius(radius)) throw new IllegalArgumentException();
		this.radius = radius;
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
	public Ship(double XPosition, double YPosition, double XSpeed, double YSpeed, double radius, double angle) throws IllegalArgumentException{
		if(!isValidRadius(radius)||!isValidCoordinate(XPosition) || !isValidCoordinate(YPosition)) throw new IllegalArgumentException();
		this.ySpeed = 0;
		this.xSpeed = XSpeed;
		if(!isValidYSpeed(YSpeed)) throw new IllegalArgumentException();
		this.ySpeed = YSpeed;
		this.angle = angle;
		this.radius = radius;
		this.x = XPosition;
		this.y = YPosition;
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
	 * 		TODO: add comment on when we throw.
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
	 * 			TODO: add comment on when we throw
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
	 * 			| result = !(coordinate > Double.MAX_VALUE || coordinate < Double.MIN_VALUE || Double.isNaN(coordinate));
	 */
	public static boolean isValidCoordinate(double coordinate) {
		return !(coordinate > Double.MAX_VALUE || coordinate < Double.MIN_VALUE || Double.isNaN(coordinate));
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
		return Util.fuzzyLessThanOrEqualTo(a, this.getMaxSpeed());
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
		return Util.fuzzyLessThanOrEqualTo(a, this.getMaxSpeed());
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
	 * @return !(Double.isNaN(speed) || Math.Abs(speed) > LIGHTSPEED)
	 */
	public static boolean isValidMaxSpeed(double speed) {
		return !(Double.isNaN(speed) || Util.fuzzyLessThanOrEqualTo(LIGHTSPEED, speed));
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
		assert Util.fuzzyLessThanOrEqualTo(0, angle);
		assert Util.fuzzyLessThanOrEqualTo(angle, 2*Math.PI);
		assert !Util.fuzzyEquals(angle, 2*Math.PI);
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
		assert Util.fuzzyLessThanOrEqualTo(-1*Math.PI, angle);
		assert !Util.fuzzyEquals(-1*Math.PI, angle);
		assert Util.fuzzyLessThanOrEqualTo(angle, Math.PI);
		double newAngle = this.getAngle() + angle;
		if(Util.fuzzyEquals(newAngle,2*Math.PI) || Util.fuzzyEquals(newAngle, 0)) newAngle = 0;
		if(Util.fuzzyLessThanOrEqualTo(2*Math.PI, newAngle)) newAngle -= 2*Math.PI;
		if(newAngle < 0) newAngle += 2*Math.PI;
		this.setAngle(newAngle);
				
	}
	
	public void turn(double angle){
		this.relativeTurn(angle);
	}
	
	@Basic @Immutable
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Radii for ships are not free for the choosing
	 * @param radius
	 * @return TODO:leuke commentaar, Deevid
	 * 			| result = radius >= LOWERBOUND_RADIUS
	 */
	public static boolean isValidRadius(double radius){
		return radius >= LOWERBOUND_RADIUS;
	}
	
	private final double radius;
	
	public static double LOWERBOUND_RADIUS;
	public static double getLowerboundRadius(){
		if(LOWERBOUND_RADIUS == 0) return 10;
		return LOWERBOUND_RADIUS;
	}
	
	/**
	 * TODO: comment
	 * @param newLower
	 * @throws IllegalArgumentException
	 */
	public static void setLowerBoundRadius(double newLower) throws IllegalArgumentException{
		if(!isValidLowerRadius(newLower)) throw new IllegalArgumentException();
		LOWERBOUND_RADIUS = newLower;
	}
	
	/**
	 * checker for the lowerbound of radii of ships
	 * @param radius
	 * @return lowerbound radii of ships have to be bigger than 0 and have to be a number.
	 * 			| result = radius > 0
	 */
	public static boolean isValidLowerRadius(double radius){
		if(Double.isNaN(radius)) return false;
		return radius > 0;
	}

	/**
	 * 
	 * 
	 * @param interval
	 * @Pre as given in the assignement, the time interval will never be lower than zero, hence
	 * 		we define the precondition
	 * 		|interval >0
	 * @Post After the ship has moved, its new coordinates will be equal to the time interval
	 * 			multiplied by the speed in the coresponding direction.
	 * 		| (new this).getXCoordinate() = this.getXCoordinate() + interval * this.getXSpeed()
	 * 			&& (new this).getYCoordinate() = this.getYCoordinate() + interval * this.getYSpeed()
	 * @throws IllegalArgumentException
	 * 			| We throw an IllegalArgumentException when the given time interval does not fit our
	 * 				method. This is the case when the interval is lower than zero, when it is 
	 * 				not a number or when it exceeds the Double.MAX_VALUE
	 */
	public void move(double interval) throws IllegalArgumentException{
		
		if(!isValidTimeInterval(interval)) throw new IllegalArgumentException();
		this.relativeXDisplacement(interval * this.getXSpeed());
		this.relativeYDisplacement(interval * this.getYSpeed());		
	}

	/**
	 * This method is used to check if a given time interval is valid for the use in methods of this 
	 * class. Time intervals should be bigger than zero, be a number and cannot exceed the maximum
	 * representable value for doubles.
	 * @param interval
	 * @return if the value is acceptable for use as time interval in this class, e.g. whether
	 * 			the value is bigger than zero, is an actual number and does not exceed Double.MAX_VALUE
	 * 		   | result = !(Double.isNaN(interval)) && !(interval > Double.MAX_VALUE) && interval > 0
	 */
	public static boolean isValidTimeInterval(double interval){
		boolean check = !(Double.isNaN(interval));
		check = check && !(interval > Double.MAX_VALUE);
		check = check && Util.fuzzyLessThanOrEqualTo(0, interval) && !Util.fuzzyEquals(0, interval);
		return check;
	}
	
	

	/**
	 * This method is used to accelerate in the current direction of the ship. This method is implemented 
	 * totally. We check the interval and possible speeds a few times and depending on our findings, we 
	 * discard the changes or apply them
	 * @param interval
	 * @Post if the acceleration isn't valid, nothing happens
	 * 			| if(!isValidAcceleration(acceleration)
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
		double usedAcceleration = 0;
		//If our acceleration is not acceptable, we don't have to do a thing.
		if(isValidAcceleration(acceleration)){ usedAcceleration = acceleration;
		double newXSpeed = this.getXSpeed() + usedAcceleration * Math.cos(this.getAngle());
		double newYSpeed = this.getYSpeed() + usedAcceleration * Math.sin(this.getAngle());
		if(Util.fuzzyLessThanOrEqualTo(getMaxSpeed(), newXSpeed * newXSpeed + newYSpeed * newYSpeed)){
			newXSpeed = getMaxSpeed() * Math.cos(this.getAngle());
			newYSpeed = getMaxSpeed() * Math.sin(this.getAngle());
		}
		/**
		 * circumvene the fact that our setter checks whether we exceed the maximum allowable speed
		 * by setting the y speed to zero first
		 */
		this.setYSpeed(0);
		this.setXSpeed(newXSpeed);
		this.setYSpeed(newYSpeed);
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
		return !(Double.isNaN(acceleration)) && !Util.fuzzyLessThanOrEqualTo(0, acceleration);
	}
	
	
	
	/**
	 * This method is used to determine the distance to another ship as defined by the assignement
	 * @param ship
	 * @return the distance between the two centers of the ships minus the two radii. If the method is called with the ship itself, we always return 0, otherwise
	 * 			we calculate the distance between the two ships and substract the two radii.
	 * 			|if(ship == this) then result = 0 else result = getDistanceBetweenCenters(ship) - this.getRadius() - ship.getRadius()
	 * @throws NullPointerException
	 * 			we throw a NullPointerException if the paramater ship is null.
	 * 			| ship == null
	 * 
	 */
	public double getDistanceBetween(Ship ship) throws NullPointerException{
		if(ship == null) throw new NullPointerException();
		if(ship == this) return 0;
		double cDistance = this.getDistanceBetweenCenters(ship);
		return cDistance - this.getRadius() - ship.getRadius();
	}
	
	
	
	/**
	 * This method is used to determine the distance between the centers of a given ship and this one. Distances between centers of ships are always positive.
	 * @param ship
	 * @return the geometrical distance between the two centers of the ships
	 * 			| result = Math.Sqrt((this.getXCoordinate() - ship.getXCoordinate())² + (this.getYCoordinate() - ship.getYCoordinate())²)
	 */
	private double getDistanceBetweenCenters(Ship ship){
		return Math.sqrt((this.getXCoordinate() - ship.getXCoordinate()) * (this.getXCoordinate() - ship.getXCoordinate()) 
							+ (this.getYCoordinate() - ship.getYCoordinate()) * (this.getYCoordinate() - ship.getYCoordinate()));
	}
	
	
	
	/**
	 * This method is used to check whether two ships overlap. A ship always overlaps with itself
	 * @param ship
	 * @return whether or not the ships overlap. If the given ship is this ship, we always return true, otherwise we return true if the distance between ships is strictly
	 * 			lower than zero (e.g. touching ships don't overlap)
	 * @throws NullPointerException
	 * 			We throw a nullpointerException if the parameter ship is null.
	 * 			| ship == null
	 */
	public boolean overlap(Ship ship) throws NullPointerException{
		if(ship == null) throw new NullPointerException();
		if(ship == this) return true;
		return Util.fuzzyLessThanOrEqualTo(this.getDistanceBetween(ship), 0);
	}
	
	/**
	 * Quick check if 2 ships are moving towards each other
	 * TODO: Check if this works, comments
	 * @param ship
	 */
	public boolean MovingTowardsEachOther(Ship ship) {
		return ((ship.getXCoordinate()- this.getXCoordinate()) * (this.getXSpeed() - ship.getXSpeed())) + ((ship.getYCoordinate() - this.getYCoordinate()) * (this.getYSpeed() - ship.getYSpeed())) > 0;
		}
	
	/**
	 * Calculates the time to collision of 2 ships, or returns infinity if they will not collide
	 * TODO: pray this works/test it
	 * @param ship
	 * @return
	 * @throws NullPointerException
	 */
	public double getTimeToCollision(Ship ship) throws NullPointerException {
		if(ship == null) throw new NullPointerException();
		if(ship == this) return 0;
		if(this.MovingTowardsEachOther(ship) == false) return Double.POSITIVE_INFINITY;

		double a = 2 * ((this.getXSpeed()*this.getXSpeed()) - (2 * this.getXSpeed() * ship.getXSpeed()) + (ship.getXSpeed()*ship.getXSpeed()) 
				 + (this.getYSpeed()*this.getYSpeed()) - (2 * this.getYSpeed() * ship.getYSpeed()) + (ship.getYSpeed()*ship.getYSpeed()));
		
		double b = 2 * ((-this.getXCoordinate()*this.getXSpeed()) - (this.getYCoordinate()*this.getYSpeed()) 
				+ (this.getXSpeed() * ship.getXCoordinate()) + (this.getYSpeed() * ship.getYCoordinate()) 
				+ (this.getXCoordinate() * ship.getXSpeed()) - (ship.getXCoordinate() * ship.getXSpeed()) 
				+ (this.getYCoordinate() * ship.getYSpeed()) - (ship.getYCoordinate() * ship.getYSpeed()));

		double c = (this.getXCoordinate()*this.getXCoordinate()) + (this.getYCoordinate()*this.getYCoordinate())
				- (this.getRadius()*this.getRadius()) - (2 * this.getXCoordinate() * ship.getXCoordinate())
				+ (ship.getXCoordinate()*ship.getXCoordinate()) - (2 * this.getYCoordinate() * ship.getYCoordinate())
				+ (ship.getYCoordinate()*ship.getYCoordinate()) - (2 * this.getRadius() * ship.getRadius()) 
				- (ship.getRadius()*ship.getRadius());
		
		double discriminant = (b * b) - (4 * a * c);
		if(discriminant<0) return Double.POSITIVE_INFINITY;
		return Math.min((b-Math.sqrt(discriminant))/a, (b+Math.sqrt(discriminant))/a);
				
	}
	
	/**
	 * Returns the x- and y-coordinate of the predicted collision point in an array.
	 * TODO: comments, defensief uitwerken
	 * @param ship
	 * @return
	 * @throws NullPointerException
	 */
	public double[] getCollisionPosition(Ship ship) {
		double time = this.getTimeToCollision(ship);
		double coordX = ship.getXCoordinate() + (time * ship.getXSpeed()) - this.getXCoordinate() + (time * this.getXSpeed());
		double coordY = ship.getYCoordinate() + (time * ship.getYSpeed()) - this.getYCoordinate() + (time * this.getYSpeed());
		double offsetX = ship.getRadius() * (coordX/(ship.getRadius()+this.getRadius()));
		double offsetY = ship.getRadius() * (coordY/(ship.getRadius()+this.getRadius()));
		double[] coord = {ship.getXCoordinate() + offsetX,ship.getYCoordinate() + offsetY};
		return coord;
	}
	
}

