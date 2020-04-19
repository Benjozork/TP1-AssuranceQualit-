package tp1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tp1.models.Commande;

import java.io.*;

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
            new Commande.LigneCommande(clients[0], plats[0], "1"),
            new Commande.LigneCommande(clients[0], plats[2], "1"),
            new Commande.LigneCommande(clients[0], plats[3], "1"),
            new Commande.LigneCommande(clients[1], plats[1], "1"),
            new Commande.LigneCommande(clients[1], plats[2], "3"),
            new Commande.LigneCommande(clients[2], plats[3], "1"),
    };

    Commande commandeSansErreurs = new Commande(clients, plats, lignesCommande);

    @Test public void devrait_afficher_la_meme_chose_dans_terminal_et_fichier() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.setOut(new PrintStream(baos));

        Afficheur.afficherFacture(commandeSansErreurs);

        File fichierOut = new File("fichier_output.tkt");

        Afficheur.ecrireFacture(commandeSansErreurs, fichierOut);

        StringBuilder contenFichier = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(fichierOut));
        String ligne = ""; while ((ligne = bf.readLine()) != null)
            contenFichier.append(ligne).append("\n");

        Assertions.assertEquals(baos.toString().trim(), contenFichier.toString().trim());
    }

}