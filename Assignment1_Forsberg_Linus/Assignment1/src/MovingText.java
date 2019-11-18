import java.util.Random;

/**
 * Class handling logic for moving text label to new random locations
 * implements Runnable
 * @author Linus Forsberg
 */
public class MovingText implements Runnable {

    private GUIAssignment1 guiAssignment1; // reference to gui
    private Random random = new Random(); // random int generator
    private int width = 0; // variable for x-location of text label
    private int height = 0; // variable for y-location of text label
    private boolean threadIsRunning; // decides if thread is running or not

    /**
     * constructor recieves reference to gui and sets thread boolean to running
     * @param guiAssignment1
     */
    public MovingText(GUIAssignment1 guiAssignment1) {
        this.guiAssignment1 = guiAssignment1;
        threadIsRunning = true;
    }


    /**
     * runs thread and updates new text label location every 300 milliseconds
     * loops until boolean threadIsRunning is set to false
     */
    @Override
    public void run() {
        while(threadIsRunning) {
            width = random.nextInt(100);
            height = random.nextInt(100);
            guiAssignment1.setMovingTextLocation(width, height);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * receives boolean running and sets value accordingly
     * thus ending while-loop in run-method if set to false
     * @param running
     */
    public void stopThread(boolean running) {
        this.threadIsRunning = running;
    }
}
