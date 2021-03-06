package core.db.impl;

import core.db.HibernateUtil;
import core.db.entity.Office;
import core.db.ints.OfficeDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Logger;


public class OfficeDaoImpl implements OfficeDao {

	private final Logger logger = Logger.getLogger(getClass().getName());
	private SessionFactory sessionFactory;

	// for dev
	public OfficeDaoImpl() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	// for tests
	public OfficeDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	// save
	// - will execute an INSERT statement called outside or inside of transaction boundaries
	// - identifier value will be assigned to the persistent instance immediately
	@Override
	public void create(Office office) {
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.persist(office); // save
			tx.commit();
		} catch (HibernateException ex) {
			logger.info("Create error: " + ex.getLocalizedMessage());
			if (tx != null) { tx.rollback(); }
		} finally {
			if (session != null) { session.close(); }
		}
	}

	@Override
	public void delete(Office office) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Office> getAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Office getById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			Office office = session.get(Office.class, id);
			return office;
		} catch (HibernateException ex) {
			logger.info("Get by id error: " + ex.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public void update(Office office) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Office> selectPage(int start, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long countAll() {
		try (Session session = sessionFactory.openSession()) {
			return (Long) session.
					createQuery("select count(*) from Office").
					uniqueResult();
		} catch (HibernateException ex) {
			logger.info("getCount error: " + ex.getLocalizedMessage());
			return null;
		}
	}


	@Override
	public Integer deleteAll() {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			int result = session.
					createQuery("delete from Office").
					executeUpdate();
			tx.commit();
			return result;
		} catch (HibernateException ex) {
			logger.info("deleteAll error: " + ex.getLocalizedMessage());
			if (tx != null) { tx.rollback(); }
			return null;
		} finally {
			if (session != null) { session.close(); }
		}
	}
}
