package tp1.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Commande {

    private static final int MAX_QUANTITE_PLATS = 10;

    public Client[] clients;
    public Plat[] plats;
    public LigneCommande[] commandes;

    public Erreur[] erreurs;

    public Commande(Client[] clients, Plat[] plats, LigneCommande[] commandes) {
        this.clients = clients;
        this.plats = plats;
        this.commandes = commandes;

        this.erreurs = this.verifierErreurs();
    }

    public enum TypeErreur {
        NomClient, NomPlat, ChiffreQuantite
    }
    public static class Erreur {
        public Erreur(TypeErreur type, String objet) {
            this.type = type;
            this.objet = objet;
        }

        public final TypeErreur type;
        public final String     objet;

        public String seDecrire() {
            switch (this.type){
                case NomClient:
                    return "Le client " + this.objet + " n'existe pas.";
                case NomPlat:
                    return "Le plat " + this.objet + " n'existe pas.";
                case ChiffreQuantite:
                    return this.objet + " du plat.";
            }
            return "Erreur inconnue";
        }

        @Override
        public String toString() {
            return "Erreur{" +
                    "type=" + type +
                    ", objet='" + objet + '\'' +
                    '}';
        }
    }

    private Erreur[] verifierErreurs() {
        ArrayList<Erreur> erreursRet = new ArrayList<>();

        for (LigneCommande l : commandes) {
            if (Stream.of(this.clients)
                    .noneMatch(c -> c.nomClient.equals(l.client.nomClient))) {

                erreursRet.add(new Erreur(TypeErreur.NomClient, l.client.nomClient));
            }
            if (Stream.of(this.plats)
                    .noneMatch(p -> p.nomPlat.equals(l.plat.nomPlat))) {

                erreursRet.add(new Erreur(TypeErreur.NomPlat, l.plat.nomPlat));
            }
            if (!l.erreurDansQuantite.equals("")) {
                erreursRet.add(new Erreur(TypeErreur.ChiffreQuantite, l.erreurDansQuantite));
            }
        }

        return erreursRet.toArray(new Erreur[] {});
    }

    public static class Client {

        public final String nomClient;

        public Client(String nomC) {
            this.nomClient = nomC;
        }

    }

    public static class Plat {

        public String nomPlat;
        public double prix;

        public Plat(String nomP, double prixP) {
            this.nomPlat = nomP;
            this.prix = prixP;
        }

    }

    @SuppressWarnings("SpellCheckingInspection")
    public static class LigneCommande {

        public Client client;
        public Plat plat;
        public int quantite;

        public String erreurDansQuantite = "";

        public double sousTotal() {
            return this.plat.prix * this.quantite;
        }

        public LigneCommande(Client client, Plat plat, String chaineQuantitye) {
            this.client = client;
            this.plat = plat;
            try {
                this.quantite = Integer.parseInt(chaineQuantitye);
            } catch (Exception e) {
                this.erreurDansQuantite = "Quantité invalide";
            }
            if (this.quantite >= MAX_QUANTITE_PLATS) {
                this.erreurDansQuantite = "Quantité en haut de 10";
            }
        }

    }

    public double totalClient(Client client) {
        return Arrays.stream(commandes)
                     .filter(commande -> commande.client.nomClient.equals(client.nomClient)) // Seulement prendre les lignes de commandes concernant notre client
                     .mapToDouble(commande -> commande.plat.prix * commande.quantite)        // Transformer ces lignes en coûts par ligne
                     .sum();                                                                 // Faire la somme de ces coûts
    }

}
