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
    public Integer[] range;
    public Integer number;
    
    public FieldElement(SubjectType t, int n)
    {
        type = t;
        range = new Integer[10];
        number = 1;
        populateValues();
    }
    private void populateValues()
    {
        for(int i = 0; i < range.length; i++)
            range[i] = i+1;
    }
    public Integer[] getRange()
    {
        return range;
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
