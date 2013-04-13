package space;

import ship.*;
import be.kuleuven.cs.som.annotate.*;
import extraUtil.*;

public abstract class Flying {
	
	protected Flying(double radius, Flyer flyertype) throws IllegalArgumentException{
		this(radius, new Vector(0, 0), new Vector(0, 0), flyertype);
	}
	
	protected Flying(double radius, Vector coordinates, Vector speeds, Flyer flyertype) throws IllegalArgumentException{
		if(!isValidRadius(radius)) throw new IllegalArgumentException();
		this.radius = radius;
		this.coordinates = coordinates;
		this.speeds = speeds;
		this.flyertype = flyertype;
	}
	
	protected Flying(double radius, double XPos, double YPos, double XSpeed, double YSpeed, Flyer flyertype) throws IllegalArgumentException{
		this(radius, new Vector(XPos, YPos), new Vector(XSpeed, YSpeed), flyertype);
	}
	
	public static boolean isValidRadius(double radius){
		return radius >= 0;
	}

	/**
	 * 
	 * @param coordinate
	 * @return If the given coordinate lies between double.NEGATIVE_INFINITY and double.POSITIVE_INFINITY (both included), we return true, otherwise we return false
	 * 			| result = !(Math.abs(coordinate) > Double.MAX_VALUE || Double.isNaN(coordinate));
	 */
	public static boolean isValidCoordinate(double coordinate) {
		return !(Math.abs(coordinate) > Double.MAX_VALUE || Double.isNaN(coordinate));
	}

	public static boolean isValidSpeed(double speed, double otherspeed) {
		return Math.sqrt(speed*speed + otherspeed * otherspeed) <= getMaxSpeed();
	}

