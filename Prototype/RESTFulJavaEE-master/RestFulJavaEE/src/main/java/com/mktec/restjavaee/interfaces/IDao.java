package com.mktec.restjavaee.interfaces;

import java.util.List;

public interface IDao {
	public <T> T persist(T entity);
	public <T> void remove(T entity);
	public <T> T update (T entity);
	public <T> T getObjectById(Long id, Class<T> entityClass);
	public <T> List<T> findAll(Class<T> entityClass);
}
