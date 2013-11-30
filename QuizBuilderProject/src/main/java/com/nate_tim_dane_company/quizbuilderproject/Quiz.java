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
    @JoinTable(name="Quiz_Questions", joinColumns={@JoinColumn(name="Quiz_ID")}, inverseJoinColumns={@JoinColumn(name="Question_ID")})
    private List<Question> questions = new ArrayList<Question>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Grade")
    @Column(name = "Value")
    private List<Double> grades = new ArrayList<Double>();
    
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
    
    public int getNumberOfQuestions()
    {
        return questions.size();
    }
    
    public List<Double> getGrades()
    {
        return grades;
    }
    
    public double getAverageGrade()
    {
        double average = 0;
        for(int i = 0; i < grades.size(); i++)
            average += grades.get(i) / grades.size();
        return average;
    }
}
