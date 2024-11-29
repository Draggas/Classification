module sae {
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    exports fr.univlille.iut.sae302.utils;
    exports fr.univlille.iut.sae302;
    opens fr.univlille.iut.sae302.utils;
    opens fr.univlille.iut.sae302;
}
