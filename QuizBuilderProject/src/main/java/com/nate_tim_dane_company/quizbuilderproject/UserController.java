package com.nate_tim_dane_company.quizbuilderproject;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Stateful
@ManagedBean(name = "userController")
@SessionScoped
public class UserController 
{
    @EJB
    private UserEJB ejb;
    private String searchStr = new String();
    private List<User_Obj> usersList = null;
    private Long correspondingId = -100l;
    private User_Obj user = new User_Obj();
    private String passwordVerify;
    private String errorMessage = "";
    private String sortedBy = "";
    
    public String doCreateUser() {
        if(!passwordVerify.equals(user.getPassword()))
        {
            errorMessage = "Password Do Not Match";
            
            return null;
        }
        if(ejb.exists(user))
        {
            errorMessage = "Username Already In Use";
         //   FacesContext context = FacesContext.getCurrentInstance();
         //   context.addMessage("incorrect", new FacesMessage(FacesMessage.SEVERITY_WARN, "Username Already In Use", "Username Already In Use"));
            
            return null;
        }
        
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
    
    public String doEditUser()
    {
        ejb.editUser(user);
        usersList = ejb.findUsers();
        return "userPage.xhtml";
    }
    
    public String search()
    {
        return null;
    }
    
    public void setSearchStr(String str)
    {
        searchStr = str;
    }
    
    public String getSearchStr()
    {
        return searchStr;
    }
    
    public String sortByFirstName()
    {
        List<User_Obj> newList = new ArrayList<User_Obj>();
        boolean order = true;
        if(sortedBy.equals("F_A"))
        {
            order = false;
            sortedBy = "F_D";
        }
        else
            sortedBy = "F_A";
        while(!usersList.isEmpty())
        {
            User_Obj first = usersList.get(0);
            for(int j = 1; j < usersList.size(); j++)
            {
                if(usersList.get(j).getFirstName().compareTo(first.getFirstName()) < 0 == order)
                {
                    first = usersList.get(j);
                }
            }
            newList.add(first);
            usersList.remove(first);
        }
        usersList = newList;
        return null;
    }
    
    public String sortByLastName()
    {
        List<User_Obj> newList = new ArrayList<User_Obj>();
        boolean order = true;
        if(sortedBy.equals("L_A"))
        {
            order = false;
            sortedBy = "L_D";
        }
        else
            sortedBy = "L_A";
        while(!usersList.isEmpty())
        {
            User_Obj first = usersList.get(0);
            for(int j = 1; j < usersList.size(); j++)
            {
                if(usersList.get(j).getLastName().compareTo(first.getLastName()) < 0 == order)
                {
                    first = usersList.get(j);
                }
            }
            newList.add(first);
            usersList.remove(first);
        }
        usersList = newList;
        return null;
    }
    
    public String sortByUsername()
    {
        List<User_Obj> newList = new ArrayList<User_Obj>();
        boolean order = true;
        if(sortedBy.equals("U_A"))
        {
            order = false;
            sortedBy = "U_D";
        }
        else
            sortedBy = "U_A";
        while(!usersList.isEmpty())
        {
            User_Obj first = usersList.get(0);
            for(int j = 1; j < usersList.size(); j++)
            {
                if(usersList.get(j).getUsername().compareTo(first.getUsername()) < 0 == order)
                {
                    first = usersList.get(j);
                }
            }
            newList.add(first);
            usersList.remove(first);
        }
        usersList = newList;
        return null;
    }
    
    public String sortByPassword()
    {
        List<User_Obj> newList = new ArrayList<User_Obj>();
        boolean order = true;
        if(sortedBy.equals("P_A"))
        {
            order = false;
            sortedBy = "P_D";
        }
        else
            sortedBy = "P_A";
        while(!usersList.isEmpty())
        {
            User_Obj first = usersList.get(0);
            for(int j = 1; j < usersList.size(); j++)
            {
                if(usersList.get(j).getPassword().compareTo(first.getPassword()) < 0 == order)
                {
                    first = usersList.get(j);
                }
            }
            newList.add(first);
            usersList.remove(first);
        }
        usersList = newList;
        return null;
    }
    
    public String sortByEmail()
    {
        List<User_Obj> newList = new ArrayList<User_Obj>();
        boolean order = true;
        if(sortedBy.equals("E_A"))
        {
            order = false;
            sortedBy = "E_D";
        }
        else
            sortedBy = "E_A";
        while(!usersList.isEmpty())
        {
            User_Obj first = usersList.get(0);
            for(int j = 1; j < usersList.size(); j++)
            {
                if(usersList.get(j).getEmail().compareTo(first.getEmail()) < 0 == order)
                {
                    first = usersList.get(j);
                }
            }
            newList.add(first);
            usersList.remove(first);
        }
        usersList = newList;
        return null;
    }
    
    public List<User_Obj> getUsersList(Long id) {
        User_Obj currentUser = null;
        try{
            currentUser = ejb.findUser(id);
        }
        catch(Exception e){}
        if(currentUser == null || !currentUser.getAdmin())
        {
            usersList = new ArrayList<User_Obj>();
            try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/home.faces");
            }
            catch(Exception e){}
        }
        else
            usersList = ejb.findUsers(id);
        correspondingId = id;
        return getFilteredUsers();
    }
    
    public List<User_Obj> getFilteredUsers()
    {
        List<User_Obj> returnList = new ArrayList<User_Obj>();
        
        if(searchStr == null)
            searchStr = "";
        for(int i = 0; i < usersList.size(); i++)
        {
            if(usersList.get(i).getFirstName().trim().toLowerCase().contains(searchStr.trim().toLowerCase())
                   || usersList.get(i).getLastName().trim().toLowerCase().contains(searchStr.trim().toLowerCase())
                   || usersList.get(i).getPassword().trim().toLowerCase().contains(searchStr.trim().toLowerCase()) 
                   || usersList.get(i).getEmail().trim().toLowerCase().contains(searchStr.trim().toLowerCase()) )
            {
                returnList.add(usersList.get(i));
            }
        }
        
        return returnList;
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
    
    public String getPasswordVerify()
    {
        return passwordVerify;
    }
    
    public void setPasswordVerify(String p)
    {
        passwordVerify = p;
    }

    public String getAdmin(Long id){
        boolean admin = ejb.findUser(id).getAdmin();
        String string;

        if(admin == true){
            string = "";
        }
        else{
            string = "display:none;";
        }

        return string;
    }
    
    public String getErrorMessage()
    {
        String returnStr = new String(errorMessage);
        errorMessage = "";
        return returnStr;
    }
}
