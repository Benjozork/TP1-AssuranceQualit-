package tp1;

import org.junit.jupiter.api.Test;
import tp1.models.Commande;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSectionsClients {

    Commande.Client[] clients = new Commande.Client[] {
            new Commande.Client("Éric"),
            new Commande.Client("Benjamin"),
            new Commande.Client("Louis-Marcel"),
    };

    Commande.Plat[] plats = new Commande.Plat[] {
            new Commande.Plat("Poutine", 5.00),
            new Commande.Plat("Frites", 2.50),
            new Commande.Plat("Hamburger", 3.50),
            new Commande.Plat("Boisson", 1.75),
    };

    Commande.LigneCommande[] lignesCommande = new Commande.LigneCommande[] {
            new Commande.LigneCommande(clients[0], plats[0], "1"),
            new Commande.LigneCommande(clients[0], plats[3], "1"),
            new Commande.LigneCommande(clients[1], plats[1], "1"),
            new Commande.LigneCommande(clients[1], plats[2], "3"),
            new Commande.LigneCommande(clients[2], plats[3], "1"),
    };

    Commande commandeAUtiliser = new Commande(clients, plats, lignesCommande);

    @Test public void devrait_afficher_une_section_pour_chaque_client_avec_chaque_plat() throws IOException {
        File fichierOut = new File("fichier_test.txt");

        Afficheur.ecrireFacture(commandeAUtiliser, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        System.out.println(contenFichier.toString());

        assertTrue(contenFichier.toString().contains("\n\n=== Total Éric ===\n\n" +
                                                     "Boisson (1): 1.75$\n" +
                                                     "Poutine (1): 5.0$\n" +
                                                     "\nSOUS-TOTAL - 6.75$"));
        assertTrue(contenFichier.toString().contains("\n\n=== Total Benjamin ===\n\n" +
                                                     "Frites (1): 2.5$\n" +
                                                     "Hamburger (3): 10.5$\n" +
                                                     "\nSOUS-TOTAL - 13.0$"));
        assertTrue(contenFichier.toString().contains("\n\n=== Total Louis-Marcel ===\n\n" +
                                                    "Boisson (1): 1.75$\n" +
                                                     "\nSOUS-TOTAL - 1.75$"));

       assertFalse(contenFichier.toString().contains("=== Erreurs ==="), "ne doit pas contenir d'erreurs");
    }

    Commande.LigneCommande[] lignesCommandeIncomplet = new Commande.LigneCommande[] {
            new Commande.LigneCommande(clients[0], plats[0], "1"),
            new Commande.LigneCommande(clients[1], plats[2], "3"),
    };

    Commande commandeIncomplete = new Commande(clients, plats, lignesCommandeIncomplet);

    @Test public void devrait_ne_pas_afficher_une_section_pour_les_clients_avec_un_total_de_zero() throws IOException {
        File fichierOut = new File("fichier_test.txt");

        Afficheur.ecrireFacture(commandeIncomplete, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        System.out.println(contenFichier.toString());

        assertTrue(contenFichier.toString().contains("\n\n=== Total Éric ===\n\n" +
                                                             "Poutine (1): 5.0$\n" +
                                                             "\nSOUS-TOTAL - 5.0$"));
        assertTrue(contenFichier.toString().contains("\n\n=== Total Benjamin ===\n\n" +
                                                             "Hamburger (3): 10.5$\n" +
                                                             "\nSOUS-TOTAL - 10.5$"));

        assertFalse(contenFichier.toString().contains("\n\n=== Total Louis-Marcel ===\n\n"));

        assertFalse(contenFichier.toString().contains("=== Erreurs ==="), "ne doit pas contenir d'erreurs");
    }

}
  