package POSTSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InitialDisplay {
  private JFrame window = new JFrame();
  private JPanel mainPanel = new JPanel();
  private JPanel titlePanel = new JPanel();
  private JPanel buttonPanel = new JPanel();
  
  private JLabel title = new JLabel("POST System");
  private JButton newSale = new JButton("New Sale");
  private JButton returnItem = new JButton("Return Item");
  private JButton salesReport = new JButton("Daily Sales Report");
  
  public InitialDisplay() {
    setLayouts();
    addElements();
    displayWindow();
  }
  
  private void setLayouts() {
    mainPanel.setLayout(new GridLayout(2, 1, 5, 5));
    titlePanel.setLayout(new FlowLayout());
    buttonPanel.setLayout(new GridLayout(1, 3, 20, 20));
    mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    
    Font label = new Font("Dialog", Font.BOLD, 24);
    title.setFont(label);
  }
  
  private void addElements() {
    titlePanel.add(title);
    buttonPanel.add(newSale);
    buttonPanel.add(returnItem);
    buttonPanel.add(salesReport);
    mainPanel.add(titlePanel);
    mainPanel.add(buttonPanel);
    window.add(mainPanel);
  }
  
  private void displayWindow() {
    window.setSize(500, 175);
    window.setTitle("POST System");
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
  }
}