	public static double getMaxSpeed(){
		return World.LIGHTSPEED;
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
	public static boolean isValidTimeInterval(double interval) {
		return interval >= 0;
	}

	/**
	 * This method is used as the static interface to determine the distance between two ships.
	 * @param flying1
	 * @param flying2
	 * @return the distance using the non-static method getDistanceBetween(Ship ship) against one of the given ships
	 * 			| @Eff result = ship1.getDistanceBetween(ship2)
	 * @throws NullPointerException if the ship we want to call the non-static method against is null
	 * 			| ship1 == null
	 */
	public static double getDistanceBetween(Flying flying1, Flying flying2)
			throws NullPointerException {
				if(flying1 == null) throw new NullPointerException();
				return flying1.getDistanceBetween(flying2);
			}

	/**
	 * This method is used as the interfacing method for the private method MovingTowardsEachOther
	 * @param ship1
	 * @param ship2
	 * @return We return 
	 * @throws
	 * TODO: comment
	 */
	public static boolean movingTowardsEachOther(Flying one, Flying two) throws NullPointerException {
				if(one == null) throw new NullPointerException();
				return one.MovingTowardsEachOther(two);
			}

	/**
	 * This is the static interfacing method to get the collision position of two ships with the getCollisionPosition method (which is non-static)
	 * @param flying1
	 * @param flying2
	 * @return We call the public non-static method getCollisionPoint against one of the ships if that one isn't null.
	 * 			| result = ship1.getCollisionPosition(ship2)
	 * @throws NullPointerException if the ship we want to call the non-static method against is null
	 * 			| ship1 == null
	 */
	public static double[] getCollisionPoint(Flying flying1, Flying flying2)
			throws NullPointerException {
				if(flying1 == null) throw new NullPointerException();
				return flying1.getCollisionPosition(flying2);
			}

	public static boolean isValidWorld(World world) {
		return true;
	}

	protected Vector coordinates;

	public double getXCoordinate() {
		return coordinates.getX();
	}

	public double getYCoordinate() {
	return coordinates.getY();
	}

	protected Vector speeds;
	
	public Vector getSpeeds(){
		return new Vector(speeds.getX(), speeds.getY());
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
		coordinates = new Vector(coordinate, coordinates.getY());
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
	private void relativeXDisplacement(double distance)
			throws IllegalArgumentException {
				double newX = this.getXCoordinate() + distance;
				setXCoordinate(newX);
			}

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
		coordinates = new Vector(coordinates.getX(), coordinate);
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
	private void relativeYDisplacement(double distance)
			throws IllegalArgumentException {
				double newY = this.getYCoordinate() + distance;
				setYCoordinate(newY);
			}

	protected double angle;

	@Basic
	public Vector getCoordinates() {
		return coordinates;
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
		if(isValidXSpeed(speed)) speeds = new Vector(speed, this.getYSpeed());
	}

	@Basic
	public double getXSpeed() {
		return speeds.getX();
	}

	/**
	 * Before we adjust the horizontal speed (that is: the speed projected onto the x coordinate), we check if the proposed new speed in that direction is valid
	 * to do this, we check if the total speed would exceed the maximum speed of this ship.
	 * @param speed
	 * @return whether the total speed we would have if we change the horizontal speed to the given speed would still be valid (by which we mean exceed the max speed)
	 * 			| result = Math.sqrt(speed*speed + this.getYSpeed()*this.getYSpeed()) <= this.getMaxSpeed()
	 */
	private boolean isValidXSpeed(double speed) {
		return isValidSpeed(speed, this.getYSpeed());
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
		if(isValidYSpeed(speed)) speeds = new Vector(this.getXSpeed(), speed);
	}

	@Basic
	public double getYSpeed() {
		return speeds.getY();
	}

	/**
	 * Before we adjust the vertical speed (that is: the speed projected onto the y coordinate), we check if the proposed new speed in that direction is valid
	 * to do this, we check if the total speed would exceed the maximum speed of this ship.
	 * @param speed
	 * @return whether the total speed we would have if we change the vertical speed to the given speed would still be valid (by which we mean exceed the max speed)
	 * 			| result = Math.sqrt(speed*speed + this.getXSpeed()*this.getXSpeed()) <= this.getMaxSpeed()
	 */
	private boolean isValidYSpeed(double speed) {
		return isValidSpeed(speed, this.getXSpeed());
	}

	protected final double radius;

	/**
	 * returns the total speed of the ship.
	 * @return sqrt(this.getXSpeed()**2 + this.getYSpeed()**2)
	 */
	public double getSpeed() {
		return Vector.dotProduct(speeds, speeds);
	}

	/**This method is used to change the total speed of the ship to the maximum speed but retain the same direction of movement.
	 * @post after this method has resolved our ship will be moving in the same direction it was earlier but at max speed
	 * 			| (new this).getSpeed() == this.getMaxSpeed() && (new this).getXSpeed()/(new this).getYSpeed() == this.getXSpeed()/this.getYSpeed()
	 * TODO: this is dirty programming due to problems with the checkers for speeds.
	 */
	public void changeToMaxSpeed() {
		if(this.getSpeed() == 0) return;
		double fracture = this.getSpeed() / getMaxSpeed();
		double oldYS = this.getYSpeed();
		this.setYSpeed(0);
		this.setXSpeed(this.getXSpeed() / fracture);
		speeds = new Vector(this.getXSpeed(), oldYS/fracture);
	}

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

	public static final double RHO1 = 2.65e12;
	public static final double RHO2 = 7.8e12;

	@Basic
	public double getAngle() {
		return this.angle;
	}

	@Basic
	@Immutable
	public double getRadius() {
		return this.radius;
	}

	@Basic
	@Immutable
	public abstract double getMassDensity();

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
	public void move(double interval) throws IllegalArgumentException {
		if(!isValidTimeInterval(interval)) throw new IllegalArgumentException();
		try{
			this.collideWithBoundary();
		}
		catch(NullPointerException e){}
		this.setCoordinates(Vector.add(this.getCoordinates(), Vector.scalarMult(interval, this.getSpeeds())));
	}
	
	/**
	 * TODO: comment
	 * @param vec
	 */
	public void setSpeeds(Vector vec){
		if(isValidSpeed(vec.getX(), vec.getY())) this.speeds = vec;
	}
	
	/**
	 * TODO: comment
	 * @param vec
	 */
	public void setCoordinates(Vector vec){
		if(isValidCoordinates(vec)) this.coordinates = vec;
	}
	
	/**
	 * TODO: comment
	 * @param vec
	 * @return
	 */
	public static boolean isValidCoordinates(Vector vec){
		return !(vec == null) && !Double.isNaN(vec.getX()) && !Double.isNaN(vec.getY());
	}

	private World world;

	/**
	 * This method is used to determine the distance to another ship as defined by the assignment
	 * @param flying
	 * @return the distance between the two centers of the ships minus the two radii. If the method is called with the ship itself, we always return 0, otherwise
	 * 			we calculate the distance between the two ships and substract the two radii.
	 * 			|if(ship == this) then result = 0 else result = getDistanceBetweenCenters(ship) - this.getRadius() - ship.getRadius()
	 * @throws NullPointerException
	 * 			we throw a NullPointerException if the paramater ship is null.
	 * 			| ship == null
	 * 
	 */
	public double getDistanceBetween(Flying flying) throws NullPointerException {
		if(flying == null) throw new NullPointerException();
		if(flying == this) return 0;
		double cDistance = this.getDistanceBetweenCenters(flying);
		return cDistance - this.getRadius() - flying.getRadius();
	}

	public World getWorld() {
		return world;
	}

	/**
	 * This method is used to determine the distance between the centers of a given ship and this one. Distances between centers of ships are always positive.
	 * @param flying
	 * @return the geometrical distance between the two centers of the ships
	 * 			| result = Math.Sqrt((this.getXCoordinate() - ship.getXCoordinate())² + (this.getYCoordinate() - ship.getYCoordinate())²)
	 * @throws NullPointerException if the given ship is null
	 * 			| ship == null
	 */
	private double getDistanceBetweenCenters(Flying flying)
			throws NullPointerException {
				if(flying == null) throw new NullPointerException();
				return Math.sqrt(Vector.square(Vector.difference(this.getCoordinates(), flying.getCoordinates())));
			}

	/**
	 * This method is used to check whether two ships overlap. A ship always overlaps with itself
	 * @param flying
	 * @return whether or not the ships overlap. If the given ship is this ship, we always return true, otherwise we return true if the distance between ships is strictly
	 * 			lower than zero (e.g. touching ships don't overlap)
	 * @throws NullPointerException
	 * 			We throw a nullpointerException if the parameter ship is null.
	 * 			| ship == null
	 */
	public boolean overlap(Flying flying) throws NullPointerException {
		if(flying == null) throw new NullPointerException();
		if(flying == this) return true;
		return Util.fuzzyLessThanOrEqualTo(this.getDistanceBetween(flying), 0);
	}
	
	/**
	 * TODO: comment!
	 * @param flying1
	 * @param flying2
	 * @return
	 */
	public static boolean overlap(Flying flying1, Flying flying2){
		if(flying1 == null) throw new NullPointerException();
		return flying1.overlap(flying2);
	}

	/**
	 * Quick check if 2 ships are moving towards each other using basic vector calculus
	 * @param ship
	 * @return we multiply the delta_speed and delta_coordinate vectors and check if the result is smaller than 0, if it is, the ships are
	 * 			moving towards each other, if it isn't, they aren't.
	 * 			| result = ((ship.getXCoordinate()- this.getXCoordinate()) * (ship.getXSpeed() - this.getXSpeed())) + 
				((ship.getYCoordinate() - this.getYCoordinate()) * (ship.getYSpeed() - this.getYSpeed())) < 0
	 */
	private boolean MovingTowardsEachOther(Flying flying) {
		return Vector.dotProduct(Vector.difference(this.getSpeeds(), flying.getSpeeds()), Vector.difference(this.getCoordinates(), flying.getCoordinates())) < 0;
	}
	
	public static boolean MovingTowardsEachOther(Flying one, Flying two){
		if(one == null || two == null) throw new NullPointerException();
		return Vector.square(Vector.difference(one.getSpeeds(), two.getSpeeds())) < 0;
	}


	/**
	 * Calculates the time to collision of 2 ships, or returns infinity if they will not collide.
	 * TODO: works, praying unnecesary now
	 * TODO: recomment
	 * @param flying
	 * @return The point where the two ships touch each other when (and if) they collide
	 * 			| ...
	 * @throws NullPointerException
	 */
	public double getTimeToCollision(Flying flying) throws NullPointerException {
		if(flying == null) throw new NullPointerException();
		if(flying == this) return 0;
		if(!this.MovingTowardsEachOther(flying)) return Double.POSITIVE_INFINITY;
		double txs = this.getXSpeed();
		double sxs = flying.getXSpeed();
		double txc = this.getXCoordinate();
		double sxc = flying.getXCoordinate();
		double tys = this.getYSpeed();
		double sys = flying.getYSpeed();
		double tyc = this.getYCoordinate();
		double syc = flying.getYCoordinate();
		double sor = this.getRadius() + flying.getRadius();
		// Now we just calculate d according to the assignment
		double d = (
				(
				// (DELTA_v * DELTA_r)
				((txs - sxs) * (txc - sxc) + (tys - sys) * (tyc - syc))
				// ²
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
				// SIGMA ²
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
	 * TODO: comment!
	 * @param flying1
	 * @param flying2
	 * @return
	 * @throws NullPointerException
	 */
	public static double getTimeToCollision(Flying flying1, Flying flying2) throws NullPointerException{
		if(flying1 == null) throw new NullPointerException();
		return flying1.getTimeToCollision(flying2);
	}

	/**
	 * 
	 * @param flying
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	public double[] getCollisionPosition(Flying flying)
			throws NullPointerException, IllegalArgumentException,
			IllegalStateException {
					if(flying == null) throw new NullPointerException();
					if(flying == this) throw new IllegalArgumentException();
					double time = this.getTimeToCollision(flying);
					if(time == Double.POSITIVE_INFINITY) throw new IllegalStateException();
					double[] result = new double[2];
					double txc = this.getXCoordinate() + time * this.getXSpeed();
					double sxc = flying.getXCoordinate() + time * flying.getXSpeed();
					double tyc = this.getYCoordinate() + time * this.getYSpeed();
					double syc = flying.getYCoordinate() + time * flying.getYSpeed();
					double tr = this.getRadius();
					double sr = flying.getRadius();
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
	
	public void setWorld(World world) {
		if(this.getWorld() == null) this.world = world;
	}

	@Immutable
	public abstract double getMass();
	
	public double getSphericalMass(){
		double mass = 4 / 3 * Math.PI *
				this.getRadius() * this.getRadius() * this.getRadius() *
				this.getMassDensity();
		return mass;
	}

	public boolean isAvailableToAdd() {
		return this.getWorld() == null;
	}

	
	public void addToWorld(World world) {
		// TODO Auto-generated method stub
		
	}

	private Flyer getFlyertype() {
		return flyertype;
	}
	
	private final Flyer flyertype;
	
	protected enum Flyer { SHIP, ASTEROID, BULLET }
	
	private void collideWith(Flying flying){
		switch(flying.getFlyertype()) {
		case SHIP: this.collideWithShip((Ship)flying); break;
		case ASTEROID : this.collideWithAsteroid((Asteroid)flying); break;
		case BULLET : this.collideWithBullet((Bullet) flying); break;
		default : assert false;
		}
	}
	
	protected abstract void collideWithShip(Ship ship);
	
	protected abstract void collideWithBullet(Bullet bullet);
	
	protected abstract void collideWithAsteroid(Asteroid asteroid);
	
	public void collideWithBoundary(){
		boolean invertx = false;
		boolean inverty = false;
		double newx = this.getXSpeed();
		double newy = this.getYSpeed();
		double radius = this.getRadius();
		if(newx > 0 &&((this.getXCoordinate() + radius) >= this.getWorld().getWidth())) invertx = true;
		if(newx < 0 &&((this.getXCoordinate() - radius) <= (-1 * this.getWorld().getWidth()))) invertx = true;
		if(newy > 0 && ((this.getYCoordinate() + radius) >= this.getWorld().getHeight())) inverty = true;
		if(newy < 0 && ((this.getYCoordinate() - radius) <= (-1 * this.getWorld().getHeight()))) inverty = true;
		if(invertx || inverty){
			if(invertx) newx = -1 * newx;
			if(inverty) newy = -1 * newy;
			this.setSpeeds(new Vector(newx, newy));
		}
	}
	
	public static void collide(Flying flying1, Flying flying2){
		flying1.collideWith(flying2);
		flying2.collideWith(flying1);
	}
	
	protected abstract void terminate();

	
}
