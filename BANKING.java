import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BANKING {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InnerBANKING obj = new InnerBANKING();
        System.out.println();
        System.out.println("                   \s\bGAJENDRA BANK PVT. LTD.                       ");
        System.out.println("                                                                    ");
        while (true) {
            System.out.println("                                                                      ");

            System.out.println(".....................................................................");
            System.out.println("------------------PLEASE ENTER THE PREFFERED CHOICE------------------");
            System.out.println("---------------------------------------------------------------------");
            System.out.println("---------        TYPE 1 FOR CREATING A NEW ACCOUNT         ----------");
            System.out.println("---------        TYPE 2 FOR  BANK  ACCOUNT DETAILS         ----------");
            System.out.println("---------       TYPE 3 FOR DEPOSITING MONEY IN ACCOUNT     ----------");
            System.out.println("---------      TYPE 4 FOR WITHDRAWING MONEY FROM ACCOUNT   ----------");
            System.out.println("---------         TYPE 5 FOR TRANSATION HISYORY            ----------");
            System.out.println("---------                  TYPE 6 FOR EXIT                 ----------");
            System.out.println("---------------------------------------------------------------------");
            System.out.println(".....................................................................");

            System.out.println("                                    ");
            int o = sc.nextInt();
            switch (o) {
                case 1:
                    System.out.println("THANK'S FOR OPENING ACCOUNT IN OUR BANK PLEASE ENTER THE DETAILS CAREFULLY");
                    obj.openAccount();
                    break;
                case 2:
                    System.out.println("YOUR BANK ACCOUNT DETAILS ARE AS FOLLOWS: ");
                    obj.showAccount();
                    break;
                case 3:
                    obj.deposit();
                    break;
                case 4:
                    obj.withdrawal();
                    break;
                case 5:
                    obj.showTransactionHistory();
                    break;
                case 6:
                    System.out.println("------  PLEASE VISIT AGAIN   ------");
                    System.exit(0);
                    break;
            }
        }
    }
}

class InnerBANKING {
    private String accountno;
    private String name;
    private String accounttype;
    private int pass;
    private double balance;
    Scanner sc = new Scanner(System.in);
    private List<Transaction> Transaction = new ArrayList<>();
    private long amt;
    private int pin;

    // ACCOUNT NUMBER GENERATION METHOD
    private String generateAccNumber() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        System.out.println(number);
        return String.format("%06d", number);
    }

    void openAccount() {

        // ACCOUNT NUMBER
        System.out.print("Your Auto Generated Account No : ");
        accountno = String.valueOf(generateAccNumber());

        // ACCOUNT TYPE
        System.out.println("Enter Account type 1.savings\t 2.current: ");
        int enterednumber = sc.nextInt();
        if (enterednumber == 1) {
            System.out.println("YOUR PREFFERED  ACCOUNT IS SAVINGS ACCOUNT.");
            accounttype = "SAVINGS";
        } else if (enterednumber == 2) {
            System.out.println("YOUR PREFFERED   ACCOUNT IS CURRENT ACCOUNT.");
            accounttype = "CURRENT";
        } else {
            System.out.println("\nInvalid Choice \nPlease Enter Again.");
        }
        System.out.print("Enter Your Name: ");
        name = sc.next();

        // MPIN
        System.out.println("Enter The Mpin For Your Acoount: ");
        pass = mpin();

        // BALANCE

        double amt;

        // DEPOSIT
        System.out.println("Enter the amount you want to deposit: ");
        amt = sc.nextDouble();
        if (amt > 0) {
            balance = amt;
            System.out.println("Deposited Successfully!");
        } else {
            System.out.println("Invalid Amount! Please enter a positive number.");
        }

    }

    // MPIN GENERATION METHOD
    int mpin() {
        System.out.println("Enter Your Mpin: ");
        int mpin = sc.nextInt();
        int checkmpin;
        System.out.println("Confirm your Mpin: ");
        checkmpin = sc.nextInt();
        if (mpin != checkmpin) {
            System.out.println("Mpins do not match.\nTry again.");
            mpin();
        } else {
            System.out.println("Your mpin is: " + mpin);
        }
        return mpin;

    }

    // SHOWING OF THE ACCOUNT METHOD
    void showAccount() {
        System.out.println("Please enter your account no: ");
        String acno = sc.next();
        if (accountno.equals(acno)) {
            System.out.println("Enter your mpin: ");
            int mpintemp = sc.nextInt();
            if (mpintemp=mpin()) {
                System.out.println("Name of account holder: " + name);
                System.out.println("Account no.: " + accountno);
                System.out.println("Your Account type: " + accounttype);
                System.out.println("Your Balance: " + balance);
            }
            else{
                System.out.println("Entered mpin is incorrect please start again. \n");
            }
        } 
        else  {
            System.out.println("Please enter a valid account no.");
        }
    }
    

    // DEPOSIT METHOD
    void deposit() {

        System.out.println("Please enter the account number :");
        String accNo = sc.next();
        if (accNo.equals(accountno)) {
            System.out.println("Enter the amount you want to deposit: ");
            amt = sc.nextLong();
            if (amt > 0) {
                balance += amt;
                System.out.print("Deposited Successfully! Hence balance in your account is :  Rs. ");
                System.out.println(balance);
            } else {
                System.out.print("Invalid Amount! Please enter a positive number.");
            }
        } else {
            System.out.println("Sorry , this account does not exist.");
        }
        balance += amt;
        Transaction.add(new Transaction("Deposit", amt, new Date(System.currentTimeMillis()).toString()));
    }

    // WITHDRAWL METHOD
    void withdrawal() {

        System.out.println("Please enter the account no: ");
        String acno = sc.next();
        if (accountno.equals(acno)) {
            System.out.println("Please enter your pin: ");
            pin = sc.nextInt();
            if (pin == pass) {
                System.out.println("Enter the amount you want to withdraw: ");
                amt = sc.nextLong();
                if (balance >= amt) {
                    balance = balance - amt;
                    System.out.println("Balance after withdrawal: " + balance);
                } else {
                    System.out.println("Your balance is less than " + amt + "\tTransaction failed...!!");
                }
            } else {
                System.out.println("You entered the wrong pin please start again");
            }
        } else {
            System.out.println("Your entered account number is inncorrect plesae begin again");
        }
        balance -= amt;
        Transaction.add(new Transaction("Withdrawal", amt, new Date(System.currentTimeMillis()).toString()));
    }

    // TRANSACTION METHOD
    void showTransactionHistory() {
        System.out.println("Please enter the account no: ");
        String acno = sc.next();
        if (accountno.equals(acno)) {
            System.out.println("Transaction history:");
            for (int i = 0; i < Transaction.size(); i++) {
                Transaction transaction = Transaction.get(i);
                System.out.println((i + 1) + ". " + transaction.getType() + ": Rs. " + transaction.getAmount() + " on "
                        + transaction.getDate());
            }
        } else {
            System.out.println("Your entered account number is inncorrect plesae begin again");

        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private String date;

    public Transaction(String type, double amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
