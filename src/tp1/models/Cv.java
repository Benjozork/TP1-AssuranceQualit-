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

	public void main(String[] args) {

		String[] competencesBen = new String[] { "rien" };
		String[] competencesEric = new String[] {
				"Can do something seemingly brain dead without forgetting how to breathe." };

		Cv Benjamin = new Cv("Benjamin", "Dupont", "Rien", 69420, competencesBen, "Aucune");
		Cv Eric = new Cv("Pintea", "Eric", "nope", -3, competencesEric, "I want to learn how to moonwalk.");

		System.out.println("\nBienvenue chez Barette!");
		afficher(Benjamin);
		afficher(Eric);

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

	public void afficher(Cv membreAffiche) {
		System.out.print("\nNom Membre:\t" + membreAffiche.nom + "\nPrenom Membre:\t" + membreAffiche.prenom
				+ "\nFormation Membre:\t" + membreAffiche.formation + "\nExperience Membre:\t"
				+ membreAffiche.experience + "\nCompetences Membre:\t");
		for (int i = 0; i < competences.length; i++) {
			if (i == competences.length - 1) {
				System.out.println(competences[i]);
			} else {
				System.out.print(competences[i] + ", ");
			}
		}
		System.out.print("Attentes Membre:\t" + membreAffiche.attentes);
	}
}
