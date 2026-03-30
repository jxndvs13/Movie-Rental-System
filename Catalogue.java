package movieRentalSystem;

import java.sql.*;
import java.util.Scanner;

public class Catalogue {
	static final String DB_URL = "jdbc:mysql://localhost:3306/Movie_DB";
	static final String USER = "root";
	static final String PASS = "";
	static Scanner scnr = new Scanner(System.in);
	
	public static void main(String[] args) {
		Boolean going = true;
		while (going = true) {
			System.out.println("");
			System.out.println("1. View Catalogue");
			System.out.println("2. Find Movie from ID");
			System.out.println("3. Rent Movie");
			System.out.println("4. Return Movie");
			System.out.println("0. Quit");
			System.out.print("Select Operation: ");
			int op = scnr.nextInt();
			System.out.println("");
			
			if (op == 1) {
				viewCat();
			}
			
			if (op == 2) {
				System.out.print("Enter Movie ID: ");
				displayMov(scnr.nextInt());
			}
			
			if (op == 3) {
				System.out.print("Enter Movie ID: ");
				rentMov(scnr.nextInt());
			}
			
			if (op == 4) {
				System.out.print("Enter Movie ID: ");
				returnMov(scnr.nextInt());
			}
		}
		viewCat();
		int fid = scnr.nextInt();
		scnr.nextLine();
		rentMov(fid);
		System.out.println("");
		viewCat();
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
	
	static void displayMov(int fid) {
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
	
	static void rentMov(int fid) {
		String Find = "SELECT id, title, year, stock FROM movies WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement pstmt = conn.prepareStatement(Find)) {

				pstmt.setInt(1, fid);
	            ResultSet rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
	      			System.out.print("ID: " + rs.getInt("id"));
	      			System.out.print(", Title: " + rs.getString("title"));
	      			System.out.print(", Year: " + rs.getInt("year"));
	      			System.out.println(", In Stock: " + rs.getInt("stock"));
	      			if (rs.getInt("stock") > 0) {
	      				System.out.println("Rent Movie? (Y/N)");
			            if (scnr.next().equals("Y")) {
			            	reduceStock(fid);
			            	System.out.println("Movie Rented");
			            }
	      			}
	      			else {
	      				System.out.println("Movie Currently Unavailible");
	      			}
	      		}
	      		rs.close();
	            // last exception
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
	
	static void returnMov(int fid) {
		String Title = "SELECT title FROM movies WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement pstmt = conn.prepareStatement(Title)) {

				pstmt.setInt(1, fid);
	            ResultSet rs = pstmt.executeQuery();
	            
	            if (1 == 1) {
	            	increaseStock(fid);
	            	while (rs.next()) {
	            		System.out.println("'" + rs.getString("title") + "' returned. Thank you!");
	            	}
	            }
	      		rs.close();
	            // last exception
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
	
	static void reduceStock(int fid) {
		String reduceStock = "UPDATE movies SET stock = stock - 1 WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement pstmt = conn.prepareStatement(reduceStock)) {

				pstmt.setInt(1, fid);
	            pstmt.executeUpdate();
	            
	            // last exception
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
	static void increaseStock(int fid) {
		String increaseStock = "UPDATE movies SET stock = stock + 1 WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	            PreparedStatement pstmt = conn.prepareStatement(increaseStock)) {

				pstmt.setInt(1, fid);
	            pstmt.executeUpdate();
	            
	            // last exception
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
}
