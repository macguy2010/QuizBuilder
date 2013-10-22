package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.*;

@ManagedBean(name = "quizController")
@RequestScoped
public class QuizController 
{
    @EJB
    private QuizEJB ejb;
    @EJB
    private QuestionEJB questionejb;
    private Question[] questionsCheckList;
    private Question[] questionsSelectionList = null;
    private String searchStr = new String();
    private Quiz quiz = new Quiz();
    private List<Quiz> quizList = null;
    
    public String doCreateQuiz() {
        for(int i = 0; i < questionsSelectionList.length; i++)
            quiz.addQuestion(questionsSelectionList[i]);
        quiz = ejb.createQuiz(quiz);
        quizList = ejb.findQuizzes();
        return "quizList.xhtml";
    }
    
    public String search()
    {
        quizList = ejb.searchQuestions(searchStr);
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
        
        if (questionsSelectionList == null)
        {
            Object[] q = questionejb.findQuestions().toArray();
            questionsSelectionList = Arrays.copyOf(q, q.length, Question[].class);
        }
        return questionsCheckList;
    }
    
    public void setQuestionsSelectionList(Question[] q)
    {
        questionsSelectionList = q;
    }
    
    public Question[] getQuestionsSelectionList()
    {
        return questionsSelectionList;
    }
}
