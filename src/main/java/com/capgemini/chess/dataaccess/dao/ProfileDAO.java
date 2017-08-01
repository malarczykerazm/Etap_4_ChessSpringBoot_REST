package com.capgemini.chess.dataaccess.dao;

import java.util.List;

import com.capgemini.chess.service.to.ProfileTO;

public interface ProfileDAO {

	ProfileTO findByID(Long iD);

	ProfileTO save(ProfileTO tO);

	List<ProfileTO> findProfilesWithinLevelRange(int level, int assumedLevelRange);

}