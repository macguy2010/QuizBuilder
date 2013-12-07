/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

/**
 *
 * @author nathanaston
 */
public class QuestionElement {
    
    private String question = "";
    private String answer = "";
    private String userAnswer = "";
    
    public QuestionElement(String q, String a)
    {
        question = q;
        answer = a;
    }
    
    public void setQuestion(String q)
    {
        answer = q;
    }
    
    public String getQuestion()
    {
        return answer;
    }
    
    public void setAnswer(String a)
    {
        answer = a;
    }
    
    public String getAnswer()
    {
        return answer;
    }
    
    public void setUserAnswer(String ua)
    {
        answer = ua;
    }
    
    public String getUserAnswer()
    {
        return userAnswer;
    }
}