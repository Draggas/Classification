package fr.univlille.iut.sae302;

import java.util.*;

//Classe pour récupérer le CSV
public class CSVGenerique {
    private final Map<String, ArrayList<Object>> columns; //Corps
    
    public CSVGenerique(Map<String, ArrayList<Object>> columns){
        this.columns = columns;
    }
    public void addRow(Map<String, ArrayList<Object>> columns) {
        this.columns.putAll(columns);
    }

    public  ArrayList<Object> getColumns(String nom) {
        return columns.get(nom);
    }

    public Map<String,  ArrayList<Object>> getAllRows() {
        return columns;
    }

    public Set<String> getHeaders() {
        return columns.keySet();
    }

    public int size(){
        return getAllRows().size();
    }
}