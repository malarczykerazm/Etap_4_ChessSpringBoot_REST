package com.capgemini.chess.dataaccess.mapper;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.to.ChallengeTO;

public class ChallengeMapper {

	public static ChallengeEntity map(ChallengeTO tO) {
		ChallengeEntity entity = new ChallengeEntity();
		entity.setChallengeID(tO.getChallengeID());
		entity.setSenderID(tO.getSenderID());
		entity.setRecieverID(tO.getRecieverID());
		entity.setChallengeStatus(tO.getChallengeStatus());
		return entity;
	}

	public static ChallengeTO map(ChallengeEntity entity) {
		ChallengeTO tO = new ChallengeTO();
		tO.setChallengeID(entity.getChallengeID());
		tO.setSenderID(entity.getSenderID());
		tO.setRecieverID(entity.getRecieverID());
		tO.setChallengeStatus(entity.getChallengeStatus());
		return tO;
	}

}
