
/**
 * CharacterBuffer buffers a single character
 *
 * @author Linus Forsberg
 */
public class CharacterBuffer {

    private char buffer;
    private char syncedBuffer;
    private boolean hasCharacter = false;

    /**
     * Put character in synchronized buffer
     *
     * @param toBuffer received char from writer
     * @throws InterruptedException
     */
    synchronized public void setBufferSynchronized(char toBuffer) throws InterruptedException {
        while (hasCharacter) { // if buffer is full
            wait(); // Release lock and wait until notified by reader thread
        }
        this.syncedBuffer = toBuffer; // Set buffer to received character
        hasCharacter = true; // Buffer is full
        notify(); // Notify reader thread
    }

    /**
     * Return character from synchronized buffer
     *
     * @return character to Reader
     * @throws InterruptedException
     */
    synchronized public char getBufferSynchronized() throws InterruptedException {
        char helpChar;

        while (!hasCharacter) {
            wait(); // Release lock and wait until notified by writer thread
        }
        hasCharacter = false; // Buffer is emptied
        helpChar = syncedBuffer;
        notify(); // Notify writer thread that buffer can be replaced with next char

        return helpChar; // Return buffer to reader
    }

    /**
     * Put character in asynchronous buffer
     *
     * @param toBuffer received char from writer
     */
    public void setBuffer(char toBuffer) {
        this.buffer = toBuffer;
    }

    /**
     * Return character from asynchronous buffer
     *
     * @return character to Reader
     */
    public char getBuffer() {
        return buffer;
    }
}
