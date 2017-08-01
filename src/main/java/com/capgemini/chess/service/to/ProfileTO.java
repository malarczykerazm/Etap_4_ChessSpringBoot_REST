package com.capgemini.chess.service.to;

public class ProfileTO {
	private Long iD;
	private String name;
	private String surname;
	private String aboutMe;
	private int level = 0;

	public Long getID() {
		return iD;
	}

	public void setID(Long id) {
		this.iD = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
