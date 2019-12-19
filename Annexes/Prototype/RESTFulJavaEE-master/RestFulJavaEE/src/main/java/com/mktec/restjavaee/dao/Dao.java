package com.mktec.restjavaee.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mktec.restjavaee.interfaces.IDao;

@Stateless
public class Dao implements IDao {
	
	@PersistenceContext(name="RestFulJavaEE")
	protected EntityManager em;
	
	@Override
	public <T> T persist(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}

	@Override
	public <T> void remove(T entity) {
		entity = em.merge(entity);
		em.remove(entity);
		
	}

	@Override
	public <T> T update(T entity) {
		entity = em.merge(entity);
		return entity;
	}

	@Override
	public <T> T getObjectById(Long id, Class<T> entityClass) {
		TypedQuery<T> typedQuery = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id=:id", entityClass);
		typedQuery.setParameter("id", id);
		
		if(typedQuery.getResultList().isEmpty()) {
			return null;
		}
		
		return typedQuery.getSingleResult();
	}
	
	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		TypedQuery<T> typedQuery = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
		
		if(typedQuery.getResultList().isEmpty()) {
			return new ArrayList<>();
		}
		return typedQuery.getResultList();
	}
	
}
