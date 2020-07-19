package com.bridgelabz.fundoo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Note;

@Repository
@SuppressWarnings({ "rawtypes" })
public class NoteRepository implements INoteRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public Note saveOrUpdate(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

	@Override
	@Transactional
	public Note getNote(long nId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE n_id=:id");
		query.setParameter("id", nId);
		return (Note) query.uniqueResult();
	}
	
	@Override
	@Transactional
	public boolean deleteNote(long nId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM Note WHERE n_id=:id");
		query.setParameter("id", nId);
		query.executeUpdate();
		return true;
	}
	@Transactional
	@Override
	
	public List<Note> getAllNotes(long uid) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE u_id=:id and is_trashed=false and is_archived=false");
				query.setParameter("id", uid);
				return query.getResultList();
//				System.out.println("notes  repo; " + notes);                       
//				return notes;
			

	}
	@Override
	public List<Note> getAllTrashed(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE u_id=:id and is_trashed=true");
				query.setParameter("id", uId);
		return query.getResultList();
	}
	@Override
	public List<Note> getAllArchived(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE u_id=:id and is_archived=true and is_trashed=false");
				query.setParameter("id", uId);
		return query.getResultList();
	}
	@Override
	public List<Note> getAllReminderNotes(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE u_id=:id and reminder_date!=null and is_trashed=false");
				query.setParameter("id", uId);
		return query.getResultList();
	}
	
	
	@Override
	public List<Note> getPinned(long uId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE id=:id and is_pinned=true");
				query.setParameter("id", uId);
		return query.getResultList();
	}
	
	@Override
	public long findId(long userId, String name) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("select n_id from note where u_id = ? and title = ?");
				
		return query.getFirstResult();
	}
	
	@Transactional
	public Note findBynotesId(long n_id) {
		Session session = entityManager.unwrap(Session.class);
		Query<?> q = session.createQuery("from Note where n_id=:n_id");
		q.setParameter("n_id", n_id);
		return (Note) q.uniqueResult();
	}

	
	
}