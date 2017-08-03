package com.capgemini.chess.service.challenge;

import java.util.List;

import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

public interface ChallengeSearchService {

	List<ChallengeTO> findAwaitingChallenges(ProfileTO sender);

}
