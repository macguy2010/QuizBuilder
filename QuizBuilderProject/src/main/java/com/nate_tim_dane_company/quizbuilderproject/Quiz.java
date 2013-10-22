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
public class Quiz {
    
    // variables
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Quiz_Questions")
    private List<Question> questions = new ArrayList<Question>();
    
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
}
