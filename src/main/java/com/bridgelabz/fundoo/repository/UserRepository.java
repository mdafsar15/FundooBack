package com.bridgelabz.fundoo.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoo.dto.UpdatePassDto;
import com.bridgelabz.fundoo.model.User;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository implements IUserRepository {
	@Autowired
	private EntityManager entityManager;

	@Override
	public User save(User newUser) {

		Session sess = entityManager.unwrap(Session.class);
		sess.saveOrUpdate(newUser);
		return newUser;

	}

	@Override
	public User getUser(String email) {
		Session sess = entityManager.unwrap(Session.class);
		Query emailFetchQuery = sess.createQuery("FROM User where email=:email");

		emailFetchQuery.setParameter("email", email);
		return (User) emailFetchQuery.uniqueResult();
	}

	// to check for verified user
	@Override
	@Transactional
	public boolean verify(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update User set is_verified=:verified" + " where id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		int status = query.executeUpdate();
		if (status > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean updatePassword(UpdatePassDto UPdto, long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("UPDATE User set password=:updatedPassword" + " WHERE id=:id");
		query.setParameter("updatedPassword", UPdto.getConfirmPassword());
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}

	@Override
	@Transactional
	public User getUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(" FROM User WHERE id=:id");
		query.setParameter("id", id);
		return (User) query.uniqueResult();
	}


	@Transactional
	public User getusersByid(Long u_id) {
		Session session = entityManager.unwrap(Session.class);
		Query<?> q = session.createQuery("from User where u_id=:u_id");
		q.setParameter("u_id", u_id);
		return (User) q.uniqueResult();

	}

	public User getusersByemail(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query<?> q = session.createQuery("from User where email=:email");
		q.setParameter("email", email);
		return (User) q.uniqueResult();
	}
}
