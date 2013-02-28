/**
 * We want to circumvene the fact that, even with double precision, numbers can't be represented
 * correctly in a computer, which might give problems when comparing them.
 * @author Joost Verplancke
 * @version 1.0
 *
 */
public abstract class ExtraMath {
	
	public static double errorMarge = 2e-50;
	
	
	/**
	 * 
	 * @param firstdouble
	 * @param secondouble
	 * @return We check if the given doubles are close enough together to consider them equal.
	 * 			| result = Math.abs(firstdouble - seconddouble) < getErrorMarge() || 
							Math.abs(seconddouble - firstdouble) < getErrorMarge();
	 */
	public static boolean compare(double firstdouble, double seconddouble){
		boolean result = Math.abs(firstdouble - seconddouble) < getErrorMarge() || 
							Math.abs(seconddouble - firstdouble) < getErrorMarge();
		return result;
	}


	private static double getErrorMarge() {
		return errorMarge;
	}
	
	//TODO: check + comment
	public static void setErrorMarge(double newMarge) throws IllegalArgumentException{
		if(!isValidErrorMarge(newMarge)) throw new IllegalArgumentException();
			errorMarge = newMarge;
	}
	
	
	//TODO: check + comment
	private static boolean isValidErrorMarge(double marge){
		boolean result = !Double.isNaN(marge);
		result = result && marge > 0;
		result = result && marge < 2e-10;
		return result;
	}
	
	//TODO: replace comparisons in rest of code
	//TODO: check if other methods are needed
}
