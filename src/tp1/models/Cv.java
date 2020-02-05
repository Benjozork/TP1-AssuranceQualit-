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

	public static void main(String[] args) {

		String[] competencesBen = new String[] { "rien" };
		String[] competencesEric = new String[] {
				"Can do something seemingly brain dead without forgetting how to breathe." };

		Cv benjamin = new Cv("Benjamin", "Dupont", "Rien", 69420, competencesBen, "Aucune");
		Cv eric = new Cv("Pintea", "Eric", "nope", -3, competencesEric, "I want to learn how to moonwalk.");

		System.out.println("\nBienvenue chez Barette!");
		afficher(benjamin);
		afficher(eric);

	}

	public Cv(String nomMembre, String prenomMembre, String formationMembre, int experienceMembre,
			String[] competencesMembre, String attentesMembre) {
		this.nom = nomMembre;
		this.prenom = prenomMembre;
		this.formation = formationMembre;
		this.experience = experienceMembre;
		this.competences = competencesMembre;
		this.attentes = attentesMembre;
	}

	public static void afficher(Cv cv) {
		System.out.print("\nNom Membre:\t\t" + cv.nom + "\nPrenom Membre:\t\t" + cv.prenom
				+ "\nFormation Membre:\t" + cv.formation + "\nExperience Membre:\t"
				+ cv.experience + "\nCompetences Membre:\t");
		for (int i = 0; i < cv.competences.length; i++) {
			if (i == cv.competences.length - 1) {
				System.out.println(cv.competences[i]);
			} else {
				System.out.print(cv.competences[i] + ", ");
			}
	        }

		System.out.print("Attentes Membre:\t" + cv.attentes);
S		System.out.print("\n");
	}
}
