package com.selflearn.tree.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public enum Sex {
	MALE(1),
	FEMALE(2);
	
	private int value;
	
	Sex(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	 public static int getByCode(int code) {
	        for (Sex sex : Sex.values()) {
	            if (sex.getValue() == code) {
	            	System.out.println(sex.toString());
	                return sex.value;
	            }
	        }
	        throw new IllegalArgumentException("Invalid code: " + code);
	    }
	
	
	
}
