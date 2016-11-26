package org.bc.reason.core;

/**
 * 限定二元组
 * A,B形式 A限定B,自然语言表达为
 * A的B(定性)或BA(定量)
 * 
 * 限定分为定性和定量
 */
public class Diploid {

	//当text!=null时，filter和target无效
	public String text;
	
	public Diploid filter;
	
	public Diploid target;

	public Diploid(String text){
		this.text = text;
	}
	
	public Diploid(Diploid filter , Diploid target){
		this.filter = filter;
		this.target = target;
	}
}
