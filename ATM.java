package BankDetails;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		//init scanner
		Scanner sc = new Scanner(System.in);
		
		//init Bank
		Bank theBank = new Bank("Bank of Drausin");
		
		//add a user, whith also creates a saving account
		User aUser = theBank.addUser("John", "Doe", "1234");
		
		// add a checking account for our user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while (true) {
			
			//stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			//stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}
	
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		//inits
		String userID;
		String pin;
		User authUser;
		
		//prompt the user for user ID/pin combo until a correct one is reached 
		do {
			
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			//try to get the user object corresponding to the ID and pin combo
			authUser = theBank.userLogin(userID, pin);
			if(authUser == null) {
				System.out.println("Incorrect user ID/pin combination." +
			"Pleas try again.");
			}
			
		}while(authUser == null);//continue looping until successful login
		
		return authUser;
	}
		
		 public static void printUserMenu(User theUser, Scanner sc) {
			 
			 //print a summary of the user`s accounts
			 theUser.printAccountsSummary();
			 
			 //init
			 int choice;
			 //user menu
			 do {
				 System.out.printf("Welcome %s, what would you like to do?\n", 
						 theUser.getFirstName());
				 System.out.println(" 1) Show account transaction history");
				 System.out.println(" 2) Withdraw");
				 System.out.println(" 3) Deposit");
				 System.out.println(" 4) Transfer");
				 System.out.println(" 5) Quit");
				 System.out.println();
				 System.out.print("Enter choice: ");
				 choice = sc.nextInt();
				 
				 if (choice < 1 || choice > 5) {
					 System.out.println("Invalid choice. Please choose 1-5");
				 }
			 } while (choice < 1 || choice > 5);
			 
			 //process the choice
			 switch (choice) {
			 case 1:
				 ATM.showTransHistory(theUser, sc);
				 break;
			 case 2:
				 ATM.withdrawFunds(theUser, sc);
				 break;
			 case 3:
				 ATM.depositFunds(theUser, sc);
				 break;
			 case 4:
				 ATM.transferFunds(theUser, sc);
				 break;
			 case 5:
				 sc.nextLine();
				 break;
			 
			 }
			 
			 //redisplay this menu unless the user wants to quit
			 if(choice != 5) {
				 ATM.printUserMenu(theUser, sc);
			 }
			 
		 }
		 
		 public static void showTransHistory(User theUser, Scanner sc) {
			 
			 int theAcct;
			 
			 //get account, whose transaction history to look at
			 do {
				 
				 System.out.printf("Enter the number (1-%d) of the account\n" +
				 " whose transactions you want to see: ", 
						 theUser.numAccounts());
				 
				 theAcct = sc.nextInt()-1;
				 if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
					 System.out.println("Invalid account. Please try again.");
				 }
				 
			 } while (theAcct < 0 || theAcct >= theUser.numAccounts());
			 
			 //print the transaction history
			 theUser.printAcctTransactionHistory(theAcct);
			 			 
		 }
		 
		 public static void transferFunds(User theUser, Scanner sc) {
			
			 //inits
			 int formAcct;
			 int toAcct;
			 double amount;
			 double acctBal;
			 
			 //get the account to transfer from
			 do {
				 System.out.printf("Enter the number (1-%d) of the account\n +"
				 		+ "to transfer from: ", theUser.numAccounts());
				 formAcct = sc.nextInt()-1;
				 if (formAcct < 0 || formAcct >= theUser.numAccounts()) {
					 System.out.println("Invalid account. Please try again.");
				 }
			 } while (formAcct < 0 || formAcct >= theUser.numAccounts());
			 acctBal = theUser.getAcctBalance(formAcct);
			 
			 //get the account to transfer to
			 do {
				 System.out.printf("Enter the number (1-%d) of the account\n +"
				 		+ "to transfer to: ", theUser.numAccounts());
				 toAcct = sc.nextInt()-1;
				 if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
					 System.out.println("Invalid account. Please try again.");
				 }
			 } while (toAcct < 0 || toAcct >= theUser.numAccounts());
			 
			 //get the amount to transfer
			 
			 do {
				 System.out.printf("Enter the amount to transfer (max $%.02f) : $",
						 acctBal);
				 amount = sc.nextDouble();
				 if (amount < 0) {
					 System.out.println("Amount must be greater than zero.");
					 
				 } else if (amount > acctBal) {
					 System.out.printf("Amount must not be greater than\n" +
				 "balance of $%.02f\n", acctBal);
				 }
			 } while (amount < 0 || amount > acctBal);
			 
			 //finally, to the transfer
			 theUser.addAcctTransaction(formAcct, -1*amount, String.format(
					 "Transfer to account %s", theUser.getAcctUUID(toAcct)));
			 theUser.addAcctTransaction(toAcct, amount, String.format(
					 "Transfer to account %s", theUser.getAcctUUID(formAcct)));
			 
		}
		 
		 /**
		  * 
		  * @param theUser
		  * @param sc
		  */
		 
		 public static void withdrawFunds(User theUser, Scanner sc) {
				// inits
				int formAcct;
				double amount;
				double acctBal;
				String memo;

				// get the account to transfer from
				do {
					System.out.printf("Enter the number (1-%d) of the account\n +" + 
				"to withdraw from: ", theUser.numAccounts());
					formAcct = sc.nextInt() - 1;
					if (formAcct < 0 || formAcct >= theUser.numAccounts()) {
						System.out.println("Invalid account. Please try again.");
					}
				} while (formAcct < 0 || formAcct >= theUser.numAccounts());
				acctBal = theUser.getAcctBalance(formAcct);
				
				//get the amount to transfer
				 
				do {
					System.out.printf("Enter the amount to withdraw (max $%.02f) : $", acctBal);
					amount = sc.nextDouble();
					if (amount < 0) {
						System.out.println("Amount must be greater than zero.");

					} else if (amount > acctBal) {
						System.out.printf("Amount must not be greater than\n" + "balance of $%.02f\n", acctBal);
					}
				} while (amount < 0 || amount > acctBal);
				
				//gobble up rest of previous input
				sc.nextLine();
				
				//get a memo
				System.out.print("Enter a memo: ");
				memo = sc.nextLine();
				
				//do the withdraw
				theUser.addAcctTransaction(formAcct, -1 * amount, memo);
				
		 }
		 
		 /**
		  * 
		  * @param theUser
		  * @param sc
		  */
		 
		 public static void depositFunds(User theUser, Scanner sc) {
			 
				// inits
				int toAcct;
				double amount;
				double acctBal;
				String memo;

				// get the account to transfer from
				do {
					System.out.printf("Enter the number (1-%d) of the account\n +" + 
				"to deposit in: ", theUser.numAccounts());
					toAcct = sc.nextInt() - 1;
					if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
						System.out.println("Invalid account. Please try again.");
					}
				} while (toAcct < 0 || toAcct >= theUser.numAccounts());
				acctBal = theUser.getAcctBalance(toAcct);

				// get the amount to transfer

				do {
					System.out.printf("Enter the amount to transfer (max $%.02f) : $", acctBal);
					amount = sc.nextDouble();
					if (amount < 0) {
						System.out.println("Amount must be greater than zero.");

					} 
				} while (amount < 0);

				// gobble up rest of previous input
				sc.nextLine();

				// get a memo
				System.out.print("Enter a memo: ");
				memo = sc.nextLine();

				// do the withdraw
				theUser.addAcctTransaction(toAcct, amount, memo);
				
		 }
		
	
}
