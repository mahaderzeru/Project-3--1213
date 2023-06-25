import java.util.*;
import java.io.*; 
import java.util.Scanner;

/**
 *
 * ITSC 1213 
 * University of North Carolina at Charlotte
 * @author Mary Zeru
 */

public class FastFoodKitchenDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
            //FastFoodKitchen object
            FastFoodKitchen kitchen = new FastFoodKitchen();
            //Scanner object
            Scanner sc = new Scanner(System.in);

            while (kitchen.getNumOrdersPending() != 0) {
                // menu to see what the user wants to do
                System.out.println("Please select from the following menu of options, by typing a number:");
                System.out.println("\t 1. Order food");
                System.out.println("\t 2. Cancel last order");
                System.out.println("\t 3. Show number of orders currently pending");
                System.out.println("\t 4. Complete order");
                System.out.println("\t 5. Check on order");
                System.out.println("\t 6. Cancel order");
                System.out.println("\t 7. Exit");

                int num = sc.nextInt();
                    switch (num) {
                        case 1: //Get user order input 
                            //try catch block
                            try{
                                System.out.println("How many hamburgers do you want?");
                                int ham = sc.nextInt();
                                    if (!(ham >= 0) && !(ham <= 0)) {
                                        System.out.println("Please put the correct input. ");
                                        ham = sc.nextInt(); 
                                    } 
                                    if (ham < 0) {
                                        System.out.println("Invalid number of hamburgers. Try again!");
                                        System.out.println("How many hamburgers do you want?");
                                        ham = sc.nextInt();
                                    }
                                    
                                System.out.println("How many cheeseburgers do you want?");
                                int cheese = sc.nextInt();
                                    if (cheese < 0) {
                                        System.out.println("Invalid number of cheeseburgers. Try again!");
                                        System.out.println("How many cheeseburgers do you want?");
                                        cheese = sc.nextInt();
                                    }
                                
                                System.out.println("How many veggieburgers do you want?");
                                int veggie = sc.nextInt();
                                    if (veggie < 0) {
                                        System.out.println("Invalid number of veggieburgers. Try again!");
                                        System.out.println("How many veggieburgers do you want?");
                                        veggie = sc.nextInt();
                                    }
                                System.out.println("How many sodas do you want?");
                                int sodas = sc.nextInt();
                                    if (sodas < 0) {
                                        System.out.println("Invalid number of sodas. Try again!");
                                        System.out.println("How many sodas do you want?");
                                        sodas = sc.nextInt();
                                    }
                                System.out.println("Is your order to go? (Y/N)");
                                String letter = sc.nextLine();
                                sc.nextLine();
                                boolean TOGO = false;

                                if (letter != "Y" && letter != "y" && letter != "N" && letter != "n") {
                                    System.out.println("Please put the correct input. ");
                                    letter = sc.nextLine(); 
                                }
                                if (letter == "Y" || letter == "y") {
                                    TOGO = true;
                                }

                                int orderNum = kitchen.addOrder(ham, cheese, veggie, sodas, TOGO);
                                System.out.println("Thank you. Your order number is " + orderNum);
                                System.out.println();
                                break;
    
                            } catch(InputMismatchException ex) { // Prints the error message passed by throw statement
                                System.out.println("Invalid type entered. Please try again!");
                                sc.nextLine();
                                
                            } catch (Exception e) {
                                System.out.println("Exception Error. Please try again!");
                                sc.nextLine();
                            }
                        case 2: //Cancel previous order
                            boolean ready = kitchen.cancelLastOrder(); 
                            if (ready) {
                                System.out.println("Thank you. The last order has been canceled");
                            } else {
                                System.out.println("Sorry. There are no orders to cancel.");
                            }
                            System.out.println();
                            break;
                        case 3: //Tell user number of pending orders 
                            System.out.println("There are " + kitchen.getNumOrdersPending() + " pending orders");
                            break;
                        case 4: //Get input for number of orders to be completed from user and complete order
                            //try catch block
                            try{
                                System.out.println("Enter order number to complete: ");
                                int order = sc.nextInt();
                                    if (order < 0) {
                                        System.out.println("Invalid order number. Try again!");
                                    }
                                kitchen.completeSpecificOrder(order);
                                System.out.println("Your order is ready. Thank you!");
                                break;

                            } catch (Exception e) {
                                System.out.println("Exception Error. Please try again!");
                            }
                        case 5: //Get order number from user and check on order 
                            //try catch block
                            try{ 
                                System.out.println("What is your order number?");
                                int order = sc.nextInt();
                                if (order < 0) {
                                    System.out.println("Invalid order number. Try again!");
                                }
                                ready = kitchen.isOrderDone(order);
                                if (ready) {
                                    System.out.println("Sorry, no order with this number was found.");
                                } else {
                                    System.out.println("No, it's not ready, but it should be up soon. Sorry for the wait.");
                                }
                                System.out.println();
                                break;

                            }catch (Exception e) {
                                System.out.println("Exception Error. Please try again!");
                            }
                        case 6: //Get order number from user and cancel order
                            //try catch block
                            try{
                                System.out.println("What is your order number?");
                                int order = sc.nextInt();
                                if (order < 0) {
                                    System.out.println("Invalid order number. Try again!");
                                }
                                boolean cancel = kitchen.cancelOrder(order);
                                if (cancel) {
                                    System.out.println("Your order has been successfully cancelled ");
                                    break; 
                                } else {
                                    System.out.println("Sorry, we can’t find your order number in the system");
                                }
                                System.out.println();
                                break;
                            } catch (Exception e) {
                                System.out.println("Exception Error. Please try again!");
                            }
                        case 7:
                            //print end of day report (part B)
                            kitchen.saveOrders(); //creates another BurgerOrder csv file automatically
                            kitchen.printReport(); //Creates a txt file report automatically
                            System.out.println("End of Day report generated in report.txt"); 
                            System.out.println("Thanks for visiting!"); 
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Sorry, but you need to enter a 1, 2, 3, 4, 5, 6, or a 7");

                    } //end switch
                
            } //end while loop 

    }// end main
    

}// end class

