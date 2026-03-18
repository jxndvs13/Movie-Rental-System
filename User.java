package movie_rental_system;

import java.util.ArrayList;

public class User {
	String id;
	String name;
	String password;
	String checkedOut;
	ArrayList<String> history;
	float credit;
	
	User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
        this.checkedOut = ""; //what about these
        this.history = new ArrayList<>();
        this.credit = 0.0f;
	}
	
    // First is the Login (check name + password)
    public boolean login(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
    
    // This is for changing a Name
    public void changename(String newName) {
        this.name = newName;
    }
    
    // To Change a Password
    public void changepassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Password updated successfully.");
        } else { //else if does not match
            System.out.println("Incorrect old password.");
        }
    }
    
    // View an Account (or the credit, maybe)
    public float viewaccount() {
        return credit;
    }
    
    // Adding Credit
    public void addcredit(float amount) {
        if (amount > 0) {
            credit += amount;
        }
    }
    
    // View History
    public ArrayList<String> viewhistory() {
        return history;
    }
    
    // Add to the History (when renting/returning movies)
    public void addtohistory(String record) {
        history.add(record);
    }
    
    // Check out Move
    public void checkoutmovie(String movieTitle) {
        this.checkedOut = movieTitle;
        addtohistory("Checked out: " + movieTitle);
    }
    
    // To Return a Movie
    public void returnmovie() {
        if (!checkedOut.isEmpty()) {
        	addtohistory("Returned: " + checkedOut);
            checkedOut = "";
        }
    }
    
    // optional getters
    public String getId() {
        return id;
    }
    
    public String getname() {
        return name;
    }
    
    public String getcheckedout() {
        return checkedOut;
    }
}
