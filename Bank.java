package BankDetails;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;

	private ArrayList<User> users;

	private ArrayList<Account> accounts;
	
	//Create a new Bank object with empty lists of users and accounts
	//@param name the name of the bank
	
	public Bank (String name) {
		
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	
	/**
	 * 
	 * @return
	 */

	public String getNewUserUUID() {

		// inits
		String uuid;
		Random rng = new Random();
		int len = 5;
		boolean nonUnique;

		// continue looping until we get a unique ID
		do {
			// generate the number
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer) rng.nextInt(5)).toString();
			}

			// check to make sure it`s unique
			nonUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}

		} while (nonUnique);

		return uuid;

	}

	public String getNewAccountUUID() {
		
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;

		// continue looping until we get a unique ID
		do {
			// generate the number
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer) rng.nextInt(10)).toString();
			}

			// check to make sure it`s unique
			nonUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}

		} while (nonUnique);

		return uuid;
	}
	
	
		
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

		/**
		 * 
		 * @param firstName
		 * @param lastName
		 * @param pin
		 * @return
		 */
	
	public User addUser(String firstName, String lastName, String pin) {
		
		//create a new User object and add it to our list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//create savings account for the user and add to User and Bank
		//accounts lists
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
		
	}
	
	
	/**
	 * 
	 * @param userID
	 * @param pin
	 * @return
	 */
	public User userLogin(String userID, String pin) {
		
		//search through list of user
		for(User u : this.users) {
			
			//check user ID if correct
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		//if we haven`t found the user or have incorrect pin
		return null;
		
	}
	public String getName() {
		return this.name;
	}

}
