package Socket.Server;

public class Server extends Thread implements Runnable {

    private final int HTTP_port;
    private final int HTTPS_port;
    private int DEFAULT_PORT_HTTP = 2323;
    private int DEFAULT_PORT_HTTPS = 2324;

    public Server(int HTTP_port, int HTTPS_port){
        this.HTTP_port = HTTP_port;
        this.HTTPS_port = HTTPS_port;
    }

    public Server(){
        this.HTTP_port = DEFAULT_PORT_HTTP;
        this.HTTPS_port = DEFAULT_PORT_HTTPS;
    }

    @Override
    public void run() {

        try {
            System.out.println("Tentative de démarrage sur les ports " + this.HTTP_port + " pour HTTP et " + this.HTTPS_port + " pour HTTPS");
            //Lancement de deux threads : HTTP et HTTPS.
            new Thread(new HTTP(this.HTTP_port)).start();
            new Thread(new HTTPS(this.HTTPS_port)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Si on a bien deux ports de spécifiés, on les définit comme ports des serveurs.
        if(args.length == 2){
            System.out.println("Avec paramètres");
            int httpPort = Integer.parseInt(args[0]);
            int httpsPort = Integer.parseInt(args[1]);
            new Server(httpPort, httpsPort).start();
        //Auquel cas, on ne considère pas les arguments spécifiés par l'utilisateur. On récupère les ports par défaut.
        }else{
            System.out.println("Sans paramètres - Ports par défaut");
            new Server().start();
        }

    }


}