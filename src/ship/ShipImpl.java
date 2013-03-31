package ship;

import asteroids.IShip;
import be.kuleuven.cs.som.annotate.*;
import java.lang.Math;

import extraUtil.Util;


/**
 * 
 * @author Deevid De Meyer and Joost Verplancke
 * @Version 1.0
 * @Invar The radius will always be valid, that is: bigger than the LOWERBOUND_RADIUS
 * 			| isValidRadius(this.getRadius())
 * @Invar The LOWERBOUND_RADIUS will always be bigger than zero
 * 			| LOWERBOUND_RADIUS > 0
 * @Invar The coordinates will always be valid
 * 			| isValidCoordinate(this.getXCoordinate()) && isValidCoordinate(this.getYCoordinate())
 * @Invar The total speed will always be lower than or equal to the max allowable speed at that moment
 * 			| this.getSpeed() < this.getMaxSpeed()
 * TODO: Other Change invars for better compatibility
 *
 */

public class ShipImpl implements IShip {
	
	// Constructors, all implemented defensively
	
	/**
	 * The basic constructor of the ship, the position, speed and direction are undefined,
	 * only the radius is implemented.
	 * @param radius
	 * @throws IllegalArgumentException
	 * 			The given radius must be valid.
	 * 			|!isValidRadius(radius)
	 */
	public ShipImpl (double radius) throws IllegalArgumentException{
		if(!isValidRadius(radius)) throw new IllegalArgumentException();		
		this.setXCoordinate(0);
		this.setYCoordinate(0);
		this.setYSpeed(0);
		this.setXSpeed(0);
		this.radius = radius;
		this.setAngle(0);
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
	public ShipImpl(double XPosition, double YPosition, double XSpeed, double YSpeed, double radius, double angle) throws IllegalArgumentException{
		if(!isValidRadius(radius)) throw new IllegalArgumentException();		
		this.setXCoordinate(XPosition);
		this.setYCoordinate(YPosition);
		this.setYSpeed(YSpeed);
		this.setXSpeed(XSpeed);
		this.radius = radius;
		this.setAngle(angle);
	}
	

	// Coordinates, all implemented defensively
	
	
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
	 * @throws IllegalArgumentException when the new coordinate (after displacement) does not qualify as a valid coordinate
	 * 			e.g. is NaN or exceeds the bounds of the double representation.
	 * 			| !isValidCoordinate(this.getXCoordinate() + distance)
	 * @Post Changes the x coordinate by the given value 
	 * 		@Eff setXCoordinate(this.getXCoordinate() + distance)
	 */
	private void relativeXDisplacement(double distance) throws IllegalArgumentException {
		double newX = this.getXCoordinate() + distance;
		setXCoordinate(newX);
	}
	
	/**
	 * 
	 * @return The X Coordinate of this ship
	 * 			| result = this.X;
	 */
	@Basic
	@Override
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
	 * @throws IllegalArgumentException when the new coordinate (after displacement) does not qualify as a valid coordinate
	 * 			e.g. is NaN or exceeds the bounds of the double representation.
	 * 			| !isValidCoordinate(this.getYCoordinate() + distance)
	 * @Post Changes the y coordinate by the given value 
	 * 		@Eff setYCoordinate(this.getYCoordinate() + distance)
	 */
	private void relativeYDisplacement(double distance) throws IllegalArgumentException {
		double newY = this.getYCoordinate() + distance;
		setYCoordinate(newY);
	}
	
	/**
	 * 
	 * @return The Y Coordinate of this ship
	 * 			| result = this.Y;
	 */
	@Basic
	@Override
	public double getYCoordinate() {
		return this.y;
	}
	
	private double y;
	
	
	/**
	 * 
	 * @param coordinate
	 * @return If the given coordinate lies between double.NEGATIVE_INFINITY and double.POSITIVE_INFINITY (both included), we return true, otherwise we return false
	 * 			| result = !(Math.abs(coordinate) > Double.MAX_VALUE || Double.isNaN(coordinate));
	 */
	public static boolean isValidCoordinate(double coordinate) {
		return !(Math.abs(coordinate) > Double.MAX_VALUE || Double.isNaN(coordinate));
	}
	
	
	// Velocities, all implemented totally
	
	
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
	
	@Basic
	@Override
	public double getXSpeed() {
		return this.xSpeed;
	}
	
	private double xSpeed;
		
	/**
	 * Before we adjust the horizontal speed (that is: the speed projected onto the x coordinate), we check if the proposed new speed in that direction is valid
	 * to do this, we check if the total speed would exceed the maximum speed of this ship.
	 * @param speed
	 * @return whether the total speed we would have if we change the horizontal speed to the given speed would still be valid (by which we mean exceed the max speed)
	 * 			| result = Math.sqrt(speed*speed + this.getYSpeed()*this.getYSpeed()) <= this.getMaxSpeed()
	 */
	private boolean isValidXSpeed(double speed) {
		return Math.sqrt(speed*speed + this.getYSpeed()*this.getYSpeed()) <= this.getMaxSpeed();
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
	
	@Basic
	@Override
	public double getYSpeed() {
		return this.ySpeed;
	}
	
	private double ySpeed;
	
	/**
	 * Before we adjust the vertical speed (that is: the speed projected onto the y coordinate), we check if the proposed new speed in that direction is valid
	 * to do this, we check if the total speed would exceed the maximum speed of this ship.
	 * @param speed
	 * @return whether the total speed we would have if we change the vertical speed to the given speed would still be valid (by which we mean exceed the max speed)
	 * 			| result = Math.sqrt(speed*speed + this.getXSpeed()*this.getXSpeed()) <= this.getMaxSpeed()
	 */
	private boolean isValidYSpeed(double speed) {
		double a = Math.sqrt(speed*speed + this.getXSpeed()*this.getXSpeed());
		return a <= this.getMaxSpeed();
	}		
	
	@Basic
	public double getMaxSpeed() {
		return this.MAX_SPEED;
	
	}
	
	
	/**
	 * returns the total speed of the ship.
	 * @return sqrt(this.getXSpeed()**2 + this.getYSpeed()**2)
	 */
	public double getSpeed(){
		return Math.sqrt(this.getXSpeed() * this.getXSpeed() + this.getYSpeed() + this.getYSpeed());
	}
		
	/**
	 * 
	 * @param speed
	 * @post 	If the proposed speed for MAX_SPEED is valid, we adjust the maximum speed to that value, otherwise we fall back to the speed of light.
	 * 			|if(isValidMaxSpeed(speed))
	 * 				then (new this).getMaxSpeed() == speed
	 * 			 else then (new this).getMaxSpeed() == LIGHTSPEED
	 * @post	If the change in max speed would mean that our current speed exceeds the max speed, we fall back to max speed
	 * 			|if(this.getSpeed >= (new this).getMaxSpeed())
	 * 				then @Eff changeToMaxSpeed()
	 */
	public void setMaxSpeed(double speed) {
		if(isValidMaxSpeed(speed)) this.MAX_SPEED = speed;
		else MAX_SPEED = LIGHTSPEED;
		if(Util.fuzzyLessThanOrEqualTo(this.getSpeed(), this.getMaxSpeed())) this.changeToMaxSpeed();
	}
	
	/**This method is used to change the total speed of the ship to the maximum speed but retain the same direction of movement.
	 * @post after this method has resolved our ship will be moving in the same direction it was earlier but at max speed
	 * 			| (new this).getSpeed() == this.getMaxSpeed() && (new this).getXSpeed()/(new this).getYSpeed() == this.getXSpeed()/this.getYSpeed()
	 * TODO: this is dirty programming due to problems with the checkers for speeds.
	 */
	public void changeToMaxSpeed(){
		if(this.getSpeed() == 0) return;
		double fracture = this.getSpeed() / this.getMaxSpeed();
		double oldYS = this.getYSpeed();
		this.setYSpeed(0);
		this.setXSpeed(this.getXSpeed() / fracture);
		this.ySpeed = oldYS / fracture;
	}
		
	/**
	 * We want to be able to adapt our maximum speed, but want to check if the proposed speed is valid. Max speeds must stay below the speed of light and have to be a number.
	 * Max speeds are always bigger than 0.
	 * @param speed
	 * @return !(Double.isNaN(speed) || speed > LIGHTSPEED || speed < 0)
	 */
	public static boolean isValidMaxSpeed(double speed) {
		return !(Double.isNaN(speed) || Util.fuzzyLessThanOrEqualTo(LIGHTSPEED, speed) || Util.fuzzyLessThanOrEqualTo(speed, 0));
	}
	
	public static final double LIGHTSPEED = 300000;
	
	private double MAX_SPEED = LIGHTSPEED;
	
	
	// Orientation, all implemented nominally (turning is included in the controlling paragraph and is also implemented nominally)
	
	
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
	@Override
	public double getAngle() {
		return this.angle;
	}
	
	
	// Radii
	
	
	@Basic @Immutable
	@Override
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Radii for ships are not free for the choosing
	 * @param radius
	 * @return 	whether the radius is valid, by which we mean we check if the radius is a number, isn't null and is bigger than the set static lowerbound radius.
	 * 			| result = radius != null && radius != NaN && radius >= LOWERBOUND_RADIUS
	 */
	public static boolean isValidRadius(double radius){
		return (Double)radius != null && (Double)radius != Double.NaN && radius >= LOWERBOUND_RADIUS;
	}
	
	private final double radius;
	
	public static final double LOWERBOUND_RADIUS = 10;

	
	// Controlling the ship
	
	
	/**
	 * This is the defensively implemented method to move a ship at its current speed, given a certain time interval during which the ship moves.
	 * This method is implemented defensively
	 * @param interval
	 * @Post After the ship has moved, its new coordinates will be equal to the time interval
	 * 			multiplied by the speed in the corresponding direction.
	 * 		| (new this).getXCoordinate() = this.getXCoordinate() + interval * this.getXSpeed()
	 * 			&& (new this).getYCoordinate() = this.getYCoordinate() + interval * this.getYSpeed()
	 * @throws IllegalArgumentException
	 * 			| We throw an IllegalArgumentException when the given time interval does not fit our
	 * 				method. This is the case when the interval is lower than zero, when it is 
	 * 				not a number or when it exceeds the Double.MAX_VALUE
	 * 			|!(isValidTimeInterval(interval)
	 */
	@Override
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
	 * 			| if( (this.getXSpeed() + usedAcceleration * Math.cos(this.getAngle()))� 
	 * 					+ (this.getYSpeed() + usedAcceleration * Math.sin(this.getAngle()))� > getMaxSpeed())
	 * 					then 
	 * 						 @Eff setXSpeed(getMaxSpeed() * Math.cos(this.getAngle()))
	 * 						 @Eff setYSpeed(getMaxSpeed() * Math.sin(this.getAngle()))
	 * @Post if both the acceleration and the would-be speed are viable, we accelerate in the direction the ship is currently facing
	 * 				| else
	 * 					@Eff this.setXSpeed(this.getXSpeed() + usedAcceleration * Math.cos(this.getAngle()))
	 * 					@Eff this.setYSpeed(this.getYSpeed() + usedAcceleration * Math.sin(this.getAngle()))
	 */
	@Override
	public void thrust(double acceleration){
		//If our acceleration is not acceptable, we don't have to do a thing.
		if(!isValidAcceleration(acceleration)) return;
		double newXSpeed = this.getXSpeed() + acceleration * Math.cos(this.getAngle());
		double newYSpeed = this.getYSpeed() + acceleration * Math.sin(this.getAngle());
		// If the acceleration would make us move faster than the max allowable speed, we switch to the max speed in the direction the ship is facing at the moment.
		if(Util.fuzzyLessThanOrEqualTo(this.getMaxSpeed() * this.getMaxSpeed(), newXSpeed * newXSpeed + newYSpeed * newYSpeed)){
			newXSpeed = getMaxSpeed() * Math.cos(this.getAngle());
			newYSpeed = getMaxSpeed() * Math.sin(this.getAngle());
		}
		/**
		 * circumvent the fact that our setter checks whether we exceed the maximum allowable speed
		 * by setting the y speed to zero first
		 */
		this.setYSpeed(0);
		this.setXSpeed(newXSpeed);
		this.setYSpeed(newYSpeed);
		
	}
	
	/**
	 * This method checks whether a given acceleration is acceptable for use in this class. Accelerations
	 * must be numbers representable in doubles and have to be bigger than zero.
	 * @param acceleration
	 * @return whether the given acceleration is a number bigger than zero
	 * 			| result = !(Double.isNaN(acceleration)) && !(acceleration < 0)
	 */
	public static boolean isValidAcceleration(double acceleration){
		return !(Double.isNaN(acceleration)) && !(acceleration > Double.MAX_VALUE) &&!Util.fuzzyLessThanOrEqualTo(acceleration, 0);
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
	@Override
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
	
	
	//Collisions and Distances
	
	
	/**
	 * This method is used to determine the distance to another ship as defined by the assignment
	 * @param ship
	 * @return the distance between the two centers of the ships minus the two radii. If the method is called with the ship itself, we always return 0, otherwise
	 * 			we calculate the distance between the two ships and substract the two radii.
	 * 			|if(ship == this) then result = 0 else result = getDistanceBetweenCenters(ship) - this.getRadius() - ship.getRadius()
	 * @throws NullPointerException
	 * 			we throw a NullPointerException if the paramater ship is null.
	 * 			| ship == null
	 * 
	 */
	public double getDistanceBetween(ShipImpl ship) throws NullPointerException{
		if(ship == null) throw new NullPointerException();
		if(ship == this) return 0;
		double cDistance = this.getDistanceBetweenCenters(ship);
		return cDistance - this.getRadius() - ship.getRadius();
	}
	
	
	/**
	 * This method is used as the static interface to determine the distance between two ships.
	 * @param ship1
	 * @param ship2
	 * @return the distance using the non-static method getDistanceBetween(Ship ship) against one of the given ships
	 * 			| @Eff result = ship1.getDistanceBetween(ship2)
	 * @throws NullPointerException if the ship we want to call the non-static method against is null
	 * 			| ship1 == null
	 */
	public static double getDistanceBetween(ShipImpl ship1, ShipImpl ship2) throws NullPointerException{
		if(ship1 == null) throw new NullPointerException();
		return ship1.getDistanceBetween(ship2);
	}
	
	
	/**
	 * This method is used to determine the distance between the centers of a given ship and this one. Distances between centers of ships are always positive.
	 * @param ship
	 * @return the geometrical distance between the two centers of the ships
	 * 			| result = Math.Sqrt((this.getXCoordinate() - ship.getXCoordinate())� + (this.getYCoordinate() - ship.getYCoordinate())�)
	 * @throws NullPointerException if the given ship is null
	 * 			| ship == null
	 */
	private double getDistanceBetweenCenters(ShipImpl ship) throws NullPointerException{
		if(ship == null) throw new NullPointerException();
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
	public boolean overlap(ShipImpl ship) throws NullPointerException{
		if(ship == null) throw new NullPointerException();
		if(ship == this) return true;
		return Util.fuzzyLessThanOrEqualTo(this.getDistanceBetween(ship), 0);
	}
	
	/**
	 * Quick check if 2 ships are moving towards each other using basic vector calculus
	 * @param ship
	 * @return we multiply the delta_speed and delta_coordinate vectors and check if the result is smaller than 0, if it is, the ships are
	 * 			moving towards each other, if it isn't, they aren't.
	 * 			| result = ((ship.getXCoordinate()- this.getXCoordinate()) * (ship.getXSpeed() - this.getXSpeed())) + 
				((ship.getYCoordinate() - this.getYCoordinate()) * (ship.getYSpeed() - this.getYSpeed())) < 0
	 */
	private boolean MovingTowardsEachOther(ShipImpl ship) {
		return ((ship.getXCoordinate()- this.getXCoordinate()) * (ship.getXSpeed() - this.getXSpeed())) + 
				((ship.getYCoordinate() - this.getYCoordinate()) * (ship.getYSpeed() - this.getYSpeed())) < 0;
		}
	
	
	/**
	 * This method is used as the interfacing method for the private method MovingTowardsEachOther
	 * @param ship1
	 * @param ship2
	 * @return We return 
	 * @throws
	 */
	public static boolean movingTowardsEachOther(ShipImpl ship1, ShipImpl ship2) throws NullPointerException{
		if(ship1 == null) throw new NullPointerException();
		return ship1.MovingTowardsEachOther(ship2);
	}
	
	/**
	 * Calculates the time to collision of 2 ships, or returns infinity if they will not collide.
	 * TODO: works, praying unnecesary now
	 * @param ship
	 * @return The point where the two ships touch each other when (and if) they collide
	 * 			| ...
	 * @throws NullPointerException
	 */
	public double getTimeToCollision(ShipImpl ship) throws NullPointerException {
		if(ship == null) throw new NullPointerException();
		if(ship == this) return 0;
		if(!this.MovingTowardsEachOther(ship)) return Double.POSITIVE_INFINITY;
		double txs = this.getXSpeed();
		double sxs = ship.getXSpeed();
		double txc = this.getXCoordinate();
		double sxc = ship.getXCoordinate();
		double tys = this.getYSpeed();
		double sys = ship.getYSpeed();
		double tyc = this.getYCoordinate();
		double syc = ship.getYCoordinate();
		double sor = this.getRadius() + ship.getRadius();
		// Now we just calculate d according to the assignment
		double d = (
				(
				// (DELTA_v * DELTA_r)
				((txs - sxs) * (txc - sxc) + (tys - sys) * (tyc - syc))
				// �
				* ((txs - sxs) * (txc - sxc) + (tys - sys) * (tyc - syc))
				)
				-
				( (
				// DELTA_v * DELTA_v
				(txs - sxs) * (txs - sxs) + (tys - sys) * (tys - sys)
				)
				*
				( (
				// DELTA_r * DELTA_r
				(txc - sxc) * (txc - sxc) + (tyc - syc) * (tyc - syc)
				)
				-
				// SIGMA �
				(sor * sor) 
				) 
				) );
		if(d < 0) return Double.POSITIVE_INFINITY;
		d = Math.sqrt(d);
		double result = -1 * (
				(
				(
				//DELTA_v * DELTA_r
				(txs - sxs) * (txc - sxc) + (tys - sys) * (tyc - syc)
				)
				+
				//SQRT(d)
				d
				)
				/
				(
				//DELTA_v * DELTA_v
				(txs - sxs) * (txs - sxs) + (tys - sys) * (tys - sys)
				)
				);
		return result;
	}
	

	/**
	 * 
	 * @param ship
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	public double[] getCollisionPosition(ShipImpl ship) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		if(ship == null) throw new NullPointerException();
		if(ship == this) throw new IllegalArgumentException();
		double time = this.getTimeToCollision(ship);
		if(time == Double.POSITIVE_INFINITY) throw new IllegalStateException();
		double[] result = new double[2];
		double txc = this.getXCoordinate() + time * this.getXSpeed();
		double sxc = ship.getXCoordinate() + time * ship.getXSpeed();
		double tyc = this.getYCoordinate() + time * this.getYSpeed();
		double syc = ship.getYCoordinate() + time * ship.getYSpeed();
		double tr = this.getRadius();
		double sr = ship.getRadius();
//		double coordX = ship.getXCoordinate() + (time * ship.getXSpeed()) - this.getXCoordinate() + (time * this.getXSpeed());
//		double coordY = ship.getYCoordinate() + (time * ship.getYSpeed()) - this.getYCoordinate() + (time * this.getYSpeed());
//		double offsetX = ship.getRadius() * (coordX/(ship.getRadius()+this.getRadius()));
//		double offsetY = ship.getRadius() * (coordY/(ship.getRadius()+this.getRadius()));
//		double[] coord = {ship.getXCoordinate() + offsetX,ship.getYCoordinate() + offsetY};
//		return coord;
		if(txc == sxc){
			result[0] = txc;
			if(tyc > syc){
				result[1] = tyc - tr;
			}
			else {
				result[1] = tyc + tr;
			}
		}
		else{
			double thales = tr/(tr + sr);
			// We use thales' theorem to project unto the x and y axis.
				result[0] = txc + thales * (sxc - txc);
				result[1] = tyc + thales * (syc - tyc);			
		}
		return result;
	}
	
	/**
	 * This is the static interfacing method to get the collision position of two ships with the getCollisionPosition method (which is non-static)
	 * @param ship1
	 * @param ship2
	 * @return We call the public non-static method getCollisionPoint against one of the ships if that one isn't null.
	 * 			| result = ship1.getCollisionPosition(ship2)
	 * @throws NullPointerException if the ship we want to call the non-static method against is null
	 * 			| ship1 == null
	 */
	public static double[] getCollisionPoint(ShipImpl ship1, ShipImpl ship2) throws NullPointerException{
		if(ship1 == null) throw new NullPointerException();
		return ship1.getCollisionPosition(ship2);
	}
	
}

