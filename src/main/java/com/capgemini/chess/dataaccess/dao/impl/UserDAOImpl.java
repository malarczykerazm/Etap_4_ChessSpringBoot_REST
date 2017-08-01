package com.capgemini.chess.dataaccess.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.dataaccess.entities.UserEntity;
import com.capgemini.chess.dataaccess.mapper.ProfileMapper;
import com.capgemini.chess.dataaccess.mapper.UserMapper;
import com.capgemini.chess.service.to.ProfileTO;
import com.capgemini.chess.service.to.UserTO;

@Repository
@Scope("singleton")
public class UserDAOImpl implements UserDAO {

	private final Map<Long, UserEntity> users = new HashMap<>();

	@Autowired
	private ProfileDAO profileDAO;

	@Override
	public UserTO save(UserTO tO) {
		UserEntity user = UserMapper.map(tO);
		Long iD = generateID();
		user.setID(iD);
		user.getProfile().setID(iD);
		users.put(iD, user);
		profileDAO.save(UserMapper.map(user).getProfile());
		return UserMapper.map(user);
	}

	@Override
	public UserTO findByEmail(String email) {
		UserEntity user = users.values().stream().filter(u -> u.getEmail() == email).findFirst().orElse(null);
		return UserMapper.map(user);
	}

	@Override
	public ProfileTO findByID(Long iD) {
		return users.values().stream().map(u -> ProfileMapper.map(u.getProfile())).filter(p -> p.getID().equals(iD))
				.findFirst().orElse(null);
	}

	private Long generateID() {
		return users.keySet().stream().max((i1, i2) -> i1.compareTo(i2)).orElse(0L) + 1;
	}
}