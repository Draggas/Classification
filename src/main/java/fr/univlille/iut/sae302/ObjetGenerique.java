package fr.univlille.iut.sae302;

import java.util.*;

//Classe pour récupérer le CSV
public class ObjetGenerique {
    private List<Map<String, Object>> rows = new ArrayList<>(); //Corps
    private List<String> headers = new ArrayList<>(); //En-tête

    public void addRow(Map<String, Object> row) {
        rows.add(row);
    }

    public Map<String, Object> getRow(int index) {
        return rows.get(index);
    }

    public List<Map<String, Object>> getAllRows() {
        return rows;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
