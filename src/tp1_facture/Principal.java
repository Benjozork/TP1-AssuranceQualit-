package tp1_facture;

import tp1_facture.models.Commande;

import java.io.File;
import java.io.IOException;

public class Principal {

    public static void main(String[] args) {
        try {
            File inputCommande = new File("facture.txt");
            Commande commande = Lecteur.lireCommande(inputCommande);

            File outputFacture = new File("facture_output.txt");
            Afficheur.ecrireFacture(commande, outputFacture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
