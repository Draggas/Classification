package fr.univlille.iut.sae302;
/**
 * Cette énumération représente les différents types de Pokémon.
 */
public enum Type {
    normal,grass,electric,bug,psychic,steel,water,fighting,fire,ice,rock,poison,fairy,ghost,ground,dragon,dark,flying,none, UNKNOWN;

    /**
     * Obtient le nom du type sous forme de chaîne.
     *
     * @return Le nom du type.
     */
    public String getName(){
        return name();
    }
}