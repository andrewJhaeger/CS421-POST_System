package POSTSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class NewSaleDisplay {

  private JFrame window = new JFrame();
  private JPanel mainPanel = new JPanel();
  private JPanel topPanel = new JPanel();
  private JPanel middlePanel = new JPanel();
  private JPanel middleTPanel = new JPanel();
  private JPanel middleBPanel = new JPanel();
  private JPanel middleB1Panel = new JPanel();
  private JPanel middleB2Panel = new JPanel();
  private JPanel bottomPanel = new JPanel();

  private JLabel date = new JLabel("Date: ");
  private JLabel time = new JLabel("Time: ");
  private JTextArea saleInfo = new JTextArea();
  private JLabel itemLabel = new JLabel("Item Code: ");
  private JTextField itemCode = new JTextField();
  private JLabel quantityLabel = new JLabel("   Quantity: ");
  private JTextField quantity = new JTextField();
  private JButton addItem = new JButton("Add Item");
  private JButton calcTotal = new JButton("Calculate Total");
  private JButton clearItem = new JButton("Clear Item");
  private JButton cancelSale = new JButton("Cancel Item");

  public NewSaleDisplay() {
    setLayouts();
    addElements();
    displayWindow();
  }
  
  private void setLayouts() {
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    saleInfo.setBorder(border);
    mainPanel.setLayout(new BorderLayout());
    topPanel.setLayout(new GridLayout(1, 2, 5, 5));
    middlePanel.setLayout(new BorderLayout());
    middleTPanel.setLayout(new BorderLayout());
    middleBPanel.setLayout(new GridLayout(2, 1, 5, 5));
    middleB1Panel.setLayout(new BorderLayout());
    middleB2Panel.setLayout(new BorderLayout());
    bottomPanel.setLayout(new GridLayout(2, 2, 20, 20));
    middleBPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
    middlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
    mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
  }
  
  private void addElements() {
    topPanel.add(date);
    topPanel.add(time);
    middleTPanel.add(saleInfo, BorderLayout.CENTER);
    middleB1Panel.add(itemLabel, BorderLayout.WEST);
    middleB1Panel.add(itemCode, BorderLayout.CENTER);
    middleB2Panel.add(quantityLabel, BorderLayout.WEST);
    middleB2Panel.add(quantity, BorderLayout.CENTER);
    middleBPanel.add(middleB1Panel);
    middleBPanel.add(middleB2Panel);
    middlePanel.add(middleTPanel, BorderLayout.CENTER);
    middlePanel.add(middleBPanel, BorderLayout.SOUTH);
    
    bottomPanel.add(addItem);
    bottomPanel.add(calcTotal);
    bottomPanel.add(clearItem);
    bottomPanel.add(cancelSale);
    
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(middlePanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    window.add(mainPanel);
  }
  
  private void displayWindow() {
    window.setSize(400, 500);
    window.setTitle("New Sale");
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
  }
}
