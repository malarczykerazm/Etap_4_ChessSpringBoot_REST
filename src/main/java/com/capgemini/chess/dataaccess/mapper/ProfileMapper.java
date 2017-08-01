package com.capgemini.chess.dataaccess.mapper;

import com.capgemini.chess.dataaccess.entities.ProfileEntity;
import com.capgemini.chess.service.to.ProfileTO;

public class ProfileMapper {

	public static ProfileEntity map(ProfileTO tO) {
		ProfileEntity entity = new ProfileEntity();
		entity.setID(tO.getID());
		entity.setName(tO.getName());
		entity.setSurname(tO.getSurname());
		entity.setAboutMe(tO.getAboutMe());
		entity.setLevel(tO.getLevel());
		return entity;
	}

	public static ProfileTO map(ProfileEntity entity) {
		ProfileTO tO = new ProfileTO();
		tO.setID(entity.getID());
		tO.setName(entity.getName());
		tO.setSurname(entity.getSurname());
		tO.setAboutMe(entity.getAboutMe());
		tO.setLevel(entity.getLevel());
		return tO;
	}

}
