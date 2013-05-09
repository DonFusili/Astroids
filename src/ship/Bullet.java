package ship;

import extraUtil.Vector;
import asteroids.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import space.*;

/**
 * comment!
 * @version 1.0
 * @author Joost Verplancke
 *
 */
public class Bullet extends Flying{

	/**
	 * Constructor for bullet
	 * @param shooter
	 * @param radius
	 * @param speed
	 * @effect 
	 * | super(radius, Vector.add(shooter.getCoordinates(), new Vector(shooter.getAngle(), radius + shooter.getRadius(), Double.MAX_VALUE)), 
	 * | new Vector(shooter.getAngle(), speed, World.LIGHTSPEED), Flyer.BULLET);
	 */
	public Bullet (Ship shooter, double radius, double speed){
		super(radius, Vector.add(shooter.getCoordinates(), new Vector(shooter.getAngle(), radius + shooter.getRadius(), Double.MAX_VALUE)), 
				new Vector(shooter.getAngle(), speed, World.LIGHTSPEED), Flyer.BULLET);
		this.shooter = shooter;
	}
	
	private final Ship shooter;

	/**
	 * Returns mass density
	 * @return RHO2
	 */
	@Override
	public double getMassDensity() {
		return RHO2;
	}

	/**
	 * Adds the bullet to the world
	 * @param world
	 * 			Defines which world the bullet is added to
	 * @post this.getWorld() == world
	 * @throws IllegalStateException
	 * 			The bullet is already assigned to a world
	 * 		| !this.isAvailableToAdd()
	 */
	@Override
	public void addToWorld(World world) {
		
	}

	/**
	 * Returns the mass of the bullet using the getsphericalmass method, which calculates the mass from the radius and the density
	 * @return this.getSphericalMass()
	 */
	@Override
	public double getMass() {
		return this.getSphericalMass();
	}

	/**
	 * Returns the ship that shot the bullet
	 * @return shooter
	 */
	@Immutable
	@Basic
	public Ship getShooter() {
		return shooter;
	}
	
	/**
	 * Handles a collision with a ship by killing the bullet
	 * @param ship
	 * @effect this.getWorld().remove(this)
	 */
	@Override
	protected void collideWithShip(Ship ship) {
		this.die();
	}
	
	/**
	 * Handles a collision with a bullet by killing the bullet
	 * @param bullet
	 * @effect this.getWorld().remove(this)
	 */
	@Override
	protected void collideWithBullet(Bullet bullet) {
		this.die();
	}

	/**
	 * Handles a collision with an asteroid by killing the bullet
	 * @param asteroid
	 * 		The asteroid to check collision with
	 * @effect this.getWorld().remove(this)
	 */
	@Override
	protected void collideWithAsteroid(Asteroid asteroid) {
		this.die();
	}

	/**
	 * Terminates the bullet by removing it from the world
	 * @effect this.getWorld().remove(this)
	 */
	@Override
	protected void terminate() {
		this.getWorld().remove(this);
	}
	
	private boolean ticked;
	
	/**
	 * Handles a collision of a bullet with the boundary
	 * @post 
	 * 		| invertx = (this.getXSpeed() > 0 && ((this.getXCoordinate() + this.getRadius()) >= this.getWorld().getWidth())) ||
	 * 		  (this.getXSpeed() < 0 &&((this.getXCoordinate() - this.getRadius()) <= (-1 * this.getWorld().getWidth())))
	 * 		| inverty = (this.getYSpeed() > 0 && ((this.getYCoordinate() + this.getRadius()) >= this.getWorld().getHeight())) ||
	 * 		  (this.getYSpeed() < 0 && ((this.getYCoordinate() - this.getRadius()) <= (-1 * this.getWorld().getHeight())))
	 * @post if either invertx or inverty are true the method checks if the bullet has bounced before, if it has it kills the bullet,
	 * 		 otherwise it flags that the bullet has bounced
	 * 		| if(invertx || inverty)
	 * 			then
	 * 				@Eff if(ticked) then this.die(); return;
	 *				@Eff ticked = true;		
	 * @post If the bullet bounces against a vertical wall, it inverts the horizontal speed
	 *  	| if(invertx) then new.this.getXSpeed = -1 * this.getXSpeed;
	 * @post If the bullet bounces against a horizontal wall, it inverts the horizontal speed
	 *		| if(inverty) then new.this.getYSpeed = -1 * this.getYSpeed;
	 * @post The new speeds are set, and we recalibrate the collision detections
	 *		| this.setSpeeds(new Vector(new.this.getXSpeed, new.this.getYSpeed));
	 *		| this.getWorld().recalibrate(this);
	 */
	
	@Override
	protected void collideWithBoundary(CollisionListener collisionListener){
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
			if(ticked) {die(); return;}
			ticked = true;
			if(invertx) newx = -1 * newx;
			if(inverty) newy = -1 * newy;
			this.setSpeeds(new Vector(newx, newy));
			this.getWorld().recalibrate(this);
		}
	}
	
	
	/**
	 * Kills the bullet by terminating it
	 * @effect this.getWorld().remove(this)
	 */
	@Override
	public void die() {
		this.terminate();
	}


}