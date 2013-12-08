/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

/**
 *
 * @author nathanaston
 */
public class SelectedQuestionElement {
    private Question question;
    private Boolean selected = false;
    
    public SelectedQuestionElement(Question q)
    {
        question = q;
    }
    
    public Boolean getSelected()
    {
        return selected;
    }
    
    public void setSelected(Boolean s)
    {
        selected = s;
    }
    
    public Question getQuestion()
    {
        return question;
    }
    
    public void setQuestion(Question q)
    {
        question = q;
    }
}
