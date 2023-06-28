package com.sat.graphsatsolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.sat.graphsatsolver.utils.Properties.*;

public class SatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphApplication.class.getResource("sat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SAT");
        Image image = new Image(Objects.requireNonNull(getClass().getResource("icons/app.png")).toString());
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
