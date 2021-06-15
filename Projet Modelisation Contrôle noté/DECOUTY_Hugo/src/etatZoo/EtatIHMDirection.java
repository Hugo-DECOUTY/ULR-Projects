package etatZoo;

import decorateurZoo.DecorateurIHMDirection;
import zoo.Catalogue;

public class EtatIHMDirection extends EtatIHM{

    public EtatIHMDirection(Controleur controleur) {
        super(controleur);
        this.decorateur = new DecorateurIHMDirection(this.implementationIHM);
    }

    @Override
    public void afficherMenu(Catalogue catalogue) {

        this.decorateur.afficherMenu();

        int choixMenu = this.controleur.lireEntree();
        switch(choixMenu){
            case 1:
                catalogue.genererBrochure();
                this.afficherMenu(catalogue);
                break;
            case 2:
                System.out.println(catalogue.toString());
                this.afficherMenu(catalogue);
                break;
            case 3:
                catalogue.afficherMammiferes();
                this.afficherMenu(catalogue);
                break;
            case 4:
                catalogue.afficherReptiles();
                this.afficherMenu(catalogue);
                break;
            case 5:
                this.controleur.changeEtat(new EtatIHMChoixUtilisateur(this.controleur));
                break;
        }


    }
}
