package com.magm.compras.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magm.compras.business.service.IProductService;
import com.magm.compras.model.Product;
import com.magm.compras.persistence.IProductDAO;
import com.magm.core.bussiness.service.exception.ServiceException;
import com.magm.core.bussiness.service.impl.GenericService;
import com.magm.core.persistence.exception.PersistenceException;

public class ProductService extends GenericService<Product, Integer> implements IProductService {
	private static Logger LOG = LoggerFactory.getLogger(ProductService.class);
	private IProductDAO productDAO;

	public ProductService(IProductDAO productDAO) {
		super(productDAO);
		this.productDAO = productDAO;
	}

	@Override
	public List<Product> list(String part) throws ServiceException {
		try {
			return productDAO.list(part);
		} catch (PersistenceException e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> listUniqueTags() throws ServiceException {
		try {
			return productDAO.listUniqueTags();
		} catch (PersistenceException e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	

}
