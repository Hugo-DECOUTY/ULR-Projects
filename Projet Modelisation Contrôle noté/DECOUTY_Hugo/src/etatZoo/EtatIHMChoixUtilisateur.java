package etatZoo;

import decorateurZoo.DecorateurIHMChoixUtilisateur;
import zoo.Catalogue;

public class EtatIHMChoixUtilisateur extends EtatIHM{

    public EtatIHMChoixUtilisateur(Controleur controleur) {
        super(controleur);
        this.decorateur = new DecorateurIHMChoixUtilisateur(this.implementationIHM);
    }

    @Override
    public void afficherMenu(Catalogue catalogue) {

        this.decorateur.afficherMenu();

        int choixMenu = this.controleur.lireEntree();
        switch(choixMenu){
            case 1:
                this.controleur.changeEtat(new EtatIHMDirection(this.controleur));
                break;
            case 2:
                this.controleur.changeEtat(new EtatIHMSoigneur(this.controleur));
        }

    }
}
