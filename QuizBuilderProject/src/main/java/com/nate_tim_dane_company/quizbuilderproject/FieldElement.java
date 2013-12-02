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
    private SubjectType type;
    private Integer number;
    
    public FieldElement(SubjectType t)
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
    
    public void setType(SubjectType t)
    {
        type = t;
    }
}
