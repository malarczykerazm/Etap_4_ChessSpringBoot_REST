package com.capgemini.chess.service.unittests;

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
import com.capgemini.chess.service.to.ProfileTO;
import com.capgemini.chess.service.user.impl.UserValidationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiecesTests {

	private Long iD;

	@Mock
	UserDAO userDAO;

	@InjectMocks
	UserValidationServiceImpl userValidation;

	@Rule
	public ExpectedException e = ExpectedException.none();

	@Test
	public void shouldValidateUser() throws UserValidationException {
		// given
		iD = 8L;

		Mockito.when(userDAO.findByID(iD)).thenReturn(new ProfileTO());

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
