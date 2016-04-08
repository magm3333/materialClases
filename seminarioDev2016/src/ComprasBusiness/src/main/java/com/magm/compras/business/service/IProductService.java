package com.magm.compras.business.service;

import java.util.List;

import com.magm.compras.model.Product;
import com.magm.core.bussiness.service.IGenericService;
import com.magm.core.bussiness.service.exception.ServiceException;

public interface IProductService extends IGenericService<Product, Integer> {
	public List<Product> list(String part) throws ServiceException;

	public List<String> listUniqueTags() throws ServiceException;
	
	
}
