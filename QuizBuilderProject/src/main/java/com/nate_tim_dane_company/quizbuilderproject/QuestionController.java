package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import javax.faces.model.SelectItem;

@ManagedBean(name = "questionController")
@RequestScoped
public class QuestionController 
{
    @EJB
    private QuizBuilderEJB ejb;
    private String searchStr = new String();
    private Question question = new Question();
    private List<Question> questionList = null;
    
    public String doCreateQuestion() {
        question = ejb.createQuestion(question);
        questionList = ejb.findQuestions();
        return "questionsList.xhtml";
    }
    
    public String doDeleteQuestion(Long id) {
        ejb.deleteQuestion(id);
        questionList = ejb.findQuestions();
        return "questionsList.xhtml";
    }
    
    public String toEditQuestion(Long id)
    {
        question = ejb.getQuestion(id);
        return "editQuestion.xhtml";
    }
    
    public String doEditQuestion()
    {
        ejb.editQuestion(question);
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
    
    public SelectItem[] getSubjectTypeValues() 
    {
        SelectItem[] items = new SelectItem[SubjectType.values().length];
        int i = 0;
        for(SubjectType g: SubjectType.values()) {
          items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }
}
