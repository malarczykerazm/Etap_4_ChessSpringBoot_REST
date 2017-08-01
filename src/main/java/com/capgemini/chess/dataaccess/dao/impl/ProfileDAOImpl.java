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

}
