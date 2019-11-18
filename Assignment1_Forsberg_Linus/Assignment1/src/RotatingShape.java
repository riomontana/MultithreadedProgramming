import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Class describing a Rectangle that rotates using a thread
 * extends JComponent and implements Runnable
 * @author Linus Forsberg
 */
public class RotatingShape extends JComponent implements Runnable {

    private boolean isRunning; // boolean decides if loop in thread should run or not
    private Rectangle2D rectangle; // rectangle
    private int degree = 0; // variable for rotating degree, increments in run method
    private int x = 25; // location for rectangles placement on x-axis
    private int y = 40; // location for rectangles placement on y-axis
    private int width = 100; // width of rectangle
    private int height = 70; // height of rectangle
    private GUIAssignment1 guiAssignment1; // reference to gui


    /**
     * constructor receiving reference to gui
     * @param guiAssignment1
     */
    public RotatingShape(GUIAssignment1 guiAssignment1) {
        this.guiAssignment1 = guiAssignment1;
    }

    /**
     * inherited method for painting graphic object
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // call super class
        Graphics2D g2d = (Graphics2D) g; // casting to 2d graphic object
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // makes smoother graphics
        rectangle = new Rectangle2D.Float(x, y, width, height); // create new rectangle
        AffineTransform transform = g2d.getTransform(); // needed for object rotation
        g2d.rotate(Math.toRadians(degree), rectangle.getX() + width/2, rectangle.getY() + height/2); // rotates object
        g2d.draw(rectangle); // paints object
        g2d.setTransform(transform); // overwrites the Transform in the Graphics2D context.
    }

    /**
     * receives boolean isRunning and sets value accordingly
     * thus ending while-loop in run-method if set to false
     * @param isRunning
     */
    public void stopThread(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * runs thread as long as isRunning = true.
     * repaints component with updated rotation and sends it to gui calling the addShape method
     * increments rotation degree with 2 for every 50 milliseconds
     */
    @Override
    public void run() {
        isRunning = true;
        while(isRunning) {
            repaint();
            guiAssignment1.addShape(this);
            degree = degree+2;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
