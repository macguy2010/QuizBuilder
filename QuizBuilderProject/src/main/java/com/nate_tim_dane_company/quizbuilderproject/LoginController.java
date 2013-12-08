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
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import java.lang.StringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/5/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
//@WebServlet(name="loginController")
@ManagedBean(name="loginController")
@Stateful
@SessionScoped
public class LoginController {

    //private List<User_Obj> userList = null; This has been set in verifyUser().
    public String inputUsername;
    public String inputPassword;
    @EJB
    private LoginEJB ejb = new LoginEJB();
    private User_Obj user = new User_Obj();
    boolean authenticated = false;
    private Long id = -1l;
    private String errorMessage = "";

    public String verifyUser(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        List<User_Obj> userList = ejb.verifyUser(inputUsername);
        if(!userList.isEmpty()){
            user = userList.get(0);
            if(user.getPassword().equals(inputPassword)){
                //user is then verified
                authenticated = true;
                id = user.getId();
                return "home.xhtml";
            }
            else{
                errorMessage = "Incorrect Username or Password";
                return null;
            }
        }
        else{
            errorMessage = "Incorrect Username or Password";
            //ctx.addMessage("incorrect", new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Incorrect Username or Password"));
            return null;
        }
    }

    public String logout(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        authenticated = false;
        id = -1l;
        ctx.addMessage("logout",new FacesMessage(FacesMessage.SEVERITY_INFO,"You have been successfully logged out","You have been successfully logged out"));
        return "home.xhtml";
    }

    public void setInputUsername(String inputUsername){
        this.inputUsername = inputUsername;
    }

    public void setInputPassword(String inputPassword){
        this.inputPassword = inputPassword;
    }
    
    public String getInputUsername(){
        return inputUsername;
    }

    public String getInputPassword(){
       return inputPassword;
    }

    public Long getId(){
        return id;
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

    public String redirect(){
        if(authenticated == true){
            return logout();
        }
        else{
            return "loginPage.xhtml";
        }
    }

    public String getLoginValue(){
        StringBuilder sb = new StringBuilder();
        if(authenticated == true){
            sb.append("Logout");
            sb.append(" "+ user.getFirstName());
            return sb.toString();
        }
        else
            return "Login";
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
/***********
 *
 *   Not needed as authentication this is the only page that ever changes authentication status.
 *
 **********/
   //public void setAuthenticated(boolean authenticated){
   //  this.authenticated = authenticated;
   //}


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
