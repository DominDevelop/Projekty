package com.domain.app;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class Uzytkownik {
	
	@NotEmpty
	@Size(min = 2, max = 10)
	private String user;
	
	@NotEmpty
	@Size(min = 2, max = 10)
	private String password;
	private boolean login;
	private int id;
	private String role;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String toString() {
        return "Person(Name: " + this.user + ", Age: " + this.password + ")";
    }
}
