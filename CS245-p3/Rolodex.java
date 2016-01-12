//
// Name:       Lee, Kevin
// Project:    3
// Due:        Friday, February 28, 2014
// Course:     cs-245-02-w14
//
// Description:   This is a rolodex program with a GUI.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;

class Rolodex
{
   JFrame frame;
   JMenu file, tabs, help, placement, layout;
   JMenuItem open, exit, defaults, about, top, right, bottom, left, scroll, wrap;
   JTabbedPane pane;
   int tabIndex, numOfContacts;
 
////////////////////////////////////////////////////////////////////////////////////////////////////
   Rolodex()
   {
      // Create frame, set size, location to center, layout
      frame = new JFrame("Rolodex");
      frame.setSize(600,180);
      frame.setLocationRelativeTo(null);
      
      // Terminate program when user closes application.
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // Create the main menu bar
      JMenuBar mainBar = new JMenuBar();
      file = new JMenu("File");
      tabs = new JMenu("Tabs");
      help = new JMenu("Help");
      
      addFileMenu(); // Populate the file menu
      addTabsMenu(); // Populate the tabs menu
      addHelpMenu(); // Populate the help menu

      // Add file, tabs, and help to the main bar
      mainBar.add(file);
      mainBar.add(tabs);
      mainBar.add(help);
      
      // Add tabs & content
      pane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
      addDevContactInfo();
      addContactsFromFile();
      
      // Add the menu bar to the frame and display the frame
      frame.setJMenuBar(mainBar);
      frame.add(pane);
      frame.setVisible(true);
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
   public void addFileMenu()
   {
      open = new JMenuItem("Open");
      open.setEnabled(false);
      exit = new JMenuItem("Exit");
      exit.setMnemonic('x');
      exit.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent ae) { 
         System.exit(0); }});
      file.add(open);
      file.addSeparator();
      file.add(exit);
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
   public void addTabsMenu()
   {
      // Placement menu
      placement = new JMenu("Placement");
      top = new JMenuItem("Top");
      top.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) {
         pane.setTabPlacement(JTabbedPane.TOP); }});
      
      right = new JMenuItem("Right");
      right.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) {
         pane.setTabPlacement(JTabbedPane.RIGHT); }});
      
      bottom = new JMenuItem("Bottom");
      bottom.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) {
         pane.setTabPlacement(JTabbedPane.BOTTOM); }});
      
      left = new JMenuItem("Left");
      left.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) {
         pane.setTabPlacement(JTabbedPane.LEFT); }});
     
      placement.add(top);
      placement.add(right);
      placement.add(bottom);
      placement.add(left);
      
      // Layout menu
      layout = new JMenu("Layout");
      scroll = new JMenuItem("Scroll");
      scroll.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) {
         pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); }});
      
      wrap = new JMenuItem("Wrap");
      wrap.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) {
         pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); }});
      
      layout.add(scroll);
      layout.add(wrap);
      
      // Default menu item
      defaults = new JMenuItem("Defaults");
      defaults.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
      defaults.addActionListener(new ActionListener() { 
         public void actionPerformed(ActionEvent ae)
         {
            pane.setTabPlacement(JTabbedPane.TOP);
            pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
         }});
      tabs.add(placement);
      tabs.add(layout);
      tabs.addSeparator();
      tabs.add(defaults);
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
   public void addHelpMenu()
   {
      about = new JMenuItem("About");
      about.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent ae) {
         JOptionPane.showMessageDialog(frame, "Rolodex\nVersion 0.1\n(c) 2014 Lee", 
               "About", JOptionPane.PLAIN_MESSAGE, new ImageIcon("Rolodex.png")); }});
      help.add(about);
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
   public void addDevContactInfo()
   {
      JPanel devPanel = new JPanel();
      JPanel imgPanel = new JPanel();
      JPanel infoPanel = new JPanel();
      infoPanel.setLayout(new GridLayout(2,1));
      devPanel.add(imgPanel);
      devPanel.add(infoPanel);
      
      imgPanel.add(new JLabel(new ImageIcon("nopic.jpg")));
      infoPanel.add(new JLabel("                              Name: "));
      infoPanel.add(new JTextField("Lee, Kevin", 16));
      infoPanel.add(new JLabel("                              Email: "));
      infoPanel.add(new JTextField("kmlee@csupomona.edu", 16));
      pane.addTab("Lee, Kevin", devPanel);
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
   public void addContactsFromFile()
   {
      try
      {
         Scanner readFile = new Scanner(new File("contacts.txt"));
         while(readFile.hasNextLine())
         {
            String line = readFile.nextLine();
            String[] toSplit = line.split("~");
  
            JPanel contentPanel = new JPanel();
            JPanel imgPanel = new JPanel();
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(2,1));
            contentPanel.add(imgPanel);
            contentPanel.add(infoPanel);

            // Add image if found, add nopic.jpg if not found
            for(int i = 0; i < toSplit.length; i++)
            {
               URL imgURL = getClass().getResource(toSplit[i]);
               
               if(imgURL == null && toSplit[i].contains(".jpg"))
                  imgPanel.add(new JLabel(new ImageIcon("nopic.jpg")));
               else imgPanel.add(new JLabel(new ImageIcon(toSplit[i])));
            }

            if(toSplit.length == 3)
            {
               infoPanel.add(new JLabel("                              Name: "));
               infoPanel.add(new JTextField(toSplit[0], 16));
               infoPanel.add(new JLabel("                              Email: "));
               infoPanel.add(new JTextField(toSplit[1], 16));
            }
            
            // Add newly created panel to the JTabbedPane
            pane.add(toSplit[0], contentPanel);
            tabIndex++;
         }
         readFile.close();
      }
      catch(FileNotFoundException e) {}
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
   public static void main(String args[]) throws Exception
   {
      //Create the frame on the event dispatching thread.
      SwingUtilities.invokeLater(new Runnable() { public void run() { new Rolodex(); } } );
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
   }
////////////////////////////////////////////////////////////////////////////////////////////////////
}