package com.mktec.restjavaee.interfaces;

import com.mktec.restjavaee.model.User;

public interface IUser extends IDao {
	
	public User findUserByUsernameAndPassword(String username, String password);
}
