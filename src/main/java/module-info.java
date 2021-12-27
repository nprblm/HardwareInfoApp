module ua.vitalik.hardwareinfoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.oshi;
    requires java.sql;


    opens ua.vitalik.hardwareinfoapp to javafx.fxml;
    exports ua.vitalik.hardwareinfoapp;
}