package com.sat.graphsatsolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GraphApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphApplication.class.getResource("graph-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Graph");
        Image image = new Image(Objects.requireNonNull(getClass().getResource("icons/app.png")).toString());
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            System.exit(1);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}