package org.bc.reason.core;

/**
 * 限定二元组
 * A,B形式 A限定B,自然语言表达为
 * A的B(定性或者定量) ， 也有可能限定词放在后面    苹果都 ，都限定了苹果的数量
 * 
 * 限定分为定性和定量
 * 洋葱结构
 * 
 * 如果text是名称，通常是最终元素，如果不是则属于组合元素，并且此时应该做限定词用
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
