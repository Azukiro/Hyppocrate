package com.mktec.restjavaee.dao;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.mktec.restjavaee.interfaces.IUser;
import com.mktec.restjavaee.model.User;

@Stateless
public class UserDao extends Dao implements IUser {
	
	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		Query query = em.createNamedQuery(User.FIND_USER_BY_USERNAME_PASS);
		query.setParameter("username", username);
		query.setParameter("password", password);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		if(!query.getResultList().isEmpty()) {
			return (User) query.getResultList().get(0);
		}
		
		return null;
	}

}
