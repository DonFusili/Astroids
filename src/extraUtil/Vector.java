package extraUtil;

import be.kuleuven.cs.som.annotate.*;

@Value
public class Vector {

	
	public Vector(double xValue, double yValue) throws IllegalArgumentException{
		if(!isValidFieldValue(xValue) || !isValidFieldValue(yValue)) throw new IllegalArgumentException();
		x = xValue;
		y = yValue;
	}
	
	@Immutable
	@Basic
	public double getX(){
		return x;
	}
	
	private final double x;
	
	@Immutable
	@Basic
	public double getY(){
		return y;
	}
	
	private final double y;
	
	public double compareXTo(Vector vec){
		return this.getX() - vec.getX();
	}
	
	public double compareYTo(Vector vec){
		return this.getY() - vec.getY();
	}
	
	
	public static boolean isValidFieldValue(double test){
		return (Double)test != null && !Double.isNaN((Double)test);
	}
	
	public static Vector add(Vector vec1, Vector vec2){
		return new Vector(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY());
	}
	
	public static boolean equals(Vector vec1, Vector vec2){
		return (vec1.getX() == vec2.getX() && vec1.getY() == vec2.getY());
	}
	
	
	public static double dotProduct(Vector vec1, Vector vec2){
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY();
	}

}
