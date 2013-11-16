package POSTSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class SalesReportDisplay {
  private JFrame window = new JFrame();
  private JPanel mainPanel = new JPanel();
  private JPanel topPanel = new JPanel();
  private JPanel topTPanel = new JPanel();
  private JPanel topBPanel = new JPanel();
  private JPanel middlePanel = new JPanel();
  
  private JLabel dateLabel = new JLabel("Date:  ");
  private JTextField date = new JTextField();
  private JButton runReport = new JButton("Run Report");
  private JTextArea info = new JTextArea();
  private JScrollPane reportInfo = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  
  private JFrame fromWindow;
  
  public SalesReportDisplay(JFrame from) {
    fromWindow = from;
    setLayouts();
    addElements();
    displayWindow();
    bindButtons();
  }
  
  private void setLayouts() {
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    reportInfo.setBorder(border);
    mainPanel.setLayout(new BorderLayout());
    topPanel.setLayout(new GridLayout(2, 1, 5, 5));
    topTPanel.setLayout(new BorderLayout());
    topBPanel.setLayout(new BorderLayout());
    middlePanel.setLayout(new BorderLayout());
    middlePanel.setBorder(new EmptyBorder(15, 0, 0, 0));
    mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
  }
  
  private void addElements() {
    topTPanel.add(dateLabel, BorderLayout.WEST);
    topTPanel.add(date, BorderLayout.CENTER);
    topBPanel.add(runReport, BorderLayout.CENTER);
    topPanel.add(topTPanel);
    topPanel.add(topBPanel);
    middlePanel.add(reportInfo, BorderLayout.CENTER);
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(middlePanel, BorderLayout.CENTER);
    window.add(mainPanel);
  }
  
  private void displayWindow() {
    window.setSize(400, 440);
    window.setTitle("Sales Report");
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
    window.addWindowListener(new WindowListener() {
      @Override
      public void windowClosing(WindowEvent e) {
        window.dispose();
        fromWindow.setVisible(true);
      }

      @Override
      public void windowOpened(WindowEvent e) {
      }

      @Override
      public void windowClosed(WindowEvent e) {
      }

      @Override
      public void windowIconified(WindowEvent e) {
      }

      @Override
      public void windowDeiconified(WindowEvent e) {
      }

      @Override
      public void windowActivated(WindowEvent e) {
      }

      @Override
      public void windowDeactivated(WindowEvent e) {
      }
    });
    
    window.setVisible(true);
  }
  
  private void bindButtons() {
    runReport.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        
      }
    });
  }
}
