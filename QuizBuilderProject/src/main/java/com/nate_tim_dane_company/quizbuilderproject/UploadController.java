/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nate_tim_dane_company.quizbuilderproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@ManagedBean(name = "uploadController")
public class UploadController {
    
    public static ArrayList<Question> parseJson(String str) throws Exception
    {
        ArrayList<Question> qs = new ArrayList<Question>();
        JSONParser parser = new JSONParser();
        
        String[] lines = str.split("\n|\r");
        for(String line : lines)
        {
            Question q = new Question();
            Object obj = parser.parse(line);
            JSONObject jsonObject = (JSONObject) obj;
            
            q.setQuestion((String)jsonObject.get("Question"));
            q.setAnswer((String)jsonObject.get("Answer"));
            q.setDifficulty((Integer)jsonObject.get("Difficulty"));
            
            String sub = (String)jsonObject.get("Subject");
            if(sub.toUpperCase().contains("MATH"))
                q.setSubject(SubjectType.MATHEMATICS);
            else if(sub.toUpperCase().contains("ENGLISH"))
                q.setSubject(SubjectType.ENGLISH);
            else if(sub.toUpperCase().contains("LITER"))
                q.setSubject(SubjectType.LITERATURE);
            else if(sub.toUpperCase().contains("GEO"))
                q.setSubject(SubjectType.GEOGRAPHY);
            else if(sub.toUpperCase().contains("HISTORY"))
                q.setSubject(SubjectType.HISTORY);
            else if(sub.toUpperCase().contains("COMPUTER"))
                q.setSubject(SubjectType.COMPUTER_SCIENCE);
            else if(sub.toUpperCase().contains("SCIENCE"))
                q.setSubject(SubjectType.SCIENCE);
            else
                q.setSubject(SubjectType.OTHER);
            
            JSONArray msg = (JSONArray) jsonObject.get("Tags");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                q.addTag(iterator.next());
     
            qs.add(q);
            
        }
    }
    return qs;
}
 
  public static ArrayList<Question> parseFile(String str) throws Exception
  {
      String[] lines = str.split("\n|\r");
      String[] first = lines[0].split(",");
      ArrayList<String> order = new ArrayList<String>();
      ArrayList<Question> qs = new ArrayList<Question>();
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
          qs.add(q);
      }
      return qs;
  }
}