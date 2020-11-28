package com.sneha.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDao {
    @Id
    //@OneToOne
    private String employeeCode;
    @Column
    private String name;
    @Column
    private String username;
	@Column
    @JsonIgnore
    private String password;
 
    
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="employeeCode")},inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="id")})
	private Set<UserRoles> roles;

    
    public UserDao() {
		super();
	}
    
	public UserDao(String employeeCode, String name, String username, String password) {
		super();
		this.employeeCode = employeeCode;
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public Set<UserRoles> getRoles() {
		return roles;
	}
	public void setRole(Set<UserRoles> role) {
		this.roles = role;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

   
}

