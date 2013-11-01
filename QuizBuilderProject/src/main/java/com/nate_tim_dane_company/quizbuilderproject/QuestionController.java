package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name = "questionController")
@RequestScoped
public class QuestionController 
{
    @EJB
    private QuizBuilderEJB ejb;
    private String searchStr = new String();
    private Question question = new Question();
    private List<Question> questionList = null;
    private Integer subject;
    
    public String doCreateQuestion() {
        switch(subject){
            case 1:
                question.setSubject(SubjectType.MATHEMATICS); break;
            case 2:
                question.setSubject(SubjectType.ENGLISH); break;
            case 3:
                question.setSubject(SubjectType.SCIENCE); break;
            case 4:
                question.setSubject(SubjectType.GEOGRAPHY); break;
            case 5:
                question.setSubject(SubjectType.HISTORY); break;
            case 6:
                question.setSubject(SubjectType.LITERATURE); break;
            case 7:
                question.setSubject(SubjectType.COMPUTER_SCIENCE); break;
            default:
                question.setSubject(SubjectType.OTHER); break;
        }
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

    public Question getQuestion() {
        return question;
    }
    
    public Integer getSubject()
    {
        return subject;
    }

    public void setSubject(Integer s) {
        subject = s;
    }

    public void setQuestion(Question u) {
        question = u;
    }

    public List<Question> getQuestionList() {
        if(questionList == null)
            questionList = ejb.findQuestions();
        return questionList;
    }

    public void setQuestionList(List<Question> qList) {
        questionList = qList;
    }
}
