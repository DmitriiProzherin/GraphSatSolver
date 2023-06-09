package com.sat.graphsatsolver.controllers;

import com.sat.graphsatsolver.gui.Graph;
import com.sat.graphsatsolver.gui.GraphNode;
import com.sat.graphsatsolver.solvers.DPLL;
import com.sat.graphsatsolver.utils.Drawer;
import com.sat.graphsatsolver.utils.StringToList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class SatController implements Initializable {

    @FXML
    public AnchorPane mainAnchorpane;
    @FXML
    public MenuItem systemExitMenuItem;
    @FXML
    public MenuItem satRunMenuItem;
    @FXML
    public TextArea cnfTextArea;
    @FXML
    public TextArea satOutputTextArea;
    @FXML
    public ProgressBar satProgressBar;

    @FXML
    protected void systemExit() {
        System.exit(1);
    }

    @FXML
    protected void satRun() {
        satOutputTextArea.clear();
        satOutputTextArea.setEditable(false);
        cnfTextArea.setEditable(false);

        var satInput = StringToList.fromStringAsList(cnfTextArea.getText());

        Runnable solve = () -> {
            satProgressBar.setVisible(true);

            DPLL dpll = new DPLL();
            dpll.init(satInput);

            long startTime = System.nanoTime();
            dpll.solve();
            long endTime = System.nanoTime();

            long time = (endTime - startTime) / 1_000_000_000;
            String result = dpll.result();
            satOutputTextArea.setText("TIME: " + time + "s\n" + String.join("\n", result.split(" ")));

            satOutputTextArea.setEditable(true);
            cnfTextArea.setEditable(true);

            satProgressBar.setVisible(false);
        };
        Thread thread = new Thread(solve);
        thread.start();

    }

    @FXML
    protected void loadCnf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(new Stage());

        String line;

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath())))) {
                StringBuilder builder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                cnfTextArea.setText(builder.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void saveCnf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовый документ", "*.txt"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath())))) {
                writer.write(cnfTextArea.getText());
            }
            catch (IOException e){
                throw new RuntimeException();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        satProgressBar.setProgress(-1);
        satProgressBar.setVisible(false);
    }
}
