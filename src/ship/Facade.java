package ship;

import java.util.Random;
import java.util.Set;

import space.*;


import extraUtil.Util;

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
			world.addShip(ship);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void addAsteroid(World world, Asteroid asteroid) {
		try{
			world.addAsteroid(asteroid);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
		
	}



	@Override
	public void removeShip(World world, Ship ship) {
		try{
			world.removeShip(ship);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}



	@Override
	public void removeAsteroid(World world, Asteroid asteroid) {
		try{
			world.removeAsteroid(asteroid);
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
		try{
			return ship.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipY(Ship ship) {
		try{
			return ship.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipXVelocity(Ship ship) {
		try{
			return ship.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipYVelocity(Ship ship) {
		try{
			return ship.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipRadius(Ship ship) {
		try{
			return ship.getRadius();
		}
		catch(Exeption e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipDirection(Ship ship) {
		try{
			return ship.getAngle();
		}
		catch(Exception e){
			throw new ModelException(NA);
		}
	}



	@Override
	public double getShipMass(Ship ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public World getShipWorld(Ship ship) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isShipThrusterActive(Ship ship) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setThrusterActive(Ship ship, boolean active) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void turn(Ship ship, double angle) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void fireBullet(Ship ship) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public double getAsteroidX(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidY(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidXVelocity(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidYVelocity(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidRadius(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidMass(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public World getAsteroidWorld(Asteroid asteroid) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public double getBulletX(Bullet bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletY(Bullet bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletXVelocity(Bullet bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletYVelocity(Bullet bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletRadius(Bullet bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletMass(Bullet bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public World getBulletWorld(Bullet bullet) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Ship getBulletSource(Bullet bullet) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public double getWorldWidth(World world) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getWorldHeight(World world) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public Ship createShip(double x, double y, double xVelocity,
			double yVelocity, double radius, double direction, double mass) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isShip(Object o) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius, Random random) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isAsteroid(Object o) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isBullets(Object o) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean overlap(IShip ship1, IShip ship2) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public double getTimeToCollision(IShip ship1, IShip ship2) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double[] getCollisionPosition(IShip ship1, IShip ship2) {
		// TODO Auto-generated method stub
		return null;
	}

}
