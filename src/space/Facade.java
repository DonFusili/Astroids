package space;

import java.util.Random;
import java.util.Set;

import ship.Bullet;
import ship.Ship;
// import space.*;


// import extraUtil.Util;

import asteroids.CollisionListener;
import asteroids.IFacade;
import asteroids.IShip;
import asteroids.ModelException;

/**
 * @version 2.0
 * @author Joost Verplancke
 *
 */

public class Facade implements IFacade<World, Ship, Asteroid, Bullet> {
	
	public final static String NA = "This exception should never be thrown";

	public Facade() {

	}



	@Override
	public World createWorld(double width, double height) throws ModelException {
		try{
			return new World(width, height);			
		}
		catch(Exception e){
			throw new ModelException(e);
		}

	}



	@Override
	public Set<Ship> getShips(World world) throws ModelException  {
		try{
			return world.getShips();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Set<Asteroid> getAsteroids(World world) throws ModelException {
		try{
			return world.getAsteroids();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Set<Bullet> getBullets(World world) throws ModelException {
		try{
			return world.getBullets();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public void addShip(World world, Ship ship) throws ModelException {
		try{
			world.add(ship);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void addAsteroid(World world, Asteroid asteroid) throws ModelException {
		try{
			world.add(asteroid);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public void removeShip(World world, Ship ship) throws ModelException {
		try{
			world.remove(ship);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void removeAsteroid(World world, Asteroid asteroid) throws ModelException {
		try{
			world.remove(asteroid);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public void evolve(World world, double dt,
			CollisionListener collisionListener) throws ModelException {
		try{
			world.evolve(dt, collisionListener);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public double getShipX(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipY(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipXVelocity(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipYVelocity(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipRadius(Ship ship) throws ModelException  {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getRadius();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipDirection(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getAngle();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipMass(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getMass();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public World getShipWorld(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getWorld();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public boolean isShipThrusterActive(Ship ship) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
		return ship.isThrusterActive();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public void setThrusterActive(Ship ship, boolean active) throws ModelException {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			ship.setThrusting(active);
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public void turn(Ship ship, double angle) throws ModelException {
		if(ship == null) throw new ModelException(new NullPointerException());
		try{
		ship.turn(angle);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void fireBullet(Ship ship) throws ModelException {
		if(ship == null) throw new ModelException(new NullPointerException());
		try{
			ship.fireBullet();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public double getAsteroidX(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidY(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidXVelocity(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidYVelocity(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidRadius(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getRadius();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidMass(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getMass();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public World getAsteroidWorld(Asteroid asteroid) throws ModelException {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getWorld();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletX(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletY(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletXVelocity(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletYVelocity(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletRadius(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getRadius();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletMass(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getMass();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public World getBulletWorld(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getWorld();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Ship getBulletSource(Bullet bullet) throws ModelException {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getShooter();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getWorldWidth(World world) throws ModelException {
		if(world == null) throw new ModelException(new NullPointerException());
		try{
			return world.getWidth();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getWorldHeight(World world) throws ModelException {
		if(world == null) throw new ModelException(new NullPointerException());
		try{
			return world.getHeight();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Ship createShip(double x, double y, double xVelocity,
			double yVelocity, double radius, double direction, double mass) throws ModelException {
		try{
			return new Ship(x, y, xVelocity, yVelocity, radius, direction, mass);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public boolean isShip(Object o) throws ModelException {
		if(o == null) throw new ModelException(new NullPointerException());
		return o instanceof Ship;
	}



	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius) throws ModelException {
		try{
			return new Asteroid(radius, x, y, xVelocity, yVelocity);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius, Random random) throws ModelException {
		try{
			return new Asteroid(radius,x,y,xVelocity,yVelocity,random);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public boolean isAsteroid(Object o) throws ModelException {
		if(o == null) throw new ModelException(new NullPointerException());
		return o instanceof Asteroid;
	}



	@Override
	public boolean isBullets(Object o) throws ModelException {
		if(o == null) throw new ModelException(new NullPointerException());
		return o instanceof Bullet;
	}



	@Override
	public boolean overlap(IShip ship1, IShip ship2) throws ModelException {
		try{
			return Flying.overlap((Ship)ship1, (Ship)ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public double getTimeToCollision(IShip ship1, IShip ship2) throws ModelException {
		try{
			return Flying.getTimeToCollision((Ship)ship1, (Ship)ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public double[] getCollisionPosition(IShip ship1, IShip ship2) throws ModelException {
		try{
			return Flying.getCollisionPoint((Ship)ship1, (Ship)ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

}
