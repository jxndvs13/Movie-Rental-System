package movie_rental_system;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

// may need the jar
public class UserGUI extends JFrame implements ActionListener {
	//text feilds, and areas
    private JTextField idField, nameField, passwordField, creditField, historyField;
    private JTextArea outputArea;
    // buttonns, use Btn-shorter
    private JButton createBtn, loginBtn, changeNameBtn, changePassBtn;
    private JButton addCreditBtn, viewCreditBtn, addHistoryBtn, viewHistoryBtn;
    
    public UserGUI() {
    	// Set the title, but it can barley be seen?
        setTitle("User System GUI");
        
        // layout
        setLayout(new GridBagLayout());
        GridBagConstraints c;
        
        // Labels
        JLabel idLabel = new JLabel("User ID:"); //ID
        JLabel nameLabel = new JLabel("Name:"); //name
        JLabel passLabel = new JLabel("Password:"); //pass
        JLabel creditLabel = new JLabel("Credit:"); //credit
        JLabel historyLabel = new JLabel("History:"); //history
        
        
        // Fields(5)
        idField = new JTextField(10);
        nameField = new JTextField(10);
        passwordField = new JTextField(10);
        creditField = new JTextField(10);
        historyField = new JTextField(10);

        // I should have an output area
        outputArea = new JTextArea(10, 20);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Buttons, need 8 for 8 actions
        createBtn = new JButton("Create User");
        loginBtn = new JButton("Login");
        changeNameBtn = new JButton("Change Name");
        changePassBtn = new JButton("Change Password");
        addCreditBtn = new JButton("Add Credit");
        viewCreditBtn = new JButton("View Credit");
        addHistoryBtn = new JButton("Add History");
        viewHistoryBtn = new JButton("View History");
        
        // my buttons
        JButton[] buttons = {createBtn, loginBtn, changeNameBtn, changePassBtn,
                             addCreditBtn, viewCreditBtn, addHistoryBtn, viewHistoryBtn};
        
        for (JButton b : buttons) {
            b.addActionListener(this); //actions
        }
        
        // Layout for the view, make 5,5,5,5 and only two columns
        c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        
        // Row 0
        c.gridx=0; c.gridy=0; add(idLabel,c);
        c.gridx=1; add(idField,c);
        
        // Row 1
        c.gridx=0; c.gridy=1; add(nameLabel,c);
        c.gridx=1; add(nameField,c);
        
        // Row 2
        c.gridx=0; c.gridy=2; add(passLabel,c);
        c.gridx=1; add(passwordField,c);
        
        // Row 3
        c.gridx=0; c.gridy=3; add(creditLabel,c);
        c.gridx=1; add(creditField,c);
        
        // Row 4
        c.gridx=0; c.gridy=4; add(historyLabel,c);
        c.gridx=1; add(historyField,c);
        
        // Buttons (but with grid style)
        c.gridx=2; c.gridy=0; add(createBtn,c);
        c.gridy=1; add(loginBtn,c);
        c.gridy=2; add(changeNameBtn,c);
        c.gridy=3; add(changePassBtn,c);
        c.gridy=4; add(addCreditBtn,c);
        c.gridy=5; add(viewCreditBtn,c);
        c.gridy=6; add(addHistoryBtn,c);
        c.gridy=7; add(viewHistoryBtn,c);
        
        // Output
        c.gridx=0; c.gridy=6;
        c.gridwidth=2;
        add(scrollPane,c);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int id = idField.getText().isEmpty() ? 0 : Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String pass = passwordField.getText();

            if (e.getSource() == createBtn) { // should need just three for here
                UserDB.createUser(id, name, pass); // I can just connect to UserDB for this part to make it smaller
                // Do this with all of the options!!!
                outputArea.append("User created!\n");
            }

            else if (e.getSource() == loginBtn) { // may need more here
                User user = UserDB.login(name, pass);
                if (user != null) {
                    outputArea.append("Login successful!\n");
                } else {
                    outputArea.append("Login failed.\n");
                }
            }

            else if (e.getSource() == changeNameBtn) {
                UserDB.changeName(id, name); // id and name to change
                outputArea.append("Name changed.\n");
            }

            else if (e.getSource() == changePassBtn) {
                UserDB.changePassword(id, pass); //need an id and new password
                outputArea.append("Password changed.\n");
            }

            else if (e.getSource() == addCreditBtn) {
                double credit = Double.parseDouble(creditField.getText());
                UserDB.addCredit(id, credit); //id and credit
                outputArea.append("Credit added.\n");
            }

            else if (e.getSource() == viewCreditBtn) {
                UserDB.viewCredit(id); //of id
                outputArea.append("Check console for credit.\n");
            }

            else if (e.getSource() == addHistoryBtn) {
                String record = historyField.getText();
                UserDB.addHistory(id, record); //record
                outputArea.append("History added.\n");
            }

            else if (e.getSource() == viewHistoryBtn) {
                UserDB.viewHistory(id);
                outputArea.append("Check console for history.\n"); // could not get to show up
            }

        } catch (Exception ex) {
            outputArea.append("Error: " + ex.getMessage() + "\n");
        }
    }
    
    public static void main(String[] args) {
        UserGUI gui = new UserGUI(); //new
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.pack();
        gui.setVisible(true); //can see
    }
}
