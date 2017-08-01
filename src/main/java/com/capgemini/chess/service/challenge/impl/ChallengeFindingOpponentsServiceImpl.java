package com.capgemini.chess.service.challenge.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.challenge.ChallengeValidationService;
import com.capgemini.chess.service.challenge.ChallengeFindingOpponentsService;
import com.capgemini.chess.service.to.ProfileTO;
import com.capgemini.chess.service.user.UserValidationService;

@Service
@Scope("singleton")
public class ChallengeFindingOpponentsServiceImpl implements ChallengeFindingOpponentsService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	ProfileDAO profileDAO;

	@Autowired
	UserValidationService userValidationService;

	@Autowired
	ChallengeValidationService challengeValidationService;

	@Override
	public List<ProfileTO> findPotentialOpponents(ProfileTO tO, int assumedLevelRange) throws UserValidationException {

		userValidationService.validateID(tO.getID());

		return profileDAO.findProfilesWithinLevelRange(tO.getLevel(), assumedLevelRange).stream()
				.filter(p -> (p.getID() != tO.getID()
						&& challengeValidationService.isPotentialChallengeUnique(tO.getID(), p.getID())))
				.limit(5).collect(Collectors.toList());
	}

}
