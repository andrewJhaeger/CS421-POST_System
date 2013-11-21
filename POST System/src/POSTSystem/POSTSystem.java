// Homework 6 POST System
// CS421, Fall 2013
// Due date: 11/21/2013
// Completed date: 11/21/2013
// Name: Ryan Bickham, Nicholas Borushko, Ryan Gillett, Andrew Haeger
// Instructor: Il-Hyung Cho
// Program description:  This program implements the POST system described in
//  class.  It initially loads to a start screen that gives the user the choice
//  to start a new sale, return an item, or get a daily report.  
//
//  - Clicking new sale will open a new GUI and will allow the user to start a 
//  sale by entering items and quantities.  At the end of the sale, it will 
//  accept cash payment and determine the change.
//
//  - Clicking return item will open a new GUI and allow the user to enter the 
//  receipt number.  It will search for the receipt and then load the receipt 
//  info info a list.  The user can then select the item they want to return.
//
//  - Clicking daily report will open a new GUI and allow the user to enter a
//  date.  The program will then gather all receipts created during that day 
//  and display a report of the items that were purchased and the revenue earned
//
package POSTSystem;

/**
 * Main class.
 */
public class POSTSystem {

  /**
   * Entry point for the POST system. Creates a new Store object and initializes
   * the initial display GUI.
   *
   * @param args
   */
  public static void main(String[] args) {
    Store store = new Store();
    Register register = store.getRegister();
    InitialDisplay frame = new InitialDisplay(register);
  }
}
