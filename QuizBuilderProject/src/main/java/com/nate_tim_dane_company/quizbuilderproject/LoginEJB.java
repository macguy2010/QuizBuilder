package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import javax.ejb.Stateful;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/5/13
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateless
public class LoginEJB {
    @PersistenceContext(unitName = "QuizBuilderDB")
    private EntityManager em;

    public List<User_Obj> verifyUser(String username){
        Query query = em.createQuery("SELECT s FROM User_Obj s WHERE s.username = '"+username+"'");
        List<User_Obj> list = query.getResultList();
        return list;
    }
    
    public User_Obj findUser(Long id)
     {
        User_Obj user = em.find(User_Obj.class, id);
        if(user == null)
            user = new User_Obj();
         return user;
     }
}
