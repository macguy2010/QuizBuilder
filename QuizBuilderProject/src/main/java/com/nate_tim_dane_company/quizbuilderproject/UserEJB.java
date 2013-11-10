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
     
     public User_Obj findUser(Long id)
     {
         return em.find(User_Obj.class, id);
     }
     
     public boolean exists(User_Obj u)
     {
         Query q = em.createQuery("SELECT u FROM User_Obj u where u.username = '"+u.getUsername()+"'");
         if(q.getResultList().isEmpty())
             return false;
         return true;
     }
     
     public void deleteUser(Long id)
     {
        User_Obj user = em.find(User_Obj.class, id);
        em.remove(user);
     }
     
     public void editUser(User_Obj u)
     {
         User_Obj user = em.find(User_Obj.class, u.getId());
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
