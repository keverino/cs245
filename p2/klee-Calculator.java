//
// Name:       Lee, Kevin
// Project:    2
// Due:        Friday, February 7, 2014
// Course:     cs-245-02-w14
//
// Description:   This is a simple calculator program with a GUI.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Calculator implements ActionListener
{
   JFrame frame;
   JPanel displayPanel, buttonPanel;
   JLabel display;
   String result, output = "", previousDisplay;
   double temp, temp2;
   Boolean divBool = false, multBool = false, subBool = false, addBool = false;
   Boolean copyrightBool = false, opFinished = false;
   ///////////////////////////////////////////////////////////////////////////////////////
   Calculator()
   {
      // Create frame, set size, location to center, layout
      frame = new JFrame("Calculator");
      frame.setSize(335,385);
      frame.setLocationRelativeTo(null);
      frame.setLayout(new FlowLayout());
      ImageIcon img = new ImageIcon("Calculator.png");
      frame.setIconImage(img.getImage());

      
      // Terminate program when user closes application.
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // Create display (JPanel)
      display = new JLabel(output);
      display.setFont(new Font("Courier", Font.BOLD, 18));
      display.setHorizontalAlignment(JLabel.RIGHT);
      display.setPreferredSize(new Dimension(325,100));
      display.setBorder(BorderFactory.createLineBorder(Color.black));
      
      // Add frame and buttons to a JPanel
      displayPanel = new JPanel();
      buttonPanel = new JPanel(new GridLayout(4,4));
      displayPanel.add(display);
      
      // Create buttons
      JButton buttons;
      String [] buttonsString = { "7", "8", "9", "/",
                                  "4", "5", "6", "*",
                                  "1", "2", "3", "-",
                                  "0", "C", "=", "+" };
      
      for(int i = 0; i < buttonsString.length; i++)
      {
         buttons = new JButton(buttonsString[i]);
         if(buttonsString[i] == "C")
            buttons.addActionListener(new ActionListener() 
            {
               public void actionPerformed(ActionEvent ae) 
               {
                  int mask = ae.getModifiers();
                  
                  if((mask & KeyEvent.SHIFT_MASK) == KeyEvent.SHIFT_MASK)
                  {
                     previousDisplay = display.getText();
                     display.setText("(c) 2014 Kevin Lee");
                     copyrightBool = true;
                  }
                  else if ((mask & KeyEvent.CTRL_MASK) == KeyEvent.CTRL_MASK)
                     display.setText("0");
                  else if (copyrightBool == true) 
                  {
                     display.setText(previousDisplay);
                     copyrightBool = false;
                  }
                  else display.setText("0");
               }
            });
         else if (buttonsString[i] == "=") 
         {
            buttons.addActionListener(this);
            // Set default button to the "=" key
            frame.getRootPane().setDefaultButton(buttons);
         } 
         else buttons.addActionListener(this);
         buttons.setPreferredSize(new Dimension(80,60));
         buttonPanel.add(buttons);
      }//end for

      // Add components to frame
      frame.add(displayPanel);
      frame.add(buttonPanel);
      
      // Display the frame
      frame.setVisible(true);   
      
   }//end Calculator()
   ///////////////////////////////////////////////////////////////////////////////////////
   public void actionPerformed(ActionEvent ae)
   {
      if(display.getText() == "Overflow" || display.getText() == "Div by 0" 
            || display.getText() == "(c) 2014 Kevin Lee") return;
      
      if(ae.getActionCommand() == "0") numberButtonAction(ae);
      if(ae.getActionCommand() == "1") numberButtonAction(ae);
      if(ae.getActionCommand() == "2") numberButtonAction(ae);
      if(ae.getActionCommand() == "3") numberButtonAction(ae);
      if(ae.getActionCommand() == "4") numberButtonAction(ae);
      if(ae.getActionCommand() == "5") numberButtonAction(ae);
      if(ae.getActionCommand() == "6") numberButtonAction(ae);
      if(ae.getActionCommand() == "7") numberButtonAction(ae);
      if(ae.getActionCommand() == "8") numberButtonAction(ae);
      if(ae.getActionCommand() == "9") numberButtonAction(ae);
      
      if(ae.getActionCommand() == "+")
      {
         output = ae.getActionCommand();
         temp = Double.parseDouble(display.getText());
         display.setText("0");
         addBool = true;
      }
      if(ae.getActionCommand() == "-")
      {
         output = ae.getActionCommand();
         temp = Double.parseDouble(display.getText());
         display.setText("0");
         subBool = true;
      }
      if(ae.getActionCommand() == "*")
      {
         output = ae.getActionCommand();
         temp = Double.parseDouble(display.getText());
         display.setText("0");
         multBool = true;
      }
      if(ae.getActionCommand() == "/")
      {
         output = ae.getActionCommand();
         temp = Double.parseDouble(display.getText());
         display.setText("0");
         divBool = true;
      }
      if(ae.getActionCommand() == "=")
      {
         // Answer will be a decimal number since most calculators show decimals
         temp2 = Double.parseDouble( display.getText() );
         if (addBool == true) 
         {
            result = String.valueOf(temp += temp2);
            if(result.length() <= 10) display.setText(result);
            else display.setText("Overflow");
         }
         else if (subBool == true)
         {  
            result = String.valueOf(temp - temp2);
            if(result.length() <= 10) display.setText(result);
            else display.setText("Overflow");
         }
         else if (multBool == true) 
         {
            result = String.valueOf(temp *= temp2);
            if(result.length() <= 10) display.setText(result);
            else display.setText("Overflow");
         }
         else if (divBool == true) 
         {
            if(temp2 == 0) display.setText("Cannot divide by zero.");
            else display.setText(  Double.toString( temp /= temp2 ) );
         }

         addBool = false ;
         subBool = false ;
         multBool = false ;
         divBool = false ;
         opFinished = true;
      }
   }//end actionPerformed
   ///////////////////////////////////////////////////////////////////////////////////////
   // Pass the action event from actionPerformed 
   public void numberButtonAction(ActionEvent ae)
   {
      if (opFinished == true) display.setText("");
      if (display.getText() == "0" && display.getText().length() < 10)
         display.setText(ae.getActionCommand());
      else if (display.getText().length() < 10)
         display.setText(display.getText() + ae.getActionCommand());
      opFinished = false;
   }
   ///////////////////////////////////////////////////////////////////////////////////////
   public static void main(String args[])
   {
      //Create the frame on the event dispatching thread.
      SwingUtilities.invokeLater(new Runnable() { public void run() { new Calculator(); } } );
   }
}// end of Calculator class
