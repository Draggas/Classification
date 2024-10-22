module sae {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    opens fr.univlille.iut.sae302 to java.sql, javafx.graphics,  javafx.controls, com.opencsv, org.apache.commons.lang3;
}
