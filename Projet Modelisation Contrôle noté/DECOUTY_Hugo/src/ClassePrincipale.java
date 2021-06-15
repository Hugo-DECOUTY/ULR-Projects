import etatZoo.Controleur;
import fabriqueZoo.SimpleFabrique;
import zoo.Catalogue;

import java.io.BufferedReader;
import java.io.FileReader;

public class ClassePrincipale {

    public static void main(String[] args)
    {
        SimpleFabrique simpleFabrique = new SimpleFabrique();
        Catalogue catalogue = new Catalogue();
        BufferedReader lecteurAvecBuffer = null;
        String ligne;

        try
        {
            lecteurAvecBuffer = new BufferedReader(new FileReader("animaux.txt"));
            while ((ligne = lecteurAvecBuffer.readLine()) != null)
                catalogue.ajouterAnimal(simpleFabrique.creerAnimal(ligne));
            lecteurAvecBuffer.close();
        }
        catch (Exception exc)
        {
            System.out.println("Erreur à la lecture du fichier de stock" + exc);
        }

        Controleur controleur = new Controleur(catalogue);
        controleur.initMenu();
    }
}
