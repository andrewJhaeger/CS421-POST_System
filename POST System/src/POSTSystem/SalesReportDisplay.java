package POSTSystem;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class defines the Sales Report GUI to the user.
 */
public class SalesReportDisplay {

  private Register register;
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
  private JScrollPane reportInfo = new JScrollPane(info,
          JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  private JFrame fromWindow;

  /**
   * Start the process of creating the new sale GUI.
   *
   * @param from
   * @param inRegister
   */
  public SalesReportDisplay(JFrame from, Register inRegister) {
    register = inRegister;
    fromWindow = from;
    setLayouts();
    addElements();
    displayWindow();
    bindButtons();
  }

  /**
   * Set the layouts of the different panels on the form to properly arrange the
   * elements on the GUI.
   */
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

  /**
   * Add the different elements to their respective panels and then add the
   * different panels to the main panel.
   */
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

  /**
   * Make the window visible, set the title and size of the window, set it's
   * location on the screen and the action of the "X" button.
   */
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
        info.setEditable(false);
        info.setFont(new Font("Courier New", Font.PLAIN, 12));
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

  /**
   * Bind action listeners to the buttons on the GUI.
   */
  private void bindButtons() {
    runReport.addActionListener(new ActionListener() {
      @Override
      /**
       * Grabs date and prints out daily report for that date
       */
      public void actionPerformed(ActionEvent e) {
        if (register.isValidDate(date.getText()) == false) {
          JOptionPane.showMessageDialog(null,
                  "Invalid date format or date, use MM-dd-yy!");
        } else {
          try {
            register.printSalesReport(date.getText());
            printReceipt();
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error while reading receipts!");
          }
        }

      }
    });
  }

  /**
   * Prints the sale item that is made of the combined receipts
   */
  public void printReceipt() {
    info.setText("");
    for (String line : register.getReceipt()) {
      info.append(line);
      info.append("\n");
    }
  }
}
