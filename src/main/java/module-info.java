module com.example.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires java.desktop;
    requires com.opencsv;
    requires io.github.cdimascio.dotenv.java;



    opens com.example.finalproject to javafx.fxml;
    exports com.example.finalproject;
    exports com.example.finalproject.controllers;
    opens com.example.finalproject.controllers to javafx.fxml;
}