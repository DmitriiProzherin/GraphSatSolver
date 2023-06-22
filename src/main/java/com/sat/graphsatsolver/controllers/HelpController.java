package com.sat.graphsatsolver.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    @FXML
    TreeView<String> treeView;

    @FXML
    protected void selectItem() {

        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if(item != null) {
            System.out.println(item.getValue());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> rootItem = new TreeItem<>("Содержание");

        TreeItem<String> branchItem1 = new TreeItem<>("Алгоритмы");
        TreeItem<String> branchItem2 = new TreeItem<>("О программе");

        TreeItem<String> leafItem1_1 = new TreeItem<>("КНФ");
        TreeItem<String> leafItem1_2 = new TreeItem<>("Задача SAT");
        TreeItem<String> leafItem1_3 = new TreeItem<>("Раскраска графа");
        TreeItem<String> leafItem1_4 = new TreeItem<>("Гамильтонов путь");
        TreeItem<String> leafItem1_5 = new TreeItem<>("DPLL");
        TreeItem<String> leafItem1_6 = new TreeItem<>("CDCL");

        TreeItem<String> leafItem2_1 = new TreeItem<>("Создание графа");
        TreeItem<String> leafItem2_2 = new TreeItem<>("Сохранение/загрузка графа");
        TreeItem<String> leafItem2_3 = new TreeItem<>("Решение задачи раскраски");
        TreeItem<String> leafItem2_4 = new TreeItem<>("Решение задачи Гамильтонова пути");
        TreeItem<String> leafItem2_5 = new TreeItem<>("DPLL-солвер");

        branchItem1.getChildren().addAll(leafItem1_1, leafItem1_2, leafItem1_3, leafItem1_4, leafItem1_5, leafItem1_6);
        branchItem2.getChildren().addAll(leafItem2_1, leafItem2_2, leafItem2_3, leafItem2_4, leafItem2_5);

        rootItem.getChildren().addAll(branchItem1, branchItem2);

        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
    }
}
