import java.util.Random;

/**
 * Writer class uses a thread to write characters to buffer, one at a time, until end of string is reached.
 * Implements Runnable interface.
 *
 * @author Linus Forsberg
 */

public class Writer implements Runnable {

    private Controller controller;
    private CharacterBuffer buffer;
    private String textToBuffer;
    private boolean isSynchronized;
    private volatile boolean threadIsRunning;


    /**
     * Constructor gets reference to Controller and CharacterBuffer
     *
     * @param controller
     * @param buffer
     */
    public Writer(Controller controller, CharacterBuffer buffer) {
        this.controller = controller;
        this.buffer = buffer;
    }

    /**
     * Starts new thread for writing chars. Gets passed boolean value
     *
     * @param isSynchronized
     */
    public void startThread(boolean isSynchronized) {
        this.isSynchronized = isSynchronized;
        threadIsRunning = true;
        new Thread(this).start();
    }

    /**
     * Writes character to buffer and print it to GUI
     */
    @Override
    public void run() {
        textToBuffer = controller.getTextToBuffer(); // Get string that will be written to buffer
        StringBuilder sentString = new StringBuilder(); // Create new string for view purpose in GUI
        char sentChar; // Sent character

        while(threadIsRunning) {

            for (int i = 0; i < textToBuffer.length(); i++) { // Loop til end of string
                sentChar = textToBuffer.charAt(i); // sentChar value is set to character at index i in string

                if (isSynchronized) // if synchronized
                    try {
                        buffer.setBufferSynchronized(sentChar); // write char to synchronized buffer
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                else {
                    buffer.setBuffer(sentChar); // else write char to unsynchronized buffer
                }
                controller.addToGUIWriterList(sentChar); // add character to GUI
                sentString.append(sentChar); // add char to new string that is used for viewing purpose
                controller.setTextToBuffer(sentString.toString()); // update sent string to controller for viewing purpose in GUI
                try {
                    Thread.sleep(new Random().nextInt(200) + 50); // thread sleeps at random time between 50 - 250 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threadIsRunning = false;
        }
        controller.writerThreadRunning(false); // notify controller that thread has ended
    }
}
