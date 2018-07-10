package com.giladsagi.privateteacher;

public class teacherSubject {

	public teacherSubject() {
	}

	public void setSubject(String subject) {
	    this.subject = subject;
	}

	public void setPrice(int price) {
	    this.price = price;
	}
	
	public void setSize(int size) {
	    this.size = size;
	}

	private String subject;
	private int price;
	private int size;

	public teacherSubject(String subject, int price, int size) {
	    this.subject = subject;
	    this.price = price;
	    this.size = size;
	}
	 public String getSubject() { 
	     return subject;
	 }

	@Override
	public String toString() {
	    return size + ".  "  + subject + "     " + price;
	}

	public int getPrice() {
	    return price;
	}
	
	public int getSize() {
	    return size;
	}
}
