package tp1;

import tp1.models.Cv;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestAfficheur {

    @Test public void devrait_afficher_un_cv_avec_une_competence() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.setOut(new PrintStream(baos));

        Cv cv = new Cv("Dupont","Benjamin", "Cégèp", 7, new String[] { "aucune" }, "aucune");

        Cv.afficher(cv);
        System.err.println(baos.toString());

        assertEquals(baos.toString().trim(), "Nom Membre:\t\t\tDupont\n" +
                                             "Prenom Membre:\t\tBenjamin\n" +
                                             "Formation Membre:\tCégèp\n" +
                                             "Experience Membre:\t7\n" +
                                             "Competences Membre:\taucune\n" +
                                             "Attentes Membre:\taucune");
    }

    @Test public void devrait_afficher_un_cv_avec_plusieurs_competences() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.setOut(new PrintStream(baos));

        Cv cv = new Cv("Dupont","Benjamin", "Cégèp", 7, new String[] { "une", "deux", "trois" }, "aucune");

        Cv.afficher(cv);
        System.err.println(baos.toString());

        assertEquals(baos.toString().trim(), "Nom Membre:\t\t\tDupont\n" +
                                             "Prenom Membre:\t\tBenjamin\n" +
                                             "Formation Membre:\tCégèp\n" +
                                             "Experience Membre:\t7\n" +
                                             "Competences Membre:\tune, deux, trois\n" +
                                             "Attentes Membre:\taucune");
    }

}
