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

     public User_Obj createUser(User_Obj u)
    {
        em.persist(u);
        return u;
    }
     
     public void deleteUser(Long id)
     {
        User_Obj user = em.find(User_Obj.class, id);
        em.remove(user);
     }
     
     public void editUser(Long id, User_Obj u)
     {
         User_Obj user = em.find(User_Obj.class, id);
         user.setFirstName(u.getFirstName());
         user.setLastName(u.getLastName());
         user.setUsername(u.getUsername());
         user.setPassword(u.getPassword());
         user.setEmail(u.getEmail());
     }
    
    public List<User_Obj> findUsers() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<User_Obj> query = em.createNamedQuery("findAllUsers", User_Obj.class);
        return query.getResultList();
    }
    
    public List<User_Obj> searchUsers(String str)
    {
        Query q = em.createQuery("SELECT u FROM User_Obj u where u.firstName like '%"+str+"%' or u.lastName like '%"+str+"%'");
        return q.getResultList();
    }
}
