package tp1;

import tp1.models.Commande;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestErreursCommandes {

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
            new Commande.LigneCommande(clients[0], plats[0], "10"),
            new Commande.LigneCommande(clients[0], new Commande.Plat("Soupe au vide", 0.00), "1"),
            //                                           Erreur ici --^
            new Commande.LigneCommande(clients[0], plats[3], "1a"),
            new Commande.LigneCommande(clients[1], plats[1], "11"),
            new Commande.LigneCommande(clients[1], plats[2], "3"),
            new Commande.LigneCommande(new Commande.Client("Mr. Inexistant"), plats[3], "1"),
            //                                 Erreur ici --^
    };

    private Commande commandeAvecErreurs = new Commande(clients, plats, lignesCommande);

    @Test public void devrait_contenir_des_erreurs() {
        System.out.println(Arrays.toString(commandeAvecErreurs.erreurs).replace("},", "}\n"));

        assertTrue(Stream.of(commandeAvecErreurs.erreurs)
                           .anyMatch(erreur -> erreur.type == Commande.TypeErreur.NomPlat &&
                                   erreur.objet.equals("Soupe au vide")));
        assertTrue(Stream.of(commandeAvecErreurs.erreurs)
                           .anyMatch(erreur -> erreur.type == Commande.TypeErreur.NomClient &&
                                   erreur.objet.equals("Mr. Inexistant")));
        assertTrue(Stream.of(commandeAvecErreurs.erreurs)
                           .anyMatch(erreur -> erreur.type == Commande.TypeErreur.ChiffreQuantite &&
                                   erreur.objet.equals("Quantité invalide")));
        assertTrue(Stream.of(commandeAvecErreurs.erreurs)
                           .anyMatch(erreur -> erreur.type == Commande.TypeErreur.ChiffreQuantite &&
                                   erreur.objet.equals("Quantité en haut de 10")));
    }

    Commande.LigneCommande[] lignesCommande1 = new Commande.LigneCommande[] {
            new Commande.LigneCommande(clients[0], plats[0], "1"),
            new Commande.LigneCommande(new Commande.Client("Ha !"), new Commande.Plat("Soupe d'antimatière", 0.00), "1"),
            //                                 Erreur ici --^             Erreur ici --^
    };

    Commande commandeAUtiliser1 = new Commande(clients, plats, lignesCommande1);

    @Test public void devrait_detecter_plusieurs_erreurs_sur_une_ligne() {
        System.out.println(Arrays.toString(commandeAUtiliser1.erreurs).replace("},", "}\n"));

        assertTrue(Stream.of(commandeAUtiliser1.erreurs)
                           .anyMatch(erreur -> erreur.type == Commande.TypeErreur.NomClient &&
                                   erreur.objet.equals("Ha !")));
        assertTrue(Stream.of(commandeAUtiliser1.erreurs)
                           .anyMatch(erreur -> erreur.type == Commande.TypeErreur.NomPlat &&
                                   erreur.objet.equals("Soupe d'antimatière")));
    }

    @Test public void devrait_afficher_une_section_erreurs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        Afficheur.afficherFacture(commandeAvecErreurs);

        String contenuSout = baos.toString();
        System.err.println(contenuSout);

        assertTrue(contenuSout.contains("\n\n=== Erreurs ===\n\n"));
        assertTrue(contenuSout.contains("Quantité en haut de 10 du plat."));
        assertTrue(contenuSout.contains("Le plat Soupe au vide n'existe pas."));
        assertTrue(contenuSout.contains("Quantité invalide du plat."));
        assertTrue(contenuSout.contains("Quantité en haut de 10 du plat."));
        assertTrue(contenuSout.contains("Le client Mr. Inexistant n'existe pas."));

        assertEquals(5 ,commandeAvecErreurs.erreurs.length);
    }

}
