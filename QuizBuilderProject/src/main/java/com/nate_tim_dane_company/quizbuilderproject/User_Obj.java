/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author nathanaston
 */
@Entity
@NamedQuery(name = "findAllUsers", query = "SELECT u FROM User_Obj u")
public class User_Obj implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    
    private boolean admin = false;
    
    @OneToMany
    @JoinTable(name="User_Questions", joinColumns={@JoinColumn(name="User_ID")}, inverseJoinColumns={@JoinColumn(name="Question_ID")})
    private List<Question> questions = new ArrayList<Question>();
    
    @OneToMany
    @JoinTable(name="User_Quizzes", joinColumns={@JoinColumn(name="User_ID")}, inverseJoinColumns={@JoinColumn(name="Quiz_ID")})
    private List<Quiz> quizzes = new ArrayList<Quiz>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFirstName(String first)
    {
        firstName = first;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setLastName(String last)
    {
        lastName = last;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public void setUsername(String name)
    {
        username = name;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setPassword(String pass)
    {
        password = pass;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setEmail(String e)
    {
        email = e;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setQuestions(ArrayList<Question> q)
    {
        questions = q;
    }
    
    public List<Question> getQuestions()
    {
        return questions;
    }
    
    public void setQuizzes(ArrayList<Quiz> q)
    {
        quizzes = q;
    }
    
    public List<Quiz> getQuizzes()
    {
        return quizzes;
    }
    
    public void setAdmin(boolean a)
    {
        admin = a;
    }
    
    public boolean getAdmin()
    {
        return admin;
    }
    
    public void addQuestion(Question q)
    {
        questions.add(q);
    }
    
    public void addQuiz(Quiz q)
    {
        quizzes.add(q);
    }
}
