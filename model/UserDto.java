package com.sneha.model;



public class UserDto {
	private String employeeCode;
    private String username;
    private String password;
    private String name;
    private String role;
    
    public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
		this.name = name;
	}
    public String getName() {
        return name;
    }

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
