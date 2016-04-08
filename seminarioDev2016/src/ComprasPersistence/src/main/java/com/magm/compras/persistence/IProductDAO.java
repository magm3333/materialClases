package com.magm.compras.persistence;

import java.util.List;

import com.magm.compras.model.Product;
import com.magm.core.persistence.dao.IGenericDAO;
import com.magm.core.persistence.exception.PersistenceException;

public interface IProductDAO extends IGenericDAO<Product, Integer>{

	public List<Product> list(String part) throws PersistenceException;
	public List<String> listUniqueTags() throws PersistenceException;
}
