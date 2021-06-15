package etatZoo;

import decorateurZoo.DecorateurIHMSoigneur;
import zoo.Animal;
import zoo.Catalogue;

public class EtatIHMSoigneur extends EtatIHM {

    public EtatIHMSoigneur(Controleur controleur){
        super(controleur);
        this.decorateur = new DecorateurIHMSoigneur(this.implementationIHM);
    }

    public void declarerDecesAnimal(int numeroAnimal){
        Animal animal = this.controleur.getCatalogue().recupererAnimalParNum(numeroAnimal);
        if(animal != null){
            this.controleur.getCatalogue().supprimerAnimal(animal);
            System.out.println("L'animal " + animal.getNom() + " a été sauvagement tué !");
        } else {
            System.out.println("Erreur ! Ce numéro d'animal n'est pas ou plus attribué.");
        }
    }

    @Override
    public void afficherMenu(Catalogue catalogue) {
        this.decorateur.afficherMenu();

        int choixMenu = this.controleur.lireEntree();
        switch(choixMenu){
            case 1:
                System.out.println(catalogue.toString());
                this.afficherMenu(catalogue);
                break;
            case 2:
                System.out.println("----- Qui est mort? -----");

                for(Animal a : this.controleur.getCatalogue().getAnimaux()){
                    System.out.println("\t" + a.getNumAnimal() + " - " + a.getNom());
                }

                int choixAnimalMort = this.controleur.lireEntree();
                this.declarerDecesAnimal(choixAnimalMort);
                this.afficherMenu(catalogue);
                break;
            case 3:
                this.controleur.changeEtat(new EtatIHMChoixUtilisateur(this.controleur));
                break;

        }
    }
}
