package fr.univlille.iut.sae302;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

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

    @CsvBindByName(column = "isLegendary")
    private OuiNon legendary;

    public String toString() {
        return "" + name + ',' + attack + ',' + egg_steps + ',' + capture_rate + ',' + defense + ',' + experience + ',' + hp + ',' + sp_attack + ',' + sp_defense + ',' + type1.getName() + ',' + type2.getName() + ',' + speed + ',' + legendary.getName();
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getEgg_steps() {
        return egg_steps;
    }

    public double getCapture_rate() {
        return capture_rate;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperience() {
        return experience;
    }

    public int getHp() {
        return hp;
    }

    public int getSp_attack() {
        return sp_attack;
    }

    public int getSp_defense() {
        return sp_defense;
    }

    public Type getType1() {
        return type1;
    }

    public Type getType2() {
        return type2;
    }

    public double getSpeed() {
        return speed;
    }

    public OuiNon getLegendary() {
        return legendary;
    }
}