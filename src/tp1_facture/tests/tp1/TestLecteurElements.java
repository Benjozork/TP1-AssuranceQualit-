package tp1;

import org.junit.jupiter.api.Test;

import tp1.models.Commande;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLecteurElements {

    @Test public void devrait_lire_un_client_correctement() {
        String source = "Robert";

        Commande.Client c = Lecteur.parserClient(source);

        assertEquals(c.nomClient, source);
    }

    @Test public void devrait_lire_un_plat_correctement() {
        String source = "Poutine 5.0";

        Commande.Plat p = Lecteur.parserPlat(source);

        assertEquals("Poutine", p.nomPlat);
        assertEquals(5.0, p.prix);
    }

    @Test public void devrait_ne_pas_lire_un_plat_erronee() {
        String source = "Poutine";

        assertThrows(IndexOutOfBoundsException.class, () -> Lecteur.parserPlat(source));
    }

}
