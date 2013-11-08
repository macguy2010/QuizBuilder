package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name = "quizGenerationController")
@RequestScoped
public class QuizGenerationController 
{
    private Integer numberOfQuestions;
    private int[] numberRange = null;
    private int maxNumber = 50;
    private Quiz quiz = new Quiz();
    
    public String doGenerateQuiz()
    {
        // logic to generate quiz
        
        return "";
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
}
