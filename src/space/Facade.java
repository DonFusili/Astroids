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
	public World createWorld(double width, double height) {
		try{
			return new World(width, height);			
		}
		catch(Exception e){
			throw new ModelException(e);
		}

	}



	@Override
	public Set<Ship> getShips(World world) {
		try{
			return world.getShips();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Set<Asteroid> getAsteroids(World world) {
		try{
			return world.getAsteroids();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Set<Bullet> getBullets(World world) {
		try{
			return world.getBullets();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public void addShip(World world, Ship ship) {
		try{
			world.add(ship);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void addAsteroid(World world, Asteroid asteroid) {
		try{
			world.add(asteroid);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public void removeShip(World world, Ship ship) {
		try{
			world.remove(ship);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void removeAsteroid(World world, Asteroid asteroid) {
		try{
			world.remove(asteroid);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public void evolve(World world, double dt,
			CollisionListener collisionListener) {
		try{
			world.evolve(dt, collisionListener);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public double getShipX(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipY(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipXVelocity(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipYVelocity(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipRadius(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getRadius();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipDirection(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getAngle();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipMass(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getMass();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public World getShipWorld(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
			return ship.getWorld();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public boolean isShipThrusterActive(Ship ship) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
		return ship.isThrusterActive();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public void setThrusterActive(Ship ship, boolean active) {
		if( ship == null) throw new ModelException(new NullPointerException());
		try{
		ship.setThrusting(active);
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public void turn(Ship ship, double angle) {
		if(ship == null) throw new ModelException(new NullPointerException());
		try{
		ship.turn(angle);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void fireBullet(Ship ship) {
		if(ship == null) throw new ModelException(new NullPointerException());
		try{
		ship.fireBullet();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public double getAsteroidX(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidY(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidXVelocity(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidYVelocity(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidRadius(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getRadius();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getAsteroidMass(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getMass();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public World getAsteroidWorld(Asteroid asteroid) {
		if(asteroid == null) throw new ModelException(new NullPointerException());
		try{
			return asteroid.getWorld();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletX(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletY(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletXVelocity(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletYVelocity(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletRadius(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getRadius();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getBulletMass(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getMass();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public World getBulletWorld(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getWorld();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public Ship getBulletSource(Bullet bullet) {
		if(bullet == null) throw new ModelException(new NullPointerException());
		try{
			return bullet.getShooter();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getWorldWidth(World world) {
		if(world == null) throw new ModelException(new NullPointerException());
		try{
			return world.getWidth();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getWorldHeight(World world) {
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
			double yVelocity, double radius, double direction, double mass) {
		try{
			return new Ship(x, y, xVelocity, yVelocity, radius, direction, mass);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public boolean isShip(Object o) {
		if(o == null) throw new ModelException(new NullPointerException());
		return o instanceof Ship;
	}



	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius) {
		try{
			return new Asteroid(radius, x, y, xVelocity, yVelocity);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius, Random random) {
		return null;
	}



	@Override
	public boolean isAsteroid(Object o) {
		if(o == null) throw new ModelException(new NullPointerException());
		return o instanceof Asteroid;
	}



	@Override
	public boolean isBullets(Object o) {
		if(o == null) throw new ModelException(new NullPointerException());
		return o instanceof Bullet;
	}



	@Override
	public boolean overlap(IShip ship1, IShip ship2) {
		try{
			return Flying.overlap((Ship)ship1, (Ship)ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public double getTimeToCollision(IShip ship1, IShip ship2) {
		try{
			return Flying.getTimeToCollision((Ship)ship1, (Ship)ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public double[] getCollisionPosition(IShip ship1, IShip ship2) {
		try{
			return Flying.getCollisionPoint((Ship)ship1, (Ship)ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

}
