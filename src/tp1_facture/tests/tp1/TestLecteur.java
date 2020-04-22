package tp1;

import tp1.models.Commande;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestLecteur {

    @Test public void devrait_lire_une_commande_correctement() throws IOException {
        String factureCorrecte = "Bienvenue chez Barette!\n" +
                                 "\n" +
                                 "=== Total Roger ===\n" +
                                 "\n" +
                                 "Poutine (10): 105.0$\n" +
                                 "\n" +
                                 "SOUS-TOTAL - 105.0$\n" +
                                 "\n" +
                                 "=== Total Céline ===\n" +
                                 "\n" +
                                 "Frites (2): 5.0$\n" +
                                 "Repas_Poulet (1): 15.75$\n" +
                                 "\n" +
                                 "SOUS-TOTAL - 20.75$\n" +
                                 "\n" +
                                 "=== Grand total ===\n" +
                                 "\n" +
                                 "Roger: 105.0$\n" +
                                 "Céline: 20.75$\n" +
                                 "---\n" +
                                 "TPS: 6.29$\n" +
                                 "TVQ: 12.54$\n" +
                                 "TOTAL: 144.58$\n" +
                                 "\n";

        File input = new File("facture.txt");

        Commande c = Lecteur.lireCommande(input);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        Afficheur.afficherFacture(c);

        assertEquals(factureCorrecte, baos.toString());
    }

}
