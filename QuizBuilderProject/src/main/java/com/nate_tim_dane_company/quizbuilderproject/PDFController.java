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
import javax.faces.context.ExternalContext;
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


    public void writePdf(List<Question> questions) throws IOException{
        PDDocument quiz = null;

        int xPos = 0;
        int yPos = 750;
        int leftBuffer = 25;
        String filePath = null;
        String fileName = "quizExport.pdf";

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
            pageContentStream.moveTextPositionByAmount( leftBuffer,yPos );
            pageContentStream.drawString("Quiz");
            pageContentStream.endText();
            //pageContentStream.close();
            yPos -= 14;

            pageContentStream.beginText();
            pageContentStream.setFont(font,12);
            pageContentStream.moveTextPositionByAmount( leftBuffer,yPos );
            pageContentStream.drawString("Name:");
            pageContentStream.endText();
            //pageContentStream.close();
            yPos -= 14;

            pageContentStream.beginText();
            pageContentStream.setFont(font,12);
            pageContentStream.moveTextPositionByAmount( leftBuffer,yPos );
            pageContentStream.drawString("Grade:");
            yPos -= 25;

            pageContentStream.endText();
            pageContentStream.close();

            for(int i = 0; i < questions.size(); i++){
                StringBuilder sb = new StringBuilder();
                //get question only
                sb.append((i+1) + ". " +questions.get(i).getQuestion());
                //sb.append("\n\n\n");
                pageContentStream.beginText();
                pageContentStream.moveTextPositionByAmount(leftBuffer,yPos);
                pageContentStream.drawString(sb.toString());
                yPos -= 30;
                pageContentStream.endText();
                pageContentStream.close();
            }

            //String filePath;
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

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset();
        ec.setResponseContentType("pdf");
        ec.setResponseContentLength((int)new File(filePath+fileName).length());
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        OutputStream outputStream = ec.getResponseOutputStream();

        try{
            FileInputStream input = new FileInputStream(filePath+fileName);
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = input.read(buffer)) != -1) {
                outputStream.write(buffer);
                outputStream.flush();
            }
            input.close();
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        fc.responseComplete();
        new File(filePath+fileName).delete();

        //return null;

        //return quiz;
    }
}
