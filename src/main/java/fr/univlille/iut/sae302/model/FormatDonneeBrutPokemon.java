package fr.univlille.iut.sae302.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Cette classe représente les données brutes d'un Pokémon
 * chargées depuis un fichier CSV. Elle utilise la bibliothèque OpenCSV 
 * pour le mapping des colonnes du fichier CSV vers les attributs de la classe.
 */
public class FormatDonneeBrutPokemon {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "attack")
    private int attack;

    @CsvBindByName(column = "base_egg_steps")
    private int egg_steps;

    @CsvBindByName(column = "capture_rate")
    private double capture_rate;

    @CsvBindByName(column = "defense")
    private int defense;

    @CsvBindByName(column = "experience_growth")
    private int experience;

    @CsvBindByName(column = "hp")
    private int hp;

    @CsvBindByName(column = "sp_attack")
    private int sp_attack;

    @CsvBindByName(column = "sp_defense")
    private int sp_defense;

    @CsvBindByName(column = "type1")
    private Type type1;

    @CsvBindByName(column = "type2")
    private Type type2;

    @CsvBindByName(column = "speed")
    private double speed;

    @CsvBindByName(column = "is_legendary")
    private boolean legendary;

    /**
     * Retourne une représentation sous forme de chaîne des données du Pokémon.
     *
     * @return Une chaîne contenant les attributs du Pokémon séparés par des virgules.
     */
    @Override
    public String toString() {
        String t2 = "";
        if(type2 != null){
            t2 = type2.getName() ;
        }
        return "" + name + ',' + attack + ',' + egg_steps + ',' + capture_rate + ',' + defense + ',' + experience + ',' + hp + ',' + sp_attack + ',' + sp_defense + ',' + type1.getName() + ',' + t2 + ',' + speed + ',' + legendary;
    }

    /**
     * Obtient le nom du Pokémon.
     *
     * @return Le nom du Pokémon.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtient le niveau d'attaque du Pokémon.
     *
     * @return Le niveau d'attaque.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Obtient le nombre d'étapes nécessaires pour obtenir un œuf.
     *
     * @return Le nombre d'étapes pour l'œuf.
     */
    public int getEggSteps() {
        return egg_steps;
    }

    /**
     * Obtient le taux de capture du Pokémon.
     *
     * @return Le taux de capture.
     */
    public double getCaptureRate() {
        return capture_rate;
    }

    /**
     * Obtient le niveau de défense du Pokémon.
     *
     * @return Le niveau de défense.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Obtient le niveau d'expérience du Pokémon.
     *
     * @return Le niveau d'expérience.
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Obtient le nombre de points de vie du Pokémon.
     *
     * @return Le nombre de points de vie.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Obtient le niveau d'attaque spéciale du Pokémon.
     *
     * @return Le niveau d'attaque spéciale.
     */
    public int getSpAttack() {
        return sp_attack;
    }

    /**
     * Obtient le niveau de défense spéciale du Pokémon.
     *
     * @return Le niveau de défense spéciale.
     */
    public int getSpDefense() {
        return sp_defense;
    }

    /**
     * Obtient le premier type du Pokémon.
     *
     * @return Le premier type.
     */
    public Type getType1() {
        return type1;
    }

    /**
     * Obtient le deuxième type du Pokémon, s'il existe.
     *
     * @return Le deuxième type, ou null si non défini.
     */
    public Type getType2() {
        return type2;
    }

    /**
     * Obtient la vitesse du Pokémon.
     *
     * @return La vitesse.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Vérifie si le Pokémon est légendaire.
     *
     * @return True si le Pokémon est légendaire, sinon false.
     */
    public boolean getLegendary() {
        return legendary;
    }

    /**
     * Définit le deuxième type du Pokémon (en cas de pokémon avec la valeur type2 définit à Null).
     *
     * @param type2 Le deuxième type à définir.
     */
    public void setType2(Type type2) {
        this.type2 = type2;
    }
}