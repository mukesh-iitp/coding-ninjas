package com.CodingNinjas.TaxEase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 1. Add the lombok annotations for getters and setters
// 2. Add AllArgsConstructor and NoArgsConstructor

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	/**
	 # Add the following attributes
	 1. username: String
	 2. password: String
	 3. age: int
	 4. role: String
	 **/
	
	private String username;
	private String password;
	private int age;
	private String role;


}