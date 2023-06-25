import java.io.*; 
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.PrintWriter; 
import java.io.IOException; 
import java.io.FileOutputStream; 

/**
 *
 * ITSC 1213 
 * University of North Carolina at Charlotte
 */

public class FastFoodKitchen {
    private ArrayList<BurgerOrder> orderList = new ArrayList(); //arrayList for orders
    private static int nextOrderNum = 1; //variable to track number of orders
    private ArrayList<BurgerOrder> completedOrders = new ArrayList(); //arrayList to store completed orders 

    /**
    *Constructor for FastFoodKitchen
    */
    FastFoodKitchen(){
        //call order loader method
        orderLoader();
    }

    /**
    * Save orders into burgerOrders2 file
    */
    public void saveOrders(){
            try { 
                File s = new File("burgerOrders2.csv");
                PrintWriter printer = new PrintWriter(s);
                //for each loop to print information into burgerOrders2
                for(BurgerOrder b: orderList){
                    printer.print(b.getNumHamburger()); 
                    printer.print(",");
                    printer.print(b.getNumCheeseburgers()); 
                    printer.print(",");
                    printer.print(b.getNumVeggieburgers()); 
                    printer.print(",");
                    printer.print(b.getNumSodas()); 
                    printer.print(",");
                    printer.print(b.isOrderToGo() + "\n"); 
                }
                printer.println();
                printer.close(); //close printWriter
            }catch (IOException ex) {
                //statement that will execute when exception occurs
                System.out.println("Error IOException. Please try again!");
            }
        }

    /**
    * Load orders from burgerOrders file
    */
    public void orderLoader(){
        //try catch block to read csv file 
        try {
            File f = new File("burgerOrders.csv"); 
            Scanner scnr = new Scanner(f);  
            BurgerOrder o = null; 
            scnr.nextLine();
            while (scnr.hasNext()) {
                String line = scnr.nextLine();
                String[] lineArray = line.split(",");
                    int numHamburgers = Integer.parseInt(lineArray[0]); 
                    int numCheeseburgers = Integer.parseInt(lineArray[1]); 
                    int numVeggieburgers = Integer.parseInt(lineArray[2]);
                    int numSodas = Integer.parseInt(lineArray[3]);
                    boolean orderToGo = Boolean.parseBoolean(lineArray[4]); 
                    int orderNum = Integer.parseInt(lineArray[5]);
                    //new burgerOrder object to store attributes
                    o = new BurgerOrder(numHamburgers, numCheeseburgers, numVeggieburgers, numSodas, orderToGo, orderNum); 
                    //add the object to the order list arrayList and increment nextOrderNum
                    orderList.add(o); 
                    //nextOrderNum++;  
                    incrementNextOrderNum();
            } 
        scnr.close(); //close scanner
        } catch (NumberFormatException w) {
            //statement that will execute when NumberFormatException occurs
            System.out.println("Caught NumberFormatException for burgerOrders.csv.");
        } catch (FileNotFoundException ex) {
            //statement that will execute when FileNotFoundException occurs
            System.out.println("Caught FileNotFoundException for burgerOrders.csv. Try again making sure the file name and path are correct.");
        } 
    }

    /**
     * Get the value of getNextOrderNum
     *
     * @return the value of getNextOrderNum
     */
    public static int getNextOrderNum() {
        return nextOrderNum;
    }
    /**
     * Method to increment nextOrderNum
     */
    private void incrementNextOrderNum() {
        nextOrderNum++;
    }
    
    /**
     * Add order to orderList and callout order
     *
     * @return the value of orderNum
     */
    public int addOrder(int ham, int cheese, int veggie, int soda, boolean toGo) {
        int orderNum = getNextOrderNum();
        orderList.add(new BurgerOrder(ham, cheese, veggie, soda, toGo, orderNum));
        incrementNextOrderNum();
        orderCallOut(orderList.get(orderList.size() - 1));
        return orderNum;
    }
    
    /**
     * Check if order is done
     *
     * @param orderID
     * @return true or false
     */

    public boolean isOrderDone(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cancel order
     *
     * @param orderID
     * @return true or false
     */
    public boolean cancelOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                orderList.remove(i);
                return true;
            }
        }
        return false;
    }
    /**
     * Get number of orders pending
     *
     * @return size of orderList
     */
    public int getNumOrdersPending() {
        return orderList.size();
    }

    /**
     * Cancel previous order
     *
     * @return true or false
     */
    public boolean cancelLastOrder() {

        if (!orderList.isEmpty()) { // same as if (orderList.size() > 0) 
            orderList.remove(orderList.size() - 1);
            return true;
        }

        return false;
    }

    /**
     * Call out each order
     *
     * @param order (BurgerOrder object)
     */
    private void orderCallOut(BurgerOrder order) {
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumHamburger() + " hamburgers");
        }
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumCheeseburgers() + " cheeseburgers");
        }
        if (order.getNumVeggieburgers() > 0) {
            System.out.println("You have " + order.getNumVeggieburgers() + " veggieburgers");
        }
        if (order.getNumSodas() > 0) {
            System.out.println("You have " + order.getNumSodas() + " sodas");
        }

    }

    /**
     * Complete a specific order
     *
     * @param orderID
     */
    public void completeSpecificOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                System.out.println("Order number " + orderID + " is done!");
                if (orderList.get(i).isOrderToGo()) {
                    orderCallOut(orderList.get(i));
                }
                completedOrders.add(new BurgerOrder(orderList.get(i).getNumHamburger(), orderList.get(i).getNumCheeseburgers(), orderList.get(i).getNumVeggieburgers(), orderList.get(i).getNumSodas(), orderList.get(i).isOrderToGo(), orderList.get(i).getOrderNum())); 
                BurgerOrder b = orderList.remove(i);
            }
        }
    }
    
    /**
     * Complete next order
     *
     */
    public void completeNextOrder() {
        int nextOrder = orderList.get(0).getOrderNum();
        completeSpecificOrder(nextOrder);
    }

    // Part 2

    /**
     * Get orderList
     *
     * @return orderList 
     */
    public ArrayList<BurgerOrder> getOrderList() {
        return orderList;
    }

    /**
     * Sort orderList
     *
     * @param whatWeAreLookingFor
     * @return j or -1
     */
    public int findOrderSeq(int whatWeAreLookingFor) {
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getOrderNum() == whatWeAreLookingFor) {
                return j;
            }
        }
        return -1;
    }

