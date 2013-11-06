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

    private List<User> userList = null;
    private LoginEJB ejb = new LoginEJB();
    private User user = new User();
    public String inputUsername = new String();
    public String inputPassword = new String();

    //check against the database to make sure username exists, then that inputPassword == password

    public String verifyUser(){
        userList =  ejb.verifyUser(inputUsername, inputPassword);

        if(userList.isEmpty()){
            return null;
            //fill in from page 361.  Create Faces Context, insert cookie.
        }
        else{
            HttpCookie loginCookie = new HttpCookie(inputUsername, inputPassword);
        }

        return null;
    }

    public void setInputUsername(String inputUsername){
        this.inputUsername = inputUsername;
    }

    public String getInputUsername(){
        return inputUsername;
    }

    public void setInputPassword(String inputPassword){
        this.inputPassword = inputPassword;
    }

    public String getInputPassword(){
        return inputPassword;
    }
}
