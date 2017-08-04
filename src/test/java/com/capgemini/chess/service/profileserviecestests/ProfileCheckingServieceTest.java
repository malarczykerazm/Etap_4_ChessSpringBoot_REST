package com.capgemini.chess.service.profileserviecestests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.chess.dataaccess.dao.ProfileDAO;
import com.capgemini.chess.exceptions.ProfileValidationException;
import com.capgemini.chess.service.profile.ProfileValidationService;
import com.capgemini.chess.service.profile.impl.ProfileCheckingServiceImpl;
import com.capgemini.chess.service.to.ProfileTO;

@RunWith(MockitoJUnitRunner.class)
public class ProfileCheckingServieceTest {

	private Long iD;

	@Mock
	private ProfileDAO profileDAO;

	@Mock
	private ProfileValidationService profileValidation;

	@InjectMocks
	private ProfileCheckingServiceImpl profileChecking;

	@Test
	public void shouldCheckRequestedProfile() throws ProfileValidationException {
		// given
		iD = 8L;

		Mockito.doNothing().when(profileValidation).validateProfile(iD);
		Mockito.when(profileDAO.findByID(iD)).thenReturn(new ProfileTO());

		// when
		profileChecking.checkProfile(iD);

		// then
		Mockito.inOrder(profileValidation, profileDAO);
		Mockito.verify(profileValidation, Mockito.times(1)).validateProfile(iD);
		Mockito.verify(profileDAO, Mockito.times(1)).findByID(iD);
	}

}
