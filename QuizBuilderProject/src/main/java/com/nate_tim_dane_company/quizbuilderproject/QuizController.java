package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.*;
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
    
    public String doCreateQuiz() {
        for(int i = 0; i < questionsSelectionList.length; i++)
        {
            Question q = ejb.getQuestion(questionsSelectionList[i]);
            quiz.addQuestion(q);
        }
        quiz = ejb.createQuiz(quiz);
        quizList = ejb.findQuizzes();
        return "quizList.xhtml";
    }
    
    public String doDeleteQuiz(Long id) {
        ejb.deleteQuiz(id);
        quizList = ejb.findQuizzes();
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
        quizList = ejb.findQuizzes();
        quiz = new Quiz();
        return "quizList.xhtml";
    }
    
    public String search()
    {
        quizList = ejb.searchQuizzes(searchStr);
        return "quizList.xhtml";
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
    
}
