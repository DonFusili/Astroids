package asteroids;

import be.kuleuven.cs.som.annotate.*;

public class Ship implements IShip {

	/**
	 * @param coordinate
	 * @post If the given coordinate is valid, we make the new x coordinate equal to the given coordinate
	 * 		| if(isValidCoordinate(coordinate))
	 * 			then new.X == coordinate
	 * @throws IllegalArgumentException
	 * 		| If the given coordinate is not valid, we throw an exception for the caller to handle.
	 * 		| if(!isValidCoordinate(coordinate))
	 */
	public void setXCoordinate(double coordinate) throws IllegalArgumentException {
		if(!isValidCoordinate(coordinate)) throw new IllegalArgumentException();
		this.X = coordinate;
	}
	
	/**
	 * 
	 * @return The X Coordinate of this ship
	 * 			| result = this.X;
	 */
	@Basic
	public double getXCoordinate() {
		return this.X;
	}
	
	private double X;
	
	/**
	 * This method is used to adjust the Y coordinate appropriately
	 * @param coordinate
	 * @post If the given coordinate is valid, we make the new Y coordinate equal to the given coordinate
	 * 		| if(isValidCoordinate(coordinate))
	 * 			then new.Y == coordinate
	 * @throws IllegalArgumentException
	 * 		| If the given coordinate is not valid, we throw an exception for the caller to handle.
	 * 		| if(!isValidCoordinate(coordinate))
	 */
	public void setYCoordinate(double coordinate) throws IllegalArgumentException {
		if(!isValidCoordinate(coordinate)) throw new IllegalArgumentException();
		this.Y = coordinate;
	}
	
	
	/**
	 * 
	 * @return The Y Coordinate of this ship
	 * 			| result = this.Y;
	 */
	@Basic
	public double getYCoordinate() {
		return this.Y;
	}
	
	private double Y;
	
	
	/**
	 * 
	 * @param coordinate
	 * @return If the given coordinate lies between double.NEGATIVE_INFINITY and double.POSITIVE_INFINITY (both included), we return true, otherwise we return false
	 * 			| result = !(coordinate > Double.MAX_VALUE || coordinate < Double.MIN_VALUE || coordinate == Double.NaN);
	 */
	public static boolean isValidCoordinate(double coordinate){
		return !(coordinate > Double.MAX_VALUE || coordinate < Double.MIN_VALUE || coordinate == Double.NaN);
	}
	
	
	/**
	 * This method is used to set the horizontal speed appropriately 
	 * @param speed
	 * @post 
	 */
	public void setXSpeed(double speed) {
		
	}
	
	public static boolean isValidXSpeed(double speed) {
		
	}	
	
	public static boolean isValidYSpeed(double speed) {
		
	}	
}
