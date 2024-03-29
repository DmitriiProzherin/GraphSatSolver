package com.sat.graphsatsolver.controllers;

import com.sat.graphsatsolver.utils.LabelFactory;
import static com.sat.graphsatsolver.utils.Text.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    @FXML
    TreeView<String> treeView;
    @FXML
    ScrollPane mainContextPane;

    private VBox root = new VBox();
    @FXML
    protected void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            switch (item.getValue()) {
                case "КНФ и DIMACS" -> setContentCnf(root);
                case "Задача SAT" -> setContentSat(root);
                case "Раскраска графа" -> setContentGraphColoring(root);
                case "Гамильтонов путь" -> setContentHp(root);
                case "DPLL" -> setContentDpll(root);
                case "CDCL" -> setContentCdcl(root);
                case "Создание графа" -> setContentGraphCreation(root);
                case "Сохранение/загрузка графа" -> setContentSaveLoadGraph(root);
                case "Решение задачи раскраски" -> setContentGraphColoringSolving(root);
                case "Решение задачи гамильтонова пути" -> setContentHpSolving(root);
                case "DPLL-солвер" -> setContentDpllSolving(root);
                default -> setContentDefault(root);
            }
        }
    }

    private void setContentCnf(VBox root){
        root.getChildren().clear();

        Label cnfLabel = LabelFactory.createHelpLabel(CNF_TITLE, true);
        Label cnfDescriptionLabel = LabelFactory.createHelpLabel(CNF_DESCRIPTION, false);
        Label cnfExampleLabel = LabelFactory.createHelpLabel(CNF_EXAMPLE_TITLE, true);
        Label cnfExampleDescriptionLabel = LabelFactory.createHelpLabel(CNF_EXAMPLE_DESCRIPTION, false);
        Label dimacsLabel = LabelFactory.createHelpLabel(DIMACS_TITLE, true);
        Label dimacsDescriptionLabel = LabelFactory.createHelpLabel(DIMACS_DESCRIPTION, false);
        Label dimacsExampleLabel = LabelFactory.createHelpLabel(DIMACS_EXAMPLE_TITLE, true);
        Label dimacsExampleDescriptionLabel = LabelFactory.createHelpLabel(DIMACS_EXAMPLE_DESCRIPTION, false);

        root.getChildren().addAll(cnfLabel, cnfDescriptionLabel, cnfExampleLabel, cnfExampleDescriptionLabel,
                dimacsLabel, dimacsDescriptionLabel, dimacsExampleLabel, dimacsExampleDescriptionLabel);
    }

    private void setContentSat(VBox root){
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(SAT_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(SAT_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);

    }

    private void setContentGraphColoring(VBox root){
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(GRAPH_COLORING_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(GRAPH_COLORING_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);

    }

    private void setContentHp(VBox root){
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(HP_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(HP_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);

    }

    private void setContentDpll(VBox root){
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(DPLL_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(DPLL_IO, false);
        Label l3 = LabelFactory.createHelpLabel(DPLL_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2, l3);

    }

    private void setContentCdcl(VBox root){
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(CDCL_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(CDCL_IO, false);
        Label l3 = LabelFactory.createHelpLabel(CDCL_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2, l3);

    }

    private void setContentGraphCreation(VBox root) {
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(GRAPH_CREATION_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(GRAPH_CREATION_INFO, false);

        root.getChildren().addAll(l1, l2);
    }

    private void setContentSaveLoadGraph(VBox root) {
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(SAVE_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(SAVE_DESCRIPTION, false);
        Label l3 = LabelFactory.createHelpLabel(LOAD_TITLE, true);
        Label l4 = LabelFactory.createHelpLabel(LOAD_DESCRIPTION, false);
        Label l5 = LabelFactory.createHelpLabel(FILE_EXAMPLE_TITLE, true);
        Label l6 = LabelFactory.createHelpLabel(FILE_EXAMPLE_1, false);
        Label l7 = LabelFactory.createHelpLabel(FILE_EXAMPLE_2, false);

        root.getChildren().addAll(l1, l2, l3, l4, l5, l6, l7);

    }

    private void setContentGraphColoringSolving(VBox root) {
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(SOLVING_GRAPH_COLORING_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(SOLVING_GRAPH_COLORING_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);
    }

    private void setContentHpSolving(VBox root) {
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(SOLVING_HP_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(SOLVING_HP_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);
    }

    private void setContentDpllSolving(VBox root) {
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(SOLVING_SAT_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(SOLVING_SAT_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);
    }

    private void setContentDefault(VBox root) {
        root.getChildren().clear();

        Label l1 = LabelFactory.createHelpLabel(DEFAULT_TITLE, true);
        Label l2 = LabelFactory.createHelpLabel(DEFAULT_DESCRIPTION, false);

        root.getChildren().addAll(l1, l2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> rootItem = new TreeItem<>("Содержание");

        TreeItem<String> branchItem1 = new TreeItem<>("Алгоритмы");
        TreeItem<String> branchItem2 = new TreeItem<>("О программе");

        TreeItem<String> leafItem1_1 = new TreeItem<>("КНФ и DIMACS");
        TreeItem<String> leafItem1_2 = new TreeItem<>("Задача SAT");
        TreeItem<String> leafItem1_3 = new TreeItem<>("Раскраска графа");
        TreeItem<String> leafItem1_4 = new TreeItem<>("Гамильтонов путь");
        TreeItem<String> leafItem1_5 = new TreeItem<>("DPLL");
        TreeItem<String> leafItem1_6 = new TreeItem<>("CDCL");

        TreeItem<String> leafItem2_1 = new TreeItem<>("Создание графа");
        TreeItem<String> leafItem2_2 = new TreeItem<>("Сохранение/загрузка графа");
        TreeItem<String> leafItem2_3 = new TreeItem<>("Решение задачи раскраски");
        TreeItem<String> leafItem2_4 = new TreeItem<>("Решение задачи гамильтонова пути");
        TreeItem<String> leafItem2_5 = new TreeItem<>("DPLL-солвер");

        branchItem1.getChildren().addAll(leafItem1_1, leafItem1_2, leafItem1_3, leafItem1_4, leafItem1_5, leafItem1_6);
        branchItem2.getChildren().addAll(leafItem2_1, leafItem2_2, leafItem2_3, leafItem2_4, leafItem2_5);

        rootItem.getChildren().addAll(branchItem1, branchItem2);


        root.setPadding(new Insets(10));
        root.setFocusTraversable(false);
        mainContextPane.setFocusTraversable(false);
        setContentDefault(root);
        mainContextPane.setContent(root);

        treeView.setShowRoot(false);
        treeView.setFocusTraversable(false);
        treeView.setRoot(rootItem);
    }
}
