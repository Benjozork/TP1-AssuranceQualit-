package tp1;

import tp1.models.Commande;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestSortieCommandes {

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
            new Commande.LigneCommande(clients[0], plats[0], 1),
            new Commande.LigneCommande(clients[0], new Commande.Plat("Soupe au vide", 0.00), 1),
            //                                           Erreur ici --^
            new Commande.LigneCommande(clients[0], plats[3], 1),
            new Commande.LigneCommande(clients[1], plats[1], 1),
            new Commande.LigneCommande(clients[1], plats[2], 3),
            new Commande.LigneCommande(new Commande.Client("Mr. Inexistant"), plats[3], 1),
            //                                 Erreur ici --^
    };

    Commande commandeAUtiliser = new Commande(clients, plats, lignesCommande);

    @Test public void devrait_afficher_une_section_erreurs_dans_le_terminal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        Afficheur.afficherFacture(commandeAUtiliser);

        String contenuSout = baos.toString();
        System.err.println(contenuSout);
        
        assertTrue(contenuSout.contains("\n\n=== Erreurs ===\n\n"));
        assertTrue(contenuSout.contains("Le client Mr. Inexistant n'existe pas."));
        assertTrue(contenuSout.contains("Le plat Soupe au vide n'existe pas."));
    }

    @Test public void devrait_afficher_une_section_erreurs_dans_le_fichier() throws IOException {
        File fichierOut = new File("fichier_test.txt");

        Afficheur.ecrireFacture(commandeAUtiliser, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        System.out.println(contenFichier.toString());

        assertTrue(contenFichier.toString().contains("\n\n=== Erreurs ===\n\n"));
        assertTrue(contenFichier.toString().contains("Le client Mr. Inexistant n'existe pas."));
    }

}
