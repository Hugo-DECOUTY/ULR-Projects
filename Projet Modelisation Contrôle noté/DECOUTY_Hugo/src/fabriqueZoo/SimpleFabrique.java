package fabriqueZoo;

import zoo.Animal;
import zoo.ExceptionAjoutInvalide;
import zoo.Mammifere;
import zoo.Reptile;

public class SimpleFabrique {

    public Animal creerAnimal(String ligne) throws ExceptionAjoutInvalide {
        Animal p = null;
        String[] tab = ligne.split(";");
        switch(tab[0])
        {
            case "Mammifere":
                p = new Mammifere(tab);
                break;
            case "Reptile":
                p = new Reptile(tab);
                break;
        }
        return p;
    }

}
