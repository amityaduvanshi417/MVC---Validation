package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="user")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotBlank(message = "Name is required")
	@Size(min = 3,message = "Name is Min 3 characters")
    private String name;
	@NotEmpty(message = "Email is required")
	@Email(message = "Invalid Email")
    private String email;
	@NotBlank(message = "Password is required")
	@Size(min = 6,message = "Min 6 characters is required")
    private String password;
	@Size(min = 10,max = 11,message = "Invalid Mobile Number")
    private String mobile;
	@NotBlank(message = "Role is required")
    private String role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attendances> attendances;

	// Getter for attendances
	public List<Attendances> getAttendances() {
		return attendances;
	}


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", mobile='" + mobile + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
