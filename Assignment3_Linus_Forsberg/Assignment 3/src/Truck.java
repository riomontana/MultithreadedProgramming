import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Class describing a Truck consuming FoodItems from buffer
 * Implements Runnable interface
 *
 * @author Linus Forsberg
 */
public class Truck implements Runnable {

    private int itemsTotal = 0; // total items truck is holding
    private double weightTotal = 0; // total weight of items in truck
    private double volumeTotal = 0; // total volume of all items in truck
    private int maxItems; // maximum items a truck can handle
    private double maxWeight; // maximum loading weight a truck can handle
    private double maxVolume; // maximum volume a truck can handle
    private boolean running; // deciding if thread should be running or not
    private Storage storage; // reference to Storage
    private JPanel list; // reference to list in gui
    private JLabel lblStatus; // reference to status label in gui
    private FoodItem foodItem; // reference to fooditem
    private Random random = new Random(); // create instance of randomizer
    private DecimalFormat df = new DecimalFormat("#.##"); // format weight and volume
    private boolean continueBool = false; // deciding if trucks should continue consuming

    /**
     * Constructor
     *
     * @param list      list holding consumed FoodItems
     * @param lblStatus label holding status for consumer
     * @param storage   storageQueue (buffer)
     * @param maxItems  how many items a truck can hold
     * @param maxVolume maximum loading volume of truck
     * @param maxWeight maximum weight the truck can handle
     */
    public Truck(JPanel list, JLabel lblStatus, Storage storage, int maxItems,
                 double maxVolume, double maxWeight) {
        this.maxItems = maxItems;
        this.maxVolume = maxVolume;
        this.maxWeight = maxWeight;
        running = true;
        this.list = list;
        this.lblStatus = lblStatus;
        this.storage = storage;
    }

    /**
     * Set boolean deciding if trucks should keep consuming or stop
     *
     * @param continueBool passed from checkbox in gui
     */
    public void setContinueBool(boolean continueBool) {
        this.continueBool = continueBool;
    }

    /**
     * Stop thread from running
     */
    public void stopConsuming() {
        running = false;
    }

    /**
     * Run method consumes FoodItems from StorageQueue
     * if there is space in the Truck and FoodItems in Storage waiting to be consumed
     */
    @Override
    public void run() {
        try {
            while (running) {
                updateConsumerStatus("Storage empty!");
                foodItem = storage.remove(); // dequeue from storage buffer

                // consume objects until truck is filled
                if ((itemsTotal < maxItems) && (weightTotal < maxWeight) && (volumeTotal < maxVolume)) {
                    updateConsumerStatus("Consuming...");
                    itemsTotal++; // increment amount of objects loaded in truck
                    weightTotal += foodItem.getWeight(); // add weight of current FoodItem to total weight
                    volumeTotal += foodItem.getVolume(); // add volume if current FoodItem to total volume
                    printItemToList(foodItem.getName()); // print name of fooditem to consumer list in gui
                    Thread.sleep(random.nextInt(1000) + 500); // random sleep for thread

                } else { // truck is full
                    updateConsumerStatus("Truck is full!"); // update info in gui
                    Thread.sleep(1000);
                    if (continueBool) { // if truck should continue after it is filled
                        itemsTotal = 0; // reset amount of items
                        weightTotal = 0; // reset total weight
                        volumeTotal = 0; // reset total volume
                        updateConsumerStatus("Awaiting new Truck.."); // update info in gui
                        Thread.sleep(3000);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints consumed FoodItems to list in gui
     *
     * @param itemName Name of FoodItem
     */
    public void printItemToList(String itemName) {
        list.add(new JLabel(itemName));
        list.revalidate();
        list.repaint();
    }

    /**
     * Change status in gui for consumer threads.
     *
     * @param status Status of consumer
     */
    public void updateConsumerStatus(String status) {
        lblStatus.setText("status: " + status);
        if (status.equals("Awaiting new Truck")) {
            list.removeAll();
            list.revalidate();
            list.repaint();
        }
    }
}
