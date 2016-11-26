package org.bc.reason.core.utils;

import org.apache.commons.lang.StringUtils;
import org.bc.reason.core.Diploid;
import org.bc.reason.core.Quantifier;

public class DiploidHelper {

	public boolean isQuantifier(Diploid diploid){
		if(diploid==null){
			return false;
		}
		if(diploid.text!=null){
			return isQuantifier(diploid.text);
		}
		return false;
	}
	
	public boolean isQuantifier(String text){
		if(StringUtils.isEmpty(text)){
			return false;
		}
		if(Quantifier.All.name.equals(text) || Quantifier.None.name.equals(text) || Quantifier.One.name.equals(text) || Quantifier.Some.name.equals(text)){
			return true;
		}
		return false;
	}
}
