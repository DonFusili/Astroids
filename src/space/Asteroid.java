package space;

import java.util.Random;

import be.kuleuven.cs.som.annotate.*;

import ship.Bullet;
import ship.Ship;

import extraUtil.*;


/**
 * 
 * @version 1.0
 * @author Joost Verplancke
 * TODO: comment
 */
public class Asteroid extends Flying {

	public Asteroid(double radius) {
		this(radius, 0, 0, 0, 0);
		childsangle = 0;
	}
	
	public Asteroid(double radius, Vector coordinates, Vector speeds){
		super(radius, coordinates, speeds, Flyer.ASTEROID);
		childsangle = 0;
	}
	
	public Asteroid(double radius, double xpos, double ypos, double xspeed, double yspeed){
		super(radius, new Vector(xpos, ypos), new Vector(xspeed, yspeed), Flyer.ASTEROID);
		childsangle = 0;
	}
	
	public Asteroid(double radius, double xpos, double ypos, double xspeed, double yspeed, Random random){
		this(radius, xpos, ypos, xspeed, yspeed);
		this.childsangle = random.nextDouble() * 2 * Math.PI;
	}
	
	private double childsangle;
	
	@Basic
	public double getChildsangle(){
		return childsangle;
	}
	
	public double getMass() {
		return this.getSphericalMass();
	}

	@Override
	public double getMassDensity() {
		return RHO1;
	}

	@Override
	public void addToWorld(World world) throws IllegalStateException, IllegalArgumentException{
		if(! this.isAvailableToAdd()) throw new IllegalStateException();
		world.add(this);
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
	protected void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		this.terminate();
	}

}
