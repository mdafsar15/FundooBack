package com.bridgelabz.fundoo.repository;
import javax.persistence.EntityManager;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.model.Label;

@Component 
public class LabelRepository{
   @Autowired
   private EntityManager entityManager;
	
	
    @Transactional
	public Label saveLabel(Label label) {
    	Session session = entityManager.unwrap(Session.class);
    	session.save(label);
		return label;
	}


    @Transactional
	public Label fetchLabelById(long l_Id) {
		Session session = entityManager.unwrap(Session.class);
		Query<?> q=session.createQuery("from Label where l_id=:l_Id");
		q.setParameter("l_Id", l_Id);
		return (Label) q.uniqueResult();
    }
    
    
    
}
