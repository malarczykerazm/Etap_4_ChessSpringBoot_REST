package com.capgemini.chess.dataaccess.dao;

import com.capgemini.chess.service.to.UserTO;

public interface UserDAO {

	UserTO save(UserTO tO);

	UserTO findByEmail(String email);

	UserTO findByID(Long iD);

}