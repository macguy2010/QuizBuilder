package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Stateless
public class QuizBuilderEJB {
    @PersistenceContext(unitName = "QuizBuilderDB")
    private EntityManager em;
    
    public Question createQuestion(Question q)
    {
        em.persist(q);
        return q;
    }
    
    public Quiz buildQuiz(TreeMap<SubjectType, Integer> subjects)
    {
        Quiz q = new Quiz();
        Random random = new Random();
        for(SubjectType s : subjects.keySet())
        {
            int[] selections = new int[subjects.get(s)];
            Query query = em.createQuery("SELECT q FROM Question q where q.subject like '"+s.getLabel()+"'");
            List<Question> results = query.getResultList();
            for(int i = 0; i < selections.length; i++)
            {
                int n = 0;
                boolean contains = false;
                do
                {
                    contains = false;
                    n = random.nextInt(results.size());
                    for(int j = 0; j < selections.length; j++)
                        if(selections[j] == n)
                        {
                            contains = true;
                            break;
                        }
                }while(contains);

                selections[i] = n;
            }
            
            for(int i : selections)
            {
                q.addQuestion(results.get(i));
            }
        }
        return q;
    }
    
    public Question getQuestion(Long id)
    {
        return em.find(Question.class, id);
    }
     
     public void deleteQuestion(Long id)
     {
        Question question = em.find(Question.class, id);
        em.remove(question);
     }
     
     public void editQuestion(Question q)
     {
         Question question = em.find(Question.class, q.getId());
         question.setQuestion(q.getQuestion());
         question.setAnswer(q.getAnswer());
         question.setSubject(q.getSubject());
         question.setDifficulty(q.getDifficulty());
     }

    public List<Question> findQuestions() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<Question> query = em.createNamedQuery("findAllQuestions", Question.class);
        return query.getResultList();
    }
    
    public List<Question> searchQuestions(String str)
    {
        Query q = em.createQuery("SELECT q FROM Question q where q.question like '%"+str+"%' or q.answer like '%"+str+"%'");
        return q.getResultList();
    }
    
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
    
    public List<Quiz> searchQuizzes(String str)
    {
        Query q = em.createQuery("SELECT u FROM Quiz q where q.title like '%"+str+"%'");
        return q.getResultList();
    }
}
