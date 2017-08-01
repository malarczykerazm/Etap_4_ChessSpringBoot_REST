package com.capgemini.chess.service.unittests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.exceptions.ProfileValidationException;
import com.capgemini.chess.service.profile.impl.ProfileValidationServiceImpl;
import com.capgemini.chess.service.to.ProfileTO;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiecesTests {

	private Long iD;

	@Mock
	ProfileDAO profileDAO;

	@InjectMocks
	ProfileValidationServiceImpl profileValidation;

	@Rule
	public ExpectedException e = ExpectedException.none();

	@Test
	public void shouldValidateProfile() throws ProfileValidationException {
		// given
		iD = 8L;

		Mockito.when(profileDAO.findByID(iD)).thenReturn(new ProfileTO());

		// when
		profileValidation.validateProfile(iD);

		// then
		Mockito.verify(profileDAO).findByID(iD);
		// NO EXCEPTION
	}

	@Test
	public void shouldNotValidateProfileDueToNoIDInTheDatabase() throws ProfileValidationException {
		// given
		iD = 8L;

		Mockito.when(profileDAO.findByID(iD)).thenReturn(null);

		// expect
		e.expect(ProfileValidationException.class);
		e.expectMessage("No such profile");

		// when
		profileValidation.validateProfile(iD);

		// then
		// EXCEPTION
	}

}
