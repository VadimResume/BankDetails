package BankDetails;

import java.util.ArrayList;

public class Account {
	
	
	private String name;
	
	//private double balance;
	
	private String uuid;
	
	private User holder;
	
	private ArrayList<Transaction> transactions;
	
	/**
	 * 
	 * @param name
	 * @param holder
	 * @param theBank
	 */
	
	public Account(String name, User holder, Bank theBank) {
		
		//set the account name and holder
		this.name = name;
		this.holder = holder;
		
		//get next account UUID
		this.uuid = theBank.getNewAccountUUID();
		
		//init transactions
		this.transactions = new ArrayList<Transaction>();


	}
	
	/**
	 * 
	 * @return
	 */
	
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * get summary line for the account
	 * @return the string summary
	 */
	
	public String getSummaryLine() {
		
		//get the accounts balance
		double balance = this.getBalance();
		
		//format the summary line, depending on the whether the balance is
		//negative
		if (balance >= 0 ) {
			return String.format("%s : $%.02f : %s", this.uuid, balance,
				this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance,
					this.name);
		}
		
	}
	
	public double getBalance() {
		double balance = 0;
		for(Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	/**
	 * 
	 */
	
	public void printAcctTransactionHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", 
				this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummayLine());
		}
		System.out.println();
	}
	
	
	/**
	 * 
	 * @param amount
	 * @param memo
	 */
	
	public void addTransaction(double amount, String memo) {
		
		//create new transaction object and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
		
		
	}

}
