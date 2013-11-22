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
    private List<FieldElement> subjectsList = new ArrayList<FieldElement>();
    private int numberCorrect = 0;
    
    public String doGenerateQuiz()
    {
        // logic to generate quiz
        TreeMap<SubjectType, Integer> map = new TreeMap<SubjectType, Integer>();
        for(int i = 0; i < subjectsList.size(); i++)
            if(subjectsList.get(i).number > 0)
                map.put(subjectsList.get(i).type, subjectsList.get(i).number);
        quiz = ejb.buildQuiz(map);
        return "quizPage.xhtml";
    }
    
    public String doSubmitAnswers()
    {
        for(int i = 0; i < quiz.getQuestions().size(); i++)
        {
            String userAnswer = userAnswers[i].trim();
            if(userAnswer.equals(quiz.getQuestions().get(i).getAnswer()))
                numberCorrect++;
        }
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
        int sum = 0;
        for(int i = 0; i < subjectsList.size(); i++)
        {
            sum += subjectsList.get(i).number;
        }
        if(userAnswers == null || userAnswers.length != sum)
            userAnswers = new String[sum];
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
                if(getSubjectsList().get(j).type == g)
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
        if(subjectsList.isEmpty())
        {
            subjectsList.add(new FieldElement(SubjectType.MATHEMATICS, 10));
        }
        return subjectsList;
    }
        
    public String doButtonRemoveFieldClick(SubjectType type)
    {
        for(int i = 0; i < subjectsList.size(); i++)
        {
            if(subjectsList.get(i).type == type)
            {
                subjectsList.remove(i);
                break;
            }
        }
        return "quizGenerationPage.xhtml";
    }

    public String doButtonAddFieldClick()
    {
        subjectsList.add(new FieldElement(newSubject, 1));
        return "quizGenerationPage.xhtml";
    }
}
