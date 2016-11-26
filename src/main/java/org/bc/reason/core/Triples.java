package org.bc.reason.core;

/**
 * 三段式
 * 	A X B
 * 如狗会游泳，苹果是水果
 */
public class Triples {

	public Diploid subject;
	
	public Diploid predict;
	
	public Diploid object;

	public Triples setSubject(Diploid subject) {
		this.subject = subject;
		return this;
	}

	public Triples setPredict(Diploid predict) {
		this.predict = predict;
		return this;
	}

	public Triples setObject(Diploid object) {
		this.object = object;
		return this;
	}
	
}
