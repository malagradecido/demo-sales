package com.demo.sales.bean;

public class CustomerBean {

	private int id;
	private String firstname;
	private String lastname;
	private String street;
	private String city;
	
	public CustomerBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerBean(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}



	public CustomerBean(int id) {
		super();
		this.id = id;
	}

	public CustomerBean(int id, String firstname, String lastname, String street, String city) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.city = city;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return String.format("CustomerDTO [id=%s, firstname=%s, lastname=%s, street=%s, city=%s]", id, firstname,
				lastname, street, city);
	}
	
}