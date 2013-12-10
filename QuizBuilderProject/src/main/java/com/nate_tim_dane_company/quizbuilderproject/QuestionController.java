package com.nate_tim_dane_company.quizbuilderproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    private List<SelectedQuestionElement> selectedList = null;
    private Long correspondingId = -100l;
    private List<Tag> tagFields = null;
    private String currentPage;
    private Part file;
    private String fileContent;
    private Integer filter = 1;
    private Boolean enableSelection = false;
    private String errorMessage = "";
    private Integer importType = 1;
    private Integer exportType = 1;
    private Boolean valid = true;
    private String sortedBy = "";
    private boolean justSorted = false;
    
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
        selectedList = getSelectedQuestionList(getFilteredQuestions());
        question = new Question();
        tagFields = null;
        return "questionsList.xhtml";
    }
    
    public String doDeleteQuestion(Long id) {
        ejb.deleteQuestion(id);
        questionList = ejb.findQuestions(correspondingId);
        selectedList = getSelectedQuestionList(getFilteredQuestions());
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
        selectedList = getSelectedQuestionList(getFilteredQuestions());
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
    
    public void setValid(Boolean s)
    {
        valid = s;
    }
    
    public Boolean getValid()
    {
        return valid;
    }
    
    public String sortByQuestion()
    {
        List<Question> newList = new ArrayList<Question>();
        boolean order = true;
        if(sortedBy.equals("Q_A"))
        {
            order = false;
            sortedBy = "Q_D";
        }
        else
            sortedBy = "Q_A";
        while(!questionList.isEmpty())
        {
            Question first = questionList.get(0);
            for(int j = 1; j < questionList.size(); j++)
            {
                if(questionList.get(j).getQuestion().compareTo(first.getQuestion()) < 0 == order)
                {
                    first = questionList.get(j);
                }
            }
            newList.add(first);
            questionList.remove(first);
        }
        questionList = newList;
        justSorted = true;
        return null;
    }
    
   public String sortByAnswer()
    {
        List<Question> newList = new ArrayList<Question>();
        boolean order = true;
        if(sortedBy.equals("A_A"))
        {
            order = false;
            sortedBy = "A_D";
        }
        else
            sortedBy = "A_A";
        while(!questionList.isEmpty())
        {
            Question first = questionList.get(0);
            for(int j = 1; j < questionList.size(); j++)
            {
                if(questionList.get(j).getAnswer().compareTo(first.getAnswer()) < 0 == order)
                {
                    first = questionList.get(j);
                }
            }
            newList.add(first);
            questionList.remove(first);
        }
        questionList = newList;
        justSorted = true;
        return null;
    }
    
    public String sortBySubject()
    {
        List<Question> newList = new ArrayList<Question>();
        boolean order = true;
        if(sortedBy.equals("S_A"))
        {
            order = false;
            sortedBy = "S_D";
        }
        else
            sortedBy = "S_A";
        while(!questionList.isEmpty())
        {
            Question first = questionList.get(0);
            for(int j = 1; j < questionList.size(); j++)
            {
                if(questionList.get(j).getSubject().getLabel().compareTo(first.getSubject().getLabel()) < 0 == order)
                {
                    first = questionList.get(j);
                }
            }
            newList.add(first);
            questionList.remove(first);
        }
        questionList = newList;
        justSorted = true;
        return null;
    }
    
    public String sortByDifficulty()
    {
        List<Question> newList = new ArrayList<Question>();
        boolean order = true;
        if(sortedBy.equals("D_A"))
        {
            order = false;
            sortedBy = "D_D";
        }
        else
            sortedBy = "D_A";
        while(!questionList.isEmpty())
        {
            Question first = questionList.get(0);
            for(int j = 1; j < questionList.size(); j++)
            {
                if(questionList.get(j).getDifficulty() < first.getDifficulty() == order)
                {
                    first = questionList.get(j);
                }
            }
            newList.add(first);
            questionList.remove(first);
        }
        questionList = newList;
        justSorted = true;
        return null;
    }
    
    public List<SelectedQuestionElement> getQuestionList(Long id) {
        if(questionList == null || selectedList == null || correspondingId != id || ejb.questionCount(id) != (long)questionList.size())
        {
            questionList = ejb.findQuestions(id);
            
        }
        selectedList = getSelectedQuestionList(getFilteredQuestions());
        
        correspondingId = id;
        return selectedList;
    }
    
    private List<SelectedQuestionElement> getSelectedQuestionList(List<Question> q)
    {
        selectedList = new ArrayList<SelectedQuestionElement>();
        for(int i = 0; i < q.size(); i++)
        {
            selectedList.add(new SelectedQuestionElement(q.get(i)));
        }
        return selectedList;
    }
    
    private List<Question> getFilteredQuestions()
    {
        List<Question> returnList = new ArrayList<Question>();
        for(int i = 0; i < questionList.size(); i++)
        {
            if(valid)
                if(!questionList.get(i).getValid())
                    continue;
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
                if(!returnList.get(i).getQuestion().trim().toLowerCase().contains(searchStr.trim().toLowerCase()) 
                       && !returnList.get(i).getAnswer().trim().toLowerCase().contains(searchStr.trim().toLowerCase())
                        && !returnList.get(i).getSubject().getLabel().trim().toLowerCase().contains(searchStr.trim().toLowerCase()))
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
        selectedList = getSelectedQuestionList(getFilteredQuestions());
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
        ArrayList<Question> qs;
        if(importType == 1)
            qs = UploadController.parseFile(fileContent);
        else
             qs = UploadController.parseJson(fileContent);
        for(int i = 0; i < qs.size(); i++)
            ejb.createQuestion(qs.get(i));
    } catch (Exception e) {
        errorMessage = "File not formatted correctly";
     //   FacesContext cxt = FacesContext.getCurrentInstance();
     //  cxt.addMessage("Format_Error", new FacesMessage(FacesMessage.SEVERITY_WARN, "File not formatted correctly", "File not formatted correctly"));
    }
    questionList = ejb.findQuestions(correspondingId);
    selectedList = getSelectedQuestionList(getFilteredQuestions());
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
    
    public String changeValid(AjaxBehaviorEvent event)
    {
        return null;
    }
    
    public void changeValid(Long id)
    {
        Question q = ejb.getQuestion(id);
        if(q.getValid())
            q.setValid(false);
        else
            q.setValid(true);
    }
    
    public String canDeleteEdit(Long id, Long userId)
    {
        User_Obj user = ejb.findUser(id);
        if(id == userId && id > 0 || user != null && user.getAdmin())
            return "";
        else
            return "display: none;";
    }
    
    public String canShowCheckBoxForSelection()
    {
        if(enableSelection)
            return "";
        else
            return "display: none;";
    }
    
    public String getErrorMessage()
    {
        String returnStr = new String(errorMessage);
        errorMessage = "";
        return returnStr;
    }
    
    public Integer getImportType()
    {
        return importType;
    }
    
    public void setImportType(Integer i)
    {
        importType = i;
    }
    
    public Integer getExportType()
    {
        return exportType;
    }
    
    public void setExportType(Integer i)
    {
        exportType = i;
    }
    
    public String export() throws IOException
    {
        if(exportType == 1)
            return exportCSV();
        else
            return exportJSON();
    }
    
    public String exportJSON() throws IOException
    {
        List<Question> qList = new ArrayList<Question>();
        for(int i = 0; i < selectedList.size(); i++)
        {
            if(selectedList.get(i).getSelected())
                qList.add(selectedList.get(i).getQuestion());
        }
        if(qList.isEmpty())
        {
            errorMessage = "No Questions Selected";
            return null;
        }
        
        String output = "";
        for(int i = 0; i < qList.size(); i++)
        {
            Question q = qList.get(i);
            JSONObject obj = new JSONObject();
            obj.put("Question", q.getQuestion());
            obj.put("Answer", q.getAnswer());
            obj.put("Difficulty", ""+q.getDifficulty());
            obj.put("Subject", q.getSubject().getLabel());

            JSONArray list = new JSONArray();
            for(int t = 0; t < q.getTags().size(); t++)
                list.add(q.getTags().get(t));

            obj.put("Tags", list);
            
            output += obj.toString()+"\n";
        }
        
        String fileName = "questionExport.json";
        String filePath;
        filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath+fileName));
            out.write(output);
            out.close();
        }
        catch(Exception e)
        {
            errorMessage = "Error Making Your Export, Please Try Again";
            return null;
        }
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); 
        ec.setResponseContentType("text/json"); 
        ec.setResponseContentLength((int)new File(filePath+fileName).length()); 
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); 
        
        OutputStream outputStream = ec.getResponseOutputStream();
        
        try{
            FileInputStream input = new FileInputStream(filePath+fileName);  
            byte[] buffer = new byte[1024];   
            int i = 0;  
            while ((i = input.read(buffer)) != -1) {  
                outputStream.write(buffer);  
                outputStream.flush();  
            } 
            input.close();
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        fc.responseComplete();
        new File(filePath+fileName).delete();
        
        return null;
    }
    
    public String exportCSV() throws IOException
    {
        List<Question> qList = new ArrayList<Question>();
        for(int i = 0; i < selectedList.size(); i++)
        {
            if(selectedList.get(i).getSelected())
                qList.add(selectedList.get(i).getQuestion());
        }
        if(qList.isEmpty())
        {
            errorMessage = "No Questions Selected";
            return null;
        }
        String output = "Question,Answer,Subject,Difficulty,Tags\n";
        for(int i = 0; i < qList.size(); i++)
        {
            Question q = qList.get(i);
            output += q.getQuestion()+","+q.getAnswer()+","+q.getSubject().getLabel()+","+q.getDifficulty();
            if(!q.getTags().isEmpty())
                output += ","+q.getTagsString();
            output += "\n";
        }
        String fileName = "questionExport.csv";
        String filePath;
        filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath+fileName));
            out.write(output);
            out.close();
        }
        catch(Exception e)
        {
            errorMessage = "Error Making Your Export, Please Try Again";
            return null;
        }
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); 
        ec.setResponseContentType("text/csv"); 
        ec.setResponseContentLength((int)new File(filePath+fileName).length()); 
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); 
        
        OutputStream outputStream = ec.getResponseOutputStream();
        
        try{
            FileInputStream input = new FileInputStream(filePath+fileName);  
            byte[] buffer = new byte[1024];   
            int i = 0;  
            while ((i = input.read(buffer)) != -1) {  
                outputStream.write(buffer);  
                outputStream.flush();  
            } 
            input.close();
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        fc.responseComplete();
        new File(filePath+fileName).delete();
        
        return null;
    }
}
