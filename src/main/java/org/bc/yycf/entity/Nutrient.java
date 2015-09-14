package org.bc.yycf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Nutrient {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	public Integer foodId;
	
	public String name;
	
	public Float value;
	
	public String unit;
	
	public String leibie;
}
