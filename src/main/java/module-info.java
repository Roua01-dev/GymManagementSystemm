module com.example.gymmanagementsystemm {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.sql;


    opens com.example.gymmanagementsystemm to javafx.fxml;
    exports com.example.gymmanagementsystemm;
}