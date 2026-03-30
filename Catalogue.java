package movie_rental_system;

import java.sql.*;
import java.util.Scanner;

public class Catalogue {
	static final String DB_URL = "jdbc:mysql://localhost:3306/Movie_DB";
	static final String USER = "root";
	static final String PASS = "";
	static Scanner scnr = new Scanner(System.in);
	
	public static void main(String[] args) {
		viewCat();
		int fid = scnr.nextInt();
		findMov(fid);
	}
	
	static void viewCat() {
		String Select = "SELECT id, title, year, stock FROM movies";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement pstmt = conn.prepareStatement(Select)) {
				
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
	      			System.out.print("ID: " + rs.getInt("id"));
	      			System.out.print(", Title: " + rs.getString("title"));
	      			System.out.print(", Year: " + rs.getInt("year"));
	      			System.out.println(", In Stock: " + rs.getInt("stock"));
	      		}
	      		rs.close();
	            // last exception
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
	
	static void findMov(int fid) {
		String Find = "SELECT id, title, year, stock FROM movies WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement pstmt = conn.prepareStatement(Find)) {

				pstmt.setFloat(1, fid);
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
	      			System.out.print("ID: " + rs.getInt("id"));
	      			System.out.print(", Title: " + rs.getString("title"));
	      			System.out.print(", Year: " + rs.getInt("year"));
	      			System.out.println(", In Stock: " + rs.getInt("stock"));
	      		}
	      		rs.close();
	            // last exception
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
}
