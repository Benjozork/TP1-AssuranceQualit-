package tp1;

import tp1.models.Commande;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("SpellCheckingInspection")
public class Afficheur {

    private static String renderFacture(Commande commande) {
        StringBuilder affichage = new StringBuilder();

        // Affichage du titre

        affichage.append("Bienvenue chez Barette!\n");

        // Affichage de chaque client

        Stream.of(commande.clients).filter(c -> commande.totalClient(c) != 0.0).forEach(client -> {
            affichage.append("\n=== Total ").append(client.nomClient).append(" ===\n\n");

            List<Commande.LigneCommande> lcsClient = Stream.of(commande.commandes)
                    .filter(lc -> lc.client == client)
                    .sorted(Comparator.comparing(lc -> lc.plat.nomPlat))
                    .collect(Collectors.toList());

            for (Commande.LigneCommande lc : lcsClient) {
                affichage
                        .append(lc.plat.nomPlat)
                        .append(" (").append(lc.quantite).append("): ")
                        .append(lc.sousTotal())
                        .append("$\n");
            }

            affichage.append("\nSOUS-TOTAL - ").append(commande.totalClient(client)).append("$");

            affichage.append("\n");
        });

        // Affichage (ou non) des erreurs

        if (commande.erreurs.length > 0) affichage.append("\n=== Erreurs ===\n\n");

        Stream.of(commande.erreurs).forEach(erreur -> {
            affichage.append(erreur.seDecrire()).append("\n");
        });

        // Affichage (ou non) du grand total

        if (commande.commandes.length < 1) {
            affichage.append("\n<aucune commande>");
        } else {
            affichage.append("\n=== Grand total ===\n\n");

            // Total pour chaque client avec sous-total != 0.00

            Stream.of(commande.clients).filter(c -> commande.totalClient(c) != 0.0).forEach(client -> {
                double total = commande.totalClient(client);

                affichage.append(client.nomClient).append(": ").append(total).append("$\n");
            });

            affichage.append("---\n");

            // Sous-total

            double soustotal = Stream.of(commande.clients)
                    .map(commande::totalClient).reduce(0d, Double::sum);

            // Format des décimales

            DecimalFormatSymbols symbol = DecimalFormatSymbols.getInstance();
            symbol.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.##", symbol);

            // Taxes + grand total

            double tps = soustotal * 0.05;
            double tvq = soustotal * 0.09975;
            double total = soustotal + tps + tvq;

            affichage.append("TPS: ").append(decimalFormat.format(tps)).append("$\n");
            affichage.append("TVQ: ").append(decimalFormat.format(tvq)).append("$\n");
            affichage.append("TOTAL: ").append(decimalFormat.format(total)).append("$\n");
        }

        return affichage.toString();
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
