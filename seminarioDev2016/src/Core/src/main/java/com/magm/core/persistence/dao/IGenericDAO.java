package com.magm.core.persistence.dao;

import java.io.Serializable;
import java.util.List;

import com.magm.core.exception.NotFoundException;
import com.magm.core.persistence.exception.PersistenceException;

public interface IGenericDAO<Entity, PK extends Serializable> {

	public Entity load(PK id) throws PersistenceException, NotFoundException;            // /product/35  GET

	public Entity save(Entity t) throws PersistenceException;         // /product/   POST   body(.....esto contiene la entidad en JSON..........)

	public Entity saveOrUpdate(Entity t) throws PersistenceException;

	public Entity update(Entity t) throws PersistenceException, NotFoundException;       // /product/   PUT   body(.....esto contiene la entidad en JSON..........)

	public void delete(Entity t) throws PersistenceException, NotFoundException;         // /product/35  DELETE

	public List<Entity> list() throws PersistenceException;           // /product/list  GET

	public String selectToJson(String sql) throws PersistenceException;

}
