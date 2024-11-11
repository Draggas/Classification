package fr.univlille.iut.sae302;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MethodeKnn {
    public static List<Iris> datas = new ArrayList<Iris>();

    public static double amplitudePoids;
    public static  double amplitudeTaille;

    public static void main(String[] args) throws IOException {
        List<FormatDonneeBrutJoueur> loaded = MethodeKnn.charger("res/JoueursTop14_train.csv", FormatDonneeBrutJoueur.class);
        DistanceManhattan distanceManhattan = new DistanceManhattan();
        for (FormatDonneeBrutJoueur joueur : loaded) {
            System.out.println(joueur.toString());
            System.out.println(distanceManhattan.distance(ChargementDonneesUtil.createJoeur(joueur),ChargementDonneesUtil.createJoeur(joueur)));

        }
    }

    MethodeKnn(String csv) throws IOException {
        List<FormatDonneeBrutJoueur> listBrutJoeur = ChargementDonneesUtil.charger(csv, FormatDonneeBrutJoueur.class);
        for (FormatDonneeBrutJoueur brut : listBrutJoeur) {
            datas.add(new Joueur(brut.getEquipe(), brut.getNom(), brut.getPoste(), brut.getType(), brut.getDateNaiss(), brut.getTaille(), brut.getPoids()));
        }
    }

    public static <T> List<T> charger(String fileName, Class<T> type) throws IOException {
        return new CsvToBeanBuilder<T>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator('\t')
                .withType(type)
                .build().parse();
    }

    public static void calculerAmplitudes() {
        if (datas.isEmpty()) {
            amplitudePoids = 0;
            amplitudeTaille = 0;
            return;
        }

        double poidsMin = Double.MAX_VALUE;
        double poidsMax = Double.MIN_VALUE;
        double tailleMin = Double.MAX_VALUE;
        double tailleMax = Double.MIN_VALUE;

        for (Joueur joueur : datas) {
            if (joueur.getPoids() < poidsMin) poidsMin = joueur.getPoids();
            if (joueur.getPoids() > poidsMax) poidsMax = joueur.getPoids();
            if (joueur.getTaille() < tailleMin) tailleMin = joueur.getTaille();
            if (joueur.getTaille() > tailleMax) tailleMax = joueur.getTaille();
        }

        amplitudePoids = poidsMax - poidsMin;
        amplitudeTaille = tailleMax - tailleMin;
    }

    public List<Joueur> getDatas() {
        return datas;
    }

    public static double getPoidsMin() {
        return datas.stream().mapToDouble(Joueur::getPoids).min().orElse(0);
    }

    public static double getTailleMin() {
        return datas.stream().mapToDouble(Joueur::getTaille).min().orElse(0);
    }
}
