package decorateurZoo;

public class DecorateurIHMChoixUtilisateur extends DecorateurIHM {

    public DecorateurIHMChoixUtilisateur(AbstractionIHM abstraction) {
        super(abstraction);
    }

    @Override
    public void afficherMenu()
    {
        this.abstraction.afficherMenu();
        System.out.println("1 - Interface direction");
        System.out.println("2 - Interface soigneur");
    }
}
