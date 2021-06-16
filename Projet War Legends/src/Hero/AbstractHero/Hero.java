package Hero.AbstractHero;

import Passives.AbstractPassif.Passif;
import Skills.AbstractSkill.Skill;

import java.util.List;

/**
 * Attributs de la classe Héros
 *
 * Name : Nom du héros.
 * Description : Description du héros
 * maxHP, HP : Points de vie du héros
 * maxMana, mana : Points de mana du héros
 * meleeArmor : Défense du héros face aux attaques de mélée
 * magicArmor : Défense du héros face aux attaques magiques
 * cooldown : Vitesse d'attaque du héros (1 à 200)
 * meleeDamage : Attaque de base du héros en mélée
 * magicDamage : Attaque de base du héros en magie
 * critical_strike_luck : Chance de coup critique du héros
 * alive : Statut du héros (Vivant/Mort)
 * skills : Compétences du héros
 *
 */
public abstract class Hero {

    private final String name;
    private final String description;
    private int maxHP, HP;
    private int maxMana, mana;
    private int meleeArmor, magicArmor;
    private int cooldown;
    private int meleeDamage, magicDamage;
    private double critical_strike_luck;
    private boolean alive;
    private Placement placement;
    private final Passif passif;
    private final List<Skill> skills;

    public Hero(String name, String description, int maxHP, int maxMana, int meleeArmor, int magicArmor, int cooldown, int meleeDamage, int magicDamage, double critical_strike_luck, Passif passif, List<Skill> skills){
        this.name = name;
        this.description = description;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.meleeArmor = meleeArmor;
        this.magicArmor = magicArmor;
        this.cooldown = cooldown;
        this.meleeDamage = meleeDamage;
        this.magicDamage = magicDamage;
        this.critical_strike_luck = critical_strike_luck;
        this.alive = true;
        this.placement = Placement.RearGuard; //Par défaut, en arrière-garde
        this.passif = passif;
        this.skills = skills;
    }

    /**
     * Méthode pour afficher quelque chose dans l'interface graphique.
     */
    public void display(){
        System.out.println(this);
    }

    /**
     * Méthode pour afficher quelque chose dans l'interface graphique.
     */
    public void display(int i){
        System.out.println(i+" - "+this);
    }

    /**
     * Décrémente le rechargement du héros. Vérifie si le rechargement est inférieur à 0 pour le refixer correctement.
     */
    public void decrementCooldownHero(){
        if(--this.cooldown > 0){
            this.setCooldown(this.cooldown);
        } else {
            this.setCooldown(0);
        }
    }

    //TODO: Avec initialisationHeros : on pourra faire une boucle for ( < listeHeros.size ) et on le fera pour chaque heros
    /**
     *
     * @return Le string d'un héros lors de la sélection des personnages (CONSOLE uniquement)
     */
    public String toStringWhenSelectHeroes(){
        return  this.getName() + " , "
                + this.getMaxHP() + " HP, "
                + this.getMaxMana() + " MANA, "
                + this.getMagicDamage() + " MAGIC_DPS, "
                + this.getMeleeDamage() + " MELEE_DPS, "
                + this.getMagicArmor() + " MAGIC_DEF, "
                + this.getMeleeArmor() + " MELEE_DEF, "
                + this.getCooldown() + " VIT, "
                + this.getCritical_strike_luck() + " CRI";
    }

    public String toString(){
        String s = "";
        s += "Nom du héros - " + this.getName() + "\n";
        s += "Description du héros - " + this.getDescription() + "\n";
        s += "Vie - " + this.getMaxHP() + "\n";
        s += "Attaque de mélée - " + this.getMeleeDamage() + "\n";
        s += "Attaque magique - " + this.getMagicDamage() + "\n";
        s += "Défense de mélée - " + this.getMeleeArmor() + "\n";
        s += "Défense magique - " + this.getMagicArmor() + "\n";
        s += "Vitesse - " + this.getCooldown() + "\n";
        s += "Chance de coup critique - " + this.getCritical_strike_luck() + "\n";
        s += "Nom du passif - " + this.getPassif().getName() + "\n";
        s += "Description du passif - " + this.getPassif().getDescription();
        s += "Compétences du héros " + this.getName() + " : ";
        for(int i = 0; i < this.getSkills().size(); i++){
            s += "\t " + this.getSkill(i).getName();
        }
        return s;

    }



    //Getters et setters
    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getMaxHP() { return maxHP; }

    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }

    public int getHP() { return HP; }

    public void setHP(int HP) {

        this.HP = HP;

        if (this.HP > this.maxHP) {
            this.HP = this.maxHP;
        }

        if (this.HP <= 0) {
            this.HP = 0;
            this.setAlive(false);
        }
    }

    public int getMaxMana() { return maxMana; }

    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getMana() { return mana; }

    // TODO : Vérifier que le mana fasse comme le setHP
    public void setMana(int mana) {
        this.mana = mana;

        if (this.mana > this.maxMana) {
            this.mana = this.maxMana;
        }

        if (this.mana < 0) {
            this.mana = 0;
        }

    }

    public int getMeleeArmor() { return meleeArmor; }

    public void setMeleeArmor(int armor) { this.meleeArmor = armor; }

    public int getMagicArmor() { return magicArmor; }

    public void setMagicArmor(int armor) { this.magicArmor = armor; }

    public int getCooldown() { return cooldown; }

    public void setCooldown(int cooldown) { this.cooldown = cooldown; }

    public int getMeleeDamage() { return meleeDamage; }

    public void setMeleeDamage(int damage) { this.meleeDamage = damage; }

    public int getMagicDamage() { return magicDamage; }

    public void setMagicDamage(int damage) { this.magicDamage = damage; }

    public double getCritical_strike_luck() { return critical_strike_luck; }

    public void setCritical_strike_luck(double critical_strike_luck) { this.critical_strike_luck = critical_strike_luck; }

    public boolean isAlive() { return alive; }

    public void setAlive(boolean isAlive) { alive = isAlive; }

    public Placement getPlacement() { return placement; }

    public void setPlacement(Placement placement) { this.placement = placement; }

    public Passif getPassif() { return passif; }

    public List<Skill> getSkills() { return skills; }

    public Skill getSkill(int i){ return this.skills.get(i); }
}
