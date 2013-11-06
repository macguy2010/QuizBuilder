package com.nate_tim_dane_company.quizbuilderproject;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userController")
@RequestScoped
public class UserController 
{
    @EJB
    private UserEJB ejb;
    private String searchStr = new String();
    private List<User_Obj> usersList = null;
    private User_Obj user = new User_Obj();
    
    public String doCreateUser() {
        user = ejb.createUser(user);
        return "userPage.xhtml";
    }
    
    public String doDeleteUser(Long id) {
        ejb.deleteUser(id);
        usersList = ejb.findUsers();
        return "userPage.xhtml";
    }
    
    public String toEditUser(Long id)
    {
        user = ejb.findUser(id);
        return "editUser.xhtml";
    }
    
    public String doEditUser(Long id)
    {
        ejb.editUser(id, user);
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
}
