package ship;

import extraUtil.Vector;
import be.kuleuven.cs.som.annotate.*;
import space.*;

/**
 * comment!
 * @version 1.0
 * @author Joost Verplancke
 *
 */
public class Bullet extends Flying{

	public Bullet(double radius) {
		this(radius, null);
	}
	
	public Bullet(double radius, Ship shooter){
		super(radius, 0, 0, 0, 0, Flyer.BULLET);
		this.shooter = shooter;
	}
	
	public Bullet (Ship shooter, double radius, double speed){
		super(radius, Vector.add(shooter.getCoordinates(), new Vector(shooter.getAngle(), radius + shooter.getRadius(), Double.MAX_VALUE)), 
				new Vector(shooter.getAngle(), speed, World.LIGHTSPEED), Flyer.BULLET);
		this.shooter = shooter;
	}
	
	private final Ship shooter;

	@Override
	public double getMassDensity() {
		return RHO2;
	}

	@Override
	public void addToWorld(World world) {
		// TODO Deevid
		
	}

	@Override
	public double getMass() {
		return this.getSphericalMass();
	}

	@Immutable
	@Basic
	public Ship getShooter() {
		return shooter;
	}
	

	@Override
	protected void collideWithShip(Ship ship) {
		// TODO Deevid
		
	}

	@Override
	protected void collideWithBullet(Bullet bullet) {
		// TODO Deevid
		
	}

	@Override
	protected void collideWithAsteroid(Asteroid asteroid) {
		// TODO Deevid
		
	}


	@Override
	protected void terminate() {
		// TODO Deevid
		
	}
	
	private boolean ticked;
	
	@Override
	protected void collideWithBoundary(){
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
	
	

	@Override
	public void die() {
		this.terminate();
	}


}
