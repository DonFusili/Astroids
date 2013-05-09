package space;

import be.kuleuven.cs.som.annotate.*;
import extraUtil.Util;

/**
 * A datacontainer for collisions. Collisions are defined by two flying objects which will collide and a time (which we call delay) when they will collide starting from this moment.
 * @author Deevid De Meyer & Joost Verplancke
 * @version 1.0
 * @Invar ...
 * 			| this.getFlying1() != null
 * @Invar ...
 * 			| this.getFlying2() != null
 * 
 *
 */
public class Collision implements Comparable<Collision> {
	
	/**
	 * The constructor for the datacontainer, initializes all variables.
	 * @param flying1
	 * @param flying2
	 * @param delay
	 * @post ...
	 * 			| new.getFlying1() == flying1
	 * @post ...
	 * 			| new.getFlying2() == flying2
	 * @post ...
	 * 			| new.getDelay() == delay
	 */
	public Collision(Flying flying1, Flying flying2, double delay){
		if(flying1 == null || flying2 == null) throw new NullPointerException();
		if(Util.fuzzyLessThanOrEqualTo(delay, 0));
		this.flying1 = flying1;
		this.flying2 = flying2;
		this.delay = delay;
	}

	/**
	 * The compare function which we have to override in order to fully extend the interface Comparable.
	 * This method compares two collisions by their delay. A collision is said to be natural smaller than another collision if the delay is smaller.
	 * Collisions are equal if they will occur at the same moment in time.
	 * @param collision | The collision with which we compare this object.
	 * @return ...
	 * 			| if(collision.getDelay() < this.getDelay()) result = 1
	 * 			  if(collision.getDelay() == this.getDelay() result = 0
	 * 			  if(collision.getDelay() > this.getDelay()) result = -1
	 */
	@Override
	public int compareTo(Collision collision) {
		if(Util.fuzzyEquals(this.getDelay(), collision.getDelay())) return 0;
		return (int)Math.signum(this.getDelay() - collision.getDelay());
	}
	
	public boolean equals(Collision collision){
		return this.compareTo(collision) == 0;
	}
	
	
	private double delay;
	
	private final Flying flying1;
	
	private final Flying flying2;
	
	public double getDelay(){
		return delay;
	}
	
	/**
	 * 
	 * @param dt
	 * @post ...
	 * 			| new.getDelay() == this.getDelay() - dt
	 */
	public void shortenDelayWith(double dt){
		this.delay = this.delay - dt;
	}
	
	@Immutable
	@Basic
	public Flying getFlying1(){
		return this.flying1;
	}
	
	@Immutable
	@Basic
	public Flying getFlying2(){
		return this.flying2;
	}
	

}
