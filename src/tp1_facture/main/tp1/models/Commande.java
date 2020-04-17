package tp1.models;

import java.util.Arrays;

public class Commande {

    public Client[] clients;
    public Plat[] plats;
    public LigneCommande[] commandes;

    public Commande(Client[] clients, Plat[] plats, LigneCommande[] commandes) {
        this.clients = clients;
        this.plats = plats;
        this.commandes = commandes;
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

        public LigneCommande(Client client, Plat plat, int quantite) {
            this.client = client;
            this.plat = plat;
            this.quantite = quantite;
        }

    }

    public double totalClient(Client client) {
        return Arrays.stream(commandes)
                     .filter(commande -> commande.client.nomClient.equals(client.nomClient)) // Seulement prendre les lignes de commandes concernant notre client
                     .mapToDouble(commande -> commande.plat.prix * commande.quantite)        // Transformer ces lignes en coûts par ligne
                     .sum();                                                                 // Faire la somme de ces coûts
    }

}
