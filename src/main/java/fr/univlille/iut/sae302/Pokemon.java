package fr.univlille.iut.sae302;

/**
 * Cette classe représente un Pokémon avec ses caractéristiques telles que
 * les stats de combat, les types et des informations sur lui.
 */
public class Pokemon {
    private static final String NL = System.getProperty("line.separator");
    private String name;

    private Number attack;
    
    private Number eggSteps;

    private Number captureRate;

    private Number defense;

    private Number experience;

    private Number hp;

    private Number spAttack;

    private Number spDefense;

    private Type type1;

    private Type type2;

    private Number speed;

    private final boolean isLegendary;

    /**
     * Constructeur pour créer une instance de la classe Pokemon.
     *
     * @param name Le nom du Pokémon.
     * @param attack L'attaque du Pokémon.
     * @param eggSteps Le nombre d'étapes nécessaires pour faire éclore l'œuf.
     * @param captureRate Le taux de capture du Pokémon.
     * @param defense La défense du Pokémon.
     * @param experience Les points d'expérience du Pokémon.
     * @param hp Les points de vie du Pokémon.
     * @param spAttack L'attaque spéciale du Pokémon.
     * @param spDefense La défense spéciale du Pokémon.
     * @param type1 Le premier type du Pokémon.
     * @param type2 Le deuxième type du Pokémon (peut être null).
     * @param speed La vitesse du Pokémon.
     * @param isLegendary Indique si le Pokémon est légendaire.
     */
    public Pokemon(String name, Number attack, Number eggSteps, Number captureRate, Number defense, Number experience, Number hp, Number spAttack, Number spDefense, Type type1, Type type2, Number speed, boolean isLegendary) {
        this.name = name;
        this.attack = attack;
        this.eggSteps = eggSteps;
        this.captureRate = captureRate;
        this.defense = defense;
        this.experience = experience;
        this.hp = hp;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.type1 = type1;
        this.type2 = type2;
        this.speed = speed;
        this.isLegendary = isLegendary;
    }

    /**
     * Retourne une représentation sous forme de chaîne des caractéristiques du Pokémon.
     *
     * @return Une chaîne contenant les caractéristiques du Pokémon.
     */
    @Override
    public String toString() {
        String l = "Pokémon ";
        if(isLegendary) l = l + "Légendaire";
        return l + NL + "name : " + name + NL +
                "attack : " + attack + NL +
                "egg_steps : " + eggSteps + NL +
                "capture_rate : " + captureRate + NL +
                "defense : " + defense + NL +
                "experience : " + experience + NL +
                "hp : " + hp + NL +
                "sp_attack : " + spAttack + NL +
                "sp_defense : " + spDefense + NL +
                "type1 : " + type1 + NL +
                "type2 : " + type2 + NL +
                "speed : " + speed;
    }

    /**
     * Obtient la valeur de l'attaque du Pokémon.
     *
     * @return L'attaque du Pokémon.
     */
    public Number getAttack() {
        return attack;
    }

    /**
     * Obtient le nombre d'étapes nécessaires pour faire éclore l'œuf.
     *
     * @return Le nombre d'étapes.
     */
    public Number getEggSteps() {
        return eggSteps;
    }

    /**
     * Obtient le taux de capture du Pokémon.
     *
     * @return Le taux de capture.
     */
    public Number getCaptureRate() {
        return captureRate;
    }

    /**
     * Obtient la défense du Pokémon.
     *
     * @return La défense du Pokémon.
     */
    public Number getDefense() {
        return defense;
    }

    /**
     * Obtient les points d'expérience du Pokémon.
     *
     * @return Les points d'expérience.
     */
    public Number getExperience() {
        return experience;
    }

    /**
     * Obtient les points de vie du Pokémon.
     *
     * @return Les points de vie.
     */
    public Number getHp() {
        return hp;
    }

    /**
     * Obtient l'attaque spéciale du Pokémon.
     *
     * @return L'attaque spéciale.
     */
    public Number getSpAttack() {
        return spAttack;
    }

    /**
     * Obtient la défense spéciale du Pokémon.
     *
     * @return La défense spéciale.
     */
    public Number getSpDefense() {
        return spDefense;
    }

    /**
     * Obtient la vitesse du Pokémon.
     *
     * @return La vitesse.
     */
    public Number getSpeed() {
        return speed;
    }

    /**
     * Définit l'attaque du Pokémon.
     *
     * @param attack La nouvelle attaque.
     */
    public void setAttack(Number attack) {
        this.attack = attack;
    }

    /**
     * Définit le nombre d'étapes nécessaires pour faire éclore l'œuf.
     *
     * @param eggSteps Le nouveau nombre d'étapes.
     */
    public void setEggSteps(Number eggSteps) {
        this.eggSteps = eggSteps;
    }

    /**
     * Définit le taux de capture du Pokémon.
     *
     * @param captureRate Le nouveau taux de capture.
     */
    public void setCaptureRate(Number captureRate) {
        this.captureRate = captureRate;
    }

    /**
     * Définit la défense du Pokémon.
     *
     * @param defense La nouvelle défense.
     */
    public void setDefense(Number defense) {
        this.defense = defense;
    }

    /**
     * Définit les points d'expérience du Pokémon.
     *
     * @param experience Les nouveaux points d'expérience.
     */
    public void setExperience(Number experience) {
        this.experience = experience;
    }

    /**
     * Définit les points de vie du Pokémon.
     *
     * @param hp Les nouveaux points de vie.
     */
    public void setHp(Number hp) {
        this.hp = hp;
    }

    /**
     * Définit l'attaque spéciale du Pokémon.
     *
     * @param spAttack La nouvelle attaque spéciale.
     */
    public void setSpAttack(Number spAttack) {
        this.spAttack = spAttack;
    }

    /**
     * Définit la défense spéciale du Pokémon.
     *
     * @param spDefense La nouvelle défense spéciale.
     */
    public void setSpDefense(Number spDefense) {
        this.spDefense = spDefense;
    }

    /**
     * Définit la vitesse du Pokémon.
     *
     * @param speed La nouvelle vitesse.
     */
    public void setSpeed(Number speed) {
        this.speed = speed;
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
     * Obtient le premier type du Pokémon.
     *
     * @return Le premier type du Pokémon, ou UNKNOWN si type1 est null.
     */
    public Type getType1() {
        return type1 != null ? type1 : Type.UNKNOWN; // Renvoie UNKNOWN si type1 est null
    }

    /**
     * Obtient le deuxième type du Pokémon.
     *
     * @return Le deuxième type du Pokémon.
     */
    public Type getType2() {
        return type2;
    }

    /**
     * Indique si le Pokémon est légendaire.
     *
     * @return 1 si le Pokémon est légendaire, sinon 0.
     */
    public int getIsLegendary(){
        return isLegendary ? 1 : 0;
    }

    /**
     * Définit le premier type du Pokémon.
     *
     * @param type1 Le nouveau premier type du Pokémon.
     */
    public void setType1(String type1) {
        System.out.println(type1);
        try {
            this.type1 = Type.valueOf(type1.toLowerCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            this.type1 = Type.UNKNOWN;
        }
    }
}