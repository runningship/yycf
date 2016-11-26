package org.bc.reason.core;

import org.apache.commons.lang.StringUtils;

public class Expressor {

	public String expressDiploid(Diploid diploid){
		if(StringUtils.isNotEmpty(diploid.text)){
			return diploid.text;
		}
		if(diploid.filter!=null && diploid.target!=null){
			
			return expressDiploid(diploid.filter)+"的"+expressDiploid(diploid.target);
		}else if(diploid.filter==null && diploid.target==null){
			return "";
		}else if(diploid.filter==null && diploid.target!=null){
			return expressDiploid(diploid.target);
		}else{
			//wrong
			return "?";
		}
	}
	
	public String expressTriples(Triples triples){
		String result = "";
		if(triples.subject!=null){
			result +=  expressDiploid(triples.subject);
		}
		if(triples.predict!=null){
			result +=  expressDiploid(triples.predict);
		}
		if(triples.object!=null){
			result +=  expressDiploid(triples.object);
		}
		return result;
	}
}
