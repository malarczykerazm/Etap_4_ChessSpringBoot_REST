package com.capgemini.chess.service.to;

import com.capgemini.chess.enums.ChallengeStatus;

public class ChallengeTO {

	private Long challengeID;
	private ProfileTO sender;
	private ProfileTO receiver;
	private ChallengeStatus challengeStatus;

	public Long getChallengeID() {
		return challengeID;
	}

	public void setChallengeID(Long challengeID) {
		this.challengeID = challengeID;
	}

	public ProfileTO getSender() {
		return sender;
	}

	public void setSender(ProfileTO sender) {
		this.sender = sender;
	}

	public ProfileTO getReceiver() {
		return receiver;
	}

	public void setReceiver(ProfileTO receiver) {
		this.receiver = receiver;
	}

	public ChallengeStatus getChallengeStatus() {
		return challengeStatus;
	}

	public void setChallengeStatus(ChallengeStatus challengeStatus) {
		this.challengeStatus = challengeStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((challengeID == null) ? 0 : challengeID.hashCode());
		result = prime * result + ((challengeStatus == null) ? 0 : challengeStatus.hashCode());
		result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChallengeTO other = (ChallengeTO) obj;
		if (challengeID == null) {
			if (other.challengeID != null)
				return false;
		} else if (!challengeID.equals(other.challengeID))
			return false;
		if (challengeStatus != other.challengeStatus)
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}

}
