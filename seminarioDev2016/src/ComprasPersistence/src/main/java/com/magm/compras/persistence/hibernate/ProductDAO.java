package com.magm.compras.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magm.compras.model.Product;
import com.magm.compras.persistence.IProductDAO;
import com.magm.core.persistence.dao.hibernate.GenericDAO;
import com.magm.core.persistence.exception.PersistenceException;

public class ProductDAO extends GenericDAO<Product, Integer> implements IProductDAO {
	private static Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

	public ProductDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> list(String part) throws PersistenceException {

		List<Product> l = null;
		try {
			l = getSession().createCriteria(getDomainClass()).add(Restrictions.like("description", "%" + part + "%"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return l;

	}

	@Override
	public List<String> listUniqueTags() throws PersistenceException {
		List<String> r=new ArrayList<String>();
		JSONObject jo=new JSONObject(selectToJson("SELECT DISTINCT tag FROM productTags ORDER BY tag"));
		JSONArray ja=jo.getJSONArray("records");
		for(int i=0; i<ja.length(); i++) {
			r.add(ja.getJSONArray(i).getString(0));
		}
		return r;
	}

}
