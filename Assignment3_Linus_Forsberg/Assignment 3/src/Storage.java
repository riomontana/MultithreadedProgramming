import sun.misc.Queue;

import javax.swing.*;
import java.util.concurrent.Semaphore;

/**
 * Stores instances of FoodItem in a Queue.
 * using semaphores for mutual exclusion and synchronizing producing / consuming
 * extends Queue
 *
 * @author Linus Forsberg
 */

public class Storage extends Queue {

    private Queue storageQueue; // Queue stores FoodItems
    private FoodItem lastItem;
    private static final int MAX_CAPACITY = 50; // Maximum capacity of storage queue
    private static Semaphore semMutex = new Semaphore(1); // (mutex) controls buffer access providing mutual exclusion
    private static Semaphore semMin = new Semaphore(0); // prevents underflow providing synchronization, helping consumer/producer communication
    private static Semaphore semMax = new Semaphore(MAX_CAPACITY); // prevents overflow providing synchronization helping consumer/producer communication
    private JProgressBar bufferStatus; // reference to progressbar in gui

    /**
     * Constructor creates instance of Queue
     */
    public Storage(JProgressBar bufferStatus) {
        this.bufferStatus = bufferStatus;
        storageQueue = new Queue();
    }

    /**
     * Add FoodItem to storageQueue using semaphores for mutex and synchronization.
     *
     * @param item FoodItem placed in storage
     * @throws InterruptedException
     */
    public void add(FoodItem item) throws InterruptedException {
        semMax.acquire(); // acquire permit from semaphore
        semMutex.acquire(); // acquire permit from semaphore
        lastItem = item;
        storageQueue.enqueue(lastItem); // add FoodItem to queue
        semMin.release(); // release permit and return it to semaphore
        semMutex.release(); // release permit and return it to semaphore
        updateBufferStatus(semMin.availablePermits());
    }

    /**
     * Remove FoodItem from storageQueue using semaphores for mutex and synchronization
     *
     * @return FoodItem to be removed from storage
     * @throws InterruptedException
     */
    public FoodItem remove() throws InterruptedException {
        semMin.acquire(); // acquire permit from semaphore
        semMutex.acquire(); // acquire permit from semaphore
        lastItem = (FoodItem) storageQueue.dequeue(); // remove last FoodItem from storageQueue
        semMax.release(); // release permit and return it to semaphore
        semMutex.release(); // release permit and return it to semaphore
        updateBufferStatus(semMin.availablePermits());
        return lastItem; // return foodItem
    }

    /**
     * Update progressbar in gui for storage queue
     *
     * @param size size of queue
     */
    public void updateBufferStatus(int size) {
        bufferStatus.setValue(size * 2);
        bufferStatus.setStringPainted(true);
    }
}
