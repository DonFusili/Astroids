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
 * @version 1.0
 * @author Joost Verplancke
 *
 */

public class FacadeImpl implements IFacade<WorldImpl, ShipImpl, AsteroidImpl, BulletImpl> {

	public FacadeImpl() {

	}



	@Override
	public WorldImpl createWorld(double width, double height) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public Set<ShipImpl> getShips(WorldImpl world) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Set<AsteroidImpl> getAsteroids(WorldImpl world) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Set<BulletImpl> getBullets(WorldImpl world) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void addShip(WorldImpl world, ShipImpl ship) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void addAsteroid(WorldImpl world, AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeShip(WorldImpl world, ShipImpl ship) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void removeAsteroid(WorldImpl world, AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void evolve(WorldImpl world, double dt,
			CollisionListener collisionListener) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public double getShipX(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getShipY(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getShipXVelocity(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getShipYVelocity(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getShipRadius(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getShipDirection(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getShipMass(ShipImpl ship) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public WorldImpl getShipWorld(ShipImpl ship) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isShipThrusterActive(ShipImpl ship) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setThrusterActive(ShipImpl ship, boolean active) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void turn(ShipImpl ship, double angle) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void fireBullet(ShipImpl ship) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public double getAsteroidX(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidY(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidXVelocity(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidYVelocity(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidRadius(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getAsteroidMass(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public WorldImpl getAsteroidWorld(AsteroidImpl asteroid) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public double getBulletX(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletY(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletXVelocity(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletYVelocity(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletRadius(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getBulletMass(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public WorldImpl getBulletWorld(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ShipImpl getBulletSource(BulletImpl bullet) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public double getWorldWidth(WorldImpl world) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getWorldHeight(WorldImpl world) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public ShipImpl createShip(double x, double y, double xVelocity,
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
	public AsteroidImpl createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public AsteroidImpl createAsteroid(double x, double y, double xVelocity,
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
