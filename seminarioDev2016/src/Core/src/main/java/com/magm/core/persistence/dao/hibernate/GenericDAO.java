package com.magm.core.persistence.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.internal.SessionImpl;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magm.core.exception.NotFoundException;
import com.magm.core.persistence.dao.IGenericDAO;
import com.magm.core.persistence.exception.PersistenceException;

public class GenericDAO<Entity, PK extends Serializable> implements IGenericDAO<Entity, PK> {
	
	private static Logger LOG = LoggerFactory.getLogger(GenericDAO.class);
	
	
	@SuppressWarnings("unchecked")
	private Class<Entity> domainClass = getDomainClass();

	private  SessionFactory sessionFactory;

	private final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	public GenericDAO(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	public  void closeSession() throws PersistenceException {
		try {
			Session session = (Session) threadLocal.get();
			threadLocal.set(null);

			if (session != null) {
				session.close();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(Entity t) throws PersistenceException, NotFoundException {
		try {
			getSession().beginTransaction();
			getSession().delete(t);
			getSession().getTransaction().commit();
		} catch(StaleStateException nfe) {
			throw new NotFoundException(nfe.getMessage(), nfe);
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Class getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			domainClass = (Class) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	public  Session getSession() {
		Session session = threadLocal.get();

		if (session == null || !session.isOpen()) {
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

	public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> list() throws PersistenceException {
		List<Entity> l = null;
		try {
			l = getSession().createQuery(String.format("FROM %s", getDomainClass().getSimpleName())).list();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity load(PK id) throws PersistenceException, NotFoundException {
		Entity t = null;
		try {
			t = (Entity) getSession().load(getDomainClass(), id);
		} catch(ObjectNotFoundException nfe) {
			throw new NotFoundException(nfe.getMessage(), nfe);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return t;
	}

	@Override
	public Entity save(Entity t) throws PersistenceException {
		try {
			getSession().beginTransaction();
			getSession().save(t);
			getSession().getTransaction().commit();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return t;
	}

	@Override
	public Entity saveOrUpdate(Entity t) throws PersistenceException {
		try {
			getSession().beginTransaction();
			getSession().saveOrUpdate(t);
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return t;
	}

	public  void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Entity update(Entity t) throws PersistenceException, NotFoundException {
		try {
			getSession().beginTransaction();
			getSession().update(t);
			getSession().getTransaction().commit();
		} catch(StaleStateException nfe) {
			throw new NotFoundException(nfe.getMessage(), nfe);
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return t;
	}

	@Override
	public String selectToJson(String sql) throws PersistenceException {
		String r="{}";
		try {
			SessionImpl sessionImpl = (SessionImpl) getSession();
			Connection cn = sessionImpl.connection();
			r = DSL.using(cn).fetch(sql).formatJSON();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			closeSession();
		}
		return r;
	}

}
