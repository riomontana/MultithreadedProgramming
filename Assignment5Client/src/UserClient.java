import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserClient implements Runnable {

    private GUIChatClient gui;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
//    private String message;
    private Socket connection;
    private String clientName;
    private boolean isConnected = false;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public UserClient(GUIChatClient gui, String clientName) {
        this.gui = gui;
        this.clientName = clientName;
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeObject(clientName + ": " + message + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isConnected) {
            gui.displayMessage("Connecting...\n");
            try {
                connection = new Socket("127.0.0.1", 50000);
                outputStream = new ObjectOutputStream(connection.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(connection.getInputStream());
                gui.displayMessage("Connected to server!\n");
                threadPool.execute(
                        new WorkerRunnable());
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
                gui.displayMessage("No connection with server.\n" +
                        "Trying again in a few seconds\n");
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Inner class
     */
    private class WorkerRunnable implements Runnable {

        public void run() {
            while (true) {
                try {
                    String message = (String) inputStream.readObject();
                    gui.displayMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                    break;
                }
            }
        }
    }
}