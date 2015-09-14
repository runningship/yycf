package org.bc.yycf.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Food {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	public String name;
	
	public String alias;
	
	public String men;
	
	public String gang;
	
	public String mu;
	
	public String ke;
	
	public String shu;
	
	public String zhong;
	
	//简介
	public String conts;
	
	public Date addtime;
	
	public Date updatetime;
	
	public String imgPath;
	
	public String ref;
	
	public String leibie;
	
	public String pyShort;
	
	public String py;
}
