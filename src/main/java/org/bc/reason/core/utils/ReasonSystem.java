package org.bc.reason.core.utils;

import org.bc.reason.core.Diploid;
import org.bc.reason.core.Operator;
import org.bc.reason.core.Quantifier;
import org.bc.reason.core.Triples;

public class ReasonSystem {

	public static void init(){
		// add this to db tomorrow
		new Triples().setSubject(new Diploid("是")).setPredict(new Diploid(Operator.Is.name)).setObject(new Diploid(Operator.Is.name) );
		new Triples().setSubject(new Diploid("一")).setPredict(new Diploid("是")).setObject(new Diploid(Quantifier.One.name) );
	}
}
