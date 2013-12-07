package com.nate_tim_dane_company.quizbuilderproject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import java.util.Scanner;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

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
    private Long correspondingId = -100l;
    private List<Tag> tagFields = null;
    private String currentPage;
    private Part file;
    private String fileContent;
    private Integer filter = 3;
    
    public String doCreateQuestion(Long id) {
        question.setUserId(id);
        return doCreateQuestion();
    }
    
    public String doCreateQuestion() {
        List<String> tags = new ArrayList<String>();
        for(int i = 0; i < tagFields.size(); i++)
            if(!tags.contains(tagFields.get(i).getValue()) && !tagFields.get(i).getValue().equals(""))
                tags.add(tagFields.get(i).getValue());
        question.setTags(tags);
        question = ejb.createQuestion(question);
    //    long id = loginCookie.getValue();
        questionList = ejb.findQuestions(correspondingId);
        question = new Question();
        tagFields = null;
        return "questionsList.xhtml";
    }
    
    public String doDeleteQuestion(Long id) {
        ejb.deleteQuestion(id);
        questionList = ejb.findQuestions(correspondingId);
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
        questionList = ejb.findQuestions(correspondingId);
        question = new Question();
        tagFields = null;
        return "questionsList.xhtml";
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question u) {
        question = u;
    }
    
    public List<Question> getQuestionList(Long id) {
        if(questionList == null || correspondingId != id)
            questionList = ejb.findQuestions(id);
        correspondingId = id;
        return getFilteredQuestions();
    }
    
    public List<Question> getFilteredQuestions()
    {
        List<Question> returnList = new ArrayList<Question>();
        for(int i = 0; i < questionList.size(); i++)
        {
            if(filter == 1)
            {
                if(questionList.get(i).getUserId() == correspondingId)
                    returnList.add(questionList.get(i));
            }
            else if (filter == 2)
            {
                if(questionList.get(i).getUserId() < 0)
                    returnList.add(questionList.get(i));
            }
            else
                returnList.add(questionList.get(i));
        }
        
        if(searchStr != null && !searchStr.trim().equals(""))
        {
            for(int i = 0; i < returnList.size(); i++)
            {
                if(!returnList.get(i).getQuestion().trim().contains(searchStr.trim()) && !returnList.get(i).getAnswer().trim().contains(searchStr.trim()))
                {
                    returnList.remove(i);
                    i--;
                }
            }
        }
        
        return returnList;
    }

    public List<Question> getQuestionList() {
        if(questionList == null)
            questionList = ejb.findQuestions();
        return getFilteredQuestions();
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
    
    public String upload() {
    try {
      fileContent = new Scanner(file.getInputStream())
          .useDelimiter("\\A").next();
    } catch (IOException e) {

    }
    try{
        ArrayList<Question> qs = UploadController.parseFile(fileContent);
        for(int i = 0; i < qs.size(); i++)
            ejb.createQuestion(qs.get(i));
    } catch (Exception e) {
        FacesContext cxt = FacesContext.getCurrentInstance();
        cxt.addMessage("Format_Error", new FacesMessage(FacesMessage.SEVERITY_WARN, "File not formatted correctly", "File not formatted correctly"));
    }
    questionList = ejb.findQuestions(correspondingId);
    return null;
  }
    
    public Part getFile() {
    return file;
    }
 
    public void setFile(Part file) {
      this.file = file;
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
}
