package asteroids.model;

import asteroids.IFacade;
import asteroids.IShip;
import asteroids.ModelException;
import asteroids.Util;

/**
 * @version 1.0
 * @author Joost Verplancke
 *
 */

public class Facade implements IFacade {

	public Facade() {

	}

	@Override
	public IShip createShip() throws ModelException{
		try{
		IShip ship = new Ship(0, 0, 0, 0, 0, 0);
		return ship;
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public IShip createShip(double x, double y, double xVelocity,
			double yVelocity, double radius, double angle) throws ModelException {
		try{
		IShip ship = new Ship(x, y, xVelocity, yVelocity, radius, angle);
		return ship;
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getX(IShip ship) throws ModelException {
		try{
	
		return ship.getXCoordinate();
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public double getY(IShip ship) throws ModelException {
		try{
	
		return ship.getYCoordinate();
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public double getXVelocity(IShip ship) throws ModelException {
		try{
		return ship.getXSpeed();
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public double getYVelocity(IShip ship) throws ModelException {
		try{
	
		return ship.getYSpeed();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getRadius(IShip ship) throws ModelException {
		try{
	
		return ship.getRadius();
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public double getDirection(IShip ship) throws ModelException {
		try{
	
		return ship.getAngle();
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public void move(IShip ship, double dt) throws ModelException {
		try{	
		ship.move(dt);
		}
		catch(Exception e){
			throw new ModelException(e);
		}		
	}

	@Override
	public void thrust(IShip ship, double amount) throws ModelException {
		try{	
		ship.thrust(amount);
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public void turn(IShip ship, double angle) throws ModelException {
		try{
	
		boolean checkPre = Util.fuzzyLessThanOrEqualTo(-1* Math.PI, angle) && Util.fuzzyLessThanOrEqualTo(angle, Math.PI);
		assert checkPre;
		ship.turn(angle);
		}
		catch(Exception e){
			throw new ModelException("This exception should never be thrown");
		}
	}

	@Override
	public double getDistanceBetween(IShip ship1, IShip ship2) throws ModelException {
		try{
	
			return Ship.getDistanceBetween((Ship) ship1, (Ship) ship2);
		
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public boolean overlap(IShip ship1, IShip ship2) throws ModelException {
		try{
	
		return ((Ship) ship1).overlap((Ship) ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public double getTimeToCollision(IShip ship1, IShip ship2) throws ModelException {
		try{
	
		return ((Ship) ship1).getTimeToCollision((Ship) ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

	@Override
	public double[] getCollisionPosition(IShip ship1, IShip ship2) throws ModelException {
		try{
	
		return ((Ship) ship1).getCollisionPosition((Ship) ship2);
		}
		catch(Exception e){
			throw new ModelException(e);
		}
	}

}
