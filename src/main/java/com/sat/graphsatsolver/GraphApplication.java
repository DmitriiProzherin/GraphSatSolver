package com.sat.graphsatsolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GraphApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image("C:\\Users\\Behemoth\\IdeaProjects\\GraphSatSolver\\src\\main\\resources\\com\\sat\\graphsatsolver\\icons\\app.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(GraphApplication.class.getResource("graph-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SAT");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}