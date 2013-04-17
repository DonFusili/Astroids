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

	
	public Asteroid(double radius, Vector coordinates, Vector speeds){
		super(radius, coordinates, speeds, Flyer.ASTEROID);
		this.random = null;
	}
	
	public Asteroid(double radius, double xpos, double ypos, double xspeed, double yspeed){
		super(radius, new Vector(xpos, ypos), new Vector(xspeed, yspeed), Flyer.ASTEROID);
		this.random = null;
	}
	
	public Asteroid(double radius, double xpos, double ypos, double xspeed, double yspeed, Random random){
		this(radius, xpos, ypos, xspeed, yspeed);
		this.random = random;
	}
	
	public Asteroid(double radius, Vector coordinates, Vector speeds, Random random){
		super(radius, coordinates, speeds, Flyer.ASTEROID);
		this.random = random;
	}
	
	private Random random;
	
	
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
		//Nothing happens
		return;
	}

	@Override
	protected void collideWithBullet(Bullet bullet) {
		this.die();
	}

	@Override
	protected void collideWithAsteroid(Asteroid asteroid) {
		this.bounceOf(asteroid);
	}


	@Override
	protected void terminate() {
		this.getWorld().remove(this);
	}

	@Override
	public void die() {
		if(this.getRadius() >= 30){
			Asteroid kid1 = null;
			Asteroid kid2 = null;
			double speed = 1.5*this.getSpeed();
			double angle = 0;
			double angle2 = Math.PI;
			try{
				angle = this.getRandom().nextDouble()*Math.PI;
				angle2 = Math.PI + angle;
			}
			catch(NullPointerException e){ System.out.println("not randomized");}
			if(angle2 < 0) angle2 += 2*Math.PI;
			Vector coordinates = Vector.add(this.getCoordinates(), new Vector(angle, this.getRadius()/2, this.getRadius()));
			Vector coordinates2 = Vector.add(this.getCoordinates(), new Vector(angle2, this.getRadius()/2, this.getRadius()));
			Vector speeds = null;
			Vector speeds2 = null;
			try{
				speeds = new Vector(angle, speed, World.LIGHTSPEED);
				speeds2 = new Vector(angle2, speed, World.LIGHTSPEED);
			}
			catch(IllegalArgumentException e){
				speeds = new Vector(angle, World.LIGHTSPEED, World.LIGHTSPEED);
				speeds2 = new Vector(angle2, World.LIGHTSPEED, World.LIGHTSPEED);
			}
			kid1 = new Asteroid(this.getRadius()/2, coordinates, speeds, this.getRandom());
			kid2 = new Asteroid(this.getRadius()/2, coordinates2, speeds2, this.getRandom());
			this.getWorld().add(kid1);
			this.getWorld().add(kid2);
		}
		this.terminate();
		this.dead = true;
	}
	
	@Basic
	public Random getRandom(){
		return this.random;
	}

}
