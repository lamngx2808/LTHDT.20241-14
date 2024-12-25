package PacketRouting.GUI;


// Java program to show Example of CardLayout.
// in java. Importing different Package.
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import PacketGUI.MyDraggableComponent;
import PacketRouting.*;
import PacketRouting.Components.Node;
import PacketRouting.Components.Router;

// class extends JFrame
public class MainMenu extends JFrame {

  private CardLayout cl;

  public MainMenu() {

    setTitle("Packet Routing Algorithms");
    setSize(400, 400);

    JPanel cardPanel = new JPanel();

    cl = new CardLayout();

    cardPanel.setLayout(cl);

    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    

    JLabel jl1 = new JLabel("Menu");
    JLabel jl2 = new JLabel("<html><h3 style='color:red'>First Algorithm</h1>"
                          + "<p>first line</p>"
                          + "<p>second line</p>"
                          + "<html><h3 style='color:red'>Second Algorithm</h1>"
                          + "<p>first line</p>"
                          + "<p>second line</p>");

    
    jp1.add(jl1);
    jp2.add(jl2);

    cardPanel.add(jp1, "1");
    cardPanel.add(jp2, "2");
    cardPanel.add(jp3, "3");

    JPanel buttonPanel = new JPanel();
    
    JButton helpBtn = new JButton("Help");
    JButton startBtn = new JButton("Start");
    JButton quitBtn = new JButton("Quit");

    buttonPanel.add(helpBtn);
    buttonPanel.add(startBtn);
    buttonPanel.add(quitBtn);

    // add helpBtn in ActionListener
    helpBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        cl.show(cardPanel, "2");
      }
    });

    quitBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        
        int result = JOptionPane.showConfirmDialog(null, "Exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
          System.exit(0);
      }
    });

    startBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        
        VisualPanel vPanel = new VisualPanel();
        vPanel.setBorder(BorderFactory.createTitledBorder("vPanel"));
        
        JPanel buttonPanel = new JPanel();
        
        JButton btnAddNode = new JButton("Add Node");
        JButton btnAddEdge = new JButton("Add Edge");

        buttonPanel.add(btnAddNode);
        buttonPanel.add(btnAddEdge);
        
        getContentPane().removeAll();
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(vPanel, BorderLayout.CENTER);
        getContentPane().doLayout();

        
        btnAddNode.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JTextField field1 = new JTextField("Router01");
            JTextField field2 = new JTextField("1.2.3.4");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name:"));
            panel.add(field1);
            panel.add(new JLabel("IP Address:"));
            panel.add(field2);
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Node", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
              Node newNode = new Router(field1.getText(), field2.getText());

              MyDraggableComponent newNodeIcon = new MyDraggableComponent(newNode.getName(), newNode.getIpAddress());
              vPanel.add(newNodeIcon);

            } else {
              System.out.println("Cancelled");
            }
          }
        });

        btnAddEdge.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JTextField field1 = new JTextField("node 1");
            JTextField field2 = new JTextField("node 2");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name of node 1:"));
            panel.add(field1);
            panel.add(new JLabel("Name of node 2:"));
            panel.add(field2);
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Edge", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
              // TODO

            } else {
              
            }
          }
        });
        
      }
    });
    
    

    getContentPane().add(cardPanel, BorderLayout.CENTER);

    getContentPane().add(buttonPanel, BorderLayout.NORTH);
  }
  
  

  // Main Method
  public static void main(String[] args) {
    MainMenu cl = new MainMenu();

    cl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    cl.setVisible(true);
  }
}
