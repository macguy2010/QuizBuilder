package com.nate_tim_dane_company.quizbuilderproject;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@Stateful
@ManagedBean(name = "questionController")
@SessionScoped
public class QuestionController implements Serializable
{
    @EJB
    private QuizBuilderEJB ejb;
    private String searchStr = new String();
    private Question question = new Question();
    private List<Question> questionList = null;
    private List<Tag> tagFields = null;
    private String currentPage;
    
    public String doCreateQuestion() {
        List<String> tags = new ArrayList<String>();
        for(int i = 0; i < tagFields.size(); i++)
            if(!tags.contains(tagFields.get(i).getValue()) && !tagFields.get(i).getValue().equals(""))
                tags.add(tagFields.get(i).getValue());
        question.setTags(tags);
        question = ejb.createQuestion(question);
        questionList = ejb.findQuestions();
        question = new Question();
        tagFields = null;
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
        tagFields = new ArrayList<Tag>();
        for(int i = 0; i < question.getTags().size(); i++)
            tagFields.add(new Tag(question.getTags().get(i)));
        return "editQuestion.xhtml";
    }
    
    public String doEditQuestion()
    {
        List<String> tags = new ArrayList<String>();
        for(int i = 0; i < tagFields.size(); i++)
            if(!tags.contains(tagFields.get(i).getValue()) && !tagFields.get(i).getValue().equals(""))
                tags.add(tagFields.get(i).getValue());
        question.setTags(tags);
        ejb.editQuestion(question);
        questionList = ejb.findQuestions();
        question = new Question();
        tagFields = null;
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
            tagFields.add(new Tag(""));
        }
        return tagFields;
    }
    
    public String doAddTagField()
    {
        if(tagFields.get(tagFields.size()-1).getValue() != null && !tagFields.get(tagFields.size()-1).getValue().equals(""))
        {
            List<Tag> newTags = new ArrayList<Tag>();
            for(int i = 0; i < getTagFields().size(); i++)
            {
                newTags.add(getTagFields().get(i));
            }
            newTags.add(new Tag(""));
            tagFields = newTags;
        }
        return null;
    }
    
    public String doRemoveTag(Tag t)
    {
       tagFields.remove(t);
        return null;
    }
}
