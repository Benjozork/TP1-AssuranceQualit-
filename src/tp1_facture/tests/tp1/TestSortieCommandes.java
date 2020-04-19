package tp1;

import tp1.models.Commande;

public class TestSortieCommandes {

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
            new Commande.LigneCommande(clients[0], plats[0], "1"),
            new Commande.LigneCommande(clients[0], plats[2], "1"),
            new Commande.LigneCommande(clients[0], plats[3], "1"),
            new Commande.LigneCommande(clients[1], plats[1], "1"),
            new Commande.LigneCommande(clients[1], plats[2], "3"),
            new Commande.LigneCommande(clients[2], plats[3], "1"),
    };

    Commande commandeSansErreurs = new Commande(clients, plats, lignesCommande);

}
