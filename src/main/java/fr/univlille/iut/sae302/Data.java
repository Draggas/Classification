package fr.univlille.iut.sae302;

import java.util.List;

import fr.univlille.iut.sae302.utils.Observable;

public class Data <E> extends Observable {
    private List<E> eData;

    public Data(List<E> eData) {
        this.eData = eData;
    }

    public List<E> getEData() {
        return eData;
    }

    public boolean isEmpty() {
        return eData.isEmpty();
    }
}
