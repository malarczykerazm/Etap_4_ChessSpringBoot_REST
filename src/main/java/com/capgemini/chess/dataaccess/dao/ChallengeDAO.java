package com.capgemini.chess.dataaccess.dao;

import java.util.List;

import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

public interface ChallengeDAO {

	ChallengeTO save(ChallengeTO tO);

	ChallengeTO findByUserProfiles(ProfileTO sender, ProfileTO receiver);

	List<ChallengeTO> findByOneOfUsers(ProfileTO user);

}
