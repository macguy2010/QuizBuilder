package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.*;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/5/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean(name="loginController")
public class LoginController {

    private List<User_Obj> userList = null;
    @EJB
    private LoginEJB ejb = new LoginEJB();
    private User_Obj user = new User_Obj();

    //check against the database to make sure username exists, then that inputPassword == password

    public String verifyUser(){
        userList =  ejb.verifyUser(user.getUsername(), user.getPassword());
        FacesContext cxt = FacesContext.getCurrentInstance();
  //      HttpServletResponse response = new HttpServletResponse() {};
        //need to add faces context dependency
        if(userList.isEmpty()){
            
            //if wrong username/password return to login page.  Display error
            cxt.addMessage("incorrect", new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username or Password", "Incorrect Username or Password"));
            return null;
            //fill in from page 361.  Create Faces Context, insert cookie.  I'll look at this tonight
        }
        else{
            Cookie loginCookie = new Cookie("loginCookie", user.getUsername());
	    //use cookie.getValue to return user.getUsername().  Use for loading user specific database entries.
            
            loginCookie.setSecure(true);
            loginCookie.setMaxAge(-1);
            
            //Directory path the cookie is visible on.  All child directories have access.
            loginCookie.setPath("/"); 
     //       response.setCookie(loginCookie);
          
            //return them home with login cookie in place.
            return "Home.xhtml";
            //response.sendRedirect("Home.xhtml");
        }

        //return "home.xhtml";
    }

   public User_Obj getUser(){
	return user;
   }

   public void setUser(User_Obj user){
	this.user = user;
   }
}
