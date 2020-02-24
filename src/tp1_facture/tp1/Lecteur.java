package tp1;

import tp1.models.Commande;
import tp1.models.Commande.LigneCommande;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class Lecteur {

    private static final String ENTETE_CLIENTS = "Clients :";
    private static final String ENTETE_PLATS = "Plats :";
    private static final String ENTETE_COMMANDES = "Commandes :";
    private static final String FIN = "Fin";

    public static Commande lireCommande(File fichier) throws IOException {

        StringBuilder sbFichier = new StringBuilder();

        //noinspection CaughtExceptionImmediatelyRethrown
        try {
            BufferedReader fisFichier = new BufferedReader(new FileReader(fichier));

            String ligne;
            while ((ligne = fisFichier.readLine()) != null) {
                sbFichier.append(ligne).append("\n");
            }
        } catch (IOException e) {
            throw e;
        }

        String[] lignesFichier = sbFichier.toString().split("\n");

        String   ligneEnteteClients;
        String[] lignesClients   = new String[0];
        String   ligneEntetePlats;
        String[] lignesPlats     = new String[0];
        String   ligneEnteteCommandes;
        String[] lignesCommandes = new String[0];
        String   ligneFin;

        int     indLignes             = 0;
        boolean fichierValide         = true;
        String  raisonfichierInvalide = "<raison inconnue>";
        String  mauvaiseLigne         = "<mauvaise ligne inconnue>";

        ligneEnteteClients = lireLigne(lignesFichier, indLignes);
        indLignes++;
        if (!ligneEnteteClients.equals(ENTETE_CLIENTS)) {
            fichierValide = false;
            raisonfichierInvalide = "ENTETE_CLIENTS";
            mauvaiseLigne = ligneEnteteClients;
        } else {

            lignesClients = lireLignesTantQue(lignesFichier, indLignes, Pattern.compile("^[^\\s]+$"));
            indLignes += Arrays.stream(lignesClients).filter(Objects::nonNull).count();

            ligneEntetePlats = lireLigne(lignesFichier, indLignes);
            indLignes++;

            if (!ligneEntetePlats.equals(ENTETE_PLATS)) {
                fichierValide = false;
                raisonfichierInvalide = "ENTETE_PLATS";
                mauvaiseLigne = ligneEntetePlats;
            } else {

                lignesPlats = lireLignesTantQue(lignesFichier, indLignes, Pattern.compile("^\\w+\\s\\d+\\.\\d+$"));
                indLignes += Arrays.stream(lignesPlats).filter(Objects::nonNull).count();

                ligneEnteteCommandes = lireLigne(lignesFichier, indLignes);
                indLignes++;

                if (!ligneEnteteCommandes.equals(ENTETE_COMMANDES)) {
                    fichierValide = false;
                    raisonfichierInvalide = "ENTETE_COMMANDES";
                    mauvaiseLigne = ligneEnteteCommandes;
                } else {

                    lignesCommandes = lireLignesTantQueJusquA(lignesFichier, indLignes, Pattern.compile("^[^\\s]+\\s[^\\s]+\\s\\d+$"), FIN);
                    indLignes += lignesCommandes.length;

                    ligneFin = lireLigne(lignesFichier, indLignes);

                    if (!ligneFin.equals(FIN)) {
                        fichierValide = false;
                        raisonfichierInvalide = "FIN";
                        mauvaiseLigne = ligneFin;
                    }

                }

            }

        }

        if (!fichierValide)
            throw new IllegalArgumentException("Le fichier ne respecte pas le format demandé ! (" + raisonfichierInvalide + ", '" + mauvaiseLigne + "')");

        Commande.Client[] clients;
        Commande.Plat[]   plats;
        LigneCommande[]   commandes;

        clients = Arrays
            .stream(lignesClients)
            .filter(Objects::nonNull)
            .map(Lecteur::parserClient)
            .distinct()
            .toArray(Commande.Client[]::new);

        plats = Arrays
            .stream(lignesPlats)
            .filter(Objects::nonNull)
            .map(Lecteur::parserPlat)
            .distinct()
            .toArray(Commande.Plat[]::new);

        commandes = Arrays
            .stream(lignesCommandes)
            .filter(Objects::nonNull)
            .map(ligne -> {
                LigneCommande lg;
                try {
                    lg = parserCommande(ligne, clients, plats);
                } catch (Exception e) { // Ignorer si invalide
                    return null;
                }
                return lg;
            })
            .filter(Objects::nonNull)
            .distinct()
            .toArray(LigneCommande[]::new);

        return new Commande(clients, plats, commandes);

    }

    private static Commande.Client parserClient(String ligne) {
        return new Commande.Client(ligne);
    }

    private static Commande.Plat parserPlat(String ligne) {
        String[] parties = ligne.split(" ");
        return new Commande.Plat(parties[0], Double.parseDouble(parties[1]));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static LigneCommande parserCommande(String ligne, Commande.Client[] clientsDispo, Commande.Plat[] platsDispo) {
        String[] parties = ligne.split(" ");

        // Trouver un client qui match - si get() échoue on ignore l'erreur dans lireCommande()
        Commande.Client client = Arrays.stream(clientsDispo).filter(it -> it.nomClient.equals(parties[0])).findFirst().get();

        // Trouver un plat qui match - si get() échoue on ignore l'erreur dans lireCommande()
        Commande.Plat plat = Arrays.stream(platsDispo).filter(it -> it.nomPlat.equals(parties[1])).findFirst().get();

        return new LigneCommande(client, plat, Integer.parseInt(parties[2]));
    }

    private static String lireLigne(String[] lignes, int indice) {
        return lignes[indice].trim();
    }

    private static String[] lireLignesTantQue(String[] lignes, int infAPartirDe, Pattern regex) {

        int      indEcriture  = 0;
        String[] lignesRetour = new String[lignes.length];

        int    indLecture = infAPartirDe;
        String ligneLue;

        while ((ligneLue = lignes[indLecture]) != null && ligneLue.trim().matches(regex.pattern())) {
            lignesRetour[indEcriture] = ligneLue.trim();
            indLecture++;
            indEcriture++;
        }

        return lignesRetour;

    }

    private static String[] lireLignesTantQueJusquA(String[] lignes, int infAPartirDe, Pattern regex, String jusquA) {

        int      indEcriture  = 0;
        String[] lignesRetour = new String[lignes.length];

        int    indLecture = infAPartirDe;
        String ligneLue;

        while ((ligneLue = lignes[indLecture]) != null && !ligneLue.equals(jusquA)) {
            if (ligneLue.trim().matches(regex.pattern()))
                lignesRetour[indEcriture] = ligneLue.trim();
            indLecture++;
            indEcriture++;
        }

        // Enlever les derniers élements null

        int indexDernierElemNonNull = 0;
        for (int i = 0; i < lignesRetour.length; i++) {
            if (lignesRetour[i] != null)
                indexDernierElemNonNull = i;
        }

        String[] lignesRetourTaille = new String[indexDernierElemNonNull + 1];
        System.arraycopy(lignesRetour, 0, lignesRetourTaille, 0, indexDernierElemNonNull + 1);
        return lignesRetourTaille;

    }

}
