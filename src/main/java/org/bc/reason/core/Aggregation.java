package org.bc.reason.core;

public class Aggregation {
	
	public String name;
	
	private Aggregation(String name){
		this.name = name;
	}
	
	public static final Aggregation Set = new Aggregation("set");
	
	public static final Aggregation Elem = new Aggregation("elem");
}
