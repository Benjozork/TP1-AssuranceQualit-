package tp1.models;

/**
 * Classe représentant un CV.
 *
 * @author Benjamin Dupont, Eric Pintea
 **/
public class Cv {

    public final String nom;
    public final String prenom;
    public final String formation;
    public final int experience;
    public final String[] competences;
    public final String attentes;

    public Cv() {
        this.nom = "Benjamin";
        this.prenom = "Dupont";
        this.formation = "Rien";
        this.experience = 69420;
        this.competences = new String[] { "Rien" };
        this.attentes = "Aucune";
    }

}
