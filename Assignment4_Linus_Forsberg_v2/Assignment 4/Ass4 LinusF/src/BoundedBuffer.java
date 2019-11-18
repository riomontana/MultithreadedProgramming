import javax.swing.*;
import java.util.Observable;

/**
 * Class for Synchronized buffer.
 * Writer puts strings in a circular buffer, modifies it and returns to reader.
 *
 * @author Linus Forsberg
 */
public class BoundedBuffer extends Observable {

    private String[] strArray; // string buffer array
    private BufferStatus[] status; // array of BufferStatus objects, one for each element in buffer
    private int max; // elements in buffer
    private int writePos; // position pointers for each thread
    private int readPos; // -::-
    private int findPos; // -::-
    private String findStr; // string to search for
    private String replaceStr; // replace string
    private int nbrReplacements; // replacement counter
    private JLabel lblChanges; // number of strings to be modified

    /**
     * Constructor
     *
     * @param elements   amount of elements in buffer
     * @param findStr    // string to search for
     * @param replaceStr // string to replace
     */
    public BoundedBuffer(int elements, JLabel lblChanges, String findStr, String replaceStr) {
        this.max = elements;
        this.lblChanges = lblChanges;
        this.findStr = findStr;
        this.replaceStr = replaceStr;
        strArray = new String[max]; // create string array acting as circular buffer holding a fixed amount of elements
        initStatusArray(); // initialize status array
    }

    /**
     * Create new array of status enums.
     */
    public void initStatusArray() {
        status = new BufferStatus[max]; // initialize array holding status enums
        for (int i = 0; i < max; i++) {
            status[i] = BufferStatus.EMPTY; // set all elements in array to empty
        }
    }

    /**
     * Synchronized method reading strings from circular buffer
     *
     * @return string from buffer
     */
    synchronized public String readData() throws InterruptedException {
        String str; // temporary string
        while (status[readPos] != BufferStatus.CHECKED) { // thread waits while status is not "checked"
            wait();
        }
        str = strArray[readPos]; // read from correct position in buffer
        status[readPos] = BufferStatus.EMPTY; // set status to empty
        readPos = (readPos + 1) % max; // change position to 0 when end of buffer is reached
        notifyAll(); // notify waiting threads

        return str; // return string read from buffer
    }

    /**
     * Synchronized method writes strings to circular buffer
     *
     * @param str string to be written to buffer
     */
    synchronized public void writeData(String str) throws InterruptedException {
        while (status[writePos] != BufferStatus.EMPTY) { // thread waits while status is not "empty"
            wait();
        }
        strArray[writePos] = str; // write to correct position in buffer
        checkNbrReplacements(str); // check if current string has substring to replace or not
        status[writePos] = BufferStatus.NEW; // set status to "new"
        writePos = (writePos + 1) % max; // change position to 0 when end of buffer is reached
        notifyAll(); // notify waiting threads
    }

    /**
     * Synchronized method checks if string in buffer should be modified with replacement string or not.
     */
    synchronized public void modify() throws InterruptedException {
        String str; // temporary string
        while (status[findPos] != BufferStatus.NEW) { // thread waits while status is not "new"
            wait();
        }
        str = strArray[findPos]; // set temp string
        // if string to be modified is not empty and temporary string contains string to be modified
        if (!findStr.isEmpty() && str.toLowerCase().contains(findStr.toLowerCase())) {
            // replace original string with modified string
            strArray[findPos] = strArray[findPos].replaceAll(findStr, replaceStr);
        }
        status[findPos] = BufferStatus.CHECKED; // change status to "checked"
        findPos = (findPos + 1) % max; // change position to 0 when end of buffer is reached
        notifyAll(); // notify waiting threads
    }

    /**
     * Increments counter for number of replacements and updates label in GUI
     * Splits line of string from buffer to words for checking if string should be replaced or not
     * Line needs to be split so counter increments correctly if a word occurs more than once in a line
     *
     * @param line String line from buffer
     */
    private void checkNbrReplacements(String line) {
        // split line of string to words using "space" as splitter, putting them in a temporary string array
        String[] splitStrArray = line.split(" ");
        for (String str : splitStrArray) // for all strings in string array
            // if string to be found is not empty and if string element in temp array contains string to be found
            if (!findStr.isEmpty() && str.toLowerCase().contains(findStr.toLowerCase())) {
                nbrReplacements++; // increment counter
                lblChanges.setText("No. of Replacements: " + nbrReplacements); // update label in GUI
            }
    }

    /**
     * Simple getter for counter of number of replacements
     *
     * @return int counter for number of replacements
     */
    public int getNbrReplacements() {
        return nbrReplacements;
    }
}
