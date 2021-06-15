package Passives.AbstractPassif;

import Game.Fight.Fight;
import Game.Team.Team;
import Hero.AbstractHero.Hero;

public abstract class Passif {

	private String name;
	private String description;
	private PassifType type;

	/**
	 * 
	 * @param user
	 *
	 */
	public abstract void use(Hero user, Team ally, Team enemy);


	public String getName() { return name; }

	public String getDescription() { return description; }

	public PassifType getType() { return type; }


}