package com.capgemini.chess.service.challenge.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.ChallengeDAO;
import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.challenge.ChallengeValidationService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.ProfileTO;

@Service
@Scope("singleton")
public class ChallengeValidationServiceImpl implements ChallengeValidationService {

	@Autowired
	private ChallengeDAO challengeDAO;

	@Autowired
	private ProfileDAO profileDAO;

	@Override
	public void validateChallenge(ChallengeTO tO) throws ChallengeValidationException {
		if (null == profileDAO.findByID(tO.getSender().getID())) {
			throw new ChallengeValidationException("There is no player considered to be sender of the challenge!");
		}
		if (null == profileDAO.findByID(tO.getReceiver().getID())) {
			throw new ChallengeValidationException("There is no player considered to be reciever of the challenge!");
		}
		if (!(this.isPotentialChallengeUnique(tO.getSender(), tO.getReceiver()))) {
			throw new ChallengeValidationException("Such a challenge is already awaiting!");
		}
	}

	@Override
	public boolean isPotentialChallengeUnique(ProfileTO sender, ProfileTO receiver) {
		ChallengeTO challenge = challengeDAO.findByUserProfiles(sender, receiver);
		if (null == challenge) {
			return true;
		}
		if (!(challenge.getChallengeStatus().equals(ChallengeStatus.AWAITING))) {
			return true;
		}
		return false;
	}

}
