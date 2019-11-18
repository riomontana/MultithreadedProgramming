import javax.swing.*;
import java.util.Random;

/**
 * Class describing a factory producing FoodItems. Acts as a producer
 * Implements Runnable interface
 *
 * @author Linus Forsberg
 */
public class Factory implements Runnable {

    private Random random = new Random(); // random generator
    private FoodItem[] foodBuffer; // array of food items
    private boolean running; // should thread be running or not
    private Storage storage; // reference to Storage
    private JLabel lblStatus; // reference to status label in gui

    /**
     * Constructor
     *
     * @param lblStatus label holding producer status
     * @param storage   queue storing FoodItems
     */
    public Factory(JLabel lblStatus, Storage storage) {
        this.lblStatus = lblStatus;
        this.storage = storage;
        running = true;
        initFoodItems(); // call on method initializing array holding FoodItems
    }

    /**
     * Stop thread from running
     */
    public void stopProducing() {
        running = false;
    }

    /**
     * Run method puts FoodItems in storage queue
     */
    @Override
    public void run() {
        try {
            while (running) {
                updateProducerStatus("Storage is full!");
                storage.add(foodBuffer[random.nextInt(20)]); // add random FoodItem to queue
                updateProducerStatus("Producing...");
                Thread.sleep(random.nextInt(1000) + 500); // random thread sleep
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change status for producer threads in gui
     *
     * @param status status of producer
     */
    public void updateProducerStatus(String status) {
        lblStatus.setText("Status: " + status);
    }

    /**
     * Initialize FoodItem array
     */
    public void initFoodItems() {
        foodBuffer = new FoodItem[20];
        foodBuffer[0] = new FoodItem("Milk", 1.1, 0.5);
        foodBuffer[1] = new FoodItem("Cream", 0.6, 0.1);
        foodBuffer[2] = new FoodItem("Yoghurt", 1.1, 0.5);
        foodBuffer[3] = new FoodItem("Butter", 2.25, 0.65);
        foodBuffer[4] = new FoodItem("Flour", 3.4, 1.3);
        foodBuffer[5] = new FoodItem("Sugar", 2.5, 1);
        foodBuffer[6] = new FoodItem("Salt", 0.7, 0.7);
        foodBuffer[7] = new FoodItem("Almonds", 1, 0.8);
        foodBuffer[8] = new FoodItem("Bread", 0.6, 1.5);
        foodBuffer[9] = new FoodItem("Donuts", 0.4, 0.7);
        foodBuffer[10] = new FoodItem("Jam", 0.4, 0.5);
        foodBuffer[11] = new FoodItem("Ham", 0.9, 0.9);
        foodBuffer[12] = new FoodItem("Chicken", 1.5, 1.7);
        foodBuffer[13] = new FoodItem("Salad", 0.2, 0.3);
        foodBuffer[14] = new FoodItem("Oranges", 1.8, 2);
        foodBuffer[15] = new FoodItem("Apples", 1.5, 1.8);
        foodBuffer[16] = new FoodItem("Pears", 1.5, 1.9);
        foodBuffer[17] = new FoodItem("Soda", 1.2, 0.9);
        foodBuffer[18] = new FoodItem("Beer", 1.1, 0.8);
        foodBuffer[19] = new FoodItem("Hot Dogs", 0.6, 0.7);
    }
}