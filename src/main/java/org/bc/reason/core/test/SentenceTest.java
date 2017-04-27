package org.bc.reason.core.test;

import org.bc.reason.core.Diploid;
import org.bc.reason.core.Expressor;
import org.bc.reason.core.Triples;

public class SentenceTest {

	/**
	 * 尽量用Triples来组装每个句子
	 */
	public static void main(String[] args){
		//(榴莲)(都有)(一种(奇怪的味道))
		Diploid d1 = new Diploid(new Diploid("奇怪") , new Diploid("味道"));
		Diploid d2 = new Diploid(new Diploid("一种") , d1 );
		Triples tri = new Triples();
		tri.subject = new Diploid(new Diploid("榴莲") , new Diploid("都"));
		tri.predict = new Diploid("有" );
		tri.object = d2;
		
		Expressor exp = new Expressor();
		String text = exp.expressTriples(tri);
		System.out.println(text);
	}
	
}
