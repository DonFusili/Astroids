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
		// TODO Auto-generated method stub
		
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
