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
    public static String createUser(String name, String password) { //first is start
        String query = "INSERT INTO users (name, password) VALUES (?, ?)"; //then query insert
        String output = "User Creation Failed\n";
        // use try and error ,catch = SQLException
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //connect via sql
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	//id, name, password pstmt
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            
            output = "User " + name + " Created\n";
            
            pstmt.executeUpdate();
            //exception
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
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
                output = "Logged in as " + cUser + "\n";
            }
            //exception, again, just include this
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    // This is the first one, to Change a Name
    public static String changeName(String newName) { // a newName
        String query = "UPDATE users SET name=? WHERE name=?";
        String output = "Username Unchanged";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setString(1, newName);
            pstmt.setString(2, cUser);
            
            pstmt.executeUpdate();
            output = "Password changed from \"" + cUser + "\" to \"" + newName + "\"\n";
            cUser = newName;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    
    // Then this is to Change a Password
    public static String changePassword(String newPassword) { // use newPassword
        String query = "UPDATE users SET password=? WHERE name=?";
        String output = "Password Unchanged";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setString(1, newPassword);
            pstmt.setString(2, cUser);
            
            pstmt.executeUpdate();
            output = "Username changed from to \"" + newPassword + "\"\n";
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return output;
    }

    // Then add Credit
    public static String addCredit(double amount) {
        String query = "UPDATE users SET credit = credit + ? WHERE name=?";
        
        String output = "Operation Failed\n";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setDouble(1, amount);
            pstmt.setString(2, cUser);
            //added it
            pstmt.executeUpdate();
            if (amount > 0) {
                output = "$" + amount + " Added\n";
            }
            else if (amount < 0) {
                output = "$" + (-1 * amount) + " Removed\n";
            }
            else {
                output = "Credit Not Changed\n";
            }
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
    
    public static String setRented(int id) {
        String query = "UPDATE users SET rented = ? WHERE name = ?";
        String output = "Operation Failed\n";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setInt(1, id);
            pstmt.setString(2, cUser);
            
            pstmt.executeUpdate();
            
            output = Catalogue.nameMov(id) + " Succesfully Rented\n";
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return output;
    }
}
