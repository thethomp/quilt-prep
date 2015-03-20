//////////////////////////////////////////////
//
//	Source code protected under GPLv3 Open Source licence
//
//  Written by: Mike Thompson (met600@gmail.com)
//
//////////////////////////////////////////////

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


public class QuiltPrep extends JFrame implements ComponentListener {

	//******* MAIN METHOD ******//
	public static void main(String[] args) {
		QuiltPrep qp = new QuiltPrep();
		qp.setVisible( true );
	}


	//******* INSTANCE VARIABLES *******//
	
	// Number of pieces
	private static int limit = 7;
	private static int WIDTH = 600;
	private static int HEIGHT = 425;
	private static int MIN_WIDTH = 550;
	private static int MIN_HEIGHT = 400;
	
	// Static strings
	private static String version = "ALPHA BUILD";
	private static String width_label_string = "Width of fabric to cut from: ";
	private static String total_label_string = "Total length of fabric to buy: ";
	private static String[] options = {"Square", "Rectangle"};
	private static String width_string = "Width: ";
	private static String height_string = "Height: ";
	private static String qty_string = "Qty: ";

	// Labels
	private JLabel fabric_width_label; 
	private JLabel total_label; 
	private ArrayList<JLabel> width_labels = new ArrayList<JLabel>();
	private ArrayList<JLabel> height_labels = new ArrayList<JLabel>();
	private ArrayList<JLabel> qty_labels = new ArrayList<JLabel>();
	
	// Text fields
	private JTextField fabric_width_field;
	private JTextField total_field;
	private ArrayList<JTextField> width_fields = new ArrayList<JTextField>();
	private ArrayList<JTextField> height_fields = new ArrayList<JTextField>();
	private ArrayList<JTextField> qty_fields = new ArrayList<JTextField>();

	// Drop down menus
	private ArrayList<JComboBox> menus = new ArrayList<JComboBox>();

	// Check boxes
	private ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	
	// Buttons
	private JButton compute = new JButton("Compute Total");
	private JButton clear = new JButton("Clear");
	
	//***** QuiltPrep Constructor ******//
	public QuiltPrep() {
		setLayout();
		registerListeners();
	}
	

	private void setLayout() {
		setSize(WIDTH, HEIGHT);
		addComponentListener(this); // allows resizing restrictions
		setLocation(50,50);
		setTitle("QuiltPrep - " + version);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		// Create Labels
		fabric_width_label = new JLabel(width_label_string);
		total_label = new JLabel(total_label_string);
	
		for( int i = 0; i<limit; i++ ) {
			JLabel temp = new JLabel(width_string);
			width_labels.add(temp);
		}
		
		for( int i = 0; i<limit; i++ ) {
			JLabel temp = new JLabel(height_string);
			height_labels.add(temp);
		}
	
		for( int i = 0; i<limit; i++ ) {
			JLabel temp = new JLabel(qty_string);
			qty_labels.add(temp);
		}

		// Create text fields and set them up
		fabric_width_field = new JTextField(10); 		
	
		total_field = new JTextField(15);
		total_field.setEditable(false);
	
		for( int i = 0; i<limit; i++ ) {
			JTextField temp = new JTextField(10);
			width_fields.add(temp);
		}
		for( int i = 0; i<limit; i++ ) {
			JTextField temp = new JTextField(10);
			height_fields.add(temp);
		}
		for( int i = 0; i<limit; i++ ) {
			JTextField temp = new JTextField(3);
			qty_fields.add(temp);
		}

		// Drop down menus
		for( int i = 0; i<limit; i++ ) {
			JComboBox temp = new JComboBox( options );
			menus.add(temp);
		}

		// Check boxes
		for( int i = 0; i<limit; i++ ) {
			JCheckBox temp = new JCheckBox("");
			checkboxes.add(temp);
		}

		// Set up panels
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
	//	main.setLayout(new GridLayout(9,0));

		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.add(fabric_width_label);
		top.add(fabric_width_field);
		main.add(top);	// add to main panel

		for(int i = 0; i<limit; i++) {
			JPanel pan = new JPanel();
			pan.setLayout(new FlowLayout());
			pan.add(checkboxes.get(i));
			pan.add(menus.get(i));
			pan.add(width_labels.get(i));
			pan.add(width_fields.get(i));
			pan.add(height_labels.get(i));
			pan.add(height_fields.get(i));
			pan.add(qty_labels.get(i));
			pan.add(qty_fields.get(i));
			main.add(pan);

		}

		// Add buttons to the bottom
		JPanel bot = new JPanel();
		bot.setLayout(new FlowLayout());
//		bot.setLayout(new GridLayout(1,1,20,20));
		bot.add(compute);
		bot.add(clear);

		// add total to the bottom
		JPanel total = new JPanel();
		total.setLayout(new FlowLayout());
//		total.setLayout(new GridLayout(1,1));
		total.add(total_label);
		total.add(total_field);

		// Add everything to main
		main.add(bot);
		main.add(total);

		// Add main panel to the frame
		this.add(main);

	}

