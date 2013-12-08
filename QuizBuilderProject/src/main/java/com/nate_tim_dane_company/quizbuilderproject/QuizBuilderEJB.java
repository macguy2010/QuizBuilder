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
    
    public User_Obj findUser(Long id)
    {
        return em.find(User_Obj.class, id);
    }
    
    public Quiz buildQuiz(Quiz q, TreeMap<SubjectType, Integer> subjects)
    {
        Random random = new Random();
        for(SubjectType s : subjects.keySet())
        {
            int[] selections = new int[subjects.get(s)];
            for(int i = 0; i < selections.length; i++)
                selections[i] = -1;
            Query query = em.createQuery("SELECT q FROM Question q where q.subject like '"+s.getLabel()+"'");
            List<Question> results = query.getResultList();
            if(results.size() >= selections.length)
            {
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
            else
            {
                for(int i = 0; i < results.size(); i++)
                {
                    q.addQuestion(results.get(i));
                }
            }
        }
        q.setTitle("TemporaryQuiz"+(int)(Math.random() * 1000));
        q.setIsTemporary(true);
        em.persist(q);
        return q;
    }
    
    public Question getQuestion(Long id)
    {
        return em.find(Question.class, id);
    }
    
    public Quiz getQuiz(Long id)
    {
        return em.find(Quiz.class, id);
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
         question.setTags(q.getTags());
     }
     
     public void deleteQuiz(Long id)
     {
        Quiz quiz = em.find(Quiz.class, id);
        em.remove(quiz);
     }
     
     public void editQuiz(Quiz q)
     {
         Quiz quiz = em.find(Quiz.class, q.getId());
         quiz.setTitle(q.getTitle());
         quiz.getQuestions().clear();
         for(int i = 0; i < q.getQuestions().size(); i++)
             quiz.addQuestion(q.getQuestions().get(i));
     }

     public List<Question> findQuestions(Long id) {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        User_Obj user = findUser(id);
        TypedQuery<Question> query;
        if(user != null && user.getAdmin())
            query = em.createNamedQuery("findAllQuestions", Question.class);
        else
            query = em.createNamedQuery("findAllQuestionsById", Question.class).setParameter("ID", id);
        return query.getResultList();
    }
     
    public List<Question> findQuestions() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<Question> query = em.createNamedQuery("findAllQuestions", Question.class);
        return query.getResultList();
    }
    
    public List<Question> searchQuestions(String str, Long id)
    {
        Query q = em.createQuery("SELECT q FROM Question q where q.question like '%"+str+"%' or q.answer like '%"+str+"%' and q.userid = "+id);
        return q.getResultList();
    }
    
    public Quiz createQuiz(Quiz q)
    {
        em.persist(q);
        return q;
    }
    
    public List<Quiz> findQuizzes(Long id) {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        User_Obj user = findUser(id);
        TypedQuery<Quiz> query;
        if(user != null && user.getAdmin())
            query = em.createNamedQuery("findAllQuizzes", Quiz.class);
        else
            query = em.createNamedQuery("findAllQuizzesById", Quiz.class).setParameter("ID", id);
        return query.getResultList();
    }

    public List<Quiz> findQuizzes() {
        // TODO not implemented with eclipselink 2.0 TypedQuery query = em.createNamedQuery("findAllBooks", Book.class);
        TypedQuery<Quiz> query = em.createNamedQuery("findAllQuizzes", Quiz.class);
        return query.getResultList();
    }
    
    public List<Quiz> searchQuizzes(String str, Long id)
    {
        Query q = em.createQuery("SELECT q FROM Quiz q where q.title like '%"+str+"%' and q.userid = "+id);
        return q.getResultList();
    }
}
