package com.capgemini.chess.service.challenge;

import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

public interface ChallengeCreationService {

	ChallengeTO create(ProfileTO sender, ProfileTO receiver);

}
