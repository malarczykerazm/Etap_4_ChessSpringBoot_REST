package com.capgemini.chess.dataaccess.mapper;

import com.capgemini.chess.dataaccess.entities.UserEntity;
import com.capgemini.chess.service.to.UserTO;

public class UserMapper {

	public static UserEntity map(UserTO tO) {
		UserEntity user = new UserEntity();
		user.setID(tO.getID());
		user.setEmail(tO.getEmail());
		user.setPassword(tO.getPassword());
		user.setProfile(ProfileMapper.map(tO.getProfile()));
		return user;
	}

	public static UserTO map(UserEntity entity) {
		UserTO tO = new UserTO();
		tO.setID(entity.getID());
		tO.setEmail(entity.getEmail());
		tO.setPassword(entity.getPassword());
		tO.setProfile(ProfileMapper.map(entity.getProfile()));
		return tO;
	}

}
