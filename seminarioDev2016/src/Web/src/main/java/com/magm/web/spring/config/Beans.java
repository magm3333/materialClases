package com.magm.web.spring.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.magm.compras.business.service.IProductService;
import com.magm.compras.business.service.impl.ProductService;
import com.magm.compras.persistence.IProductDAO;
import com.magm.compras.persistence.hibernate.ProductDAO;

@Configuration
public class Beans {

	// DAO
	@Bean
	@Autowired
	public IProductDAO productDAO(final SessionFactory sessionFactory) {
		return new ProductDAO(sessionFactory);
	}

	// Service
	@Bean
	@Autowired
	public IProductService productService(final IProductDAO productDAO) {
		return new ProductService(productDAO);
	}
}
