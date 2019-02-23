/*
Juliana Sonan
AmortizationCalculator Project
The application will create an amortization schedule based on 
the inputs from the user
 */
package amortizationcalculator;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;

public class AmortizationCalculator extends Application {
    //Class variables
    private TextField tfAmount = new TextField();
    private TextField tfAnnualInterestRate = new TextField();
    private TextField tfTerm = new TextField();
    private Button btCalculate = new Button("Calculate");
    private VBox vBox = new VBox();
    private Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    
    @Override
    public void start(Stage primaryStage) {
        //Create new GridPane
        GridPane gridPane = new GridPane();
        //Set GridPane spacing
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //Add nodes into GridPane at specified cells
        gridPane.add(new Text("Loan Information"), 0, 1);
        gridPane.add(new Label("Loan Amount: "), 0, 2);
        gridPane.add(tfAmount, 2, 2);
        gridPane.add(new Label("Annual Interest Rate: "), 0, 3);
        gridPane.add(tfAnnualInterestRate, 2, 3);
        gridPane.add(new Label("Term in years: "), 0, 4);
        gridPane.add(tfTerm, 2, 4);
        gridPane.add(btCalculate, 2, 5);
        //Set the alignment of the button in the cell
        GridPane.setHalignment(btCalculate, HPos.RIGHT);
        //Add GridPane into the VBox
        vBox.getChildren().add(gridPane);
        //Set the alignment of the TextFields
        tfAmount.setAlignment(Pos.BOTTOM_RIGHT);
        tfAnnualInterestRate.setAlignment(Pos.BOTTOM_RIGHT);
        tfTerm.setAlignment(Pos.BOTTOM_RIGHT);
        //Set the VBox max height to the screen's height
        vBox.setMaxHeight(primScreenBounds.getHeight());
        //Display SummaryPane and SchedulePane when the button is clicked
        btCalculate.setOnMouseClicked(e -> {
            display();
            primaryStage.sizeToScene();
        });
        //Display SummaryPane and SchedulePane when the user's presses the ENTER button
        vBox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                display();    
                primaryStage.sizeToScene();
            }
        });
        //Insert the VBox into the Scene
        Scene scene = new Scene(vBox);
        //Set the stage's title
        primaryStage.setTitle("Loan Amortization Schedule");
        //Place the scene into the stage
        primaryStage.setScene(scene);
        //Display the stage
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
    //Method to display the SummaryPane and SchedulePane
    private void display() {
        SummaryPane summaryPane = new SummaryPane();
        SchedulePane schedulePane = new SchedulePane();
        //Insert the SchedulePane into a ScrollPane
        ScrollPane scrollPane = new ScrollPane(schedulePane);
        //Set the vertical scrollbar to be visible if needed
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        //Insert the SummaryPane and the ScrollPane into VBox
        vBox.getChildren().addAll(summaryPane, scrollPane);
    }
    //SummaryPane class to show the Loan's summary
    class SummaryPane extends GridPane {
        //Class variables
        private TextField tfNumberOfPayments = new TextField();
        private TextField tfMonthlyPayment = new TextField();
        private TextField tfTotalPrincipalPayment = new TextField();
        private TextField tfTotalInterestPayment = new TextField(); 
        private TextField tfTotalPayment = new TextField();
        private Loan loan = new Loan(Double.parseDouble(tfAmount.getText()), Double.parseDouble(tfAnnualInterestRate.getText()),
                Integer.parseInt(tfTerm.getText()));
        //Constructor
        SummaryPane() {
            //Set SummaryPane spacing
            setPadding(new Insets(10));
            setHgap(5);
            setVgap(5);
            //Add nodes in the specified cells
            add(new Text("Loan Summary"), 0, 0);
            add(new Label("Number of Payments: "), 0, 1);
            add(tfNumberOfPayments, 1, 1);
            add(new Label("Monthly Payment: "), 0, 2);
            add(tfMonthlyPayment, 1, 2);
            add(new Label("Total Principal Payment: "), 0, 3);
            add(tfTotalPrincipalPayment, 1, 3);
            add(new Label("Total Interest Payment: "), 0, 4);
            add(tfTotalInterestPayment, 1, 4);
            add(new Label("Total Payment: "), 0, 5);
            add(tfTotalPayment, 1, 5);
            //Set the TextFields so that the user is unable to edit them
            tfNumberOfPayments.setEditable(false);
            tfMonthlyPayment.setEditable(false);
            tfTotalPrincipalPayment.setEditable(false);
            tfTotalInterestPayment.setEditable(false);
            tfTotalPayment.setEditable(false);
            //Set the alignment of the TextFields within the cell
            tfNumberOfPayments.setAlignment(Pos.BOTTOM_RIGHT);
            tfMonthlyPayment.setAlignment(Pos.BOTTOM_RIGHT);
            tfTotalPrincipalPayment.setAlignment(Pos.BOTTOM_RIGHT);
            tfTotalInterestPayment.setAlignment(Pos.BOTTOM_RIGHT);
            tfTotalPayment.setAlignment(Pos.BOTTOM_RIGHT);
            //Calculate and display the values in the appropriate TextField
            tfNumberOfPayments.setText(Integer.toString(Integer.parseInt(tfTerm.getText()) * 12));
            tfMonthlyPayment.setText(String.format("$%,.2f", loan.getMonthlyPayment()));
            tfTotalPrincipalPayment.setText(String.format("$%,.2f", loan.getAmount()));
            tfTotalInterestPayment.setText(String.format("$%,.2f", loan.getTotalPayment() - loan.getAmount()));
            tfTotalPayment.setText(String.format("$%,.2f", loan.getTotalPayment()));
            
        }
        
    }
    //SchedulePane class to show the loan's amortization schedule
    class SchedulePane extends GridPane {
        //Class variables
        private Loan loan = new Loan(Double.parseDouble(tfAmount.getText()), Double.parseDouble(tfAnnualInterestRate.getText()),
                Integer.parseInt(tfTerm.getText()));
        private double endingPrincipal = loan.getAmount();
        //Constructor
        SchedulePane() {
            //Set SchedulePane spacing
            setPadding(new Insets(10));
            setHgap(20);
            setVgap(5);
            //Add nodes in the specified cells
            add(new Label("Month"), 0, 0);
            add(new Label("Principal"), 1, 0);
            add(new Label("Interest Payment"), 2, 0);
            add(new Label("Principal Payment"), 3, 0);
            add(new Label("Ending Principal"), 4, 0);
            //Outer loop to cycle through each month
            for (int i = 1; i <= loan.getTerm() * 12; i++) {
                //Inner loop to cycle through the columns
                for (int j = 0; j < 5; j++) {
                    switch (j) {
                        //Display the month number
                        case 0: add(new Text(Integer.toString(i)), j, i); break;
                        //Display the remaining principal of the loan
                        case 1: add(new Text(String.format("$%,.2f", endingPrincipal)), j, i); break;
                        //Display how much of the total monthly payment is interest
                        case 2: add(new Text(String.format("$%,.2f", interest())), j , i); break;
                        //Display how much of the total monthly payment goes to principal
                        case 3: add(new Text(String.format("$%,.2f", principalPayment())), j, i); break;
                        //Display the ending principal balance after the monthly payment has been made
                        case 4: endingPrincipal -= principalPayment();
                                add(new Text(String.format("$%,.2f", endingPrincipal)), j, i); break;    
                    }
                }
            }
        }
        //Method to calculate the interest payment
        public final double interest() {
            return endingPrincipal * loan.getAnnualInterestRate() / 1200;
        }
        //Method to calculate the principal payment
        public final double principalPayment() {
            return loan.getMonthlyPayment() - interest();
        }
        //Method to calculate the ending principal balance
        public final double endingPrincipal() {
            return endingPrincipal -= principalPayment();
        }
    }
    
}
//Loan class
class Loan {
    //Class variables
    private double amount;
    private double annualInterestRate;
    private int term;
    //Default Constructor
    Loan() {
       this(10000, 3.5, 5); 
    }
    //Constructor with specified values
    Loan(double amount, double annualInterestRate, int term) {
        this.amount = amount;
        this.annualInterestRate = annualInterestRate;
        this.term = term;
    }
    //Accessor method to return the loan amount
    public double getAmount() {
        return amount;
    }
    //Accessor method to return the annual interest rate
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }
    //Accessor method to return the term in years
    public int getTerm() {
        return term;
    }
    //Mutator method to set the loan amount
    public void setAmount(double amount) {
        this.amount = amount;
    }
    //Mutator method to set the annual interest rate
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
    //Mutator method to set the term in years
    public void setTerm(int term) {
        this.term = term;
    }
    //Method to calculate the monthly payment
    public double getMonthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        double monthlyPayment = amount * monthlyInterestRate / (1 - (1 / Math.pow(1 + monthlyInterestRate, term * 12)));
        return monthlyPayment; 
    }
    //Method to calculate the total amount paid at the end of the loan's term
    public double getTotalPayment() {
        double totalPayment = getMonthlyPayment() * term * 12;
        return totalPayment;
    }
}
