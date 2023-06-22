package com.sat.graphsatsolver.controllers;

import com.sat.graphsatsolver.SatApplication;
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

    private final List<Vertex> selectedNodeList = new LinkedList<>();
    private final List<Vertex> nodeList = new LinkedList<>();
    private Graph graph = new Graph();
    private List<Object> objectsOnPane = new LinkedList<>();
    private int colorsAmount;
    private HashMap<Integer, Color> colorMap;
    private int nodeCounter;
    private Problem currentProblem;
    public static final Color EDGE_SELECTED_COLOR = Color.rgb(100, 100, 100);

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
    protected void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                this.edgeCreationButton.setSelected(false);
                this.nodeCreationButton.setSelected(false);

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
                    Vertex node = new Vertex(x, y, name);
                    node.makeDraggable(true);
                    this.graph.getNodes().add(node);
                    drawingPane.getChildren().add(node);
                }

                for (int i = 0; i < k; i++) {
                    for (int j = i; j < k; j++) {
                        if (this.graph.getMatrix()[i][j] == 1) {
                            addEdge(this.graph.getNodes().get(i), this.graph.getNodes().get(j), drawingPane, objectsOnPane);
                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addEdge(Vertex n1, Vertex n2, Pane pane, List<Object> objectList) {
        Edge line = Drawer.edgeFromVertexToVertex(n1, n2, pane);
        n1.setStartEdge(line);
        n2.setEndEdgeList(line);
        objectList.add(line);
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

                int[][] matrix = this.graph.getMatrix();

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
    protected void openSatWindow() {
        var app = new SatApplication();
        try {
            app.start(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void systemExit() {
        System.exit(1);
    }

    @FXML
    protected void createGraphNode(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (x < Vertex.NODE_RADIUS) x = Vertex.NODE_RADIUS;
        else if (x > drawingPane.getWidth() - Vertex.NODE_RADIUS - 1)
            x = drawingPane.getWidth() - Vertex.NODE_RADIUS - 1;

        if (y < Vertex.NODE_RADIUS) y = Vertex.NODE_RADIUS;
        else if (y > drawingPane.getHeight() - Vertex.NODE_RADIUS - 1)
            y = drawingPane.getHeight() - Vertex.NODE_RADIUS - 1;

        if (inDrawableRange(x, y, drawingPane)) {
            if (nodeCreationButton.isSelected()) {
                nodeCounter++;
                Vertex node = new Vertex(x, y, nodeCounter);
                node.toBack();
                this.nodeList.add(node);
                graph.add(node);
                drawingPane.getChildren().add(node);
                objectsOnPane.add(node);
            } else if (edgeCreationButton.isSelected() && event.getTarget() instanceof Vertex node) {
                if (selectedNodeList.size() == 0) {
                    selectedNodeList.add(node);
                    node.select();
                } else if (!node.equals(selectedNodeList.get(0))) {
                    selectedNodeList.add(node);
                    Vertex n1 = selectedNodeList.get(0);
                    Vertex n2 = selectedNodeList.get(1);

                    boolean edgeExists = this.graph.getMatrix()[n1.getDesignation() - 1][n2.getDesignation() - 1] == 1;

                    if (!edgeExists) {
                        addEdge(n1, n2, drawingPane, objectsOnPane);
                        graph.setEdge(selectedNodeList.get(0).getDesignation(), selectedNodeList.get(1).getDesignation());
                    }

                    selectedNodeList.forEach(Vertex::unselect);
                    selectedNodeList.clear();
                } else {
                    selectedNodeList.clear();
                    node.unselect();
                }
            }
        }

    }

    @FXML
    protected void drawingPaneRemoveLastNode() {

        if (!objectsOnPane.isEmpty()) {
            Object element = objectsOnPane.get(objectsOnPane.size() - 1);
            objectsOnPane.remove(element);

            if (element instanceof Edge line) this.drawingPane.getChildren().remove(line);
            else if (element instanceof Vertex node) {
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
        this.graph.getNodes().forEach(Vertex::resetStyle);
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
        this.satSolverOutTextArea.clear();
        this.nodeCreationButton.setSelected(false);
        this.edgeCreationButton.setSelected(false);

        if ("Раскраска графа".equalsIgnoreCase(problemChoiceBox.getValue())) {
            currentProblem = Problem.GRAPH_COLORING;

            nodeCreationButton.setSelected(false);
            edgeCreationButton.setSelected(false);

            colorsAmount = Integer.parseInt(this.colorsAmountTextField.getText());

            cnfTextArea.setText(DIMACSConverter.graphColoring(graph.getMatrix(), colorsAmount));
        } else if ("Гамильтонов путь".equalsIgnoreCase(problemChoiceBox.getValue())) {
            currentProblem = Problem.HAMILTONIAN_CYCLE_PATH;

            cnfTextArea.setText(DIMACSConverter.hamiltonianCyclePath(graph.getMatrix()));
        }
    }

    @FXML
    protected void solveSat() {
        String text = cnfTextArea.getText();
        var satInput = StringToList.fromStringAsList(text);
        Solver dpll = new DPLL();
        dpll.init(satInput);
        dpll.solve();
        String result = dpll.result();
        satSolverOutTextArea.setText(String.join("\n", result.split(" ")));

        if (!result.equalsIgnoreCase("UNSAT") && !result.equals("".trim())) {
            switch (currentProblem) {
                case GRAPH_COLORING -> {

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
                            graph.getNodes().get(nodeIndex).setFill(this.colorMap.get(colorIndex));
                        }
                        i++;
                    }
                }
                case HAMILTONIAN_CYCLE_PATH -> {
                    int[] out = Arrays.stream(result.split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    HashMap<Integer, Integer> resultMap = new HashMap<>();

                    for (int j : out) {
                        if (j > 0) {
                            int temp = j % graph.getSize();
                            resultMap.put(temp == 0 ? graph.getSize() : temp, (j - 1) / graph.getSize() + 1);
                        }
                    }

                    for (int i = 1; i <= graph.getSize() - 1; i++) {
                        Vertex g1 = graph.getNodes().get(resultMap.get(i) - 1);
                        Vertex g2 = graph.getNodes().get(resultMap.get(i+1) - 1);




                        Edge line = Drawer.edgeFromVertexToVertex(g1,g2, this.drawingPane);
                        line.setStroke(Color.RED);
                        line.setStrokeWidth(6);


                        g1.setFill(Color.RED);
                    }
                    graph.getNodes().get(resultMap.get(graph.getSize()) - 1).setFill(Color.RED);
                }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] problems = {"Раскраска графа", "Гамильтонов путь"};
        this.problemChoiceBox.getItems().addAll(problems);
        this.problemChoiceBox.setValue("Раскраска графа");
        currentProblem = Problem.GRAPH_COLORING;

        ToggleGroup edgeNodeGroup = new ToggleGroup();
        this.nodeCreationButton.setToggleGroup(edgeNodeGroup);
        this.edgeCreationButton.setToggleGroup(edgeNodeGroup);

        this.nodeCreationButton.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) drawingPane.getChildren().forEach(c -> {
                if (c instanceof Vertex) {
                    ((Vertex) c).getChildren().forEach(n -> n.setOpacity(Vertex.OPACITY_SELECTED));
                    ((Vertex) c).makeDraggable(false);
                }
            });
            else if (!edgeCreationButton.isSelected()) drawingPane.getChildren().forEach(c -> {
                if (c instanceof Vertex) ((Vertex) c).makeDraggable(true);
            });
        });

        this.edgeCreationButton.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) drawingPane.getChildren().forEach(c -> {
                if (c instanceof Vertex) {
                    ((Vertex) c).getChildren().forEach(n -> n.setOpacity(Vertex.OPACITY_UNSELECTED));
                    ((Vertex) c).makeDraggable(false);

                } else if (c instanceof Line) {
                    ((Line) c).setStroke(EDGE_SELECTED_COLOR);
                }
            });
            else {
                drawingPane.getChildren().forEach(c -> {
                    if (c instanceof Vertex) {
                        ((Vertex) c).getChildren().forEach(n -> n.setOpacity(Vertex.OPACITY_SELECTED));
                        if (!nodeCreationButton.isSelected()) ((Vertex) c).makeDraggable(true);

                    } else if (c instanceof Line) {
                        ((Line) c).setStroke(Color.rgb(180, 180, 180));
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