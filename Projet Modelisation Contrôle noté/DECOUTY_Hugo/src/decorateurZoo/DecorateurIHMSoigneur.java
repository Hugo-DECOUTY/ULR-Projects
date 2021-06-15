package decorateurZoo;

public class DecorateurIHMSoigneur extends DecorateurIHM{

    public DecorateurIHMSoigneur(AbstractionIHM abstraction) {
        super(abstraction);
    }

    @Override
    public void afficherMenu()
    {
        this.abstraction.afficherMenu();
        System.out.println("1 - Voir la liste des animaux");
        System.out.println("2 - Déclarer décès");
        System.out.println("3 - Retour au menu précédent");
    }
}
