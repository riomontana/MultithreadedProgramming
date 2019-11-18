import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserServer implements Runnable, Observer {

    private GUIServer guiServer;
    private ServerSocket serverSocket;
    private Socket connection;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private List<UserServer> users = new LinkedList<>();
//    private String response;


    public UserServer(GUIServer guiServer) {
        this.guiServer = guiServer;
        guiServer.addObserver(this);
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(50000);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 50000", e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            String message = (String) arg;
            outputStream.writeObject("Server: " + message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        openServerSocket();
        guiServer.displayMessage("Waiting for clients to connect...\n");
        while (true) {
            try {
                connection = serverSocket.accept();
                outputStream = new ObjectOutputStream(connection.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                guiServer.displayMessage("Server Stopped.\n");
                break;
            }
            threadPool.execute(
                    new WorkerRunnable());
        }
        threadPool.shutdown();
        guiServer.displayMessage("Server Stopped.\n");
    }

    /**
     * Inner class
     */
    private class WorkerRunnable implements Runnable {

        public void run() {
            guiServer.displayMessage("New client connected!\n");
            while (true)
                try {
                    String message = (String) inputStream.readObject();
                    guiServer.displayMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
        }
    }
}
