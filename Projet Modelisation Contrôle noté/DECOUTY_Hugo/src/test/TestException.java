package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import zoo.*;



public class TestException {

    @Test
    public void testPaquerette() throws ExceptionAjoutInvalide {
        String[] tab = {"Mammifere", "Paquerette", "Vache", "25/12/2015", "False"};
        Animal paquerette = new Mammifere(tab);
        assertEquals(paquerette.getType(), TypeAnimal.VACHE);
    }

    @Test
    public void testVivi() throws ExceptionAjoutInvalide {
        String[] tab = {"Reptile", "Vivi", "Vipere", "25/12/2015", "True"};
        Animal vivi = new Reptile(tab);
        assertEquals(vivi.getType(), TypeAnimal.VIPERE);
    }

    //Exception doit s'effectuer ici.
    @Test
    public void testChoupi() throws ExceptionAjoutInvalide {
        //Ajout d'un autre animal (un chat ici)
        String[] tabChat = {"Mammifere", "Choupi","Chat","19/03/2010","False"};
        Mammifere chat;
        ExceptionAjoutInvalide exception = assertThrows(ExceptionAjoutInvalide.class, () -> new Mammifere(tabChat));
        assertEquals("Ajout impossible de l'animal car l'animal n'est pas conforme à la spécification", exception.getMessage());

    }

}


