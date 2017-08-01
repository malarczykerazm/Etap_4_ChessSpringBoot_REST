package com.capgemini.chess.service.profile;

import com.capgemini.chess.exceptions.ProfileValidationException;

public interface ProfileValidationService {

	void validateProfile(Long iD) throws ProfileValidationException;

}
