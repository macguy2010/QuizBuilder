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
    
    private boolean auth = false;

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
    
    public void setAdmin(boolean a)
    {
        auth = a;
    }
    
    public boolean getAdmin()
    {
        return auth;
    }
}
