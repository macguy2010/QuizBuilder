package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Stateless
public class QuizEJB {
    @PersistenceContext(unitName = "QuizBuilderDB")
    private EntityManager em;
    
    public Quiz createQuiz(Quiz q)
    {
        em.persist(q);
        return q;
    }

    public List<Quiz> findQuizzes() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<Quiz> query = em.createNamedQuery("findAllQuizzes", Quiz.class);
        return query.getResultList();
    }
    
    public Question[] findQuestions() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<Question> query = em.createNamedQuery("findAllQuestions", Question.class);
        Object[] array = query.getResultList().toArray();
        return Arrays.copyOf(array, array.length, Question[].class);
    }
    
    public List<Quiz> searchQuestions(String str)
    {
        Query q = em.createQuery("SELECT u FROM Quiz q where q.title like '%"+str+"%'");
        
        return q.getResultList();
    }
}
