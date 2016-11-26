package org.bc.yycf.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SearchHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	public String openId;
	
	public Integer foodId;
	
	public Date time;
}
