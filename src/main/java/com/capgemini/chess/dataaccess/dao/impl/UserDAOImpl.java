package com.capgemini.chess.dataaccess.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.dataaccess.entities.UserEntity;
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
	public UserTO findByID(Long iD) {
		return users.values().stream().map(u -> UserMapper.map(u)).filter(u -> u.getID().equals(iD)).findFirst()
				.orElse(null);
	}

	private Long generateID() {
		return users.keySet().stream().max((i1, i2) -> i1.compareTo(i2)).orElse(0L) + 1;
	}

	@Override
	public void initUsersAndProfiles() {
		Long iD1 = generateID();
		UserTO user1 = new UserTO();
		user1.setID(iD1);
		user1.setEmail("a1");
		ProfileTO profile1 = new ProfileTO();
		profile1.setID(iD1);
		profile1.setLevel(4);
		user1.setProfile(profile1);
		profileDAO.save(profile1);
		users.put(iD1, UserMapper.map(user1));

		Long iD2 = generateID();
		UserTO user2 = new UserTO();
		user2.setID(iD2);
		user2.setEmail("a2");
		ProfileTO profile2 = new ProfileTO();
		profile2.setID(iD2);
		profile2.setLevel(9);
		user2.setProfile(profile2);
		profileDAO.save(profile2);
		users.put(iD2, UserMapper.map(user2));

		Long iD3 = generateID();
		UserTO user3 = new UserTO();
		user3.setID(iD3);
		user3.setEmail("a3");
		ProfileTO profile3 = new ProfileTO();
		profile3.setID(iD3);
		profile3.setLevel(7);
		user3.setProfile(profile3);
		profileDAO.save(profile3);
		users.put(iD3, UserMapper.map(user3));

		Long iD4 = generateID();
		UserTO user4 = new UserTO();
		user4.setID(iD4);
		user4.setEmail("a4");
		ProfileTO profile4 = new ProfileTO();
		profile4.setID(iD4);
		profile4.setLevel(0);
		user4.setProfile(profile4);
		profileDAO.save(profile4);
		users.put(iD4, UserMapper.map(user4));

		Long iD5 = generateID();
		UserTO user5 = new UserTO();
		user5.setID(iD5);
		user5.setEmail("a5");
		ProfileTO profile5 = new ProfileTO();
		profile5.setID(iD5);
		profile5.setLevel(5);
		user5.setProfile(profile5);
		profileDAO.save(profile5);
		users.put(iD5, UserMapper.map(user5));

		// user6 = new UserTO();
		// profile6 = new ProfileTO();
		// profile6.setLevel(3);
		// user6.setProfile(profile6);
		// user6.setEmail("a6");
		// userDAO.save(user6);
		// user6.setID(userDAO.findByEmail("a6").getID());
		// profile6.setID(userDAO.findByEmail("a6").getID());
		//
		// user7 = new UserTO();
		// profile7 = new ProfileTO();
		// profile7.setLevel(6);
		// user7.setProfile(profile7);
		// user7.setEmail("a7");
		// userDAO.save(user7);
		// user7.setID(userDAO.findByEmail("a7").getID());
		// profile7.setID(userDAO.findByEmail("a7").getID());
		//
		// user8 = new UserTO();
		// profile8 = new ProfileTO();
		// profile8.setLevel(5);
		// user8.setProfile(profile8);
		// user8.setEmail("a8");
		// userDAO.save(user8);
		// user8.setID(userDAO.findByEmail("a8").getID());
		// profile8.setID(userDAO.findByEmail("a8").getID());
	}
}