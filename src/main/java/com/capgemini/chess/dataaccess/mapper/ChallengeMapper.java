package com.capgemini.chess.dataaccess.mapper;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.to.ChallengeTO;

public class ChallengeMapper {

	public static ChallengeEntity map(ChallengeTO tO) {
		ChallengeEntity entity = new ChallengeEntity();
		entity.setChallengeID(tO.getChallengeID());
		entity.setSender(tO.getSender());
		entity.setReciever(tO.getReceiver());
		entity.setChallengeStatus(tO.getChallengeStatus());
		return entity;
	}

	public static ChallengeTO map(ChallengeEntity entity) {
		ChallengeTO tO = new ChallengeTO();
		tO.setChallengeID(entity.getChallengeID());
		tO.setSender(entity.getSender());
		tO.setReceiver(entity.getReceiver());
		tO.setChallengeStatus(entity.getChallengeStatus());
		return tO;
	}

}
