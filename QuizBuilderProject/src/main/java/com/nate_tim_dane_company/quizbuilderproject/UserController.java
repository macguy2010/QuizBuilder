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
    private List<User_Obj> usersList = null;
    private User_Obj user = new User_Obj();
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
    
    public String doEditUser()
    {
        ejb.editUser(currentUserId, user);
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

    public List<User_Obj> getUsersList() {
        if(usersList == null)
            usersList = ejb.findUsers();
        return usersList;
    }

    public void setUsersList(List<User_Obj> uList) {
        usersList = uList;
    }
    
    public User_Obj getUser()
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
