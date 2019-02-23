/*
Juliana Sonan
AmortizationCalculator Project
 */
package amortizationcalculator;
//Loan class
public class Loan {
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
