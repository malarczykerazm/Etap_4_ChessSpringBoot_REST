package com.capgemini.chess.service.challenge;

import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

public interface ChallengeValidationService {

	void validateChallenge(ChallengeTO tO) throws ChallengeValidationException;

	boolean isPotentialChallengeUnique(ProfileTO sender, ProfileTO receiver);

}
