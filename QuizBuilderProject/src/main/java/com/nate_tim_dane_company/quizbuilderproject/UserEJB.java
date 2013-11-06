package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Stateless
public class UserEJB {
    @PersistenceContext(unitName = "QuizBuilderDB")
    private EntityManager em;

     public User createUser(User u)
    {
        em.persist(u);
        return u;
    }
     
     public void deleteUser(Long id)
     {
        Query q = em.createQuery("delete from User_Obj u where u.id = "+id);
        q.getResultList();
     }
    
    public List<User> findUsers() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<User> query = em.createNamedQuery("findAllUsers", User.class);
        return query.getResultList();
    }
    
    public List<User> searchUsers(String str)
    {
        Query q = em.createQuery("SELECT u FROM User u where u.firstName like '%"+str+"%' or u.lastName like '%"+str+"%'");
        return q.getResultList();
    }
}
