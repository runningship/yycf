package org.bc.reason.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Word {

	@Id
	public Integer id;
	
	public String text;
}
