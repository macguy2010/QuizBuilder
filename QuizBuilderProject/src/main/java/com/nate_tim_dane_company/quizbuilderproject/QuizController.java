package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.*;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@Stateful
@ManagedBean(name = "quizController")
@SessionScoped
public class QuizController 
{
    @EJB
    private QuizBuilderEJB ejb;
    private Question[] questionsCheckList = null;
    private Long[] questionsSelectionList;
    private String searchStr = new String();
    private Quiz quiz = new Quiz();
    private List<Quiz> quizList = null;
    private List<Quiz> filteredQuizzes = null;
    private Long correspondingId = -100l;
    private Integer filter = 1;
    private Boolean valid = false;
    private List<QuestionElement> userAnswers = null;
    private String sortedBy = "";
    private int numberCorrect = 0;
    
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
        filteredQuizzes = getFilteredQuizzes();
        return "quizList.xhtml";
    }
    
    public String doDeleteQuiz(Long id) {
        ejb.deleteQuiz(id);
        quizList = ejb.findQuizzes(correspondingId);
        filteredQuizzes = getFilteredQuizzes();
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
        filteredQuizzes = getFilteredQuizzes();
        quiz = new Quiz();
        return "quizList.xhtml";
    }
    
    public List<QuestionElement> getGeneratedQuizQuestions()
    {
        userAnswers = new ArrayList<QuestionElement>();
        List<Question> questions = quiz.getQuestions();
        for(int i = 0; i < questions.size(); i++)
        {
            userAnswers.add(new QuestionElement(questions.get(i).getQuestion(), questions.get(i).getAnswer()));
        }
        return userAnswers;
    }
    
    public String doSubmitAnswers()
    {
        numberCorrect = 0;
        for(int i = 0; i < userAnswers.size(); i++)
        {
            if(userAnswers.get(i).getUserAnswer().trim().toLowerCase().equals(userAnswers.get(i).getAnswer().trim().toLowerCase()))
            {
                numberCorrect++;
                userAnswers.get(i).setCorrect(1);
            }
        }
        quiz.getGrades().add((double)numberCorrect / userAnswers.size());
        quizList = ejb.findQuizzes(correspondingId);
        filteredQuizzes = getFilteredQuizzes();
    //    if(quiz.getIsTemporary())
    //        ejb.deleteQuiz(quiz.getId());
        return "resultsPagePublic.xhtml";
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
    
    public void setValid(Boolean b)
    {
        valid = b;
    }
    
    public Boolean getValid()
    {
        return valid;
    }
    
    public void changeValid(Long id)
    {
        Quiz q = ejb.getQuiz(id);
        if(q.getValid())
            q.setValid(false);
        else
            q.setValid(true);
    }
    
    public String sortByTitle()
    {
        List<Quiz> newList = new ArrayList<Quiz>();
        boolean order = true;
        if(sortedBy.equals("T_A"))
        {
            order = false;
            sortedBy = "T_D";
        }
        else
            sortedBy = "T_A";
        while(!quizList.isEmpty())
        {
            Quiz first = quizList.get(0);
            for(int j = 1; j < quizList.size(); j++)
            {
                if(quizList.get(j).getTitle().compareTo(first.getTitle()) < 0 == order)
                {
                    first = quizList.get(j);
                }
            }
            newList.add(first);
            quizList.remove(first);
        }
        quizList = newList;
        return null;
    }
    
    public String sortByNumQuestions()
    {
        List<Quiz> newList = new ArrayList<Quiz>();
        boolean order = true;
        if(sortedBy.equals("N_A"))
        {
            order = false;
            sortedBy = "N_D";
        }
        else
            sortedBy = "N_A";
        while(!quizList.isEmpty())
        {
            Quiz first = quizList.get(0);
            for(int j = 1; j < quizList.size(); j++)
            {
                if(quizList.get(j).getNumberOfQuestions() < first.getNumberOfQuestions() == order)
                {
                    first = quizList.get(j);
                }
            }
            newList.add(first);
            quizList.remove(first);
        }
        quizList = newList;
        return null;
    }
    
    public String sortByAverage()
    {
        List<Quiz> newList = new ArrayList<Quiz>();
        boolean order = true;
        if(sortedBy.equals("A_A"))
        {
            order = false;
            sortedBy = "A_D";
        }
        else
            sortedBy = "A_A";
        while(!quizList.isEmpty())
        {
            Quiz first = quizList.get(0);
            for(int j = 1; j < quizList.size(); j++)
            {
                if(quizList.get(j).getAverageGrade() < first.getAverageGrade() == order)
                {
                    first = quizList.get(j);
                }
            }
            newList.add(first);
            quizList.remove(first);
        }
        quizList = newList;
        return null;
    }
    
    public List<Quiz> getQuizList(Long id) {
        if(quizList == null || filteredQuizzes == null || correspondingId != id || ejb.quizCount(id) != (long)quizList.size())
        {
            quizList = ejb.findQuizzes(id);   
           
        }
        filteredQuizzes = getFilteredQuizzes();
        correspondingId = id;
        return filteredQuizzes;
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
     
    public String viewQuiz(Long id, Long uId)
    {
        for(int i = 0; i < quizList.size(); i++)
            if(quizList.get(i).getId() == id)
            {
                quiz = ejb.findQuiz(id);
                break;
            }
        if(quiz.getUserId() == uId && uId > 0)
            return "quizViewPage.xhtml";
        else
            return "quizPagePublic.xhtml";
    }

    public List<Quiz> getQuizList() {
        if(quizList == null)
            quizList = ejb.findQuizzes();
        return quizList;
    }
    
    public List<QuestionElement> getUserAnswers()
    {
        return userAnswers;
    }
    
    public String isCorrect(Integer c)
    {
        if(c == 1)
            return "check.png";
        else
            return "x.png";
    }
    
    public String getGradeString()
    {
        return ""+numberCorrect+"/"+userAnswers.size();
    }

    public void setQuizList(List<Quiz> qList) {
        quizList = qList;
        filteredQuizzes = getFilteredQuizzes();
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
