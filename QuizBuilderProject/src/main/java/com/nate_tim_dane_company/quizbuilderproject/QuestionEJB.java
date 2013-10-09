package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Stateless
public class QuestionEJB {
    @PersistenceContext(unitName = "QuizBuilderDB")
    private EntityManager em;
    
    public Question createQuestion(Question q)
    {
        em.persist(q);
        return q;
    }

    public List<Question> findQuestions() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<Question> query = em.createNamedQuery("findAllQuestions", Question.class);
        return query.getResultList();
    }
    
    public List<Question> searchQuestions(String str)
    {
        Query q = em.createQuery("SELECT u FROM Question q where q.question like '%"+str+"%' or q.answer like '%"+str+"%'");
        
        return q.getResultList();
    }
}
