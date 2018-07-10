package com.giladsagi.privateteacher;

public class teacherCity {

	public teacherCity() {
	}

	public void setCity(String city) {
	    this.city = city;
	}
	
	public void setSize(int size) {
	    this.size = size;
	}

	private String city;
	private int size;

	public teacherCity(String city, int size) {
	    this.city = city;
	    this.size = size;
	}
	 public String getCity() { 
	     return city;
	 }
	 
	 public int getSize() { 
	     return size;
	 }

	@Override
	public String toString() {
	    return size + ".  " + city;
	}

}
