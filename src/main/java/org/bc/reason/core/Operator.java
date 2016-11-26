package org.bc.reason.core;

public class Operator {
	
	public String name;
	
	private Operator(String name){
		this.name = name;
	}
	
	public static final Operator Is= new Operator("is");
	
	public static final Operator BelongsTo = new Operator("belongTo");
	
	public static final Operator Sub = new Operator("sub");
	
	public static final Operator Not = new Operator("not");
}
