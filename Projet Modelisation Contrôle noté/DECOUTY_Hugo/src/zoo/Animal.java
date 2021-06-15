package zoo;

public abstract class Animal {

    private int numAnimal;
    private String nom;
    private TypeAnimal type;
    private String dateNaissance;

    public static int cptNumAnimal = 1;

    public Animal(String[] tab) throws ExceptionAjoutInvalide {
        this.nom = tab[1];
        this.type = donnerType(tab[2]);
        this.dateNaissance = tab[3];
        this.numAnimal = cptNumAnimal;

        cptNumAnimal++;
    }

    public TypeAnimal donnerType(String type) throws ExceptionAjoutInvalide{
        TypeAnimal typeAnimal = null;
        switch(type){
            case "Vache":
                typeAnimal = TypeAnimal.VACHE;
                break;
            case "Cochon":
                typeAnimal = TypeAnimal.COCHON;
                break;
            case "Lion":
                typeAnimal = TypeAnimal.LION;
                break;
            case "Vipere":
                typeAnimal = TypeAnimal.VIPERE;
                break;
            case "Tortue":
                typeAnimal = TypeAnimal.TORTUE;
                break;
        }
        if(typeAnimal == null){
            throw new ExceptionAjoutInvalide("Ajout impossible de l'animal car l'animal n'est pas conforme à la spécification");
        } else {
            return typeAnimal;
        }

    }

    public int getNumAnimal() {
        return numAnimal;
    }

    public String getNom() {
        return nom;
    }

    public TypeAnimal getType() {
        return type;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

}



