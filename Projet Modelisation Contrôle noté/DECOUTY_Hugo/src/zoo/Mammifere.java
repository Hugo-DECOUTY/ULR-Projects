package zoo;

public class Mammifere extends Animal{

    private String carnivore;


    public Mammifere(String[] tab) throws ExceptionAjoutInvalide {
        super(tab);
        if(tab[4].equals("True")){
            this.carnivore = "carnivore";
        } else {
            this.carnivore = "herbivore";
        }
    }

    public String getCarnivore() {
        return carnivore;
    }

    public String toString(){
        return this.getNom() + ", " + this.getType() + ", " + this.getDateNaissance() + ", " + this.getCarnivore();
    }
}