//    public int findOrderBin(int whatWeAreLookingFor) {
//        int left = 0;
//        int right = orderList.size() - 1;
//        while (left <= right) {
//            int middle = (left + right) / 2;
//            if (whatWeAreLookingFor < orderList.get(middle).getOrderNum()) {
//                right = middle - 1;
//            } else if (whatWeAreLookingFor > orderList.get(middle).getOrderNum()) {
//                left = middle + 1;
//            } else {
//                return middle;
//            }
//        }
//        return -1;
//    }



    /**
     * findOrderBin method
     *
     * @param orderID
     * @return middle or -1 
     */
  public int findOrderBin(int orderID){
        int left = 0;
        int right = orderList.size()-1;
        while (left <= right){
            int middle = ((left + right)/2);
            if (orderID < orderList.get(middle).getOrderNum()){
                right = middle-1;
            }
            else if(orderID > orderList.get(middle).getOrderNum()){
                left = middle +1;
            }
            else{
                return middle;
            }
        }
        return -1;
        
    }

    /**
     * selectionSort method
     *
     */
    public void selectionSort(){
        for (int i = 0; i< orderList.size()-1; i++){
            int minIndex = i;
            for (int k = i+1; k < orderList.size(); k++){
                if (orderList.get(minIndex).getTotalBurgers() > orderList.get(k).getTotalBurgers()){
                    minIndex = k;
                }
            }
            BurgerOrder temp = orderList.get(i);
            orderList.set(i , orderList.get(minIndex));
            orderList.set(minIndex, temp);
        }
    }

    /**
     * insertionSort method
     *
     */
    public void insertionSort() {
        for (int j = 1; j < orderList.size(); j++) {
            BurgerOrder temp = orderList.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.getTotalBurgers() < orderList.get(possibleIndex - 1).getTotalBurgers()) {
                orderList.set(possibleIndex, orderList.get(possibleIndex - 1));
                possibleIndex--;
            }
            orderList.set(possibleIndex, temp);
        }
    }
    
//    public void selectionSort() { //weird method!
//
//        for (int j = 0; j < orderList.size() - 1; j++) {
//            int minIndex = j;
//            for (int k = j + 1; k < orderList.size(); k++) {
//
//                 if (orderList.get(minIndex).getTotalBurgers() > orderList.get(j).getTotalBurgers()){
//                    minIndex = k;
//                }
//            }
//            BurgerOrder temp = orderList.get(j);
//            orderList.set(j, orderList.get(minIndex));
//            orderList.set(minIndex, temp);
//
//        }
//    }



    /**
     * Generate end of day report
     *
     */
    public void printReport(){
        //try catch block
        try{
            //objects
            File report = new File("Report.txt"); 
            PrintWriter printer = new PrintWriter(report);
            printer.println("*****************");
            printer.println("End of Day Report");
            printer.println("# of hamburger orders: " + nextOrderNum); 
            printer.println("# of cheese burger orders: " + nextOrderNum); 
            printer.println("# of veggie burger orders: " + nextOrderNum); 
            printer.println("# of soda orders: " + nextOrderNum);
            printer.println("\nCompleted orders: ");

            //for loop to print completed orders
            for(BurgerOrder a: completedOrders){
                    printer.println("\tOrder #" + a.getOrderNum() + ": ");
                    printer.print("\t" + a.getNumHamburger() + " hamburgers, ");
                    printer.print(a.getNumCheeseburgers() + " cheeseburgers, ");
                    printer.print(a.getNumVeggieburgers() + " veggieburgers, "); 
                    printer.print(a.getNumSodas() + " sodas,"); 
                    if (a.isOrderToGo() == true){
                        printer.print(" to go\n"); 
                    } else if (a.isOrderToGo() == false){
                        printer.print(", dine in\n"); 
                    }
                    printer.println();
                }
            
            printer.println("\nIncomplete orders: \n");

            //for loop to print incomplete orders
            for(BurgerOrder b: orderList){
                    printer.println("\tOrder #" + b.getOrderNum() + ": ");
                    printer.print("\t" + b.getNumHamburger() + " hamburgers, ");
                    printer.print(b.getNumCheeseburgers() + " cheeseburgers, ");
                    printer.print(b.getNumVeggieburgers() + " veggieburgers, "); 
                    printer.print(b.getNumSodas() + " sodas,"); 
                    if (b.isOrderToGo() == true){
                        printer.print(" to go\n"); 
                    } else if (b.isOrderToGo() == false){
                        printer.print(" dine in\n"); 
                    }
                    printer.println();
                }
            printer.println();
            printer.close(); //close printWriter
        }catch (IOException ex) {             
            //statement that will execute when exception occurs
            System.out.println("Error IOException. please try again!");
        }

    }

}
