//
// Name:          Lee, Kevin
// Homework:      1
// Due:           Monday, February 17, 2014
// Course:        cs-245-01-w14
//
// Description:   This is a simple slideshow program with start and stop button functionality.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;
import java.net.*;

class Slideshow implements ActionListener
{   
   //images from the internet
   static String imagePaths[] = { "http://i.imgur.com/XrUdrfW.jpg",
      "http://i.imgur.com/VsuOSV3.jpg", "http://i.imgur.com/Uy6tYw1.jpg", 
      "http://i.imgur.com/dAE3mFh.jpg", "http://i.imgur.com/TNzWz7c.jpg" };
   
   //local files
   //static String imagePaths[] = { "dAE3mFh.jpg", "TNzWz7c.jpg", "Uy6tYw1.jpg", "VsuOSV3.jpg" }; 
   
   JFrame         frame;
   JScrollPane    scrollPane;
   JLabel         image;
   ImageIcon      imageIcon;
   JButton        centerButton, PlayStopButton;
   Timer          timer ;

   int seconds = 2500; //Default is 2.5 seconds
   int counter = 0;
//////////////////////////////////////////////////////////////////////////////////////////
   Slideshow(String images[])
   {
      //transfer array of images into local array
      imagePaths = images;
      
      //create the frame and set its size, layout, location, and close operation
      frame = new JFrame("Slideshow");
      frame.setSize(800,600);
      frame.setLayout(new BorderLayout());
      frame.setLocationRelativeTo(frame);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //create center and play/stop buttons
      centerButton = new JButton("center");
      PlayStopButton = new JButton("Play");
      
      //set actionListener for timer
      timer = new Timer(seconds, this);
      timer.setActionCommand("timer");
      
      //set actionListener for the Play/Stop button
      PlayStopButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae)
         {
            if(PlayStopButton.getText() == "Play")
            {
               PlayStopButton.setText("Stop");
               timer.start();
            }
            else if(PlayStopButton.getText() == "Stop") 
            {
               PlayStopButton.setText("Play");
               timer.stop();
            }
         }});
      
      //add the JLabel to the JScrollPane
      image = new JLabel("");
      scrollPane = new JScrollPane(image);
  
      //add everything to the frame and display it
      frame.add(scrollPane);
      frame.add(PlayStopButton, BorderLayout.SOUTH);
      frame.setVisible(true);
   }
//////////////////////////////////////////////////////////////////////////////////////////
   public void setDelay(int numberOfSecs) { seconds = numberOfSecs; }
//////////////////////////////////////////////////////////////////////////////////////////
   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getActionCommand() == "timer")
      {
         if(counter >= imagePaths.length)
         {
            timer.stop();
            counter = 0;
            PlayStopButton.setText("Play");
         }
         else
         {
            try 
            { 
               //if the array contains URLs
               if(imagePaths[counter].contains("http://")) 
               {
                  scrollPane.setToolTipText(imagePaths[counter]);
                  imageIcon = new ImageIcon((new URL(imagePaths[counter++])));
                  scrollPane.setViewportView(new JLabel(imageIcon)); 
               }
               //if the array contains local images
               else
               {
                  image.setIcon(new ImageIcon(imagePaths[counter]));
                  image.setToolTipText(imagePaths[counter++]);
               }
            }
            catch (MalformedURLException ex) 
            {
               Logger.getLogger(Slideshow.class.getName()).log(Level.SEVERE, null, ex);
               image = new JLabel("?" + imagePaths);
            } 
         }
      }
   }
//////////////////////////////////////////////////////////////////////////////////////////
   public static void main(String args[])
   {
      //Create the frame on the event dispatching thread.
      SwingUtilities.invokeLater(new Runnable() 
      { public void run() { new Slideshow(imagePaths); } } );

   }
//////////////////////////////////////////////////////////////////////////////////////////
}