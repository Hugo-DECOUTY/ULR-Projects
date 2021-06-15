package etatZoo;

import zoo.Catalogue;

import java.util.Scanner;

public class Controleur {
    private EtatIHM etat;
    private Catalogue catalogue;

    public Controleur(Catalogue catalogue)
    {
        this.catalogue = catalogue;
        this.etat = new EtatIHMChoixUtilisateur(this);
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void changeEtat(EtatIHM etat)
    {
        this.etat = etat;
        this.etat.afficherMenu(this.catalogue);
    }

    public void initMenu()
    {
        this.etat = new EtatIHMChoixUtilisateur(this);
        this.etat.afficherMenu(this.catalogue);
    }

    public int lireEntree()
    {
        @SuppressWarnings("resource")
        Scanner entree = new Scanner(System.in);
        return entree.nextInt();
    }

}
