package Socket.Server;

import Socket.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;

public class HTTP implements Runnable{

    private final int port;

    public HTTP(int port){this.port = port;}

    @Override
    public void run() {
        try {
            int port_serveur = this.port;
            ServerSocket serverConnect = new ServerSocket(port_serveur);
            System.out.println("Serveur démarré (HTTP).\nEn attente d'une connexion sur le port: " + port_serveur + " ...\n");
            while (true) {
                Client c = new Client(serverConnect.accept());
                System.out.println("Nouvelle connexion");
                c.startClientThread();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
