package tp1;

import tp1.models.Commande;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("SpellCheckingInspection")
public class Afficheur {

    private static String renderFacture(Commande commande) {
        StringBuilder stringBuilderFacture = new StringBuilder();

        stringBuilderFacture.append("Bienvenue chez Barette!\n");

        Stream.of(commande.clients).forEach(client -> {
            stringBuilderFacture.append("\n=== Total ").append(client.nomClient).append(" ===\n\n");

            List<Commande.LigneCommande> lcsClient = Stream.of(commande.commandes)
                    .filter(lc -> lc.client == client)
                    .sorted(Comparator.comparing(lc -> lc.plat.nomPlat))
                    .collect(Collectors.toList());

            for (Commande.LigneCommande lc : lcsClient) {
                stringBuilderFacture
                        .append(lc.plat.nomPlat)
                        .append(" (").append(lc.quantite).append("): ")
                        .append(lc.sousTotal())
                        .append("$\n");
            }

            stringBuilderFacture.append("\nSOUS-TOTAL - ").append(commande.totalClient(client)).append("$");

            stringBuilderFacture.append("\n");
        });

        Stream.of(commande.clients).forEach(client -> {
            double total = commande.totalClient(client);
            stringBuilderFacture.append(client.nomClient).append(" ").append(total).append("$\n");
        });

        if (commande.erreurs.length > 0) stringBuilderFacture.append("\n=== Erreurs ===\n\n");

        Stream.of(commande.erreurs).forEach(erreur -> {
            stringBuilderFacture.append(erreur.seDecrire()).append("\n");
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
