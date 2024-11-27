package fr.univlille.iut.sae302.genericite;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

//Classe qui charge avec généricité les classes
public class ChargementGenerique {

    public static CSVGenerique chargerGenerique(String fileName) throws IOException, CsvException {
    
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> allData = reader.readAll();
    
            if (allData.isEmpty()) {
                System.err.println("Le fichier CSV est vide.");
                return null;
            }
            String[] headers = allData.get(0);
            allData.remove(0);
            Map<String, ArrayList<Object>> rowMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                ArrayList<Object> a = rowMap.get(headers[i]);
                if(a != null){
                    for(int c = 0; i < allData.size(); i++){
                        a.add(convertValue(allData.get(c)[i]));
                    }
                    rowMap.put(headers[i], a);
                }
            }
            return new CSVGenerique(rowMap);
    }    
}

    private static Object convertValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }
        return value;
    }
}
