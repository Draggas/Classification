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

    public Number getAttack() {
        return attack;
    }

    public Number getEggSteps() {
        return eggSteps;
    }

    public Number getCaptureRate() {
        return captureRate;
    }

    public Number getDefense() {
        return defense;
    }

    public Number getExperience() {
        return experience;
    }

    public Number getHp() {
        return hp;
    }

    public Number getSpAttack() {
        return spAttack;
    }

    public Number getSpDefense() {
        return spDefense;
    }

    public Number getSpeed() {
        return speed;
    }

    public void setAttack(Number attack) {
        this.attack = attack;
    }

    public void setEggSteps(Number eggSteps) {
        this.eggSteps = eggSteps;
    }

    public void setCaptureRate(Number captureRate) {
        this.captureRate = captureRate;
    }

    public void setDefense(Number defense) {
        this.defense = defense;
    }

    public void setExperience(Number experience) {
        this.experience = experience;
    }

    public void setHp(Number hp) {
        this.hp = hp;
    }

    public void setSpAttack(Number spAttack) {
        this.spAttack = spAttack;
    }

    public void setSpDefense(Number spDefense) {
        this.spDefense = spDefense;
    }

    public void setSpeed(Number speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public Type getType1() {
        return type1 != null ? type1 : Type.UNKNOWN; // Renvoie UNKNOWN si type1 est null
    }

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

    public void setType1(String type1) {
        System.out.println(type1);
        try {
            this.type1 = Type.valueOf(type1.toLowerCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            this.type1 = Type.UNKNOWN;
        }
    }
}