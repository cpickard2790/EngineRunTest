import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class TestAnswers
{
    private String fileName;   // Hold the file name
    private ArrayList<String> answers = new ArrayList<>();  // Store answers
    
    /**
    Constructor
    @param name To hold the file name
    */
    
    public TestAnswers(String name)
    {
        fileName = name;
    }
    
    /**
    setAnswers method opens the file and and writes lines from file
    into answers ArrayList.
    @throws IOException 
    */
    
    public void setTestAnswers() throws IOException
    {
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        
        while (inputFile.hasNext())
        {
            answers.add(inputFile.nextLine());
        }
        
        inputFile.close();
    }
    
    /**
    getAnswers method
    @return answers from ArrayList answers
    */
    
    public ArrayList<String> getTestAnswers()
    {
        return answers;
    }
}
