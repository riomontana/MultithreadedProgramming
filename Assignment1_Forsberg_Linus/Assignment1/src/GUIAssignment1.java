
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The GUI for assignment 1, DualThreads
 * @author Farid Naisan & Linus Forsberg
 */
public class GUIAssignment1 {
    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     */
    private JFrame frame;        // The Main window
    private JButton btnDisplay;    // Start thread moving display
    private JButton btnDStop;    // Stop moving display thread
    private JButton btnTriangle;// Start moving graphics thread
    private JButton btnTStop;    // Stop moving graphics thread
    private JButton btnOpen;    // Open audio file
    private JButton btnPlay;    // Start playing audio
    private JButton btnStop;    // Stop playing
    private JButton btnGo;        // Start game catch me
    private JPanel pnlMove;        // The panel to move display in
    private JPanel pnlRotate;    // The panel to move graphics in
    private JPanel pnlGame;        // The panel to play in
    private JLabel lblPlaying;    // Playing text
    private JLabel lblAudio;    // Audio file
    private JTextArea txtHits;    // Dispaly hits
    private JComboBox cmbSkill;    // Skill combo box, needs to be filled in
    private JFileChooser musicChooser; // Filechooser for audio
    private MusicListener musicListener = new MusicListener(); // inner class for music player button handling
    private MovingTextListener movingTextListener = new MovingTextListener(); // inner class handling button events for moving text panel
    private RotateShapeListener rotateShapeListener = new RotateShapeListener(); // inner class handling button events for rotating graphics
    private JLabel lblMovingText; // Moving text label

    /**
     * Constructor
     */
    public GUIAssignment1() {
    }

