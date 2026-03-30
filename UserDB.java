package movie_rental_system;

import java.sql.*;
import java.util.Scanner;

public class UserDB {
    static final String DB_URL = "jdbc:mysql://localhost:3306/movie_db";
    static final String USER = "root";
    static final String PASS = "";
    static Scanner scnr = new Scanner(System.in);
    
    public static void main(String[] args) {
    	// going = true
        boolean going = true;
        
        while (going) {
        	System.out.println(" ");
            System.out.println("Menu for User");
            System.out.println("1. New User");
            System.out.println("2. User Login");
            System.out.println("3. Change Name");
            System.out.println("4. Change Password");
            System.out.println("5. Add Credit");
            System.out.println("6. View Credit");
            System.out.println("7. Add History");
            System.out.println("8. View History");
            System.out.println("9. Exit"); // need an exit
            
            int op = scnr.nextInt();
            scnr.nextLine(); // need to clear buffer
            // make the menu 1-9
            switch (op) {
            // 1
                case 1: // new login
                    System.out.println("Enter id:");
                    int id = scnr.nextInt();
                    scnr.nextLine();
                    
                    System.out.println("Enter Name:");
                    String name = scnr.nextLine();
                    
                    System.out.println("Enter Password:");
                    String pass = scnr.nextLine();
                    
                    createUser(id, name, pass);
                    break;
                    
                case 2: //user login
                    System.out.println("Enter Name:");
                    String loginName = scnr.nextLine();

                    System.out.println("Enter Password:");
                    String loginPass = scnr.nextLine();
                    
                    User user = login(loginName, loginPass);
                    if (user != null) {
                        System.out.println("Login successful!" );
                    } else {
                        System.out.println("Invalid login.");
                    }
                    break;
                    
                case 3: // change name
                    System.out.println("Enter id:");
                    int idName = scnr.nextInt();
                    scnr.nextLine();
                    // new name
                    System.out.println("Enter new name:");
                    String newName = scnr.nextLine(); //newName
                    
                    changeName(idName, newName);
                    break;
                    
                case 4: // change password
                    System.out.println("Enter id:");
                    int idPass = scnr.nextInt();
                    scnr.nextLine();
                    // new password
                    System.out.println("Enter new password:");
                    String newPassword = scnr.nextLine(); //newPassword
                    
                    changePassword(idPass, newPassword);
                    break;
                    
                case 5: // add creit
                    System.out.println("Enter id:");
                    int idCredit = scnr.nextInt();
                    
                    System.out.println("Enter Credit:");
                    double amount = scnr.nextDouble();
                    
                    addCredit(idCredit, amount); //id,amount to add
                    break;
                    
                case 6: //view crdit here
                    System.out.println("Enter id:");
                    int idView = scnr.nextInt();
                    
                    viewCredit(idView); //just view, that it
                    break;
                    
                case 7:// add history
                	
                    System.out.println("Enter user id:");
                    int userId = scnr.nextInt();
                    scnr.nextLine();
                    
                    System.out.println("Enter history record:");
                    String record = scnr.nextLine();
                    
                    addHistory(userId, record);
                    break;
                    
                case 8: //now view the history
                    System.out.println("Enter user id:");
                    int histId = scnr.nextInt();
                    
                    viewHistory(histId);
                    break;
                    
                case 9: //EXIT!!
                    going = false;
                    System.out.println("Goodbye!");
                    break;
                    
                    
                    
                default: //need a default for the menu
                	
                    System.out.println("Invalid option.");
            }
        }
    }
    
    // This is to Create a New User
    public static void createUser(int id, String name, String password) { //first is start
        String query = "INSERT INTO users (id, name, password, credit) VALUES (?, ?, ?, ?)"; //then query insert
        // use try and error ,cattch = SQLException
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
    
    // I need a way to login 
    public static User login(String name, String password) {
        String query = "SELECT * FROM users WHERE name=? AND password=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
        	//name and password
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getString("id"), //Should be a getInt, but will not work
                    rs.getString("name"),
                    rs.getString("password")
                );
            }
            //exception, again, just include this
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null; // login fail
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
    
    // Now for veiwing, like Credit
    public static void viewCredit(int id) {
        String query = "SELECT credit FROM users WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("Credit: $" + rs.getDouble("credit")); //it is a float!!
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Then add Credit
    public static void addCredit(int id, double amount) {
        String query = "UPDATE users SET credit = credit + ? WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, id);
            //added it
            pstmt.executeUpdate();
            System.out.println("Credit added.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Also history, so add to history
    public static void addHistory(int userId, String record) {
        String query = "INSERT INTO history (user_id, record) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setInt(1, userId);
            pstmt.setString(2, record);
            
            pstmt.executeUpdate();
            System.out.println("The History has been added.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // don't forgget to view history
    public static void viewHistory(int userId) {
        String query = "SELECT record FROM history WHERE user_id=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("History:");
            while (rs.next()) {
                System.out.println("Hist - " + rs.getString("record")); //get string, but maybe array, **check**
            }
            // last exception
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
