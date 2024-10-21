package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.Observable;

import java.util.List;

public class Data <E> extends Observable {
    private List<E> eData;

    public Data(List<E> eData) {
        this.eData = eData;
    }

    public List<E> getEData() {
        return eData;
    }
}
