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

	public UserDAOImpl() {
		this.initUsers();
	}

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

	private void initUsers() {
		UserTO user1 = new UserTO();
		user1.setID(1L);
		user1.setEmail("a1");
		ProfileTO profile1 = new ProfileTO();
		profile1.setID(1L);
		profile1.setName("Janusz");
		profile1.setSurname("Kowalczyk");
		profile1.setAboutMe("Inżynier.");
		profile1.setLevel(4);
		user1.setProfile(profile1);
		users.put(user1.getID(), UserMapper.map(user1));

		UserTO user2 = new UserTO();
		user2.setID(2L);
		user2.setEmail("a2");
		ProfileTO profile2 = new ProfileTO();
		profile2.setID(2L);
		profile2.setName("Marylin");
		profile2.setSurname("");
		profile2.setAboutMe("art_ysTa");
		profile2.setLevel(9);
		user2.setProfile(profile2);
		users.put(user2.getID(), UserMapper.map(user2));

		UserTO user3 = new UserTO();
		user3.setID(3L);
		user3.setEmail("a3");
		ProfileTO profile3 = new ProfileTO();
		profile3.setID(3L);
		profile3.setName("Janusz");
		profile3.setSurname("Jurczyk");
		profile3.setAboutMe("O sobie.");
		profile3.setLevel(7);
		user3.setProfile(profile3);
		users.put(user3.getID(), UserMapper.map(user3));

		UserTO user4 = new UserTO();
		user4.setID(4L);
		user4.setEmail("a4");
		ProfileTO profile4 = new ProfileTO();
		profile4.setID(4L);
		profile4.setName("Agata");
		profile4.setSurname("Musielak");
		profile4.setAboutMe("the show must go on");
		profile4.setLevel(0);
		user4.setProfile(profile4);
		users.put(user4.getID(), UserMapper.map(user4));

		UserTO user5 = new UserTO();
		user5.setID(5L);
		user5.setEmail("a5");
		ProfileTO profile5 = new ProfileTO();
		profile5.setID(5L);
		profile5.setName("Tomasz");
		profile5.setSurname("Kowalczyk");
		profile5.setAboutMe(".");
		profile5.setLevel(5);
		user5.setProfile(profile5);
		users.put(user5.getID(), UserMapper.map(user5));

		UserTO user6 = new UserTO();
		user6.setID(6L);
		user6.setEmail("a6");
		ProfileTO profile6 = new ProfileTO();
		profile6.setID(6L);
		profile6.setName("Maria");
		profile6.setSurname("Tomczak");
		profile6.setAboutMe("nauczycielka");
		profile6.setLevel(3);
		user6.setProfile(profile6);
		users.put(user6.getID(), UserMapper.map(user6));

		UserTO user7 = new UserTO();
		user7.setID(7L);
		user7.setEmail("a7");
		ProfileTO profile7 = new ProfileTO();
		profile7.setID(7L);
		profile7.setName("marcin");
		profile7.setSurname("zbieć");
		profile7.setAboutMe("aktor");
		profile7.setLevel(6);
		user7.setProfile(profile7);
		users.put(user7.getID(), UserMapper.map(user7));

		UserTO user8 = new UserTO();
		user8.setID(8L);
		user8.setEmail("a8");
		ProfileTO profile8 = new ProfileTO();
		profile8.setID(8L);
		profile8.setName("Bartosz");
		profile8.setSurname("Elong");
		profile8.setAboutMe("Szachista");
		profile8.setLevel(5);
		user8.setProfile(profile8);
		users.put(user8.getID(), UserMapper.map(user8));

	}
}