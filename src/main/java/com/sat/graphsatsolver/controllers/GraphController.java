package com.sat.graphsatsolver.controllers;

import com.sat.graphsatsolver.gui.*;
import com.sat.graphsatsolver.solvers.DPLL;
import com.sat.graphsatsolver.solvers.Solver;
import com.sat.graphsatsolver.utils.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class GraphController implements Initializable {

    private final List<GraphNode> selectedNodeList = new LinkedList<>();
    private final List<GraphNode> nodeList = new LinkedList<>();
    private Graph graph = new Graph();
    private List<Object> objectsOnPane = new LinkedList<>();
    private int colorsAmount;
    private HashMap<Integer, Color> colorMap;
    private int nodeCounter;

    @FXML
    public Pane drawingPane;
    @FXML
    public ToggleButton nodeCreationButton;
    @FXML
    public ToggleButton edgeCreationButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Button clearDrawingPaneButton;
    @FXML
    public Button colorsToDefaultButton;
    @FXML
    public ChoiceBox<String> problemChoiceBox;
    @FXML
    public TextField colorsAmountTextField;
    @FXML
    public Button decrementColorsAmountButton;
    @FXML
    public Button incrementColorsAmountButton;
    @FXML
    public Button cnfGenerationButton;
    @FXML
    public Button satSolveButton;
    @FXML
    public TextArea cnfTextArea;
    @FXML
    public TextArea satSolverOutTextArea;

    @FXML
    protected void systemExit() {
        System.exit(1);
    }

    @FXML
    protected void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {

            try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

                this.graph = new Graph();
                this.drawingPane.getChildren().clear();

                String textLine;

                int k = Integer.parseInt(br.readLine());
                int[][] matrix = new int[k][k];

                parse(br, k, matrix);

                int m = Integer.parseInt(br.readLine());
                int[][] hiddenMatrix = new int[m][m];

                parse(br, m, hiddenMatrix);


                this.graph.setSize(k);
                this.graph.setAdjacencyMatrix(matrix);
                this.graph.setCapacity(m);
                this.graph.setHiddenAdjacencyMatrix(hiddenMatrix);

                double x;
                double y;
                int name;
                String[] arr;

                nodeCounter = 0;
                for (int i = 1; i <= k; i++) {
                    textLine = br.readLine();
                    arr = textLine.trim().split(" ");
                    name = Integer.parseInt(arr[0]);
                    x = Double.parseDouble(arr[1]);
                    y = Double.parseDouble(arr[2]);

                    nodeCounter++;
                    GraphNode node = new GraphNode(x, y, name);
                    this.graph.getNodes().add(node);
                    drawingPane.getChildren().add(node);
                }

                for (int i = 0; i < k; i++) {
                    for (int j = i; j < k; j++) {
                        if (this.graph.getMatrix()[i][j] == 1) {
                            Drawer.lineFromNodeToNode(this.graph.getNodes().get(i), this.graph.getNodes().get(j), this.drawingPane);
                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void parse(BufferedReader br, int m, int[][] hiddenMatrix) throws IOException {
        String textLine;
        String[] values;
        for (int i = 0; i < m; i++) {
            textLine = br.readLine();
            values = textLine.split(" ");
            for (int j = 0; j < m; j++) {
                hiddenMatrix[i][j] = Integer.parseInt(values[j]);
            }
        }
    }

    @FXML
    protected void saveFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовый документ", "*.txt"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null && !this.drawingPane.getChildren().isEmpty()) {

            try {
                BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
                writer.write(this.graph.getSize() + "\n");

                int[][] matrix = this.graph.getAdjacencyMatrix();

                for (var r : matrix) {
                    writer.write(Arrays.stream(r)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" ")) + "\n");
                }

                writer.write(this.graph.getCapacity() + "\n");

                int[][] hiddenMatrix = this.graph.getHiddenAdjacencyMatrix();

                for (var r : hiddenMatrix) {
                    writer.write(Arrays.stream(r)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" ")) + "\n");
                }

                for (var n : graph.getNodes()) {
                    writer.write(n.getDesignation() + " " + n.getCenterX() + " " + n.getCenterY() + "\n");
                }

                writer.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void createGraphNode(MouseEvent event) {

        double x = event.getX();
        double y = event.getY();

        if (inDrawableRange(x, y, drawingPane)) {
            if (nodeCreationButton.isSelected()) {

                nodeCounter++;
                GraphNode node = new GraphNode(x, y, nodeCounter);
                node.toBack();

                this.nodeList.add(node);
                graph.add(node);
                drawingPane.getChildren().add(node);
                objectsOnPane.add(node);

            } else {
                if (edgeCreationButton.isSelected() && event.getTarget() instanceof GraphNode node) {
                    if (selectedNodeList.size() == 0) {
                        selectedNodeList.add(node);
                        node.select();
                    } else if (!node.equals(selectedNodeList.get(0))) {
                        selectedNodeList.add(node);

                        GraphNode n1 = selectedNodeList.get(0);
                        GraphNode n2 = selectedNodeList.get(1);

                        Line line = Drawer.lineFromNodeToNode(n1, n2, drawingPane);
                        objectsOnPane.add(line);

                        graph.setEdge(selectedNodeList.get(0).getDesignation(), selectedNodeList.get(1).getDesignation());

                        selectedNodeList.forEach(GraphNode::unselect);

                        selectedNodeList.clear();
                    }


                }
            }
        }

    }

    @FXML
    protected void drawingPaneRemoveLastNode() {

        if (!objectsOnPane.isEmpty()) {
            Object element = objectsOnPane.get(objectsOnPane.size() - 1);
            objectsOnPane.remove(element);

            if (element instanceof Line line) this.drawingPane.getChildren().remove(line);
            else if (element instanceof GraphNode node) {
                nodeCounter--;
                this.drawingPane.getChildren().remove(node);
            }

        }
    }

    @FXML
    protected void clearDrawingPane() {
        if (drawingPane.getChildren() != null) {

            nodeCounter = 0;
            selectedNodeList.clear();
            nodeList.clear();
            graph.clear();

            drawingPane.getChildren().clear();
        }
    }

    @FXML
    protected void colorsToDefault() {
        this.graph.getNodes().forEach(GraphNode::resetStyle);
    }

    @FXML
    protected void decrementColorsAmount() {
        int value = Integer.parseInt(this.colorsAmountTextField.getText());
        if (value > 1) this.colorsAmountTextField.setText(String.valueOf(--value));
    }

    @FXML
    protected void incrementColorsAmount() {
        int value = Integer.parseInt(this.colorsAmountTextField.getText());
        this.colorsAmountTextField.setText(String.valueOf(++value));
    }

    @FXML
    protected void generateCnf() {
        if ("Раскраска графа".equalsIgnoreCase(problemChoiceBox.getValue())) {

            nodeCreationButton.setSelected(false);
            edgeCreationButton.setSelected(false);

            colorsAmount = Integer.parseInt(this.colorsAmountTextField.getText());

            cnfTextArea.setText(DIMACSConverter.graphColoring(graph.getMatrix(), colorsAmount));
        } else if ("Гамильтонов путь".equalsIgnoreCase(problemChoiceBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Задача о поиске гамильтонова пути в графе находится в разработке.");
            alert.show();
        }
    }

    @FXML
    protected void solveSat() {
        String text = cnfTextArea.getText();
        var satInput = Loader.fromStringAsList(text);

        Solver dpll = new DPLL();

        dpll.init(satInput);
        dpll.solve();
        String result = dpll.result();

        satSolverOutTextArea.setText(String.join("\n", result.split(" ")));


        if (!result.equalsIgnoreCase("UNSAT") && !result.equals("".trim())) {
            this.colorMap = createColorMap(colorsAmount);

            List<Integer> out = Arrays.stream(result.split(" "))
                    .map(Integer::parseInt)
                    .toList();

            int nodeIndex;
            int colorIndex;
            int i = 0;

            for (var e : out) {
                if (e > 0) {
                    nodeIndex = i / colorsAmount;
                    colorIndex = i % colorsAmount;
                    //  System.out.println(nodeIndex + " " + colorIndex);
                    graph.getNodes().get(nodeIndex).setFill(this.colorMap.get(colorIndex));
                }
                i++;
            }
        }
    }

    private boolean inDrawableRange(double x, double y, Pane pane) {
        return x > 0 && x < pane.getWidth() && y > 0 && y < pane.getHeight();
    }

    private HashMap<Integer, Color> createColorMap(int colorsAmount) {

        HashMap<Integer, Color> map = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < colorsAmount; i++) {
            Color color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1);
            map.put(i, color);
        }

        return map;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] problems = {"Раскраска графа", "Гамильтонов путь"};
        this.problemChoiceBox.getItems().addAll(problems);
        this.problemChoiceBox.setValue("Раскраска графа");


        ToggleGroup edgeNodeGroup = new ToggleGroup();
        this.nodeCreationButton.setToggleGroup(edgeNodeGroup);
        this.edgeCreationButton.setToggleGroup(edgeNodeGroup);

        this.nodeCreationButton.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) drawingPane.getChildren().forEach(c -> {
                if (c instanceof GraphNode) {
                    ((GraphNode) c).getChildren().forEach(n -> n.setOpacity(1));
                }
            });
        });

        this.edgeCreationButton.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) drawingPane.getChildren().forEach(c -> {
                if (c instanceof GraphNode) {
                    ((GraphNode) c).getChildren().forEach(n -> n.setOpacity(0.7));
                }
            });
            else {
                drawingPane.getChildren().forEach(c -> {
                    if (c instanceof GraphNode) {
                        ((GraphNode) c).getChildren().forEach(n -> n.setOpacity(1));
                    }
                });
            }
        });

        this.colorsAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                colorsAmountTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }
}