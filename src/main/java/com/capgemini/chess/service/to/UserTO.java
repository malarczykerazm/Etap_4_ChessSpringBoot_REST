package com.capgemini.chess.service.to;

public class UserTO {
	private Long iD;
	private String email;
	private String password;
	private ProfileTO profile;

	public Long getID() {
		return iD;
	}

	public void setID(Long id) {
		this.iD = id;
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

	public ProfileTO getProfile() {
		return profile;
	}

	public void setProfile(ProfileTO profile) {
		this.profile = profile;
	}

}
