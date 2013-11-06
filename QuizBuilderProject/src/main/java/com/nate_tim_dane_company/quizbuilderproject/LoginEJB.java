package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

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

    public List<User_Obj> verifyUser(String username, String password){
        Query query = em.createQuery("SELECT * FROM User_Obj WHERE username = '"+username+"' AND password = '"+password+"'");

        return query.getResultList();
    }
}
