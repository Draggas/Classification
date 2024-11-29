package fr.univlille.iut.sae302;
/**
 * Cette énumération représente les différents types de Pokémon.
 */
public enum Type {
    normal,grass,electric,bug,psychic,steel,water,fighting,fire,ice,rock,poison,fairy,ghost,ground,dragon,dark,flying,none, UNKNOWN;

    public String getName(){
        return name();
    }
}