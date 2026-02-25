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
	}
}
