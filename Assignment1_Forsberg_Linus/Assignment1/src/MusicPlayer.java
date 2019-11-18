import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

/**
 * Class for playing music from file
 * @author Linus Forsberg
 */
public class MusicPlayer {
    private Player player;
    private String filename;

    /**
     * constructor receives filepath of an audio file
     * @param filename
     */
    public MusicPlayer(String filename) {
        this.filename = filename;
    }

    /**
     * stop playing file
     */
    public void stop() {
        if (player != null) player.close();
    }

    /**
     * play file to sound card
     */
    public void play() {
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        /**
         * run in new thread to play in background
         */
        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();

    }
}