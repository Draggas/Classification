package fr.univlille.iut.sae302;
import java.io.File;

public class Pokemon {

    private final String NL = File.separator;
    private String name;
    private int attack;
    private int egg_steps;

    private double capture_rate;

    private int defense;

    private int experience;

    private int hp;

    private int sp_attack;

    private int sp_defense;

    private Type type1;

    private Type type2;

    private double speed;

    private boolean isLegendary;

    public Pokemon(String name, int attack, int egg_steps, double capture_rate, int defense, int experience, int hp, int sp_attack, int sp_defense, Type type1, Type type2, double speed, boolean isLegendary) {
        this.name = name;
        this.attack = attack;
        this.egg_steps = egg_steps;
        this.capture_rate = capture_rate;
        this.defense = defense;
        this.experience = experience;
        this.hp = hp;
        this.sp_attack = sp_attack;
        this.sp_defense = sp_defense;
        this.type1 = type1;
        this.type2 = type2;
        this.speed = speed;
        this.isLegendary = isLegendary;
    }

    public String toString() {
        String l = "Pokémon ";
        if(isLegendary) l = l + "Légendaire";
        return l + NL + "name : " + name + NL +
                "attack : " + attack + NL +
                "egg_steps : " + egg_steps + NL +
                ", capture_rate : " + capture_rate + NL +
                ", defense : " + defense + NL +
                ", experience : " + experience + NL +
                ", hp : " + hp + NL +
                ", sp_attack : " + sp_attack + NL +
                ", sp_defense : " + sp_defense + NL +
                ", type1 : " + type1 + NL +
                ", type2 : " + type2 + NL +
                ", speed : " + speed;
    }
}
