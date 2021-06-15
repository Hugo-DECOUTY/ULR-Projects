package Socket.Client;

import IO.FileIOManager;
import IO.InputReader;
import IO.OutputWriter;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Client implements Runnable {

	private final String nomClient; //Nom du client.
	private String data; //Données du client.
	private final Socket s; //Socket du client.

	private static int numeroClient = 1; //Numéro d'identification du client

	private InputReader in; //Lecture des données envoyées par le OutputWriter. Se charge de print dans le terminal
	private OutputWriter out; //Ecriture des données du navigateur web.

	//Page HTML en cas d'erreur.
	private final String HTML_404 = "<html>\n" + "<head><title>404 Not Found</title></head>\n" + "<body bgcolor=\"white\">\n" + "<center><h1>404 Not Found</h1></center>\n" + "<hr><center>Veuillez donner une URL valide.</center></hr>\n" + "</body>\n" + "</html>";

	public Client(Socket client) {
		this.s = client;
		nomClient = "Client n°" + numeroClient;
		numeroClient++;
	}

	/**
	 * Création d'un nouveau thread
	 */
	public void startClientThread() throws IOException {

			printRequest("Connexion au serveur acceptée");
			new Thread(this , nomClient).start();
	}

	/**
	 * Permet de démarrer la lecture de la requête - Ne prend en compte que du HTTPS.
	 */
	@Override
	public void run() {
		try {
			in = new InputReader(this.s.getInputStream());
			out = new OutputWriter(this.s.getOutputStream());
			String line = in.readNextLine();

			// Si la ligne lue n'est pas null alors on affiche le type de la requête
			if (line != null) {
				data = line + "\r\n" + new String(in.read()).trim();
				printRequest("Nouvelle requête : " + data);
			}

			// Si la ligne est vide alors on ferme l'input et le socket
			if (line == null || line.isEmpty()) {
				out.close();
				in.close();
				s.close();
				printRequest("Connexion terminée");
			} else {
				//on récupère la ligne dans un objet StringTokenizer afin de récupérer chaque élément 1 par 1
				String[] splittedLine = line.split(" ");
				String req = splittedLine[0], path = "", requestType = "";

				if (splittedLine[1] != null)
					path = splittedLine[1];
				if (splittedLine[2] != null)
					requestType = splittedLine[2];

				// On vérifie le type de requête on redirige vers le bon gestionnaire de requête
				if (!requestType.equalsIgnoreCase("HTTP/1.1")) {
					invalidRequest();
				} else if (req.equalsIgnoreCase("GET")) {
					GETRequestHandler(path);
				} else if (req.equalsIgnoreCase("POST")) {
					POSTRequestHandler(path);
				} else {
					invalidRequest();
				}
			}

		} catch (IOException e) {

			// Message d'erreur dès lors que l'on souhaite se connecter en HTTP par le serveur HTTPS.
			System.out.println("Connexion HTTP non autorisée. Veuillez vous connecter en HTTPS");
			// Fermeture de la connexion
			try {
				out.close();
				in.close();
				s.close();
				printRequest("Connexion terminée");
			} catch (IOException io){
				io.printStackTrace();
			}
		}
	}


	/**
	 * méthode permettant d'afficher les informations d'une requête dans le terminal
	 */
	private void printRequest(String info) {
		// On affiche la date, l'ip du s et la requête effectuée
		String requestMessage = "Heure : " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + ", Nom du client : " + this.nomClient +  ", Info : " + info;
		System.out.println(requestMessage);
	}


	/**
	 * Méthode permettant de gérer les requêtes GET
	 */
	private void GETRequestHandler(String path) throws IOException {

		// Si le path n'est pas spécifié alors on récupère l'index
		if (path.equalsIgnoreCase("/"))
			path = "/index.html";

		String options = "";
		String filename;
		if(path.contains("?")){
			options = path.substring(1).split("[?]")[1];
			filename = path.substring(1).split("[?]")[0];
		} else {
			filename = path.substring(1);
		}

		// On récupère le fichier grace au nom du fichier
		File file = new File(filename);

		if (!file.canRead()) {	// Si le fichier ne peut être lu alors on renvoie une erreur 404 disant que le fichier n'existe pas
			printRequest(filename + " - Aucun fichier correspondant trouvé");
			//Affichage des options après le GET
			HTTP_Write("404 NOT FOUND" , "text/html" , HTML_404.getBytes());
		} else { // Si le fichier est trouvé on affiche dans le navigateur
			printRequest("Fichier demandé : " + file.getPath());

			//Affichage des options dans l'URL du formulaire GET si il y'a des options précisés.
			if(path.contains("?")){
				printRequest(filename + " - Options de l'URL : " + options);
			}
			HTTP_Write("200 OK" , Files.probeContentType(file.toPath()) , FileIOManager.readFileBytes(file.getPath()));
		}
	}

	/**
	 * Méthode permettant de gérer les requêtes POST
	 */
	private void POSTRequestHandler(String path) throws IOException {
		// On récupère le contenu du post
		String data_post = data.substring(data.lastIndexOf("user=") + "user=".length());
		// On l'affiche dans le terminal
		printRequest("Données reçu: " + data_post);

		String postReply = new String(FileIOManager.readFileBytes(path.substring(1))).replaceFirst("<h2> Post-> </h2>" , "<h2> Post->\"" + data + "\" </h2>");
		HTTP_Write("200 OK" , "text/html" , postReply.getBytes());
	}

	/**
	 * Méthode permettant de gérer les requêtes invalides
	 */
	private void invalidRequest() throws IOException {
		printRequest("Requête invalide");
		HTTP_Write("400 BAD REQUEST" , null , null);
	}


	/**
	 * Méthode permettant d'écrire le résultat HTTP dans le terminal et de fermer la connexion
	 */
	private void HTTP_Write(String status , String MMI , byte[] contents) throws IOException {

		// Affichage du statut de la requête
		// Affichage du type de donnée, de sa taille et prépare la fin de la connexion
		out.writeLine("HTTP/1.1 " + status);
		if (MMI != null) {
			out.writeLine("Content-Type: " + MMI);
			out.writeLine("Content-Length: " + contents.length);
			out.writeLine("Connection: close");
		}

		out.writeLine();
		if (contents != null)
			out.write(contents);
			System.out.println("Contents : " + Arrays.toString(contents));

		// Fermeture de la connexion
		out.close();
		in.close();
		s.close();
		printRequest("Connexion terminée");
	}

}



