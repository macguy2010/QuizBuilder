/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

@ManagedBean(name = "uploadController")
public class UploadController {
    
    @EJB
    private QuizBuilderEJB ejb;
    
  private Part file;
  private String fileContent;
 
  public String upload() {
    try {
      fileContent = new Scanner(file.getInputStream())
          .useDelimiter("\\A").next();
    } catch (IOException e) {

    }
    try{
        parseFile(fileContent);
    } catch (Exception e) {
        FacesContext cxt = FacesContext.getCurrentInstance();
        cxt.addMessage("Format_Error", new FacesMessage(FacesMessage.SEVERITY_WARN, "File not formatted correctly", "File not formatted correctly"));
    }
    return null;
  }
  
  private void parseFile(String str) throws Exception
  {
      String[] lines = str.split("\n|\r");
      String[] first = lines[0].split(",");
      ArrayList<String> order = new ArrayList<String>();
      for(String element : first)
      {
          if(element.trim().toLowerCase().equals("question"))
              order.add("question");
          else if (element.trim().toLowerCase().equals("answer"))
              order.add("answer");
          else if (element.trim().toLowerCase().equals("subject"))
              order.add("subject");
          else if (element.trim().toLowerCase().equals("difficulty"))
              order.add("difficulty");
          else if (element.trim().toLowerCase().equals("tags") || element.trim().toLowerCase().equals("tag"))
              break;
          else
              throw new Exception("File is not in the correct format");
      }
      for(int i = 1; i < lines.length; i++)
      {
          Question q = new Question();
          String line = lines[i];
          String[] elements = line.split(",");
          for(int j = 0; j < elements.length; j++)
          {
              if(j >= order.size())
              {
                  q.addTag(elements[j].trim());
                  continue;
              }
              if(order.get(j).equals("question"))
                  q.setQuestion(elements[j].trim());
              else if(order.get(j).equals("answer"))
                  q.setAnswer(elements[j].trim());
              else if(order.get(j).equals("subject"))
              {
                  if(elements[j].toUpperCase().contains("MATH"))
                      q.setSubject(SubjectType.MATHEMATICS);
                  else if(elements[j].toUpperCase().contains("ENGLISH"))
                      q.setSubject(SubjectType.ENGLISH);
                  else if(elements[j].toUpperCase().contains("LITER"))
                      q.setSubject(SubjectType.LITERATURE);
                  else if(elements[j].toUpperCase().contains("GEO"))
                      q.setSubject(SubjectType.GEOGRAPHY);
                  else if(elements[j].toUpperCase().contains("HISTORY"))
                      q.setSubject(SubjectType.HISTORY);
                  else if(elements[j].toUpperCase().contains("COMPUTER"))
                      q.setSubject(SubjectType.COMPUTER_SCIENCE);
                  else if(elements[j].toUpperCase().contains("SCIENCE"))
                      q.setSubject(SubjectType.SCIENCE);
                  else
                      q.setSubject(SubjectType.OTHER);
              }
              else if(order.get(j).equals("difficulty"))
                  q.setDifficulty(Integer.parseInt(elements[j].trim()));
          }
          q = ejb.createQuestion(q);
      }
  }
 
  public Part getFile() {
    return file;
  }
 
  public void setFile(Part file) {
    this.file = file;
  }
}