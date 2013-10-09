package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "questionController")
@RequestScoped
public class QuestionController 
{
    @EJB
    private QuestionEJB ejb;
    private String searchStr = new String();
    private Question question = new Question();
    private List<Question> questionList = null;
    
    public String doCreateQuestion() {
        question = ejb.createQuestion(question);
        questionList = ejb.findQuestions();
        return "questionsList.xhtml";
    }
    
    public String search()
    {
        questionList = ejb.searchQuestions(searchStr);
        return "questionsList.xhtml";
    }
    
    public void setSearchStr(String str)
    {
        searchStr = str;
    }
    
    public String getSearchStr()
    {
        return searchStr;
    }

    public Question getUser() {
        return question;
    }

    public void UserObj(Question u) {
        question = u;
    }

    public List<Question> getUserList() {
        if(questionList == null)
            questionList = ejb.findQuestions();
        return questionList;
    }

    public void setUserList(List<Question> qList) {
        questionList = qList;
    }
}
