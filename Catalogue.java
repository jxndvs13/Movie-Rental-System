package movie_rental_system;

import java.sql.*;

public class Catalogue {
	static final String DB_URL = "jdbc:mysql://localhost:3306/Movie_DB";
	static final String USER = "root";
	static final String PASS = "";
	static final String Select = "SELECT id, title, year, stock FROM movies";
	static final String Stock_d = "UPDATE movies set stock=1 WHERE id=1";
	static final String Delete = "DELETE FROM movies WHERE id=3";
	   
	public static void main(String[] args) {
		// Open a connection
	      	try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      		Statement stmt = conn.createStatement();
	      	) {
	      		// Let us check if it returns a true Result Set or not.
	      		Boolean ret = stmt.execute(Stock_d);
	      		System.out.println("Return value is : " + ret.toString() );

	      		// Let us update age of the record with ID = 103;
	      		int rows = stmt.executeUpdate(Delete);
	      		System.out.println("Rows impacted : " + rows );

	      		// Let us select all the records and display them.
	      		ResultSet rs = stmt.executeQuery(Select);		      

	      		// Extract data from result set
	      		while (rs.next()) {
	      			// Retrieve by column name
	      			System.out.print("ID: " + rs.getInt("id"));
	      			System.out.print(", Title: " + rs.getString("title"));
	      			System.out.print(", Year: " + rs.getInt("year"));
	      			System.out.println(", In Stock: " + rs.getInt("stock"));
	      		}
	      		rs.close();
	      } catch (SQLException e) {
	    	  	e.printStackTrace();
	      } 
	}
	
	static void printCatalogue(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.print("ID: " + rs.getInt("id"));
				System.out.print(", Title: " + rs.getString("title"));
				System.out.print(", Year: " + rs.getInt("year"));
				System.out.println(", In Stock: " + rs.getInt("stock"));
			}
		} catch (SQLException e) {
    	  	e.printStackTrace();
		} 
		
	}
}
