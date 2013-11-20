/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

/**
 *
 * @author nathanaston
 */
public class FieldElement {
    public SubjectType type;
    public Integer number;
    
    public FieldElement(SubjectType t, int n)
    {
        type = t;
        number = 1;
    }
    public Integer getNumber()
    {
        return number;
    }
    public SubjectType getType()
    {
        return type;
    }
    public void setNumber(Integer n)
    {
        number = n;
    }
}
