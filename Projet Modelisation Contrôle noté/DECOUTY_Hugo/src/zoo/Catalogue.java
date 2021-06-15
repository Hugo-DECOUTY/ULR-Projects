package zoo;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {

    private List<Animal> animaux;

    public Catalogue(){
        animaux = new ArrayList<>();
    }

    public List<Animal> getAnimaux() {
        return animaux;
    }

    public void ajouterAnimal(Animal a){
        if(!this.getAnimaux().contains(a)){
            this.animaux.add(a);
        }
    }

    public void supprimerAnimal(Animal a){
        this.animaux.remove(a);
    }

    public void genererBrochure(){
        System.out.println("Génération de la brochure effectuée");
    }

    public void afficherMammiferes(){
        for(Animal a : this.getAnimaux()){
            if(a instanceof Mammifere){
                System.out.println(a.toString());
            }
        }
    }

    public void afficherReptiles(){
        for(Animal a : this.getAnimaux()){
            if(a instanceof Reptile){
                System.out.println(a.toString());
            }
        }
    }

    public Animal recupererAnimalParNum(int numeroAnimal){
        for(Animal a : this.getAnimaux()){
            if(a.getNumAnimal() == numeroAnimal){
                return a;
            }
        }
        return null;
    }

    public String toString(){
        String s = "";
        for(Animal a : this.getAnimaux()){
            s += a.toString() + "\n";
        }
        return s;
    }
}
