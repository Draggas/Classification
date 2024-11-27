module sae {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    exports fr.univlille.iut.sae302.utils;
    exports fr.univlille.iut.sae302;
    opens fr.univlille.iut.sae302.utils;
    opens fr.univlille.iut.sae302;
}
