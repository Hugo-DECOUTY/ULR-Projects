package decorateurZoo;

public class DecorateurIHMDirection extends DecorateurIHM{

    public DecorateurIHMDirection(AbstractionIHM abstraction) {
        super(abstraction);
    }

    @Override
    public void afficherMenu()
    {
        this.abstraction.afficherMenu();
        System.out.println("1 - Générer la brochure");
        System.out.println("2 - Voir la liste des animaux");
        System.out.println("3 - Voir la liste des mammifères");
        System.out.println("4 - Voir la liste des reptiles");
        System.out.println("5 - Retour au menu précédent");
    }
}
