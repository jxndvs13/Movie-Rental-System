package movie_rental_system;

import java.sql.*;
import java.util.Scanner;

public class UserDB {
    static final String DB_URL = "jdbc:mysql://localhost:3306/movie_db";
    static final String USER = "root";
    static final String PASS = "";
    static Scanner scnr = new Scanner(System.in);
    static String cUser = "";
    
    public static Double getCredit () {
        String query = "SELECT credit FROM users WHERE name=?";
        Double output = 0.0;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cUser);
            ResultSet rs = pstmt.executeQuery();            
            
            if (rs.next()) {
                output = rs.getDouble("credit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public static int getRentId() {
    	String query = "SELECT rented FROM users WHERE name=?";
        int output = -1;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cUser);
            ResultSet rs = pstmt.executeQuery();            
            
            if (rs.next()) {
                output = rs.getInt("rented");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public static String getRented() {
        String query = "SELECT rented FROM users WHERE name=?";
        String output = "none";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cUser);
            ResultSet rs = pstmt.executeQuery();            
            
            if (rs.next()) {
                if (rs.getInt("rented") == 0) {}
                else{
                	output = Catalogue.displayMov(rs.getInt("rented"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public static String getRawHistory() {
        String query = "SELECT history FROM users WHERE name=?";
        String output = "";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cUser);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                output = rs.getString("history");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public static String getHistory() {
        String query = "SELECT history FROM users WHERE name=?";
        String output = "";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cUser);
            ResultSet rs = pstmt.executeQuery();
            String regex = "[,\\.\\s]";
            
            if (rs.next()) {
                if (rs.getString("history").equals("")) {}
                else{
                	String[] list = rs.getString("history").split(regex);
                    for (String i : list) {
                    	output = output + Catalogue.nameMov(Integer.parseInt(i)) + "\n";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    // This is to Create a New User
    public static void createUser(int id, String name, String password) { //first is start
        String query = "INSERT INTO users (id, name, password, credit) VALUES (?, ?, ?, ?)"; //then query insert
        // use try and error ,catch = SQLException
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //connect via sql
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	//id, name, password pstmt
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setDouble(4, 0); //may be error
            //update, say it did
            pstmt.executeUpdate();
            System.out.println("User created!"); //put system outs here!
            //exception
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String login(String name, String password) {
        String query = "SELECT * FROM users WHERE name=? AND password=?";
        String output = "Login Failed\n";
    	cUser = "";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
        	//name and password
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                cUser = name;
                output = "Login Successful\n";
            }
            //exception, again, just include this
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    // This is the first one, to Change a Name
    public static void changeName(int id, String newName) { // a newName
        String query = "UPDATE users SET name=? WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
            System.out.println("Your Name has been Changed. " );
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Then this is to Change a Password
    public static void changePassword(int id, String newPassword) { // use newPassword
        String query = "UPDATE users SET password=? WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
            System.out.println("Password Changed. ");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Then add Credit
    public static String addCredit(double amount) {
        String query = "UPDATE users SET credit = credit + ? WHERE name=?";
        
        String output = "Operation Failed";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setDouble(1, amount);
            pstmt.setString(2, cUser);
            //added it
            pstmt.executeUpdate();
            output = "Credit Added";
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    public static void takeCredit(double amount) {
        String query = "UPDATE users SET credit = credit - ? WHERE name=?";
                
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setDouble(1, amount);
            pstmt.setString(2, cUser);
            //added it
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Also history, so add to history
    public static void addHistory(int id) {
        String query = "UPDATE users SET history = ? WHERE name = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setString(1, getRawHistory() + id + " ");
            pstmt.setString(2, cUser);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void setRented(int id) {
        String query = "UPDATE users SET rented = ? WHERE name = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setInt(1, id);
            pstmt.setString(2, cUser);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // don't forget to view history
    public static String viewHistory(int userId) {
        String query = "SELECT record FROM history WHERE user_id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            String output = "History:";
            
            while (rs.next()) {
            	output = output + "Hist - " + rs.getString("record"); //it is a float!!
            }
            
            return output;
            
            // last exception
        } catch (SQLException e) {
            e.printStackTrace();
            return "Connection Broken";
        }
    }
}
