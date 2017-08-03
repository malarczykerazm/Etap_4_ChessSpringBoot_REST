package com.capgemini.chess.dataaccess.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.dataaccess.entities.ProfileEntity;
import com.capgemini.chess.dataaccess.mapper.ProfileMapper;
import com.capgemini.chess.service.to.ProfileTO;

@Repository
public class ProfileDAOImpl implements ProfileDAO {

	private final List<ProfileEntity> profiles = new ArrayList<>();

	public ProfileDAOImpl() {
		this.initProfiles();
	}

	@Override
	public ProfileTO save(ProfileTO tO) {
		ProfileEntity profile = ProfileMapper.map(tO);
		profiles.add(profile);
		return tO;
	}

	@Override
	public ProfileTO findByID(Long iD) {
		return profiles.stream().map(p -> ProfileMapper.map(p)).filter(p -> p.getID().equals(iD)).findFirst()
				.orElse(null);
	}

	@Override
	public List<ProfileTO> findProfilesWithinLevelRange(int level, int assumedLevelRange) {
		return profiles.stream().map(p -> ProfileMapper.map(p)).filter(
				p -> (p.getLevel() <= (level + assumedLevelRange)) && (p.getLevel() >= (level - assumedLevelRange)))
				.collect(Collectors.toList());
	}

	private void initProfiles() {
		ProfileTO profile1 = new ProfileTO();
		profile1.setID(1L);
		profile1.setLevel(4);
		profiles.add(ProfileMapper.map(profile1));

		ProfileTO profile2 = new ProfileTO();
		profile2.setID(2L);
		profile2.setLevel(9);
		profiles.add(ProfileMapper.map(profile2));

		ProfileTO profile3 = new ProfileTO();
		profile3.setID(3L);
		profile3.setLevel(7);
		profiles.add(ProfileMapper.map(profile3));

		ProfileTO profile4 = new ProfileTO();
		profile4.setID(4L);
		profile4.setLevel(0);
		profiles.add(ProfileMapper.map(profile4));

		ProfileTO profile5 = new ProfileTO();
		profile5.setID(5L);
		profile5.setLevel(5);
		profiles.add(ProfileMapper.map(profile5));

		ProfileTO profile6 = new ProfileTO();
		profile6.setID(6L);
		profile6.setLevel(3);
		profiles.add(ProfileMapper.map(profile6));

		ProfileTO profile7 = new ProfileTO();
		profile7.setID(7L);
		profile7.setLevel(6);
		profiles.add(ProfileMapper.map(profile7));

		ProfileTO profile8 = new ProfileTO();
		profile8.setID(8L);
		profile8.setLevel(5);
		profiles.add(ProfileMapper.map(profile8));
	}

}
