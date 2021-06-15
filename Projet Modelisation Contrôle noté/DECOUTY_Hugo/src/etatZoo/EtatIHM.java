package etatZoo;

import decorateurZoo.DecorateurIHM;
import decorateurZoo.ImplementationIHM;
import zoo.Catalogue;

public abstract class EtatIHM {

    protected ImplementationIHM implementationIHM;
    protected DecorateurIHM decorateur;
    protected Controleur controleur;

    public EtatIHM(Controleur controleur)
    {
        this.controleur = controleur;
        this.implementationIHM = new ImplementationIHM();
        this.decorateur = null;
    }

    public abstract void afficherMenu(Catalogue catalogue);


}
