package fr.univlille.csv;
import java.time.LocalDate;

public class Personne {
    private static final String NL = System.getProperty("line.separator");
    private String prenomNom;  // le prénom et le nom séparés par un espace
    private LocalDate dateNaissance;
    private Genre genre;
    private int taille;  // taille en centimètres
    private double scoreNormalise;
    private boolean souscription;  // le oui/non devient un boolean

    public Personne(String prenomNom, LocalDate dateNaissance, Genre genre, int taille, double scoreNormalise, boolean souscription) {
        this.prenomNom = prenomNom;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.taille = taille;
        this.scoreNormalise = scoreNormalise;
        this.souscription = souscription;
    }

    public double getScoreNormalise() {
        return scoreNormalise;
    }

    public void setScoreNormalise(double scoreNormalise) {
        this.scoreNormalise = scoreNormalise;
    }

    public String toString(){
        String s = "Pas de souscription";
        if(souscription) s = "Est souscris";
        return  "Prénom + Nom : " + prenomNom + NL +
                "Naissance : " + dateNaissance + NL +
                "Genre : " + genre.getName() + NL +
                "Taille : " + taille + NL +
                "Score : " + scoreNormalise + NL +
                "Souscription : " + s + NL;
    }

}
