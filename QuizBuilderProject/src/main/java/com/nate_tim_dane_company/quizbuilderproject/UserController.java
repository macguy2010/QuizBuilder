package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name = "userController")
@RequestScoped
public class UserController 
{
    @EJB
    private UserEJB ejb;
    private String searchStr = new String();
    private List<User> usersList = null;
    private User user = new User();
    private Long currentUserId;
    
    public String doCreateUser() {
        user = ejb.createUser(user);
        return "userPage.xhtml";
    }
    
    public String doDeleteUser() {
        ejb.deleteUser(currentUserId);
        usersList = ejb.findUsers();
        return "userPage.xhtml";
    }
    
    public String search()
    {
        usersList = ejb.searchUsers(searchStr);
        return "userPage.xhtml";
    }
    
    public void setSearchStr(String str)
    {
        searchStr = str;
    }
    
    public String getSearchStr()
    {
        return searchStr;
    }

    public List<User> getUsersList() {
        if(usersList == null)
            usersList = ejb.findUsers();
        return usersList;
    }

    public void setUsersList(List<User> uList) {
        usersList = uList;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public Long getCurrentUserId()
    {
        return currentUserId;
    }
    
    public void setCurrentUserId(Long id)
    {
        currentUserId = id;
    }
}
