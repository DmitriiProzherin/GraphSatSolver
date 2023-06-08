module com.sat.graphsatsolver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sat.graphsatsolver to javafx.fxml;
    exports com.sat.graphsatsolver;
    exports com.sat.graphsatsolver.controllers;
    opens com.sat.graphsatsolver.controllers to javafx.fxml;
}