package asteroids;

/**
 * Classes for representing ships should implement <code>IShip</code>. Do not
 * modify this file.
 */
public interface IShip {

	double getXCoordinate();

	double getYCoordinate();

	double getXSpeed();

	double getYSpeed();

	double getRadius();

	double getAngle();

	void move(double dt);

	void thrust(double amount);

	void turn(double angle);
	

}
