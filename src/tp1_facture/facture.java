package tp1_facture;

public class facture {

    public final String nomClient;

    public final String nomPlat;
    public final double prix;

    public final int noPlats;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public facture(Client cl, Plat pl, int noPl) {
        this.noPlats=noPl;
    }

    public Client(String nomC) {
        this.nomClient = nomC;
    }

    public Plat(String nomP, double prixP) {
        this.nomPlat = nomP;
        this.prix = prixP;
    }

}
