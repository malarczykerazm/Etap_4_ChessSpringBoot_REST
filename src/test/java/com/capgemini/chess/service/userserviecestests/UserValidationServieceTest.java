package com.capgemini.chess.service.userserviecestests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.exceptions.UserValidationException;
import com.capgemini.chess.service.to.UserTO;
import com.capgemini.chess.service.user.impl.UserValidationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserValidationServieceTest {

	private Long iD;

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private UserValidationServiceImpl userValidation;

	@Rule
	public ExpectedException e = ExpectedException.none();

	@Test
	public void shouldValidateUser() throws UserValidationException {
		// given
		iD = 8L;

		Mockito.when(userDAO.findByID(iD)).thenReturn(new UserTO());

		// when
		userValidation.validateID(iD);

		// then
		Mockito.verify(userDAO).findByID(iD);
		// NO EXCEPTION
	}

	@Test
	public void shouldNotValidateUserDueToNoIDInTheDatabase() throws UserValidationException {
		// given
		iD = 8L;

		Mockito.when(userDAO.findByID(iD)).thenReturn(null);

		// expect
		e.expect(UserValidationException.class);
		e.expectMessage("ID does not");

		// when
		userValidation.validateID(iD);

		// then
		// EXCEPTION
	}

}
