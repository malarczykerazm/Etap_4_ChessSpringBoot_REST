package com.capgemini.chess.service.challenge.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.ChallengeDAO;
import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.exceptions.ChallengeValidationException;
import com.capgemini.chess.service.challenge.ChallengeValidationService;
import com.capgemini.chess.service.to.ChallengeTO;

@Service
@Scope("singleton")
public class ChallengeValidationServiceImpl implements ChallengeValidationService {

	@Autowired
	private ChallengeDAO challengeDAO;

	@Autowired
	private UserDAO userDAO;

	@Override
	public void validateChallenge(ChallengeTO tO) throws ChallengeValidationException {
		if (null == userDAO.findByID(tO.getSenderID())) {
			throw new ChallengeValidationException("There is no player considered to be sender of the challenge!");
		}
		if (null == userDAO.findByID(tO.getRecieverID())) {
			throw new ChallengeValidationException("There is no player considered to be reciever of the challenge!");
		}
		if (!(this.isPotentialChallengeUnique(tO.getSenderID(), tO.getRecieverID()))) {
			throw new ChallengeValidationException("Such a challenge is already awaiting!");
		}
	}

	@Override
	public boolean isPotentialChallengeUnique(Long senderID, Long recieverID) {
		ChallengeTO challenge = challengeDAO.findByUserIDs(senderID, recieverID);
		if (null == challenge) {
			return true;
		}
		if (!(challenge.getChallengeStatus().equals(ChallengeStatus.AWAITING))) {
			return true;
		}
		return false;
	}

}
