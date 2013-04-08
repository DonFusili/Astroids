package extraUtil;

import be.kuleuven.cs.som.annotate.*;

@Value
public class Vector {

	
	public Vector(double xValue, double yValue) throws IllegalArgumentException{
		if(!isValidFieldValue(xValue) || !isValidFieldValue(yValue)) throw new IllegalArgumentException();
		x = xValue;
		y = yValue;
	}
	
	public Vector (double angle, double length, double maxlength) throws IllegalArgumentException {
		if(!isValidAngle(angle) || !isValidLength(length, maxlength)) throw new IllegalArgumentException();
		x = length * Math.cos(angle);
		y = length * Math.sin(angle);
	}
	
	public static boolean isValidAngle(double angle){
		return true;
	}
	
	public static boolean isValidLength(double length, double maxlength){
		return length <= maxlength;
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

	public Vector getDeepClone() {
		return new Vector(this.getX(), this.getY());
	}
	
	public static Vector difference(Vector vec1, Vector vec2){
		return new Vector(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY());
	}
	
	public static double square(Vector vec){
		return Vector.dotProduct(vec, vec);
	}
	
	public static Vector add(Vector[] args){
		double x = 0;
		double y = 0;
		for(Vector vector : args){
			x += vector.getX();
			y += vector.getY();
		}
		return new Vector(x, y);
	}
	

}
