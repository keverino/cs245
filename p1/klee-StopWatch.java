//
// Name:       Lee, Kevin
// Project:    1
// Due:        Wednesday, January 22, 2014
// Course:     cs-245-01-w14
//
// Description:   This is a simple stopwatch program with a GUI.


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class StopWatch implements ActionListener
{
   JLabel jlab;
   long start;  //holds the start timer in milliseconds

   StopWatch()
   {
      //Create a new JFrame container.
      JFrame jfrm = new JFrame("A Simple Stopwatch");

      //Specify FlowLayout for the layout manager.
      jfrm.setLayout(new FlowLayout());

      //Give the frame an initial size.
      jfrm.setSize(230, 90);

      //Terminate program when user closes application.
      jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Make two buttons.
      JButton jbtnStart = new JButton("Start");
      JButton jbtnStop = new JButton("Stop");

      //Add action listeners.
      jbtnStart.addActionListener(this);
      jbtnStop.addActionListener(this);

      //Add buttons to the content pane.
      jfrm.add(jbtnStart);
      jfrm.add(jbtnStop);

      //Create a text-based label.
      jlab = new JLabel("Press Start to begin timing.");

      //Add the label to the frame.
      jfrm.add(jlab);

      //Display the frame.
      jfrm.setVisible(true);
   }  

   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getActionCommand().equals("Start"))
      {
         start = ae.getWhen();
         jlab.setText("Stopwatch is running...");
      }
      else
         jlab.setText("Elapsed time is " + (double) (ae.getWhen() - start ) /1000);
   }
   
   public static void main(String args[])
   {
      //Create the frame on the event dispatching thread.
      SwingUtilities.invokeLater(new Runnable() 
      { public void run() { new StopWatch(); } } );

   }
}