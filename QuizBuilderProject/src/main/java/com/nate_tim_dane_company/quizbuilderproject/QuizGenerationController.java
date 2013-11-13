package com.nate_tim_dane_company.quizbuilderproject;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.*;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "quizGenerationController")
@RequestScoped
public class QuizGenerationController implements Serializable
{
    @EJB
    private QuizBuilderEJB ejb;
    private Integer numberOfQuestions = 10;
    private int[] numberRange = null;
    private int maxNumber = 50;
    private Quiz quiz = new Quiz();
    private SubjectType subject;
    private List<Integer[]> subjectsList = new ArrayList<Integer[]>();
    
    public String doGenerateQuiz()
    {
        // logic to generate quiz
        TreeMap<SubjectType, Integer> map = new TreeMap<SubjectType, Integer>();
        ejb.buildQuiz(quiz, map);
        return "quizPage.xhtml";
    }
    
    public Integer getNumberOfQuestions()
    {
        return numberOfQuestions;
    }
    
    public void setNumberOfQuestions(Integer n)
    {
        numberOfQuestions = n;
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
    
    public void setNumberRange(int[] r)
    {
        numberRange = r;
    }
    
    public SubjectType getSubject()
    {
        return subject;
    }
    
    public void setSubject(SubjectType s)
    {
        subject = s;
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
    
    public List<Integer[]> getSubjectsList()
    {
        if(subjectsList.isEmpty())
        {
            subjectsList.add(new Integer[10]);
            populateValues(subjectsList.get(0));
        }
        return subjectsList;
    }
    
    private void populateValues(Integer[] range)
    {
        for(int i = 0; i < range.length; i++)
            range[i] = i+1;
    }
    
    public void onButtonRemoveFieldClick(final Integer[] range)
    {
        subjectsList.remove(range);
    }

    public String onButtonAddFieldClick()
    {
        subjectsList.add(new Integer[10]);
        return null;
    }
}
