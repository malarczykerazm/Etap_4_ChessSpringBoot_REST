package com.capgemini.chess.service.challenge;

import java.util.List;

import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.to.ProfileTO;

public interface ChallengeFindingOpponentsService {

	List<ProfileTO> findPotentialOpponents(ProfileTO tO, int range) throws UserValidationException;
}
