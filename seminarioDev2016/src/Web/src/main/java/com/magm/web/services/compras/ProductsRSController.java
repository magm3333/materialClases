package com.magm.web.services.compras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magm.compras.business.service.IProductService;
import com.magm.compras.model.Product;
import com.magm.core.bussiness.service.SimpleResponse;
import com.magm.core.bussiness.service.exception.ServiceException;
import com.magm.core.exception.NotFoundException;
import com.magm.web.services.Constants;

@RestController
@RequestMapping(value = Constants.URL_PRODUCTS)
public class ProductsRSController {
	private static Logger LOG = LoggerFactory.getLogger(ProductsRSController.class);
	
	@Autowired
	private IProductService productService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Object> list() {
		try {
			return new ResponseEntity<Object>(productService.list(), HttpStatus.OK);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);

			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/list/filter", method = RequestMethod.GET)
	public ResponseEntity<Object> list(@RequestParam("q") String q) {
		try {
			return new ResponseEntity<Object>(productService.list(q), HttpStatus.OK);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Object> save(@RequestBody Product product) {
		try {
			return new ResponseEntity<Object>(productService.save(product), HttpStatus.OK);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Product product) {
		try {
			return new ResponseEntity<Object>(productService.update(product), HttpStatus.OK);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) {
		try {
			Product p = new Product();
			p.setId(id);
			productService.delete(p);
			return ResponseEntity.ok().body(null);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable int id) {
		try {
			Product p = productService.load(id);
			return new ResponseEntity<Object>(p, HttpStatus.OK);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public ResponseEntity<Object> tags() {
		try {
			return new ResponseEntity<Object>(productService.listUniqueTags(), HttpStatus.OK);
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);

			return new ResponseEntity<Object>(new SimpleResponse(-1, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
