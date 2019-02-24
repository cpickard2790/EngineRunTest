/*
GradeTest class grades the test
*/

public class GradeTest
{
    private int numMissed;      // Number of questions missed
    private int numQuestions;      // Number of questions
    private double pointsEach;     // How many points each question is worth
    private double score;          // Final score
    

    /**
    setNumQuestions method sets the number of questions on test
    @param questions Number of questions to set
    */
    
    public void setNumQuestions(int questions)
    {
        numQuestions = questions;
    }
    
    /**
    getNumQuestions method gets the number of questions on test
    @return The number of questions
    */
    
    public int getNumQuestions()
    {
        return numQuestions;
    }
    
    /**
    markWrong method marks the question wrong
    */
    
    public void markWrong()
    {
        numMissed += 1;
    }
    
    /**
    getMissed method
    @return The number of missed questions
    */
    
    public int getMissed()
    {
        return numMissed;
    }
    
    /**
    setScore method calculates the final score
    */
    
    public void setScore()
    {
        double numericScore;
        
        pointsEach = 100.0 / numQuestions;
        numericScore = 100.0 - (numMissed * pointsEach);
        score = numericScore;
    }
    
    /**
    getScore method
    @return The final score
    */
    
    public double getScore()
    {
        return score;
    }
}
