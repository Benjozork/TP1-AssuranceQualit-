package tp1;

import tp1.models.Commande;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestSectionTotal {

    Commande.Client[] clients = new Commande.Client[] {
            new Commande.Client("Éric"),
            new Commande.Client("Benjamin"),
            new Commande.Client("Louis-Marcel"),
            new Commande.Client("Mr. Nul")
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

    @Test public void devrait_comprendre_une_section_total_avec_taxes() throws IOException {
        File fichierOut = new File("fichier_test.txt");

        Afficheur.ecrireFacture(commandeAUtiliser, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        System.out.println(contenFichier.toString());

        assertTrue(contenFichier.toString().contains("\n\n=== Grand total ===\n\n" +
                                                     "Éric: 6.75$\n" +
                                                     "Benjamin: 13.0$\n" +
                                                     "Louis-Marcel: 1.75$\n" +
                                                     "---\n" +
                                                     "TPS: 1.07$\n" +
                                                     "TVQ: 2.14$\n" +
                                                     "TOTAL: 24.72$"));
    }

    @Test public void devrait_ne_pas_afficher_une_section_pour_les_clients_avec_un_total_de_zero() throws IOException {
        File fichierOut = new File("fichier_test.txt");

        Afficheur.ecrireFacture(commandeAUtiliser, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        System.out.println(contenFichier.toString());

        assertFalse(contenFichier.toString().contains("\n\n=== Grand total ===\n\n" +
                                                             "Éric: 6.75$\n" +
                                                             "Benjamin: 13.0$\n" +
                                                             "Louis-Marcel: 1.75$\n" +
                                                             "Mr. Nul: 0.0$\n" +
                                                             "---\n" +
                                                             "TPS: 1.07$\n" +
                                                             "TVQ: 2.14$\n" +
                                                             "TOTAL: 24.72$"));
<<<<<<< HEAD
    }

    Commande commandeSansCommandes = new Commande(clients, plats, new Commande.LigneCommande[0]);

    @Test public void devrait_ne_pas_afficher_une_section_total_si_aucune_commande() throws IOException {
        File fichierOut = new File("fichier_test.txt");

        Afficheur.ecrireFacture(commandeSansCommandes, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        System.out.println(contenFichier.toString());

        assertTrue(contenFichier.toString().contains("\n<aucune commande>"));
=======
>>>>>>> e1b8a593642277ca13599e9e4461c354d3638249
    }

}
