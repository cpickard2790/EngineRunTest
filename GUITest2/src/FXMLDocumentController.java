/*
Control FXMLDocument
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FXMLDocumentController implements Initializable
{
    @FXML
    private ToggleGroup answers;        // Group of RadioButtons
    @FXML
    private Label labelA;               // Answer choice label A
    @FXML
    private Label labelB;               // Answer choice label B
    @FXML
    private Label labelC;               // Answer choice label C
    @FXML
    private Label labelD;               // Answer choice label D
    @FXML
    private TextArea questionBox;       // Label box for questions
    @FXML
    private Button nextButton;          // Next button
    
    private int aSpot = 0;          // Start of index for answer choice A
    private int bSpot = 1;          // Start of index for answer choice B
    private int cSpot = 2;          // Start of index for answer choice C
    private int dSpot = 3;          // Start of index for answer Choice D
    private int questionSpot = 0;   // Start of index for question
    
    // ArrayList for text answer choices
    private ArrayList<String> answer = new ArrayList<>();
    
    // ArrayList for users answers
    private ArrayList<String> userAnswer = new ArrayList<>();
    
    // ArrayList for the correct answers to the test
    private ArrayList<String> correctAnswers = new ArrayList<>();
    
    // ArrayList of test questions
    private ArrayList<String> justQuestions = new ArrayList<>();
    
    GradeTest grade = new GradeTest();  // Instantiate grade object
    
    @FXML
    private Label rightWrong;       // Label to show Correct/Wrong
    @FXML
    private RadioButton aButton;    // RadioButton A
    @FXML
    private RadioButton bButton;    // RadioButton B
    @FXML
    private RadioButton cButton;    // RadioButton C
    @FXML
    private RadioButton dButton;    // RadioButton D
    @FXML
    private TextField correctBox;   // Box to show number of Correct
    @FXML
    private TextField wrongBox;     // Box to show number of Wrong
    
    private int numCorrect = 0;     // Accumulator for numCorrect
    private int numWrong = 0;       // Accumulator for numWrong
    
    // Progress bar to show progress through test
    @FXML
    private ProgressBar testProgress;
    private double p = 0;
    
    @FXML
    private Button checkButton;     // Button to check users answer choice
    @FXML
    private Label correctAnswer;    // Label for correct answer if marked wrong

    /**
    Start the program
    @param url
    @param rb 
    */
       
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       startAlert();                    // Show alert
       nextButton.setDisable(true);     // Disable next button
       setQuestions();                  // Intialize the test questions
       setAnswerLabels();               // Intialize all answer labels
       setTestAnswers();                // Intialize correct test answers
       showQuestions();                 // Display first question
       showAnswerLabels();              // Display first set of answer chocies
    }
    
    /**
    startAlert method displays a welcome alert with instructions
    */
    
    public void startAlert()
    {
       Alert alert = new Alert(AlertType.INFORMATION);
       alert.setTitle("KC135 Engine Run Practice Test");
       alert.setHeaderText("Welcome To The Test\nInstructions:");
       alert.setContentText("Read the question and pick an answer choice. " +
               "Click Check button to see if your answer is right/wrong. " +
               "Then click on the Next button.");
       alert.showAndWait();
    }
    
    /**
    finshedAlert method displays upon completion of test and displays grade
    */
    
    public void finishedAlert()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("KC135 Engine Run Practice Test");
        alert.setHeaderText("You Finished The Test\nYour Results:");
        String content = String.format("Questions missed: %s " +
                "\nGrade: %.2f",grade.getMissed(), grade.getScore());
        alert.setContentText(content);  
        alert.showAndWait();
    }
    
    /**
    setQuestions method sets the test questions.
    */

    public void setQuestions() 
    {
        Questions questions = new Questions("test1.txt");
        try
        {
            questions.setQuestions();
        }
        catch (IOException e)
        {
            System.out.println("File does not exist");
        }
        justQuestions = questions.getQuestions();
        grade.setNumQuestions(justQuestions.size());
    }
    
    public void showQuestions()
    {
       questionBox.setText(justQuestions.get(questionSpot));
       questionSpot += 1;
    }
    
    /**
    setAnswerLabels sets the answer choices.
    */
    
    public void setAnswerLabels()
    {
        // Get the text file with the answer choices
       Answers answerChoices = new Answers("answers.txt");
       
       try
       {
           answerChoices.setAnswers();
       }
       catch (IOException e)
       {
           System.out.println("File does not exist");
       }
       answer = answerChoices.getAnswers();
    }
        
    public void showAnswerLabels()
    {
        // Get index of A answer choices. Add 4 to index to get next choice
        labelA.setText(answer.get(aSpot));
        aSpot += 4;
        
        // Get index of B answer choices. Add 4 to index to get next choice
        labelB.setText(answer.get(bSpot));
        bSpot += 4;
        
        // Get index of C answer choices. Add 4 to index to get next choice
        labelC.setText(answer.get(cSpot));
        cSpot += 4;
        
        // Get index of D answer choices. Add 4 to index to get next choice
        labelD.setText(answer.get(dSpot));
        dSpot += 4;
        
        // Give RadioButtons a letter value to store in userAnswer
        aButton.setUserData("A");
        bButton.setUserData("B");
        cButton.setUserData("C");
        dButton.setUserData("D");
    }
    
    /**
    setTestAnswers method set the correct answers to the test
    */
    
    public void setTestAnswers()
    {
        TestAnswers testAnswer = new TestAnswers("testAnswers.txt");
        try
        {
            testAnswer.setTestAnswers();
        }
        catch (IOException e)
        {
            System.out.println("File does not exist");
        }
        
        correctAnswers = testAnswer.getTestAnswers(); 
    }
    
    /**
    handNextButton to handle actions of the next button. Will set
    next question and answer choices and save your answer choices.
    @param event Gets new questions and answer choices. Disables Next button
                 and re-enable RadioButtons and Check button.
    */

    @FXML
    private void handleNextButton(ActionEvent event)
    {
        // Re-enable RadioButtons
        answers.getToggles().forEach(toggle -> {
            Node node = (Node) toggle;
            node.setDisable(false);
        });
                        
        checkButton.setDisable(false); // Disable checkButton
        nextButton.setDisable(true);   // Enable nextButton
        rightWrong.setText("");        // Clear rightWrong label text
        correctAnswer.setText("");
        
        // Clear the RadioButtons
        answers.getSelectedToggle().setSelected(false); 
        
        // Add to the progress bar
        p += .0178;
        testProgress.setProgress(p);

        // Show next question and answer choices
        try
        {
            showQuestions();
            showAnswerLabels();
        }
        catch (Exception e)     // justQuestions at end of ArrayList 
        {
            grade.setScore();
            finishedAlert();
        }
    }

    /**
    handleCheckButton checks the users answer to see if correct or wrong.
    @param event Check users answer and disables RadioButtons and checkButton
                 until Next button is clicked.
    */
    
    @FXML
    private void handleCheckButton(ActionEvent event)
    {
        // Add the RadioButton userData text to userAnswers
        userAnswer.add(answers.getSelectedToggle().getUserData().toString());
        
        // Check if userAnswer matches with correct testAnswer
        
        for (int index = 0; index < userAnswer.size(); index++)
        {
            
            if (userAnswer.get(index).equals(correctAnswers.get(index)))
            {
                rightWrong.setText("Correct");
                rightWrong.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
                correctAnswer.setText("");
            }
            else
            {
                rightWrong.setText("Wrong");
                rightWrong.setStyle("-fx-text-fill: red; -fx-font-size: 15;"
                        + "-fx-font-weight: bold;");
                correctAnswer.setText("Correct answer: " + 
                                       correctAnswers.get(index));
                correctAnswer.setStyle("-fx-text-fill: red; -fx-font-size: 15;"
                        + "-fx-font-weight: bold;");
            } 
        }        
        
        // Add one numCorrect or numWrong accumulator
        if (rightWrong.getText().equals("Correct"))
            numCorrect += 1;
        else
        {
            numWrong += 1;
            grade.markWrong();
        }
        
        // Set number value to correctBox
       correctBox.setText(String.valueOf(numCorrect)); 
        
        // Set number value to wrongBox
        wrongBox.setText(String.valueOf(numWrong));
        
        checkButton.setDisable(true);       // Disable checkButton
        nextButton.setDisable(false);       // Enable nextButton
        
        // Disable RadioButtons
        answers.getToggles().forEach(toggle -> {
            Node node = (Node) toggle;
            node.setDisable(true);
        });
    }
    
}
