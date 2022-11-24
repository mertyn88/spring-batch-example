package org.example.model;

import lombok.Data;

@Data
public class Profile {
	private long empCode;	
	private String empName;
	private String profileName;
	public Profile(long empCode, String empName, String profileName) {
		this.empCode = empCode;
		this.empName = empName;
		this.profileName = profileName;
	}
        //setters and getters
} 