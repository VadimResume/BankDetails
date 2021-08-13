package BankDetails;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

	private String firstName;
	
	private String lastName;
	
	private String uuid;
	
	private byte pinHash[];
	
	private ArrayList<Account> accounts;
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param pin
	 * @param theBank
	 */
	
	
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		//set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		//store the pin`s WD5 hash, rather than the original value, for 
		//securely reasons
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error, cauht NoSuchAlgorithnExeption");
			e.printStackTrace();
			System.exit(1);
		}
		
		//get a new, unique universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		//create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		//print log message
		System.out.printf("New user %s, %s with ID %s created. \n", lastName,
				firstName, this.uuid);
			
		
	}
	/**
	 * 
	 * @param anAcct
	 */
	
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	/**
	 * 
	 * @return
	 */
	
	public String getUUID() {
		return this.uuid;
		}
	
	/**
	 * 
	 * @param aPin
	 * @return
	 */
	
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),
					this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error, cauht NoSuchAlgorithnExeption");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
		
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	
	
	public void printAccountsSummary() {
	
		System.out.printf("\n\n%s's accounts summary\n", this.firstName );
		for(int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("  %d) %s", a+1,
					this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @return
	 */
	
	public int numAccounts() {
		return this.accounts.size();
	}
	
	public void printAcctTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printAcctTransactionHistory();
	}
	
	/**
	 * 
	 * @param acctIdx
	 * @return
	 */
	
	
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}
	
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}
	
	/**
	 * 
	 * @param acctIdx
	 * @param amount
	 * @param memo
	 */
	
	
	public void addAcctTransaction (int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}

}
