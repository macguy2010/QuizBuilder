package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.*;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "quizController")
@RequestScoped
public class QuizController 
{
    @EJB
    private QuizBuilderEJB ejb;
    private Question[] questionsCheckList = null;
    private Long[] questionsSelectionList;
    private String searchStr = new String();
    private Quiz quiz = new Quiz();
    private List<Quiz> quizList = null;
    private Long correspondingId = -100l;
    private Integer filter = 1;
    
    public String doCreateQuiz(Long id) {
        quiz.setUserId(id);
        return doCreateQuiz();
    }
    
    public String doCreateQuiz() {
        for(int i = 0; i < questionsSelectionList.length; i++)
        {
            Question q = ejb.getQuestion(questionsSelectionList[i]);
            quiz.addQuestion(q);
        }
        quiz = ejb.createQuiz(quiz);
        quizList = ejb.findQuizzes(correspondingId);
        return "quizList.xhtml";
    }
    
    public String doDeleteQuiz(Long id) {
        ejb.deleteQuiz(id);
        quizList = ejb.findQuizzes(correspondingId);
        return "quizList.xhtml";
    }
    
    public String toEditQuiz(Long id)
    {
        quiz = ejb.getQuiz(id);
        questionsSelectionList = new Long[quiz.getQuestions().size()];
        for(int i = 0; i < quiz.getQuestions().size(); i++)
            questionsSelectionList[i] = quiz.getQuestions().get(i).getId();
        return "editQuiz.xhtml";
    }
    
    public String doEditQuiz()
    {
        for(int i = 0; i < questionsSelectionList.length; i++)
        {
            Question q = ejb.getQuestion(questionsSelectionList[i]);
            quiz.addQuestion(q);
        }
        ejb.editQuiz(quiz);
        quizList = ejb.findQuizzes(correspondingId);
        quiz = new Quiz();
        return "quizList.xhtml";
    }
    
    public String search()
    {
        return null;
    }
    
    public void setSearchStr(String str)
    {
        searchStr = str;
    }
    
    public String getSearchStr()
    {
        return searchStr;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz u) {
        quiz = u;
    }
    
    public List<Quiz> getQuizList(Long id) {
        if(quizList == null || correspondingId != id)
            quizList = ejb.findQuizzes(id);
        correspondingId = id;
        return getFilteredQuizzes();
    }
    
     public List<Quiz> getFilteredQuizzes()
    {
        List<Quiz> returnList = new ArrayList<Quiz>();
        for(int i = 0; i < quizList.size(); i++)
        {
            if(filter == 1)
            {
                if(quizList.get(i).getUserId() == correspondingId)
                    returnList.add(quizList.get(i));
            }
            else if (filter == 2)
            {
                if(quizList.get(i).getUserId() < 0)
                    returnList.add(quizList.get(i));
            }
            else
                returnList.add(quizList.get(i));
        }
        
        if(searchStr != null && !searchStr.trim().equals(""))
        {
            for(int i = 0; i < returnList.size(); i++)
            {
                if(!returnList.get(i).getTitle().trim().toLowerCase().contains(searchStr.trim().toLowerCase()))
                {
                    returnList.remove(i);
                    i--;
                }
            }
        }
        
        return returnList;
    }
     
    public String viewQuiz(Long id)
    {
        quiz = ejb.findQuiz(id);
        return "quizViewPage.xhtml";
    }

    public List<Quiz> getQuizList() {
        if(quizList == null)
            quizList = ejb.findQuizzes();
        return quizList;
    }

    public void setQuizList(List<Quiz> qList) {
        quizList = qList;
    }
    
    public void setQuestionsCheckList(Question[] q)
    {
        questionsCheckList = q;
    }
    
    public Question[] getQuestionsCheckList(Long id)
    {
        if (questionsCheckList == null)
        {
            Object[] q = ejb.findQuestions(id).toArray();
            questionsCheckList = Arrays.copyOf(q, q.length, Question[].class);
        }
        return questionsCheckList;
    }
    
    public Question[] getQuestionsCheckList()
    {
        if (questionsCheckList == null)
        {
            Object[] q = ejb.findQuestions().toArray();
            questionsCheckList = Arrays.copyOf(q, q.length, Question[].class);
        }
        return questionsCheckList;
    }
    
    public void setQuestionsSelectionList(Long[] q)
    {
        questionsSelectionList = q;
    }
    
    public Long[] getQuestionsSelectionList()
    {
        return questionsSelectionList;
    }
        
    public Integer getFilter()
    {
        return filter;
    }
    
    public void setFilter(Integer f)
    {
        filter = f;
    }
    
    public String changeFilter(AjaxBehaviorEvent event)
    {
        return null;
    }
    
    public String canDeleteEdit(Long id, Long userId)
    {
        User_Obj user = ejb.findUser(id);
        if(id == userId && id > 0 || user != null && user.getAdmin())
            return "";
        else
            return "display: none;";
    }
    
}
