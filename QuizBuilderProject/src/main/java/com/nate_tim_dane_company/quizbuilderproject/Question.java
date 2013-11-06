/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQuery(name = "findAllQuestions", query = "SELECT q FROM Question q")
public class Question implements Serializable{
    
    // variables
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String question;
    
    private String answer;
    
    @Enumerated(EnumType.ORDINAL)
    private SubjectType subject;
    
    private Integer difficulty;
    
    
    // methods
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id) 
    {
        this.id = id;
    }
    
    public String getQuestion()
    {
        return question;
    }
    
    public void setQuestion(String q)
    {
       question = q;
    }
    
    public String getAnswer()
    {
        return answer;
    }
    
    public void setAnswer(String a)
    {
       answer = a;
    }
    
    public Integer getDifficulty()
    {
        return difficulty;
    }
    
    public void setDifficulty(Integer d)
    {
        difficulty = d;
    }
    
    public SubjectType getSubject()
    {
        return subject;
    }
    
    public void setSubject(SubjectType st)
    {
        subject = st;
    }
}
