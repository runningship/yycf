package org.bc.reason.core.test;

import java.util.ArrayList;
import java.util.List;

import org.bc.reason.core.Triples;

public class SentenceAnalysisTest {

	/**
	 * 尽量用Triples来组装每个句子
	 */
	public static void main(String[] args){
		//(榴莲)(都有)(一种(奇怪的味道))
		List<String> terms = new ArrayList<String>();
		terms.add("榴莲");
		terms.add("全部");
		terms.add("都");
		terms.add("有");
		terms.add("一种");
		terms.add("奇怪");
		terms.add("的");
		terms.add("味道");
		
		Triples triple = new Triples();
		for(String term : terms){
			
		}
	}
	
}
