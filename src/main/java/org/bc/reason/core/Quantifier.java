package org.bc.reason.core;

/**
 * 数量词
 * @author yexinzhou
 */
public class Quantifier {
	
	public String name;
	
	private Quantifier(String name){
		this.name = name;
	}
	
	public static final Quantifier All = new Quantifier("all");
	
	public static final Quantifier One = new Quantifier("one");
	
	public static final Quantifier Some = new Quantifier("some");
	
	public static final Quantifier None = new Quantifier("none");
}
