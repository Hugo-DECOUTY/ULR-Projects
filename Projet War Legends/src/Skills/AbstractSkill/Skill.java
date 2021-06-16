package Skills.AbstractSkill;

import Game.Team.Team;
import Hero.AbstractHero.Hero;

import java.util.List;

public abstract class Skill {

	public static int SKILLS_COUNTER_PER_HERO = 4;
	public static int MIN_VALUE_PERCENTAGE = 0;
	public static int MAX_VALUE_PERCENTAGE = 100;

	private final String name;
	private final String description;
	private int manaCost;
	private int cooldown;
	private final AttackType attackType;
	private final SkillType skillType;

	public Skill(String name, String description, int manaCost, int cooldown, AttackType attackType, SkillType skillType) {
		this.name = name;
		this.description = description;
		this.manaCost = manaCost;
		this.cooldown = cooldown;
		this.attackType = attackType;
		this.skillType = skillType;
	}

	/**
	 * Renvoie si le sort est utilisable (mana supérieur à 0)
	 * @param user : L'utilisateur de la compétence
	 * @return true si le sort est utilisable.
	 */
	public boolean isSkillCastable(Hero user){
		return user.getMana() - this.getManaCost() >= 0;
	}

	/**
	 * Renvoie la réussite d'un coup critique.
	 * @param user : L'utilisateur de la compétence
	 * @return true si on réussit un coup critique.
	 */
	public boolean tryingSuccessfulCheck(Hero user){
		double SuccessPercentage = user.getCritical_strike_luck();
		return 1 + (Math.random() * (MAX_VALUE_PERCENTAGE - MIN_VALUE_PERCENTAGE) + 1) >= 100-SuccessPercentage;
	}

	/**
	 *
	 * @param user : L'utilisateur de la compétence
	 * @param target : La cible de la compétence
	 * @param damage : Les dégats infligés par la compétence en prenant
	 *                 en compte les multiplicateurs, buffs et debuffs sur l'utilisateur.
	 *
	 * @return int : Dégâts calculés.
	 */
	public int calculateDamage(Hero user, Hero target, double damage){
		AttackType type = this.getAttackType();
		double damage_dealt = 0;
		//Défense magique

		if(type != null){
			if(type == AttackType.MagicDistant || type == AttackType.MagicMelee){

				if(damage > target.getMagicArmor()){
					// user.getMagicDamage() - PROBABILITE (entre 50% et 100% des dégâts normaux -> ((1 - 0,5) + 1))
					damage_dealt = damage * (0.5 + (int)(Math.random() * 1.5)) - target.getMagicArmor();
				}

				//Défense physique
			} else {

				if(damage > target.getMeleeArmor()){
					damage_dealt = damage * (0.5 + (int)(Math.random() * 1.5)) - target.getMeleeArmor();
				}

			}

			if (tryingSuccessfulCheck(user)) {
				damage_dealt = damage_dealt * 1.2;
				target.setMagicArmor(target.getMagicArmor() - 2);
				target.setMeleeArmor(target.getMeleeArmor() - 2);
			}
		}

		return (int) damage_dealt;
	}

	/**
	 * Modifie la mana de l'utilisateur après l'utilsation d'une compétence.
	 * @param user : L'utilisateur de la compétence
	 */
	public void setManaAfterSkillUsed(Hero user){
		user.setMana(user.getMana() - this.getManaCost());
	}

	/**
	 * Utilisation d'une compétence.
	 * @param user : L'utilisateur de la compétence
	 * @param singleTarget : La cible unique de la compétence (Simple target)
	 * @param allyTeam : Les cible de la compétence (Multiple targets)
	 * @param enemyTeam : Les cible de la compétence (Multiple targets)
	 */ //Peut-être une Liste de héros serait meilleure (2 cibles uniquement)
	public void useSkillModel(Hero user, Hero singleTarget, Team allyTeam, Team enemyTeam){
		if(this.isSkillCastable(user)){ //Si le sort est utilisable
			this.use(user, singleTarget, allyTeam, enemyTeam); //Le héros l'utilise sur une cible.
			this.setManaAfterSkillUsed(user); //On modifie sa mana.
			user.setCooldown(this.getCooldown()); //Le joueur a fini son tour, son temps de rechargement est modifié.
		}
	}


	/**
	 * Utilisation généraliste d'une compétence.
	 * @param user : L'utilisateur de la compétence
	 * @param singleTarget : La cible unique de la compétence (Simple target)
	 * @param allyTeam : Les cible de la compétence (Multiple targets)
	 * @param enemyTeam : Les cible de la compétence (Multiple targets)
	 */ //Peut-être une Liste de héros serait meilleure (2 cibles uniquement)
	public abstract void use(Hero user, Hero singleTarget, Team allyTeam, Team enemyTeam);

	public String getName() { return name; }

	public String getDescription() { return description; }

	public int getManaCost() { return manaCost; }

	public void setManaCost(int manaCost) { this.manaCost = manaCost; }

	public int getCooldown() { return cooldown; }

	public void setCooldown(int cooldown) { this.cooldown = cooldown; }

	public AttackType getAttackType() { return attackType; }

	public SkillType getSkillType() { return skillType; }

}