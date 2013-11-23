package com.nate_tim_dane_company.quizbuilderproject;

import java.util.ArrayList;
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
    private List<Tag> tagFields = null;
    
    public String doCreateQuestion() {
        List<String> tags = new ArrayList<String>();
        for(int i = 0; i < tagFields.size(); i++)
            tags.add(tagFields.get(i).getValue());
        question.setTags(tags);
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
    
    public List<Tag> getTagFields()
    {
        if(tagFields == null || tagFields.isEmpty())
        {
            tagFields = new ArrayList<Tag>();
            tagFields.add(new Tag());
        }
        return tagFields;
    }
    
    public String doAddTagField()
    {
        List<Tag> newTags = new ArrayList<Tag>();
        for(int i = 0; i < getTagFields().size(); i++)
        {
            newTags.add(getTagFields().get(i));
        }
        newTags.add(new Tag());
        tagFields = newTags;
        return "newQuestion.xhtml";
    }
    
    public String doRemoveTag(String t)
    {
        List<Tag> newTags = new ArrayList<Tag>();
        boolean found = false;
        for(int i = 0; i < getTagFields().size(); i++)
        {
            if(found || !getTagFields().get(i).getValue().equals(t))
                newTags.add(getTagFields().get(i));
            else
                found = true;
        }
        tagFields = newTags;
        return "newQuestion.xhtml";
    }
}
