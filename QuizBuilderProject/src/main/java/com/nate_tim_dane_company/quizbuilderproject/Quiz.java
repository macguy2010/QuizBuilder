/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@NamedQuery(name = "findAllQuizzes", query = "SELECT q FROM Quiz q")
public class Quiz implements Serializable {
    
    // variables
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @OneToMany
    @JoinTable(name="User_Questions", joinColumns={@JoinColumn(name="User_ID")}, inverseJoinColumns={@JoinColumn(name="Question_ID")})
    private List<Question> questions = new ArrayList<Question>();
    
    @OneToMany
    @JoinTable(name="User_Quizzes", joinColumns={@JoinColumn(name="User_ID")}, inverseJoinColumns={@JoinColumn(name="Quiz_ID")})
    private List<Quiz> quizzes = new ArrayList<Quiz>();
    
    // methods
    public List<Question> getQuestions()
    {
        return questions;
    }
    
    public void setQuestions(ArrayList<Question> q)
    {
        questions = q;
    }
    
    public void addQuestion(Question q)
    {
        questions.add(q);
    }
    
    public void setTitle(String t)
    {
        title = t;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }
}
