package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import javax.faces.bean.SessionScoped;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/5/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
//@WebServlet(name="loginController")
@ManagedBean(name="loginController")
@SessionScoped
public class LoginController {

    private List<User_Obj> userList = null;
    public String inputUsername;
    public String inputPassword;
    @EJB
    private LoginEJB ejb = new LoginEJB();
    private User_Obj user = new User_Obj();
    boolean authenticated = false;
    long id = -1;

    public boolean verifyUser(String inputUsername, String inputPassword){
        FacesContext ctx = FacesContext.getCurrentInstance();
        user = ejb.verifyUser(inputUserName, inputPassword);
        if(user != null){
            if(user.getPassword().equals(inputPassword)){
                //user is then verified
                authenticated = true;
                id = user.getId();
                return "Home.xhtml"
            }
            else{
                ctx.addMessage("incorrect", new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Incorrect Username or Password"));
                return "loginPage.xhtml"
            }
        }
        else{
            ctx.addMessage("incorrect", new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Incorrect Username or Password"));
            return "loginPage.xhtml"
        }
    }

   public User_Obj getUser(){
	return user;
   }

   public void setUser(User_Obj user){
	this.user = user;
   }

   public boolean getAuthenticated(){
       return authenticated;
   }

   public void setAuthenticated(boolean authenticated){
       this.authenticated = authenticated;
   }


    //check against the database to make sure username exists, then that inputPassword == password

    /*public String setVerified() throws IOException{
        userList =  ejb.verifyUser(user.getUsername(), user.getPassword());
        FacesContext cxt = FacesContext.getCurrentInstance();

        if(userList.isEmpty()){

            //if wrong username/password return to login page.  Display error
            cxt.addMessage("incorrect", new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Incorrect Username or Password"));
            return "loginPage.xhtml";
        }
        else{

            id = user.getId();

            authenticated = true;

            return "Home.xhtml";
        }

       // return null;
    }

    public  boolean getVerified(){
        return authenticated;
    }*/
}
