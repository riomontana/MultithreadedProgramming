
/**
 * Class for modifying buffered strings
 * implements Runnable
 *
 * @author Linus Forsberg
 */
public class Modifier implements Runnable {

    private BoundedBuffer buffer; // reference to buffer
    private int count; // amount of replacement words

    /**
     * Constructor
     *
     * @param buffer reference to buffer
     */
    public Modifier(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Run method uses a thread to check for strings to modify
     */
    @Override
    public void run() {
        try {
            count = buffer.getNbrReplacements(); // get number of replacements
            while (count != 0) { // while there is strings to modify
                for (int i = 0; i < count; i++) {// looping counter
                    buffer.modify(); // call modify method in buffer
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
