package com.itvedant.skincareproducts_store.dao;


import java.util.List;

public class RegisterDao {
	
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String mobile;
	private List<String> roles;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "RegisterDao [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", mobile=" + mobile + ", roles=" + roles + "]";
	}
	
	
}