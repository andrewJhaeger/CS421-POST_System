package POSTSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class defines the return item GUI that will be displayed to the user.
 */
public class ReturnItemDisplay {

  private Register register;
  private Money[] amounts;
  private JFrame window = new JFrame();
  private JPanel mainPanel = new JPanel();
  private JPanel topPanel = new JPanel();
  private JPanel topTPanel = new JPanel();
  private JPanel topBPanel = new JPanel();
  private JPanel middlePanel = new JPanel();
  private JPanel bottomPanel = new JPanel();
  private JLabel receiptLabel = new JLabel("Receipt Number:  ");
  private JTextField receiptNumber = new JTextField();
  private JButton findButton = new JButton("Find Receipt");
  private DefaultListModel listModel = new DefaultListModel();
  private JList receiptInfo = new JList(listModel);
  private JButton returnItem = new JButton("Return Selected Item");
  private JFrame fromWindow;

  /**
   * Start the process of creating the return item GUI.
   *
   * @param from
   * @param inRegister
   */
  public ReturnItemDisplay(JFrame from, Register inRegister) {
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
    receiptInfo.setBorder(border);
    mainPanel.setLayout(new BorderLayout());
    topPanel.setLayout(new GridLayout(2, 1, 5, 5));
    topTPanel.setLayout(new BorderLayout());
    topBPanel.setLayout(new BorderLayout());
    middlePanel.setLayout(new BorderLayout());
    bottomPanel.setLayout(new BorderLayout());
    middlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));
    mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
  }

  /**
   * Add the different elements to their respective panels and then add the
   * different panels to the main panel.
   */
  private void addElements() {
    topTPanel.add(receiptLabel, BorderLayout.WEST);
    topTPanel.add(receiptNumber, BorderLayout.CENTER);
    topBPanel.add(findButton, BorderLayout.CENTER);
    topPanel.add(topTPanel);
    topPanel.add(topBPanel);
    middlePanel.add(receiptInfo, BorderLayout.CENTER);
    bottomPanel.add(returnItem, BorderLayout.CENTER);
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(middlePanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    window.add(mainPanel);
  }

  /**
   * Make the window visible, set the title and size of the window, set it's
   * location on the screen and the action of the "X" button.
   */
  private void displayWindow() {
    window.setSize(400, 440);
    window.setTitle("Return Item");
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
          receiptInfo.setFont(new Font("Courier New", Font.PLAIN, 12));
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
    findButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          amounts = register.removeItemFromReceipt(receiptNumber.getText());
          printReceipt();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, "Receipt is invalid");
        }
      }
    });

    returnItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String id = listModel.getElementAt(
                receiptInfo.getSelectedIndex()).toString();
        int itemId = Integer.parseInt(id.substring(0, 3));
        register.removeItem(itemId);
        register.endSale();
        JOptionPane.showMessageDialog(null, 
                        "You have successfully returned your item");
        register.makePayment(amounts[0], true);

        window.dispose();
        fromWindow.setVisible(true);
      }
    });
  }

  /**
   * Prints the receipt items
   */
  public void printReceipt() {
    int counter = 0;
    listModel.clear();

    for (String line : register.getReceipt()) {
      if (counter > 2) {
        listModel.addElement(line);
      }
      counter += 1;
    }
    if (listModel.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Receipt is invalid");
    }
  }
}