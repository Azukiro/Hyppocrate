package com.mktec.restjavaee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="users")
@NamedQuery(name = User.FIND_USER_BY_USERNAME_PASS, query="SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_USER_BY_USERNAME_PASS = "User.findUserByUsernameAndPassword";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 25, message = "1-25 letters")
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	@Column(name="firstname", columnDefinition = "VARCHAR(50)")
	private String firstName;
	
	@NotNull
	@Size(min = 1, max = 25, message = "1-25 letters")
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	@Column(name="lastname", columnDefinition = "VARCHAR(50)")
	private String lastName;
	
	@Column(name="email", nullable = false)
	@Email
	private String email;
	
	@NotNull
	@Size(min = 1, max = 25, message = "1-25 letters")
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	@Column(name="username", columnDefinition = "VARCHAR(50)")
	private String username;
	
	@NotNull
	@Size(min = 6, max = 12, message = "Must contain 6-12 letters")
	@Column(name="password")
	private String password;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
