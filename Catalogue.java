package movie_rental_system;

import java.util.HashMap;

public class Catalogue {
	static int addMov (HashMap<String, Movie> list, Movie mov, int id) {
		String identifier;
		if (id<10) {
			identifier = "M00" + id;
		}
		else if (id<100) {
			identifier = "M0" + id;
		}
		else {
			identifier = "M" + id;
		}
		list.put(identifier, mov);
		id += 1;
		return id;
	}
	public static void main (String[] args) {
		var database = new HashMap<String, Movie>();
		int movId = database.size()+1;
		
		User person = new User("u01", "Steve", "Password!");
		Movie film = new Movie("Star Wars", 1977, "Space", "George Lucas", "Lucasfilm", 3, 3.50);
		movId = addMov(database, film, movId);
		System.out.print(database.get("M001").title);
	}
}
