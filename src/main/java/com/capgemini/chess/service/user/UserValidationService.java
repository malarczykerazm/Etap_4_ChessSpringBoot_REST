package com.capgemini.chess.service.user;

import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.to.RegistrationTO;

public interface UserValidationService {

	void validate(RegistrationTO to) throws UserValidationException;

	void validateID(Long iD) throws UserValidationException;

}
