package POSTSystem;

import java.util.*;
import java.text.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class defines the new sale GUI to display to the user.
 */
public class NewSaleDisplay {

  private Register register;
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
  private JTextArea info = new JTextArea();
  private JScrollPane saleInfo = new JScrollPane(info,
          JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  private JLabel itemLabel = new JLabel("Item Code: ");
  private JTextField itemCode = new JTextField();
  private JLabel quantityLabel = new JLabel("   Quantity: ");
  private JTextField quantity = new JTextField();
  private JButton addItem = new JButton("Add Item");
  private JButton completeSale = new JButton("Complete Sale");
  private JButton clearItem = new JButton("Clear Item");
  private JButton cancelSale = new JButton("Cancel Sale");
  private JFrame fromWindow;

  /**
   * Start the process of creating the new sale GUI.
   *
   * @param from
   * @param inRegister
   */
  public NewSaleDisplay(JFrame from, Register inRegister) {
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

  /**
   * Add the different elements to their respective panels and then add the
   * different panels to the main panel.
   */
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
    bottomPanel.add(completeSale);
    bottomPanel.add(clearItem);
    bottomPanel.add(cancelSale);

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
    window.setSize(400, 500);
    window.setTitle("New Sale");
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

        register.makeNewSale(-1);
        printReceipt();

        Calendar calendar = register.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        date.setText("Date: " + dateFormat.format(calendar.getTime()));
        time.setText("Time: " + timeFormat.format(calendar.getTime()));
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
    addItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          register.enterItem(Integer.parseInt(itemCode.getText()),
                  Integer.parseInt(quantity.getText()));
          printReceipt();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, "Invalid Item!");
        }
      }
    });

    completeSale.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (completeSale.getText().equals("Complete Sale")) {
          register.endSale();

          itemLabel.setText("Tendered Amount: ");
          quantityLabel.setText("");

          quantity.setEditable(false);
          addItem.setEnabled(false);
          clearItem.setEnabled(false);

          completeSale.setText("Make Payment");
          itemCode.setText("");
          quantity.setText("");
          printReceipt();
        } else if (completeSale.getText().equals("Make Payment")) {
          try {
            Money payment = new Money(itemCode.getText());
            register.makePayment(payment);
            cancelSale.setEnabled(false);
            completeSale.setText("Close");
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Payment is invalid!");
            ex.printStackTrace(System.out);
          }
          printReceipt();
        } else {
          window.dispose();
          fromWindow.setVisible(true);
        }
      }
    });

    clearItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          register.removeItem(Integer.parseInt(itemCode.getText()));
          printReceipt();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null,
                  "Item to remove is invalid or has not been "
                  + "added to this sale.");
        }
      }
    });

    cancelSale.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        window.dispose();
        fromWindow.setVisible(true);
      }
    });
  }

  public void printReceipt() {
    info.setText("");
    for (String line : register.getReceipt()) {
      info.append(line);
      info.append("\n");
    }
  }
}
