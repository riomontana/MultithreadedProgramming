import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The GUI for assignment 3
 *
 * @edited by Linus Forsberg
 */
public class GUISemaphore {
    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     * Static controls are defined inline
     */
    private JFrame frame;                // The Main window
    private JProgressBar bufferStatus;    // The progressbar, showing content in buffer

    // Data for Producer Scan
    private JButton btnStartS;            // Button start Scan
    private JButton btnStopS;            // Button stop Scan
    private JLabel lblStatusS;            // Status Scan
    // DAta for producer Arla
    private JButton btnStartA;            // Button start Arla
    private JButton btnStopA;            // Button stop Arla
    private JLabel lblStatusA;            // Status Arla
    //Data for producer AxFood
    private JButton btnStartX;            // Button start AxFood
    private JButton btnStopX;            // Button stop AxFood
    private JLabel lblStatusX;            // Status AxFood

    // Data for consumer ICA
    private JLabel lblIcaItems;            // Ica limits
    private JLabel lblIcaWeight;
    private JLabel lblIcaVolume;
    private JLabel lblIcaStatus;        // load status
    private JPanel lstIca;            // The cargo list
    private JButton btnIcaStart;        // The buttons
    private JButton btnIcaStop;
    private JCheckBox chkIcaCont;        // Continue checkbox
    //Data for consumer COOP
    private JLabel lblCoopItems;
    private JLabel lblCoopWeight;
    private JLabel lblCoopVolume;
    private JLabel lblCoopStatus;        // load status
    private JPanel lstCoop;            // The cargo list
    private JButton btnCoopStart;        // The buttons
    private JButton btnCoopStop;
    private JCheckBox chkCoopCont;        // Continue checkbox
    // Data for consumer CITY GROSS
    private JLabel lblCGItems;
    private JLabel lblCGWeight;
    private JLabel lblCGVolume;
    private JLabel lblCGStatus;            // load status
    private JPanel lstCG;            // The cargo list
    private JButton btnCGStart;            // The buttons
    private JButton btnCGStop;
    private JCheckBox chkCGCont;        // Continue checkbox

    private ProducerListener producerListener = new ProducerListener(); // instance of inner class ProducerListener
    private ConsumerListener consumerListener = new ConsumerListener(); // instance of inner class ConsumerListener
    private Storage storage;
    private JScrollPane spane;
    private JScrollPane spaneC;
    private JScrollPane spaneG;

    /**
     * Constructor, creates the window
     */
    public GUISemaphore() {
    }

