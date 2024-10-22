package fr.univlille.iut.sae302;

public class Pokemon {
    private static final String NL = System.getProperty("line.separator");
    private final String name;

    private Number attack;
    
    private Number eggSteps;

    private Number captureRate;

    private Number defense;

    private Number experience;

    private Number hp;

    private Number spAttack;

    private Number spDefense;

    private final Type type1;

    private final Type type2;

    private Number speed;

    private final boolean isLegendary;

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
}
