package com.nate_tim_dane_company.quizbuilderproject;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.*;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

@Stateful
@ManagedBean(name = "quizGenerationController")
@SessionScoped
public class QuizGenerationController implements Serializable
{
    @EJB
    private QuizBuilderEJB ejb;
    private int[] numberRange = null;
    private int maxNumber = 50;
    private Quiz quiz = new Quiz();
    private SubjectType newSubject;
    private String[] userAnswers = null;
    private List<FieldElement> subjectsList = null;
    private int numberCorrect = 0;
    
    public String toQuizPage()
    {
        // logic to generate quiz
        quiz = null;
        return "quizPage.xhtml";
    }
    
    public Quiz getGeneratedQuiz()
    {
        if(quiz == null)
        {
            quiz = new Quiz();
            TreeMap<SubjectType, Integer> map = new TreeMap<SubjectType, Integer>();
            for(int i = 0; i < subjectsList.size(); i++)
                if(subjectsList.get(i).getNumber() > 0)
                    map.put(subjectsList.get(i).getType(), subjectsList.get(i).getNumber());
            quiz = ejb.buildQuiz(quiz, map);
        }
        return quiz;
    }
    
    public String doSubmitAnswers()
    {
        for(int i = 0; i < quiz.getQuestions().size(); i++)
        {
            String userAnswer = userAnswers[i].trim();
            if(userAnswer.equals(quiz.getQuestions().get(i).getAnswer()))
                numberCorrect++;
        }
        quiz.getGrades().add((double)numberCorrect / quiz.getQuestions().size());
        return "resultsPage.xhtml";
    }
    
    public Quiz getQuiz()
    {
        return quiz;
    }
    
    public int[] getNumberRange()
    {
        if(numberRange == null)
        {
            numberRange = new int[maxNumber];
            for(int i = 0; i < maxNumber; i++)
                numberRange[i] = i+1;
        }
        return numberRange;
    }
    
    public int getNumberCorrect()
    {
        return numberCorrect;
    }
    
    public void setNumberRange(int[] r)
    {
        numberRange = r;
    }
    
    public String[] getUserAnswers()
    {
        int num = getGeneratedQuiz().getNumberOfQuestions();
        if(userAnswers == null || userAnswers.length != num)
            userAnswers = new String[num];
        return userAnswers;
    }
    
    public SubjectType getNewSubject()
    {
        return newSubject;
    }
    
    public void setNewSubject(SubjectType s)
    {
        newSubject = s;
    }
    
    public SelectItem[] getSubjectTypeValues() 
    {        
        int i = 0;
        SelectItem[] itemsNew = new SelectItem[SubjectType.values().length - getSubjectsList().size()];
        for(SubjectType g: SubjectType.values())
        {
            boolean exists = false;
            for(int j = 0; j < getSubjectsList().size(); j++)
                if(getSubjectsList().get(j).getType() == g)
                {
                    exists = true;
                    break;
                }
            if(!exists)
                itemsNew[i++] = new SelectItem(g, g.getLabel());
        }
        
        return itemsNew;
    }
    
    public List<FieldElement> getSubjectsList()
    {
        if(subjectsList == null || subjectsList.isEmpty())
        {
            subjectsList = new ArrayList<FieldElement>();
            subjectsList.add(new FieldElement(SubjectType.MATHEMATICS));
        }
        return subjectsList;
    }
        
    public String doButtonRemoveFieldClick(SubjectType type)
    {
        for(int i = 0; i < subjectsList.size(); i++)
        {
            if(subjectsList.get(i).getType() == type)
            {
                subjectsList.remove(i);
                break;
            }
        }
        return "quizGenerationPage.xhtml";
    }

    public String doButtonAddFieldClick()
    {
        subjectsList.add(new FieldElement(newSubject));
        return "quizGenerationPage.xhtml";
    }
}
