import java.util.List;

/**
 * Class writing strings to buffer
 * Implements Runnable
 *
 * @author Linus Forsberg
 */
public class Writer implements Runnable {

    private BoundedBuffer buffer;
    private List<String> textToWrite;

    /**
     * Constructor
     *
     * @param buffer
     * @param textIn
     */
    public Writer(BoundedBuffer buffer, List<String> textIn) {
        this.buffer = buffer;
        this.textToWrite = textIn;
    }

    /**
     * Run method writes strings to buffer
     */
    @Override
    public void run() {
        try {
            for (String s : textToWrite) {
                buffer.writeData(s);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

