package tp1;

import tp1.models.Commande;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("SpellCheckingInspection")
public class Afficheur {

    private static String renderFacture(Commande commande) {
        StringBuilder stringBuilderFacture = new StringBuilder();

        stringBuilderFacture.append("Bienvenue chez Barette!\n");

        Arrays.stream(commande.clients).forEach(client -> {
            double total = commande.totalClient(client);
            stringBuilderFacture.append(client.nomClient).append(" ").append(total).append("$\n");
        });

        stringBuilderFacture.append("\n=== Erreurs ===\n\n");
        Arrays.stream(commande.erreurs).forEach(erreur -> {
            stringBuilderFacture.append(erreur.seDecrire())
                                .append("\n");
        });

        return stringBuilderFacture.toString();
    }

    public static void afficherFacture(Commande commande) {
        System.out.println(renderFacture(commande));
    }

    public static void ecrireFacture(Commande commande, File fichier) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));

        writer.write(renderFacture(commande));
        writer.close();
    }

}
