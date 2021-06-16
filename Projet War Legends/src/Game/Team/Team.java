package Game.Team;

import Hero.AbstractHero.Hero;

import java.util.List;

public class Team {

	private Hero warChief;
	private List<Hero> team;
	private int nbHeroAlive;

	public Team(Hero warChief, List<Hero> team, int nbHeroAlive) {
		this.warChief = warChief;
		this.team = team;
		this.nbHeroAlive = nbHeroAlive;
	}

	public Hero getWarChief() {
		return warChief;
	}

	public void setWarChief(Hero warChief) {
		this.warChief = warChief;
	}

	public List<Hero> getTeam() {
		return team;
	}

	public void setTeam(List<Hero> team) {
		this.team = team;
	}

	public int getNbHeroAlive() {
		return nbHeroAlive;
	}

	public void setNbHeroAlive(int nbHeroAlive) {
		this.nbHeroAlive = nbHeroAlive;
	}

	/**
	 * Décrémente le cooldown de chaque héros de l'équipe
	 * @return Hero, si le cooldown d'un héros est à 0, on le retourne sinon on retourne null
	 */
	public Hero decrementCooldownTeam() {
		Hero HeroReady = null;
		for(Hero h : this.team){
			h.decrementCooldownHero();
			if(h.getCooldown() == 0){
				HeroReady = h;
			}
		}
		return HeroReady;
	}

	/**
	 * Méthode regardant si un héros est mort pendant le combat, si c'est le cas on
	 * décrément le nombre de survivant et on retire le héros de la List
	 */
	public boolean checkTeamAlive() {
		int nbDeads = 0;
		for (Hero h : this.team) {
			if (h.isAlive()){
				nbDeads++;
			}
		}
		return nbDeads != this.team.size();
	}

}