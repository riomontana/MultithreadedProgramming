import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The GUI for assignment 2
 *
 * @author in parts, Linus Forsberg
 */
public class GUIMutex {
    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     */
    private JFrame frame;            // The Main window
    private JLabel lblTrans;        // The transmitted text
    private JLabel lblRec;            // The received text
    private JRadioButton bSync;        // The sync radiobutton
    private JRadioButton bAsync;    // The async radiobutton
    private JTextField txtTrans;    // The input field for string to transfer
    private JButton btnRun;         // The run button
    private JButton btnClear;        // The clear button
    private JPanel pnlRes;            // The colored result area
    private JLabel lblStatus;        // The status of the transmission
    private JTextArea listW;        // The write logger pane
    private JTextArea listR;        // The read logger pane
    private Controller controller;
    private ButtonListener listener = new ButtonListener(); // ButtonListener inner class
    private boolean isSyncronized;

    /**
     * Constructor
     */
    public GUIMutex() {
        controller = new Controller(this);
    }

    /**
     * Starts the application
     */
    public void Start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 601, 482);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Concurrent Read/Write");
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI() {
        // First, create the static components
        // First the 4 static texts
        JLabel lab1 = new JLabel("Writer Thread Logger");
        lab1.setBounds(18, 29, 128, 13);
        frame.add(lab1);
        JLabel lab2 = new JLabel("Reader Thread Logger");
        lab2.setBounds(388, 29, 128, 13);
        frame.add(lab2);
        JLabel lab3 = new JLabel("Transmitted:");
        lab3.setBounds(13, 394, 100, 13);
        frame.add(lab3);
        JLabel lab4 = new JLabel("Received:");
        lab4.setBounds(383, 394, 100, 13);
        frame.add(lab4);
        // Then add the two lists (of string) for logging transfer
        listW = new JTextArea();
        listW.setBounds(13, 45, 197, 342);
        listW.setBorder(BorderFactory.createLineBorder(Color.black));
        listW.setEditable(false);
        frame.add(listW);
        listR = new JTextArea();
        listR.setBounds(386, 45, 183, 342);
        listR.setBorder(BorderFactory.createLineBorder(Color.black));
        listR.setEditable(false);
        frame.add(listR);
        // Next the panel that holds the "running" part
        JPanel pnlTest = new JPanel();
        pnlTest.setBorder(BorderFactory.createTitledBorder("Concurrent Tester"));
        pnlTest.setBounds(220, 45, 155, 342);
        pnlTest.setLayout(null);
        frame.add(pnlTest);
        lblTrans = new JLabel();    // Replace with sent string
        lblTrans.setBounds(13, 415, 200, 13);
        frame.add(lblTrans);
        lblRec = new JLabel();        // Replace with received string
        lblRec.setBounds(383, 415, 200, 13);
        frame.add(lblRec);

        // These are the controls on the user panel, first the radiobuttons
        bSync = new JRadioButton("Synchronous Mode", false);
        bSync.setBounds(8, 37, 131, 17);
        pnlTest.add(bSync);
        bAsync = new JRadioButton("Asynchronous Mode", true);
        bAsync.setBounds(8, 61, 141, 17);
        pnlTest.add(bAsync);
        ButtonGroup grp = new ButtonGroup();
        grp.add(bSync);
        grp.add(bAsync);
        // then the label and textbox to input string to transfer
        JLabel lab5 = new JLabel("String to Transfer:");
        lab5.setBounds(6, 99, 141, 13);
        pnlTest.add(lab5);
        txtTrans = new JTextField();
        txtTrans.setBounds(6, 124, 123, 20);
        pnlTest.add(txtTrans);
        // The run button
        btnRun = new JButton("Run");
        btnRun.setBounds(26, 150, 75, 23);
        btnRun.addActionListener(listener); // add button to Run-button
        pnlTest.add(btnRun);
        JLabel lab6 = new JLabel("Running status:");
        lab6.setBounds(23, 199, 110, 13);
        pnlTest.add(lab6);
        // The colored rectangle holding result status
        pnlRes = new JPanel();
        pnlRes.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlRes.setBounds(26, 225, 75, 47);
        pnlRes.setBackground(Color.LIGHT_GRAY);
        pnlTest.add(pnlRes);
        // also to this text
        lblStatus = new JLabel("");
        lblStatus.setBounds(23, 275, 100, 13);
        pnlTest.add(lblStatus);
        // The clear input button, starts disabled
        btnClear = new JButton("Clear");
        btnClear.setBounds(26, 303, 75, 23);
        btnClear.addActionListener(listener); // add button listener to Clear-button
        btnClear.setEnabled(false);
        pnlTest.add(btnClear);
        bAsync.addActionListener(listener); // add button listener to radio button
        bSync.addActionListener(listener); // add button listener to radio button
    }

    /**
     * Add written char to writer list in GUI
     *
     * @param writtenChar
     */
    public void addToListW(char writtenChar) {
        listW.append("Writing: '" + writtenChar + "'\n");
        listW.revalidate();
        listW.repaint();
    }

    /**
     * Add received char from buffer to reader list in GUI
     *
     * @param receivedChar
     */
    public void addToListR(char receivedChar) {
        listR.append("Reading: '" + receivedChar + "'\n");
        listR.revalidate();
        listR.repaint();
    }

    /**
     * Add written string to label in GUI
     *
     * @param writtenString
     */
    public void addToLabelTransmitted(String writtenString) {
        lblTrans.setText(writtenString);
    }

    /**
     * Add received string to label in GUI
     *
     * @param receivedString
     */
    public void addToLabelReceived(String receivedString) {
        lblRec.setText(receivedString);
    }

    /**
     * Update GUI rectangle with green background if written string and received string matching
     * or update rectangle with red background if strings do not match
     * also update status label with "SUCCESS" or "FAIL" and enable clear-button
     *
     * @param statusColor green or red color
     * @param statusLabel SUCCESS or FAIL
     */
    public void updateColoredRectangle(Color statusColor, String statusLabel) {
        pnlRes.setBackground(statusColor);
        lblStatus.setText(statusLabel);
        btnClear.setEnabled(true);
    }

    /**
     * Inner class for handling button-clicks in GUI
     *
     * @author Linus Forsberg
     */
    private class ButtonListener implements ActionListener {

        /**
         * On click on btnRun: Call controller and start new threads for writer and reader
         * Synchronized or not synchronized depending on what radio button is selected
         * On click on btnClear: reset GUI labels
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnRun) {
                controller.setTextToBuffer(txtTrans.getText());
                btnClear.setEnabled(true);

                if (bAsync.isSelected()) {
                    isSyncronized = false;
                    controller.startWriterThread(isSyncronized);
                    controller.startReaderThread(isSyncronized);
                }
                if (bSync.isSelected()) {
                    isSyncronized = true;
                    controller.startWriterThread(isSyncronized);
                    controller.startReaderThread(isSyncronized);
                }
            }

            if (e.getSource() == btnClear) {
                listR.setText("");
                listW.setText("");
                lblTrans.setText("");
                lblRec.setText("");
                lblStatus.setText("");
                pnlRes.setBackground(Color.LIGHT_GRAY);
                btnClear.setEnabled(false);
            }
        }
    }
}
