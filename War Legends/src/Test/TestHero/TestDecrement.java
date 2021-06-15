package Test.TestHero;

import Hero.AbstractHero.Hero;
import Hero.Sets.BasicSet.Archer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestDecrement {

    private static Hero hero;


    @BeforeAll
    public static void initAll(){
        hero = new Archer();

    }

    @Test
    public void testDecrementCooldown(){
        hero.decrementCooldownHero();
        assertEquals(hero.getCooldown(), 1);
        hero.decrementCooldownHero();
        assertEquals(hero.getCooldown(), 0);
        hero.decrementCooldownHero();
        assertEquals(hero.getCooldown(), 0);
    }
}
