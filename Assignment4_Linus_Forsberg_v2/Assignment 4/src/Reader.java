import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class for reading strings from buffer
 * Extends Observable, implements Runnable
 *
 * @author Linus Forsberg
 */
public class Reader extends Observable implements Runnable {

    private BoundedBuffer buffer;
    private int count;
    private List<String> stringList = new ArrayList<>();

    /**
     * Constructor
     *
     * @param buffer
     * @param nbrOfStrings
     */
    public Reader(BoundedBuffer buffer, int nbrOfStrings) {
        this.buffer = buffer;
        this.count = nbrOfStrings;
    }

    /**
     * Run method reads strings from buffer and adds to stringlist
     * Notifies observing GUI when finished
     */
    @Override
   public void run() {
        try {
            for (int i = 0; i <= count; i++) {
                if (i == count) {
                    setChanged();
                    notifyObservers(stringList);
                }
                stringList.add(buffer.readData());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
