import java.awt.*;

/**
 * Controller class
 * handles different tasks for application
 *
 * @author Linus Forsberg
 */
public class Controller {

    private GUIMutex gui;
    private Writer writer;
    private Reader reader;
    private CharacterBuffer buffer;
    private String textToBuffer;
    private String textFromBuffer;
    private boolean readerThreadRunning;
    private boolean writerThreadRunning;

    /**
     * Constructor gets reference to GUI, creates instances of Writer, Reader and CharacterBuffer
     *
     * @param gui
     */
    public Controller(GUIMutex gui) {
        this.gui = gui;
        buffer = new CharacterBuffer();
        writer = new Writer(this, buffer);
        reader = new Reader(this, buffer);
    }

    /**
     * Set string that is written to buffer
     *
     * @param textToBuffer
     */
    public void setTextToBuffer(String textToBuffer) {
        this.textToBuffer = textToBuffer;
    }

    /**
     * Get string that is written to buffer
     *
     * @return textToBuffer
     */
    public String getTextToBuffer() {
        return textToBuffer;
    }

    /**
     * Set text that is received from buffer
     *
     * @param textFromBuffer
     */
    public void setTextFromBuffer(String textFromBuffer) {
        this.textFromBuffer = textFromBuffer;
    }

    /**
     * Start writer thread, boolean decides if buffer is synchronized or not
     *
     * @param isSynchronized
     */
    public void startWriterThread(boolean isSynchronized) {
        writer.startThread(isSynchronized);
    }

    /**
     * Start reader thread, boolean decides if buffer is synchronized or not
     *
     * @param isSynchronized
     */
    public void startReaderThread(boolean isSynchronized) {
        reader.startThread(isSynchronized);
    }

    /**
     * Add character to GUI writer list
     *
     * @param buffer written character
     */
    public void addToGUIWriterList(char buffer) {
        gui.addToListW(buffer);
    }

    /**
     * Add character to GUI reader list
     *
     * @param buffer received character
     */
    public void addToGUIReaderList(char buffer) {
        gui.addToListR(buffer);
    }

    /**
     * Update GUI results if both reader-thread and writer-thread has finished
     * Compares sent string with received string and passes boolean to GUI
     */
    public void updateGUIResults() {
        if (!readerThreadRunning && !writerThreadRunning) {
            gui.addToLabelTransmitted(textToBuffer);
            gui.addToLabelReceived(textFromBuffer);

            if (textToBuffer.equals(textFromBuffer))
                gui.updateColoredRectangle(Color.GREEN, "SUCCESS!");

            else gui.updateColoredRectangle(Color.RED, "FAIL!");
        }
    }

    /**
     * Receives boolean when reader thread is finished
     *
     * @param running
     */
    public void readerThreadRunning(boolean running) {
        this.readerThreadRunning = running;
        updateGUIResults();
    }

    /**
     * Receives boolean when writer thread is finished
     *
     * @param running
     */
    public void writerThreadRunning(boolean running) {
        this.writerThreadRunning = running;
        updateGUIResults();
    }
}
