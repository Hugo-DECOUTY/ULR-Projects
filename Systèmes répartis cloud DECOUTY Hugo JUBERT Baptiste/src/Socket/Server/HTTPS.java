package Socket.Server;

import Socket.Client.Client;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class HTTPS implements Runnable{

    private final int port;
    public HTTPS(int port){this.port = port;}

    @Override
    public void run() {

        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("keystoreFile.jks"), "hugobaptiste".toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, "hugobaptiste".toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext sc = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            sc.init(kmf.getKeyManagers(), trustManagers, null);

            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(this.port);
            System.out.println("Serveur démarré (HTTPS).\n" +
                    "En attente d'une connexion sur le port: " + this.port + "\n");
            while(true){
                Client c = new Client(s.accept());
                System.out.println("Nouvelle connexion");
                c.startClientThread();
            }
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }

    }
}
