package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Cookie;
import java.net.HttpCookie;
import java.util.List;

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
        //FacesContext ctx = FacesContext.getCurrentInstance();
        //need to add faces context dependency
        if(userList.isEmpty()){
            
            //if wrong username/password return to login page.  Display error
            return "loginPage.xhtml";
            //fill in from page 361.  Create Faces Context, insert cookie.  I'll look at this tonight
        }
        else{
            HttpCookie loginCookie = new HttpCookie("loginCookie", user.getUsername());
		//use cookie.getValue to return user.getUsername().  Use for loading user specific database entries.
            loginCookie.setSecure(true);
            
            //Directory path the cookie is visible on.  All child directories have access.
            loginCookie.setPath("/QuizBuilderProject/src/main"); 
            
            //return them home with login cookie in place.
            return "home.xhtml";
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
