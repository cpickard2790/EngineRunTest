/*
The Question class reads the test1.txt file
and stores the lines in the file in an ArrayList.
*/

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Questions
{
    private String fileName;
    private ArrayList<String> question = new ArrayList<>();
    private ArrayList<String> justQuestion = new ArrayList<>();
    
    /**
    Constructor
    @param file The file name to be stored in fileName
    */
    
    public Questions(String file)
    {
        fileName = file;
    }
    
    /**
    setQuestion method stores the questions and answer choices
    in question ArrayList
    @throws IOException 
    */
    
    public void setQuestions() throws IOException
    {
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        while (inputFile.hasNext())
        {
            question.add(inputFile.nextLine());
        }
       
        inputFile.close();
    }
    
    public ArrayList<String> getQuestions()
    {
        return question;
    }
    
    /**
    setJustQuestions method stores just the questions
    in the justQuestions ArrayList
    */
    
    public void setJustQuestions()
    {
        for (int index = 0; index < question.size(); index++)
        {
            justQuestion.add(question.get(index));
            index += 4;
        }
    }
    
    /**
    getJustQuestions method
    @return The Strings in justQuestions ArrayList
    */
    
    public ArrayList<String> getJustQuestions()
    {
        return justQuestion;
    }
}