package movie_rental_system;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class SystemFrame extends JFrame implements ActionListener, ChangeListener {
	private JTextArea catA;
	private JTextArea selectA;
	private JSpinner searchS;
	private JButton rentB;
	private JButton returnB;
	
	SystemFrame() {
		JLabel catL = new JLabel("Full Catalogue");
		JLabel selectL = new JLabel("Selected Film");
		JLabel idL = new JLabel("Enter ID:");
		
		catA = new JTextArea(20,20); //height, width
		JScrollPane scrollP = new JScrollPane(catA);
		catA.setEditable(false);
		
		selectA = new JTextArea(2,20);
		selectA.setEditable(false);
		
		SpinnerNumberModel idM = new SpinnerNumberModel(0, 0, 2, 1); //init, min, max, step
		searchS = new JSpinner(idM);
		searchS.addChangeListener(this);
		
		rentB = new JButton("Rent");
		rentB.addActionListener(this);
		
		returnB = new JButton("Return");
		returnB.addActionListener(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints layC = new GridBagConstraints();
		layC.insets = new Insets(10,10,10,10);
		layC.gridx = 0;
		layC.gridy = 0;
		add(catL, layC);
		
		layC.gridx = 1;
		layC.gridwidth = 2;
		add(selectL, layC);
		
		layC.gridx = 0;
		layC.gridy = 2;
		layC.gridwidth = 1;
		layC.gridheight = 4;
		layC.fill = GridBagConstraints.BOTH;
		layC.weightx = 1.0;
		layC.weighty = 1.0;
		add(scrollP, layC);
		
		layC.gridx = 1;
		layC.gridheight = 1;
		layC.gridwidth = 2;
		layC.weightx = 0;
		layC.weighty = 0;
		layC.fill = GridBagConstraints.NONE;
		add(selectA, layC);
		
		layC.gridy = 3;
		layC.gridwidth = 1;
		add(idL, layC);
		
		layC.gridx = 2;
		add(searchS, layC);
		
		layC.gridx = 1;
		layC.gridy = 4;
		layC.fill = GridBagConstraints.BOTH;
		layC.weightx = 1.0;
		layC.weighty = 0;
		add(rentB,layC);
		
		layC.gridx = 2;
		add(returnB,layC);
		
		catA.setText(Catalogue.viewCat());
	}
	
	public void stateChanged(ChangeEvent event) {
		if ((Integer) searchS.getValue() == 0) {
			selectA.setText("");
		}
		else {
			selectA.setText(Catalogue.displayMov((Integer) searchS.getValue()));
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		if ((JButton) event.getSource() == rentB) {
			if ((Integer) searchS.getValue() != 0) {
				Catalogue.reduceStock((Integer) searchS.getValue());
				catA.setText(Catalogue.viewCat());
				selectA.setText(Catalogue.displayMov((Integer) searchS.getValue()));
			}
		}
		else if ((JButton) event.getSource() == returnB) {
			if ((Integer) searchS.getValue() != 0) {
				Catalogue.increaseStock((Integer) searchS.getValue());
				catA.setText(Catalogue.viewCat());
				selectA.setText(Catalogue.displayMov((Integer) searchS.getValue()));
			}
		}
	}
	
	public static void main(String[] args) {
		SystemFrame frame = new SystemFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
