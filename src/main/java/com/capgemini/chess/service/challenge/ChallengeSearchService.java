package com.capgemini.chess.service.challenge;

import java.util.List;

import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeSearchService {

	List<ChallengeTO> findAwaitingChallenges(Long searcherID);

}