	private void registerListeners() {
		ComputeTotalListener computeTotalListener = new ComputeTotalListener();
		compute.addActionListener(computeTotalListener);

		ClearFieldsListener clearFieldsListener = new ClearFieldsListener();
		clear.addActionListener(clearFieldsListener);	
	}

	private void checkForException( double d, String field ) {
		if( d  == -1 ) {
			JOptionPane.showMessageDialog( null, "QuiltPrep encountered in error reading from the " + field + 
											 " field.", "Number Input Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ************ Component Listener Methods **************** //
	public void componentResized(ComponentEvent e) {
		int width = getWidth();
        int height = getHeight();
        //we check if either the width
        //or the height are below minimum
        boolean resize = false;
        if (width < MIN_WIDTH) {
        	resize = true;
        	width = MIN_WIDTH;
       	}
        if (height < MIN_HEIGHT) {
        	resize = true;
        	height = MIN_HEIGHT;
        }
        if (resize) {
        	setSize(width, height);
        }
	}
	public void componentHidden( ComponentEvent c ) {}
	public void componentShown( ComponentEvent c ) {}
	public void componentMoved( ComponentEvent c ) {}




	// ************ Button Listener Classes **************** //
	private class ComputeTotalListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			QuiltPrepToolbox qptb = new QuiltPrepToolbox();
			Double fabric_width = new Double(qptb.parseString(fabric_width_field.getText()));
			checkForException(fabric_width, "Width of fabric");

			Double total = new Double(0);
			for( int i = 0; i<limit; i++ ) {
				if( (checkboxes.get(i)).isSelected() ) {
					if( ((String)(menus.get(i).getSelectedItem())).compareTo("Square") == 0 ) {
						double width = qptb.parseString((width_fields.get(i)).getText());
						checkForException(fabric_width, "Width");
						double qty = qptb.parseString(qty_fields.get(i).getText());
						checkForException(fabric_width, "qty");
						total = total + qptb.computeTotal('q', fabric_width, width, width, qty);
					}
					else if( ((String)(menus.get(i).getSelectedItem())).compareTo("Rectangle") == 0 ) {
						double width = qptb.parseString(width_fields.get(i).getText());
						checkForException(fabric_width, "Width");
						double height = qptb.parseString(height_fields.get(i).getText());
						checkForException(fabric_width, "Height");
						double qty = qptb.parseString(qty_fields.get(i).getText());
						checkForException(fabric_width, "qty");
						total = total + qptb.computeTotal('s', fabric_width, width, height, qty);
					}
				}
				else
					continue;
			}
			total_field.setText(total.toString());
		}

	}

	private class ClearFieldsListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			// clear all text fields
			fabric_width_field.setText("");		
			total_field.setText("");
			for(int i = 0; i<width_fields.size(); i++) {
				(width_fields.get(i)).setText("");
			}

			for(int i = 0; i<height_fields.size(); i++) {
				(height_fields.get(i)).setText("");
			}

			for(int i = 0; i<qty_fields.size(); i++) {
				(qty_fields.get(i)).setText("");
			}
		}

	}












}
