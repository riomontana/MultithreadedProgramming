import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * The GUI for assignment 4
 */
public class GUIMonitor extends Component implements Observer {
    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     */
    private JFrame frame;                // The Main window
    private JTextField txtFind;            // Input string to find
    private JTextField txtReplace;        // Input string to replace
    private JLabel lblInfo;                // Hidden after file selected
    private JButton btnCreate;            // Start copying
    private JButton btnClear;            // Removes dest. file and removes marks
    private JLabel lblChanges;            // Label telling number of replacements
    private JButton btnFileChooser;        // Filechooser button
    private ButtonListener clickListener; // Handling button-clicks
    private JTextPane txtPaneSource; // shows original text
    private JTextPane txtPaneDest; // shows modified text
    private List<String> textFromFile = new ArrayList<>(); // arraylist holding lines from textfile

    /**
     * Constructor
     */
    public GUIMonitor() {
        clickListener = new ButtonListener(); // create instance of inner class
    }

    /**
     * Starts the application
     */
    public void Start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 714, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Text File Copier - with Find and Replace");
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI() {
        JPanel pnlFind = new JPanel();
        pnlFind.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Find and Replace"));
        pnlFind.setBounds(12, 32, 436, 122);
        pnlFind.setLayout(null);
        frame.add(pnlFind);
        JLabel lab1 = new JLabel("Find:");
        lab1.setBounds(7, 30, 80, 13);
        pnlFind.add(lab1);
        JLabel lab2 = new JLabel("Replace with:");
        lab2.setBounds(7, 63, 80, 13);
        pnlFind.add(lab2);

        txtFind = new JTextField("");
        txtFind.setBounds(88, 23, 327, 20);
        pnlFind.add(txtFind);
        txtReplace = new JTextField("");
        txtReplace.setBounds(88, 60, 327, 20);
        pnlFind.add(txtReplace);
        lblInfo = new JLabel("Select Source File..");
        lblInfo.setBounds(485, 42, 120, 13);
        frame.add(lblInfo);

        btnFileChooser = new JButton("File chooser");
        btnFileChooser.setBounds(465, 87, 230, 23);
        frame.add(btnFileChooser);
        btnCreate = new JButton("Create the destination file");
        btnCreate.setBounds(465, 119, 230, 23);
        frame.add(btnCreate);
        btnClear = new JButton("Clear Dest. file and remove marks");
        btnClear.setBounds(465, 151, 230, 23);
        frame.add(btnClear);

        lblChanges = new JLabel("No. of Replacements: ");
        lblChanges.setBounds(279, 161, 200, 13);
        frame.add(lblChanges);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(12, 170, 653, 359);
        frame.add(tabbedPane);
        txtPaneSource = new JTextPane();
        JScrollPane scrollSource = new JScrollPane(txtPaneSource);
        tabbedPane.addTab("Source", null, scrollSource, null);
        txtPaneDest = new JTextPane();
        JScrollPane scrollDest = new JScrollPane(txtPaneDest);
        tabbedPane.addTab("Destination", null, scrollDest, null);

        btnFileChooser.addActionListener(clickListener);
        btnClear.addActionListener(clickListener);
        btnCreate.addActionListener(clickListener);
    }

    /**
     * Prints original text to GUI textpane.
     *
     * @param text text from textfile
     */
    public void fileToTxtPane(List<String> text) {
        List<String> textFromReader = text; // list of strings
        StyledDocument document = (StyledDocument) txtPaneSource.getDocument(); // get document from textpane
        for (String str : textFromReader) { // looping all strings in list
            try {
                document.insertString(document.getLength(), str, null); // inserting to textpane document
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints modified text to GUI textpane
     *
     * @param o   observable object
     * @param obj passed to the notifyObservers method in observable.
     */
    @Override
    public void update(Observable o, Object obj) {
        List<String> textFromReader = (List<String>) obj;  // list of strings from observable object
        if (o instanceof Reader) {
            StyledDocument document = (StyledDocument) txtPaneDest.getDocument(); // get document from textpane
            for (String s : textFromReader) {  // looping all strings in list
                try {
                    document.insertString(document.getLength(), s, null); // inserting to textpane document
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Inner class for handling button-clicks.
     * Importing a text file. Printing to GUI.
     * Creating threads for Writer, Reader and Modifier.
     * Printing modified text to GUI.
     *
     * @author Linus Forsberg
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // If Button for choosing file is clicked
            if (e.getSource() == btnFileChooser) {
                txtPaneSource.setText(""); // clear textpane
                btnFileChooser.setEnabled(false); // disable button
                txtPaneSource.setText(""); // clear textpane
                JFileChooser jf = new JFileChooser(); // window for choosing file
                BufferedReader in; // for reading file
                FileReader fr; // for reading file
                int returnVal = jf.showOpenDialog(GUIMonitor.this); // show window
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = jf.getSelectedFile();
                    FileNameExtensionFilter filter =
                            new FileNameExtensionFilter(
                                    "TEXT FILES", "txt", "text"); // filter only textfiles
                    jf.setFileFilter(filter);
                    try {
                        fr = new FileReader(file.getAbsoluteFile()); // read file
                        in = new BufferedReader(fr); // read file
                        String line; // read line
                        while ((line = in.readLine()) != null) { // while there are lines to read in file
                            textFromFile.add(line); // add to arraylist
                        }
                        in.close();
                        fr.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    fileToTxtPane(textFromFile);
                    lblInfo.setText("File selected: " + file.getName());
                }
            }
            // If button for creating destination file is clicked
            if (e.getSource() == btnCreate) {
                txtPaneDest.setText(""); // clear textpane
                btnCreate.setEnabled(false);
                BoundedBuffer buffer = new BoundedBuffer(10, lblChanges,
                        txtFind.getText(), txtReplace.getText()); // create buffer
                Writer writer = new Writer(buffer, textFromFile); // create writer
                Thread writerThread = new Thread(writer); // create thread for writer
                writerThread.start(); // start writer thread

                Modifier modifier = new Modifier(buffer); // create modifier
                Thread modifierThread = new Thread(modifier); // create thread for modifier
                modifierThread.start(); // start modifier thread

                Reader reader = new Reader(buffer, textFromFile.size()); // create reader
                reader.addObserver(GUIMonitor.this); // add observer to reader
                Thread readerThread = new Thread(reader); // create thread for reader
                readerThread.start(); // start reader thread
            }
            // If button for clearing is clicked
            if (e.getSource() == btnClear) {
                btnFileChooser.setEnabled(true); // enable file chooser button
                btnCreate.setEnabled(true);
                txtFind.setText(""); // clear textfield
                txtReplace.setText(""); // clear textfield
                txtPaneDest.setText(""); // clear textpane
                textFromFile.clear(); // clear arraylist with text
                lblChanges.setText("No. of replacements: ");
            }
        }
    }
}
