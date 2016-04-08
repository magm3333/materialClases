package com.magm.core.bussiness.service;

import java.io.Serializable;
import java.util.List;

import com.magm.core.bussiness.service.exception.ServiceException;
import com.magm.core.exception.NotFoundException;

public interface IGenericService<Entity, PK extends Serializable> {

	public void delete(Entity entity) throws ServiceException, NotFoundException;

	public List<Entity> list() throws ServiceException;

	public Entity load(PK id) throws ServiceException, NotFoundException;

	public Entity save(Entity entity) throws ServiceException;

	public Entity saveOrUpdate(Entity entity) throws ServiceException;

	public Entity update(Entity entity) throws ServiceException, NotFoundException;
}
