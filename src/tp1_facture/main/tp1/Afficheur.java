package tp1;

import tp1.models.Commande;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("SpellCheckingInspection")
public class Afficheur {

    public static void ecrireFacture(Commande commande, File fichier) throws IOException {
        StringBuilder sbFacture = new StringBuilder();

        sbFacture.append("Bienvenue chez Barette!\n");

        Arrays.stream(commande.clients)
              .forEach(client -> {
                  double total = commande.totalClient(client);
                  sbFacture.append(client.nomClient).append(" ").append(total).append("$\n");
              });

        BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));

        writer.write(sbFacture.toString());
        writer.close();
    }

}
