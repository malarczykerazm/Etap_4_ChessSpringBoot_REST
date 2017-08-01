package com.capgemini.chess.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.UserDAO;
import com.capgemini.chess.service.to.UserTO;
import com.capgemini.chess.service.user.UserSaveService;

@Service
@Scope("singleton")
public class UserSaveServiceImpl implements UserSaveService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserTO save(UserTO to) {
		return userDao.save(to);
	}
}
