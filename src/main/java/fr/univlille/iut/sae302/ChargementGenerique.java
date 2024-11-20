package fr.univlille.iut.sae302;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

//Classe qui charge avec généricité les classes
public class ChargementGenerique {

    public static ObjetGenerique chargerGenerique(String fileName) throws IOException, CsvException {
        ObjetGenerique objetGenerique = new ObjetGenerique();
    
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> allData = reader.readAll();
    
            if (allData.isEmpty()) {
                System.err.println("Le fichier CSV est vide.");
                return objetGenerique;
            }
    
            String[] headers = allData.get(0);
            List<String> headersList = new ArrayList<>();
            for (String header : headers) {
                headersList.add(header);
            }
            objetGenerique.setHeaders(headersList);
            allData.remove(0);
    
            for (String[] row : allData) {
                Map<String, Object> rowMap = new LinkedHashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    rowMap.put(headers[i], convertValue(row[i]));
                }
                objetGenerique.addRow(rowMap);
            }
        }
    
        return objetGenerique;
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
