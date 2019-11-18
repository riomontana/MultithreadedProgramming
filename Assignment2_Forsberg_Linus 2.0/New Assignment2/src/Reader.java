import java.util.Random;

/**
 * Reader class uses a thread to collect chars from CharacterBuffer
 * implements Runnable interface
 *
 * @author Linus Forsberg
 */

public class Reader implements Runnable {

    private Controller controller;
    private CharacterBuffer buffer;
    private boolean isSynchronized;
    private volatile boolean threadIsRunning;

    /**
     * Constructor gets reference to Controller and CharacterBuffer
     *
     * @param controller
     * @param buffer
     */
    public Reader(Controller controller, CharacterBuffer buffer) {
        this.controller = controller;
        this.buffer = buffer;
    }

    /**
     * Starts new thread for reading chars. Gets passed boolean value
     *
     * @param isSynchronized
     */
    public void startThread(boolean isSynchronized) {
        this.isSynchronized = isSynchronized;
        threadIsRunning = true;
        new Thread(this).start();
    }

    /**
     * Reads character from CharacterBuffer, print it to GUI and put it in a new String
     */
    @Override
    public void run() {
        String textToBuffer = controller.getTextToBuffer(); // Get string-length to decide when to stop for-loop
        StringBuilder receivedString = new StringBuilder(); // Build new string from received characters
        char receivedChar; // Char from buffer

        while(threadIsRunning) {

            for (int i = 0; i < textToBuffer.length(); i++) { // Loop til end of string
                try {
                    if (isSynchronized) { // if synchronized
                        receivedChar = buffer.getBufferSynchronized(); // Get char from synchronized buffer

                    } else { // If not synchronized
                        receivedChar = buffer.getBuffer(); // Get char from unsynced buffer
                    }
                    controller.addToGUIReaderList(receivedChar); // Add received char to GUI
                    receivedString.append(receivedChar); // Add char to new String
                    controller.setTextFromBuffer(receivedString.toString()); // Set received text in controller for comparison
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(new Random().nextInt(200) + 50); // Random sleep between 50 - 250 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threadIsRunning = false;
        }
        controller.readerThreadRunning(false); // Notify controller that thread has ended
    }
}
