package com.nate_tim_dane_company.quizbuilderproject;


import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.faces.bean.ManagedBean;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import javax.faces.context.FacesContext;
import javax.swing.*;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.*;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 12/8/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "pdfController")
public class PDFController {


    public void writePdf(List<Question> questions){
        PDDocument quiz = null;

        //MyFileChooser fc = new MyFileChooser();
        //fc.()

        try{
            quiz = new PDDocument();
            PDPage page = new PDPage();
            quiz.addPage( page );
            PDFont font = PDType1Font.HELVETICA;
            PDPageContentStream pageContentStream = new PDPageContentStream( quiz, page );
            pageContentStream.beginText();
            pageContentStream.setFont(font, 12);
            pageContentStream.moveTextPositionByAmount(25, 100);
            pageContentStream.drawString("Quiz\n" + "Name:\n" + "Grade:\n\n\n");
            pageContentStream.endText();

            for(int i = 0; i < questions.size(); i++){
                StringBuilder sb = new StringBuilder();
                //get question only
                sb.append(i+ ".: " +questions.get(i).getQuestion());
                sb.append("\n\n\n");
                pageContentStream.beginText();
                pageContentStream.drawString(sb.toString());
                pageContentStream.endText();
            }

            String fileName = "quizTest.pdf";

            String filePath;
            filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

            quiz.save(filePath+fileName);
            quiz.close();
        }
        catch(COSVisitorException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //return quiz;
    }
}
