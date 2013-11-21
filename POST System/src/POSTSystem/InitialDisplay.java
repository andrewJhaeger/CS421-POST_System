package POSTSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This class defines the initial GUI that will be displayed to the user.
 */
public class InitialDisplay {
    private Register register;

    private JFrame window = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel messagePanel = new JPanel();

    private JLabel title = new JLabel("POST System");
    private JButton newSale = new JButton("New Sale");
    private JButton returnItem = new JButton("Return Item");
    private JButton salesReport = new JButton("Daily Sales Report");
    private JTextArea message = new JTextArea("This program uses persistance!  " 
            + "You MUST have the receiptNumbers.txt file in this application's " 
            + "directory in order to use this program.");
    
    /**
     * Start the process of creating the initial GUI.
     * @param inRegister 
     */
    public InitialDisplay(Register inRegister) {
        register = inRegister;
        
        setLayouts();
        addElements();
        displayWindow();
        bindButtons();
    }
    
    /**
     * Set the layouts of the different panels on the form to properly
     * arrange the elements on the GUI.
     */
    private void setLayouts() {
        mainPanel.setLayout(new GridLayout(3, 1, 5, 5));
        titlePanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 20));
        messagePanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        Font label = new Font("Dialog", Font.BOLD, 24);
        title.setFont(label);
    }
    
    /**
     * Add the different elements to their respective panels and then
     * add the different panels to the main panel.
     */
    private void addElements() {
        titlePanel.add(title);
        buttonPanel.add(newSale);
        buttonPanel.add(returnItem);
        buttonPanel.add(salesReport);
        message.setLineWrap(true);
        message.setEditable(false);
        message.setBackground(new Color(0, 0, 0, 0));
        messagePanel.add(message, BorderLayout.CENTER);
        mainPanel.add(titlePanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(messagePanel);
        window.add(mainPanel);
    }
    
    /**
     * Make the window visible, set the title and size of the window,
     * set it's location on the screen and the action of the "X" button.
     */
    private void displayWindow() {
        window.setSize(500, 250);
        window.setTitle("POST System");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    /**
   * Bind action listeners to the buttons on the GUI.
   */
    private void bindButtons() {
        newSale.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
            NewSaleDisplay newSale = new NewSaleDisplay(window, register);
            window.setVisible(false);
          }
      });

        returnItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              ReturnItemDisplay returnItem 
                      = new ReturnItemDisplay(window, register);
              window.setVisible(false);
            }
        });

        salesReport.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
              SalesReportDisplay salesReport 
                      = new SalesReportDisplay(window, register);
              window.setVisible(false);
            }
        });
    }
}
