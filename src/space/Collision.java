package space;

import be.kuleuven.cs.som.annotate.*;
import extraUtil.Util;

public class Collision implements Comparable<Collision> {
	
	public Collision(Flying flying1, Flying flying2, double delay){
		if(flying1 == null || flying2 == null) throw new NullPointerException();
		if(Util.fuzzyLessThanOrEqualTo(delay, 0));
		this.flying1 = flying1;
		this.flying2 = flying2;
		this.delay = delay;
	}

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
