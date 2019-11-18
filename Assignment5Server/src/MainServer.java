/**
 * Main class starts application
 */

public class MainServer {

    public static void main(String[] args) {
        GUIServer gui = new GUIServer();
        gui.Start();
        UserServer userServer = new UserServer(gui);
        new Thread(userServer).start();
    }
}