    /**
     * Starts the application
     */
    public void Start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 819, 438);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Multiple Thread Demonstrator");
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI() {
        // The music player outer panel
        JPanel pnlSound = new JPanel();
        Border b1 = BorderFactory.createTitledBorder("Music Player");
        pnlSound.setBorder(b1);
        pnlSound.setBounds(12, 12, 450, 100);
        pnlSound.setLayout(null);

        // Add labels and buttons to this panel
        lblPlaying = new JLabel("");    // Needs to be alteraed
        lblPlaying.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPlaying.setBounds(128, 16, 300, 20);
        pnlSound.add(lblPlaying);
        JLabel lbl1 = new JLabel("Loaded Audio File: ");
        lbl1.setBounds(10, 44, 130, 13);
        pnlSound.add(lbl1);
        lblAudio = new JLabel("no file loaded");                // Needs to be altered
        lblAudio.setBounds(130, 44, 300, 13);
        pnlSound.add(lblAudio);
        btnOpen = new JButton("Open");
        btnOpen.setBounds(6, 71, 75, 23);
        pnlSound.add(btnOpen);
        btnOpen.addActionListener(musicListener);
        btnPlay = new JButton("Play");
        btnPlay.addActionListener(musicListener);
        btnPlay.setBounds(88, 71, 75, 23);
        pnlSound.add(btnPlay);
        btnStop = new JButton("Stop");
        btnStop.setBounds(169, 71, 75, 23);
        btnStop.addActionListener(musicListener);
        pnlSound.add(btnStop);
        frame.add(pnlSound);

        // The moving display outer panel
        JPanel pnlDisplay = new JPanel();
        Border b2 = BorderFactory.createTitledBorder("Display Thread");
        pnlDisplay.setBorder(b2);
        pnlDisplay.setBounds(12, 118, 222, 269);
        pnlDisplay.setLayout(null);

        // Add buttons and drawing panel to this panel
        btnDisplay = new JButton("Start Display");
        btnDisplay.addActionListener(movingTextListener);
        btnDisplay.setBounds(10, 226, 121, 23);
        pnlDisplay.add(btnDisplay);
        btnDStop = new JButton("Stop");
        btnDStop.addActionListener(movingTextListener);
        btnDStop.setBounds(135, 226, 75, 23);
        pnlDisplay.add(btnDStop);
        pnlMove = new JPanel();
        pnlMove.setBounds(10, 19, 200, 200);
        Border b21 = BorderFactory.createLineBorder(Color.black);
        pnlMove.setBorder(b21);
        lblMovingText = new JLabel("");
        pnlMove.add(lblMovingText);
        pnlDisplay.add(pnlMove);
        frame.add(pnlDisplay);

        // The moving graphics outer panel
        JPanel pnlTriangle = new JPanel();
        Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
        pnlTriangle.setBorder(b3);
        pnlTriangle.setBounds(240, 118, 222, 269);
        pnlTriangle.setLayout(null);

        // Add buttons and drawing panel to this panel
        btnTriangle = new JButton("Start Rotate");
        btnTriangle.setBounds(10, 226, 121, 23);
        pnlTriangle.add(btnTriangle);
        btnTriangle.addActionListener(rotateShapeListener);
        btnTStop = new JButton("Stop");
        btnTStop.addActionListener(rotateShapeListener);
        btnTStop.setBounds(135, 226, 75, 23);
        pnlTriangle.add(btnTStop);
        pnlRotate = new JPanel();
        pnlRotate.setBounds(10, 19, 200, 200);
        Border b31 = BorderFactory.createLineBorder(Color.black);
        pnlRotate.setBorder(b31);
        pnlTriangle.add(pnlRotate);
        // Add this to main window
        frame.add(pnlTriangle);

        // The game outer panel
        JPanel pnlCatchme = new JPanel();
        Border b4 = BorderFactory.createTitledBorder("Catch Me");
        pnlCatchme.setBorder(b4);
        pnlCatchme.setBounds(468, 12, 323, 375);
        pnlCatchme.setLayout(null);

        // Add controls to this panel
        JLabel lblSkill = new JLabel("Skill:");
        lblSkill.setBounds(26, 20, 50, 13);
        pnlCatchme.add(lblSkill);
        JLabel lblInfo = new JLabel("Hit Image with Mouse");
        lblInfo.setBounds(90, 13, 150, 13);
        pnlCatchme.add(lblInfo);
        JLabel lblHits = new JLabel("Hits:");
        lblHits.setBounds(240, 20, 50, 13);
        pnlCatchme.add(lblHits);
        cmbSkill = new JComboBox();            // Need to be filled in with data
        cmbSkill.setBounds(19, 41, 61, 23);
        pnlCatchme.add(cmbSkill);
        btnGo = new JButton("GO");
        btnGo.setBounds(129, 41, 75, 23);
        pnlCatchme.add(btnGo);
        txtHits = new JTextArea();            // Needs to be updated
        txtHits.setBounds(233, 41, 71, 23);
        Border b40 = BorderFactory.createLineBorder(Color.black);
        txtHits.setBorder(b40);
        pnlCatchme.add(txtHits);
        pnlGame = new JPanel();
        pnlGame.setBounds(19, 71, 285, 283);
        Border b41 = BorderFactory.createLineBorder(Color.black);
        pnlGame.setBorder(b41);
        pnlCatchme.add(pnlGame);
        frame.add(pnlCatchme);
    }

    /**
     * sets new location for text label
     * @param width
     * @param height
     */
    public void setMovingTextLocation(int width, int height) {
        lblMovingText.setLocation(width, height);
    }

    /**
     * removes old shape from gui and add new rotated shape to gui
     * continually recieves rotatingShape from thread as long as it is  running
     * @param rotatingShape
     */
    public void addShape(RotatingShape rotatingShape) {
        pnlRotate.remove(rotatingShape);
        pnlRotate.add(rotatingShape);
        rotatingShape.setSize(200, 200);
        rotatingShape.setLocation(30,10);
    }

    /**
     * inner class for Musicplayer button handling.
     * implements ActionListener interface
     * @author Linus Forsberg
     */
    private class MusicListener implements ActionListener {
        private String fileName = null;
        private String filePath = null;
        private MusicPlayer musicPlayer;

        /**
         * handles button events for music player.
         * choose file, start playing and stop playing.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // opens filechooser and saves choosen filepath to String variable
            if (e.getSource() == btnOpen) {
                musicChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "mp3 & wav Images", "wav", "mp3");
                musicChooser.setFileFilter(filter);
                int returnVal = musicChooser.showOpenDialog(btnOpen);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileName = musicChooser.getSelectedFile().getName();
                    filePath = musicChooser.getSelectedFile().getPath();
                    lblAudio.setText(fileName);
                }
            }

            // creates instance of MusicPlayer class and plays audiofile
            if (e.getSource() == btnPlay) {
                if (fileName == null) {
                    lblPlaying.setText("Please choose an audiofile");
                } else {
                    musicPlayer = new MusicPlayer(filePath);
                    musicPlayer.play();
                    lblPlaying.setText("Now Playing: " + fileName);
                }
            }

            // stops audiofile from playing
            if (e.getSource() == btnStop) {
                if (fileName != null) {
                    musicPlayer.stop();
                }
                lblPlaying.setText("No file is playing");
            }
        }
    }


    /**
     * inner class for handling DisplayThread button events.
     * implements ActionListener interface
     * @author Linus Forsberg
     */
    private class MovingTextListener implements ActionListener {
        boolean threadIsRunning = false;
        MovingText movingText;

        /**
         * create instance of MovingText and starts thread.
         * thread runs until click on stop button
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnDisplay) {
                if(!threadIsRunning) {
                    threadIsRunning = true;
                    lblMovingText.setText("Display Thread");
                    movingText = new MovingText(GUIAssignment1.this);
                    Thread movingTextThread = new Thread(movingText);
                    try {
                        movingTextThread.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    movingTextThread.start();
                }
            }
            if (e.getSource() == btnDStop) {
                threadIsRunning = false;
                movingText.stopThread(false);
            }
        }
    }

    /**
     * inner class handling rotating graphics button events
     * implements ActionListener
     */
    private class RotateShapeListener implements ActionListener {
        RotatingShape rotatingShape;
        boolean threadIsRunning;


        /**
         * handling button clicks
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // creates instance of RotatingShape and starts thread
            if(e.getSource() == btnTriangle) {
                if(!threadIsRunning) {
                    threadIsRunning = true;
                    rotatingShape = new RotatingShape(GUIAssignment1.this);
                    Thread rotatingShapeThread = new Thread(rotatingShape);
                    try {
                        rotatingShapeThread.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    rotatingShapeThread.start();
                }
            }

            // stops thread
            if(e.getSource() == btnTStop) {
                threadIsRunning = false;
                rotatingShape.stopThread(false);
                pnlRotate.remove(rotatingShape);
            }
        }
    }
}
