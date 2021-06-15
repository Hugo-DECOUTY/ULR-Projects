package Skills.SetsSkills.BasicSetSkill.PaladinSkill;

import Game.Team.Team;
import Hero.AbstractHero.Hero;
import Skills.AbstractSkill.*;

public class Blessing extends Skill {



    public Blessing(){
        super("Blessing", "Restore 25 Health to the Paladin", 20, 40, null);
    }

    @Override
    public void use(Hero user, Hero singleTarget, Team allyTeam, Team enemyTeam) {

    }
}
