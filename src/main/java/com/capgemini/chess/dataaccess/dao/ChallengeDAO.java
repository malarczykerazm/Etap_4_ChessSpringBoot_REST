package com.capgemini.chess.dataaccess.dao;

import java.util.List;

import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeDAO {

	ChallengeTO save(ChallengeTO tO);

	ChallengeTO findByUserIDs(Long senderID, Long recieverID);

	List<ChallengeTO> findByOneOfUsersID(Long iD);

}
