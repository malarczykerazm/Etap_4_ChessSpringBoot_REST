package com.capgemini.chess.service.challenge;

import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

public interface ChallengeInitiationService {

	ChallengeTO init(ProfileTO sender, ProfileTO receiver) throws ChallengeValidationException;

}
