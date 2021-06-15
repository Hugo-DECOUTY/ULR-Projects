package zoo;

public class Reptile extends Animal {
    private String venimeux;


    public Reptile(String[] tab) throws ExceptionAjoutInvalide {
        super(tab);
        if(tab[4].equals("True")){
            this.venimeux = "venimeux";
        } else {
            this.venimeux = "non venimeux";
        }

    }

    public String getVenimeux() {
        return venimeux;
    }

    public String toString(){
        return this.getNom() + ", " + this.getType() + ", " + this.getDateNaissance() + ", " + this.getVenimeux();
    }
}