    /**
     * Starts the application
     */
    public void Start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 730, 526);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Food Supply System");
        InitializeGUI();                    // Fill in components
        storage = new Storage(bufferStatus); // create instance of Storage
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI() {
        // First create the three main panels
        JPanel pnlBuffer = new JPanel();
        pnlBuffer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Storage"));
        pnlBuffer.setBounds(13, 403, 693, 82);
        pnlBuffer.setLayout(null);
        // Then create the progressbar, only component in buffer panel
        bufferStatus = new JProgressBar();
        bufferStatus.setBounds(155, 37, 500, 23);
        bufferStatus.setBorder(BorderFactory.createLineBorder(Color.black));
        bufferStatus.setForeground(Color.GREEN);
        pnlBuffer.add(bufferStatus);
        JLabel lblmax = new JLabel("Max capacity (50):");
        lblmax.setBounds(10, 42, 126, 13);
        pnlBuffer.add(lblmax);
        frame.add(pnlBuffer);

        JPanel pnlProd = new JPanel();
        pnlProd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Producers"));
        pnlProd.setBounds(13, 13, 229, 379);
        pnlProd.setLayout(null);

        JPanel pnlCons = new JPanel();
        pnlCons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Consumers"));
        pnlCons.setBounds(266, 13, 440, 379);
        pnlCons.setLayout(null);

        // Now add the three panels to producer panel
        JPanel pnlScan = new JPanel();
        pnlScan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Producer: Scan"));
        pnlScan.setBounds(6, 19, 217, 100);
        pnlScan.setLayout(null);

        // Content Scan panel
        btnStartS = new JButton("Start Producing");
        btnStartS.addActionListener(producerListener);
        btnStartS.setBounds(10, 59, 125, 23);
        pnlScan.add(btnStartS);
        btnStopS = new JButton("Stop");
        btnStopS.setBounds(140, 59, 65, 23);
        btnStopS.addActionListener(producerListener);
        pnlScan.add(btnStopS);
        lblStatusS = new JLabel("Status:");
        lblStatusS.setBounds(10, 31, 200, 13);
        pnlScan.add(lblStatusS);
        // Add Scan panel to producers
        pnlProd.add(pnlScan);

        // The Arla panel
        JPanel pnlArla = new JPanel();
        pnlArla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Producer: Arla"));
        pnlArla.setBounds(6, 139, 217, 100);
        pnlArla.setLayout(null);

        // Content Arla panel
        btnStartA = new JButton("Start Producing");
        btnStartA.addActionListener(producerListener);
        btnStartA.setBounds(10, 59, 125, 23);
        pnlArla.add(btnStartA);
        btnStopA = new JButton("Stop");
        btnStopA.addActionListener(producerListener);
        btnStopA.setBounds(140, 59, 65, 23);
        pnlArla.add(btnStopA);
        lblStatusA = new JLabel("Status:");
        lblStatusA.setBounds(10, 31, 200, 13);
        pnlArla.add(lblStatusA);
        // Add Arla panel to producers
        pnlProd.add(pnlArla);

        // The AxFood Panel
        JPanel pnlAxfood = new JPanel();
        pnlAxfood.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Producer: AxFood"));
        pnlAxfood.setBounds(6, 262, 217, 100);
        pnlAxfood.setLayout(null);

        // Content AxFood Panel
        btnStartX = new JButton("Start Producing");
        btnStartX.setBounds(10, 59, 125, 23);
        btnStartX.addActionListener(producerListener);
        pnlAxfood.add(btnStartX);
        btnStopX = new JButton("Stop");
        btnStopX.addActionListener(producerListener);
        btnStopX.setBounds(140, 59, 65, 23);
        pnlAxfood.add(btnStopX);
        lblStatusX = new JLabel("Status:");
        lblStatusX.setBounds(10, 31, 200, 13);
        pnlAxfood.add(lblStatusX);
        // Add Axfood panel to producers
        pnlProd.add(pnlAxfood);
        // Producer panel done, add to frame
        frame.add(pnlProd);

        // Next, add the three panels to Consumer panel
        JPanel pnlICA = new JPanel();
        pnlICA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Consumer: ICA"));
        pnlICA.setBounds(19, 19, 415, 100);
        pnlICA.setLayout(null);

        // Content ICA panel
        // First the limits panel
        JPanel pnlLim = new JPanel();
        pnlLim.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Package Limits"));
        pnlLim.setBounds(6, 19, 107, 75);
        pnlLim.setLayout(null);
        JLabel lblItems = new JLabel("Items:");
        lblItems.setBounds(7, 20, 50, 13);
        pnlLim.add(lblItems);
        JLabel lblWeight = new JLabel("Weight:");
        lblWeight.setBounds(7, 35, 50, 13);
        pnlLim.add(lblWeight);
        JLabel lblVolume = new JLabel("Volume:");
        lblVolume.setBounds(7, 50, 50, 13);
        pnlLim.add(lblVolume);
        lblIcaItems = new JLabel("15");
        lblIcaItems.setBounds(60, 20, 47, 13);
        pnlLim.add(lblIcaItems);
        lblIcaWeight = new JLabel("27.00");
        lblIcaWeight.setBounds(60, 35, 47, 13);
        pnlLim.add(lblIcaWeight);
        lblIcaVolume = new JLabel("12.50");
        lblIcaVolume.setBounds(60, 50, 47, 13);
        pnlLim.add(lblIcaVolume);
        pnlICA.add(pnlLim);
        // Then rest of controls
        lstIca = new JPanel();
        lstIca.setLayout(new BoxLayout(lstIca, BoxLayout.PAGE_AXIS));
        spane = new JScrollPane(lstIca);
        spane.setBounds(307, 16, 102, 69);
        spane.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlICA.add(spane);

        btnIcaStart = new JButton("Start Loading");
        btnIcaStart.addActionListener(consumerListener);
        btnIcaStart.setBounds(118, 64, 120, 23);
        pnlICA.add(btnIcaStart);
        btnIcaStop = new JButton("Stop");
        btnIcaStop.addActionListener(consumerListener);
        btnIcaStop.setBounds(240, 64, 60, 23);
        pnlICA.add(btnIcaStop);
        lblIcaStatus = new JLabel("status: ");
        lblIcaStatus.setBounds(118, 16, 150, 23);
        pnlICA.add(lblIcaStatus);
        chkIcaCont = new JCheckBox("Continue load");
        chkIcaCont.setBounds(118, 39, 130, 17);
        pnlICA.add(chkIcaCont);
        chkIcaCont.addActionListener(consumerListener);
        // All done, add to consumers panel
        pnlCons.add(pnlICA);

        JPanel pnlCOOP = new JPanel();
        pnlCOOP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Consumer: COOP"));
        pnlCOOP.setBounds(19, 139, 415, 100);
        pnlCOOP.setLayout(null);
        pnlCons.add(pnlCOOP);

        // Content COOP panel
        // First the limits panel
        JPanel pnlLimC = new JPanel();
        pnlLimC.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Package Limits"));
        pnlLimC.setBounds(6, 19, 107, 75);
        pnlLimC.setLayout(null);
        JLabel lblItemsC = new JLabel("Items:");
        lblItemsC.setBounds(7, 20, 50, 13);
        pnlLimC.add(lblItemsC);
        JLabel lblWeightC = new JLabel("Weight:");
        lblWeightC.setBounds(7, 35, 50, 13);
        pnlLimC.add(lblWeightC);
        JLabel lblVolumeC = new JLabel("Volume:");
        lblVolumeC.setBounds(7, 50, 50, 13);
        pnlLimC.add(lblVolumeC);
        lblCoopItems = new JLabel("12");
        lblCoopItems.setBounds(60, 20, 47, 13);
        pnlLimC.add(lblCoopItems);
        lblCoopWeight = new JLabel("31.50");
        lblCoopWeight.setBounds(60, 35, 47, 13);
        pnlLimC.add(lblCoopWeight);
        lblCoopVolume = new JLabel("10.20");
        lblCoopVolume.setBounds(60, 50, 47, 13);
        pnlLimC.add(lblCoopVolume);
        pnlCOOP.add(pnlLimC);
        // Then rest of controls
        lstCoop = new JPanel();
        lstCoop.setLayout(new BoxLayout(lstCoop, BoxLayout.PAGE_AXIS));
        spaneC = new JScrollPane(lstCoop);
        spaneC.setBounds(307, 16, 102, 69);
        spaneC.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlCOOP.add(spaneC);
        btnCoopStart = new JButton("Start Loading");
        btnCoopStart.addActionListener(consumerListener);
        btnCoopStart.setBounds(118, 64, 120, 23);
        pnlCOOP.add(btnCoopStart);
        btnCoopStop = new JButton("Stop");
        btnCoopStop.addActionListener(consumerListener);
        btnCoopStop.setBounds(240, 64, 60, 23);
        pnlCOOP.add(btnCoopStop);
        lblCoopStatus = new JLabel("status:");
        lblCoopStatus.setBounds(118, 16, 150, 23);
        pnlCOOP.add(lblCoopStatus);
        chkCoopCont = new JCheckBox("Continue load");
        chkCoopCont.setBounds(118, 39, 130, 17);
        pnlCOOP.add(chkCoopCont);
        chkCoopCont.addActionListener(consumerListener);
        // All done, add to consumers panel
        pnlCons.add(pnlCOOP);

        JPanel pnlCG = new JPanel();
        pnlCG.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Consumer: CITY GROSS"));
        pnlCG.setBounds(19, 262, 415, 100);
        pnlCG.setLayout(null);
        pnlCons.add(pnlCG);

        // Content CITY GROSS panel
        // First the limits panel
        JPanel pnlLimG = new JPanel();
        pnlLimG.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Package Limits"));
        pnlLimG.setBounds(6, 19, 107, 75);
        pnlLimG.setLayout(null);
        JLabel lblItemsG = new JLabel("Items:");
        lblItemsG.setBounds(7, 20, 50, 13);
        pnlLimG.add(lblItemsG);
        JLabel lblWeightG = new JLabel("Weight:");
        lblWeightG.setBounds(7, 35, 50, 13);
        pnlLimG.add(lblWeightG);
        JLabel lblVolumeG = new JLabel("Volume:");
        lblVolumeG.setBounds(7, 50, 50, 13);
        pnlLimG.add(lblVolumeG);
        lblCGItems = new JLabel("19");
        lblCGItems.setBounds(60, 20, 47, 13);
        pnlLimG.add(lblCGItems);
        lblCGWeight = new JLabel("22.30");
        lblCGWeight.setBounds(60, 35, 47, 13);
        pnlLimG.add(lblCGWeight);
        lblCGVolume = new JLabel("17.00");
        lblCGVolume.setBounds(60, 50, 47, 13);
        pnlLimG.add(lblCGVolume);
        pnlCG.add(pnlLimG);
        // Then rest of controls
        lstCG = new JPanel();
        lstCG.setLayout(new BoxLayout(lstCG, BoxLayout.PAGE_AXIS));
        spaneG = new JScrollPane(lstCG);
        spaneG.setBounds(307, 16, 102, 69);
        spaneG.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlCG.add(spaneG);
        btnCGStart = new JButton("Start Loading");
        btnCGStart.setBounds(118, 64, 120, 23);
        pnlCG.add(btnCGStart);
        btnCGStop = new JButton("Stop");
        btnCGStart.addActionListener(consumerListener);
        btnCGStop.addActionListener(consumerListener);
        btnCGStop.setBounds(240, 64, 60, 23);
        pnlCG.add(btnCGStop);
        lblCGStatus = new JLabel("status: ");
        lblCGStatus.setBounds(118, 16, 150, 23);
        pnlCG.add(lblCGStatus);
        chkCGCont = new JCheckBox("Continue load");
        chkCGCont.setBounds(118, 39, 130, 17);
        pnlCG.add(chkCGCont);
        chkCGCont.addActionListener(consumerListener);
        // All done, add to consumers panel
        pnlCons.add(pnlCOOP);

        // Add consumer panel to frame
        frame.add(pnlCons);

        btnIcaStop.setEnabled(false);
        btnCoopStop.setEnabled(false);
        btnCGStop.setEnabled(false);
        btnStopS.setEnabled(false);
        btnStopX.setEnabled(false);
        btnStopA.setEnabled(false);
    }

    /**
     * Inner class handling button-clicks for producing FoodItems
     *
     * @author Linus Forsberg
     */
    private class ProducerListener implements ActionListener {
        private Factory scan, arla, axfood; // instances of Factory (producer)

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnStartS) { // Start Scan producer
                btnStartS.setEnabled(false);
                btnStopS.setEnabled(true);
                scan = new Factory(lblStatusS, storage); // create new instance of Factory
                Thread producerThread = new Thread(scan); // instantiate thread with reference to factory object
                producerThread.start(); // start thread
            }
            if (e.getSource() == btnStopS) { // Stop Scan producer
                btnStartS.setEnabled(true);
                btnStopS.setEnabled(false);
                scan.stopProducing(); // stop thread
                lblStatusS.setText("Status: Stopped");
            }
            if (e.getSource() == btnStartA) { // Start Arla producer
                btnStartA.setEnabled(false);
                btnStopA.setEnabled(true);
                arla = new Factory(lblStatusA, storage);
                Thread producerThread = new Thread(arla);
                producerThread.start();
            }
            if (e.getSource() == btnStopA) { // Stop Arla producer
                btnStartA.setEnabled(true);
                btnStopA.setEnabled(false);
                arla.stopProducing();
                lblStatusA.setText("Status: Stopped");
            }
            if (e.getSource() == btnStartX) { // Start Axfood producer
                btnStartX.setEnabled(false);
                btnStopX.setEnabled(true);
                axfood = new Factory(lblStatusX, storage);
                Thread producerThread = new Thread(axfood);
                producerThread.start();
            }
            if (e.getSource() == btnStopX) { // Stop Axfood producer
                btnStartX.setEnabled(true);
                btnStopX.setEnabled(false);
                axfood.stopProducing();
                lblStatusX.setText("Status: Stopped");
            }
        }
    }

    /**
     * Inner class handling button-clicks for consuming FoodItems
     *
     * @author Linus Forsberg
     */
    private class ConsumerListener implements ActionListener {
        Truck ica, cityGross, coop; // instances of Truck (consumer)

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnIcaStart) { // Start Ica consumer
                btnIcaStart.setEnabled(false);
                btnIcaStop.setEnabled(true);
                lstIca.revalidate();
                lstIca.removeAll();
                lstIca.repaint();
                ica = new Truck(lstIca, lblIcaStatus,
                        storage, 15, 27, 12.50); // instantiate new Truck
                if (chkIcaCont.isSelected()) { // if continue box is checked
                    ica.setContinueBool(true); // boolean is set in consumer making it continue
                }
                Thread consumerThread = new Thread(ica); // create new thread passing a Truck as parameter
                consumerThread.start(); // start thread
            }

            if (e.getSource() == btnCGStart) { // Start City Gross consumer
                btnCGStart.setEnabled(false);
                btnCGStop.setEnabled(true);
                lstCG.revalidate();
                lstCG.removeAll();
                lstCG.repaint();
                cityGross = new Truck(lstCG, lblCGStatus,
                        storage, 12, 31.50, 10.20);
                if (chkCGCont.isSelected()) { // if continue box is checked
                    cityGross.setContinueBool(true);
                }
                Thread consumerThread = new Thread(cityGross);
                consumerThread.start();
            }

            if (e.getSource() == btnCoopStart) { // Start Coop consumer
                btnCoopStart.setEnabled(false);
                btnCoopStop.setEnabled(true);
                lstCoop.revalidate();
                lstCoop.removeAll();
                lstCoop.repaint();
                coop = new Truck(lstCoop, lblCoopStatus,
                        storage, 19, 22.30, 17);
                if (chkCoopCont.isSelected()) { // if continue box is checked
                    coop.setContinueBool(true);
                }
                Thread consumerThread = new Thread(coop);
                consumerThread.start();
            }

            if (e.getSource() == btnIcaStop) { // Stop Ica consumer
                btnIcaStart.setEnabled(true);
                btnIcaStop.setEnabled(false);
                ica.stopConsuming(); // stop thread
                lblIcaStatus.setText("status: Stopped");
            }
            if (e.getSource() == btnCGStop) { // Stop City Gross consumer
                btnCGStart.setEnabled(true);
                btnCGStop.setEnabled(false);
                cityGross.stopConsuming();
                lblCGStatus.setText("status: Stopped");
            }
            if (e.getSource() == btnCoopStop) { // Stop Coop consumer
                btnCoopStart.setEnabled(true);
                btnCoopStop.setEnabled(false);
                coop.stopConsuming();
                lblCoopStatus.setText("status: Stopped");
            }
        }
    }
}
