package com.sat.graphsatsolver.controllers;

import com.sat.graphsatsolver.HelpApplication;
import com.sat.graphsatsolver.SatApplication;
import com.sat.graphsatsolver.gui.*;
import com.sat.graphsatsolver.solvers.DPLL;
import com.sat.graphsatsolver.solvers.Solver;
import com.sat.graphsatsolver.utils.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static com.sat.graphsatsolver.utils.ColorScheme.*;
import static com.sat.graphsatsolver.utils.Properties.*;

public class GraphController implements Initializable {


    private final List<Vertex> selectedVertexList = new LinkedList<>();
    private final List<Vertex> vertexList = new LinkedList<>();
    private Graph graph = new Graph();
    private final List<Object> objectsOnPane = new LinkedList<>();
    private int colorsAmount;
    private int vertexCounter;
    private Problem currentProblem;

    @FXML
    public HBox contentBox;
    @FXML
    public Pane drawingPane;
    @FXML
    public ToggleButton vertexCreationButton;
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
    public HBox colorsBox;
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

    public static double VERTEX_RADIUS;

    @FXML
    protected void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Граф", "*.graph"));

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
                this.edgeCreationButton.setSelected(false);
                this.vertexCreationButton.setSelected(false);

                this.graph = new Graph();
                this.drawingPane.getChildren().clear();

                String textLine;

                int k = Integer.parseInt(br.readLine());
                int[][] matrix = new int[k][k];

                parse(br, k, matrix);

                int m = (int )(k * 1.5) + 1;
                int[][] hiddenMatrix = new int[m][m];

                for (int i = 0; i < k; i++) {
                    System.arraycopy(matrix[i], 0, hiddenMatrix[i], 0, k);
                }

                this.graph.setSize(k);
                this.graph.setAdjacencyMatrix(matrix);
                this.graph.setCapacity(m);
                this.graph.setHiddenAdjacencyMatrix(hiddenMatrix);

                double x;
                double y;
                int name;
                String[] arr;
                vertexCounter = 0;

                if (br.ready()) {
                    for (int i = 1; i <= k; i++) {
                        textLine = br.readLine();
                        arr = textLine.trim().split(" ");
                        name = Integer.parseInt(arr[0]);
                        x = Double.parseDouble(arr[1]);
                        y = Double.parseDouble(arr[2]);

                        vertexCounter++;
                        Vertex node = new Vertex(x, y, name);
                        node.makeDraggable(true);
                        this.graph.getVertexes().add(node);
                        drawingPane.getChildren().add(node);
                    }
                }
                else {
                    double centerX = this.drawingPane.getWidth() / 2;
                    double centerY = this.drawingPane.getHeight() / 2;
                    double distance = 60;
                    double r = distance;
                    double theta = 0;
                    int p = 8;
                    int d = 4;

                    Random random = new Random();
                    for (int i = 1; i <= k; i++) {
                        name = i;
                        y = centerY + r * Math.sin(theta);
                        x = centerX + r * Math.cos(theta);

                        if (x <= 35 || x >= this.drawingPane.getWidth() - 35 || y <= 35 || y >= this.drawingPane.getHeight() - 35) {
                            x = random.nextInt(35, (int) this.drawingPane.getWidth() - 35);
                            y = random.nextInt(35, (int) this.drawingPane.getHeight() - 35);
                        }
                        else {
                            theta+= Math.PI / d;
                            if (i % p == 0) {
                                p*=3;
                                d*=2;
                                r+=distance;
                            }
                        }

                        vertexCounter++;
                        Vertex node = new Vertex(x, y, name);
                        node.makeDraggable(true);
                        this.graph.getVertexes().add(node);
                        drawingPane.getChildren().add(node);
                    }
                }
                for (int i = 0; i < k; i++) {
                    for (int j = i; j < k; j++) {
                        if (this.graph.getMatrix()[i][j] == 1) {
                            Edge edge = createEdge(this.graph.getVertexes().get(i), this.graph.getVertexes().get(j), drawingPane, objectsOnPane);
                            this.graph.setEdge(i + 1, j + 1, edge);
                        }
                    }
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        graph.getEdges().forEach(Edge::resetStyle);
    }

    @FXML
    protected void saveFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Граф", "*.graph"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null && !this.drawingPane.getChildren().isEmpty()) {

            try {
                BufferedWriter writer = Files.newBufferedWriter(file.toPath());
                writer.write(this.graph.getSize() + "\n");

                int[][] matrix = this.graph.getMatrix();

                for (var r : matrix) {
                    writer.write(Arrays.stream(r).mapToObj(String::valueOf).collect(Collectors.joining(" ")) + "\n");
                }

                for (var n : graph.getVertexes()) {
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
    protected void openHelpWindow(){
        var app = new HelpApplication();
        try {
            app.start(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            y = drawingPane.getHeight() - Vertex.NODE_RADIUS - 2;

        if (inDrawableRange(x, y, drawingPane)) {
            if (vertexCreationButton.isSelected()) {
                vertexCounter++;
                Vertex node = new Vertex(x, y, vertexCounter);
                node.toBack();
                this.vertexList.add(node);
                graph.add(node);
                drawingPane.getChildren().add(node);
                objectsOnPane.add(node);
            } else if (edgeCreationButton.isSelected() && event.getTarget() instanceof Vertex node) {
                if (selectedVertexList.size() == 0) {
                    selectedVertexList.add(node);
                    node.select();
                } else if (!node.equals(selectedVertexList.get(0))) {
                    selectedVertexList.add(node);
                    Vertex n1 = selectedVertexList.get(0);
                    Vertex n2 = selectedVertexList.get(1);

                    boolean edgeExists = this.graph.getMatrix()[n1.getDesignation() - 1][n2.getDesignation() - 1] == 1;

                    if (!edgeExists) {
                        Edge edge = createEdge(n1, n2, drawingPane, objectsOnPane);
                        graph.setEdge(selectedVertexList.get(0).getDesignation(), selectedVertexList.get(1).getDesignation(), edge);
                    }

                    selectedVertexList.forEach(Vertex::unselect);
                    selectedVertexList.clear();
                } else {
                    selectedVertexList.clear();
                    node.unselect();
                }
            }
        }

    }

    @FXML
    protected void cancelLastAction() {
        if (!objectsOnPane.isEmpty()) {
            Object element = objectsOnPane.get(objectsOnPane.size() - 1);
            objectsOnPane.remove(element);

            if (element instanceof Edge edge) {
                var n1 = edge.getFrom();
                var n2 = edge.getTo();
                int i = n1.getDesignation() - 1;
                int j = n2.getDesignation() - 1;

                this.drawingPane.getChildren().remove(edge);
                this.graph.getEdges().remove(edge);
                this.graph.getAdjacencyMatrix()[i][j] = 0;
                this.graph.getAdjacencyMatrix()[j][i] = 0;
                this.graph.getHiddenAdjacencyMatrix()[i][j] = 0;
                this.graph.getHiddenAdjacencyMatrix()[j][i] = 0;
                n1.getStartEdgeList().remove(edge);
                n2.getEndEdgeList().remove(edge);
            }
            else if (element instanceof Vertex vertex) {
                vertexCounter--;
                this.drawingPane.getChildren().remove(vertex);
                this.vertexList.remove(vertex);
                this.selectedVertexList.remove(vertex);
                this.graph.getVertexes().remove(vertex);

                int j = vertex.getDesignation() - 1;
                int l = this.graph.getHiddenAdjacencyMatrix().length;

                for (int i = 0; i < l; i++) {
                    this.graph.getHiddenAdjacencyMatrix()[i][j] = 0;
                    this.graph.getHiddenAdjacencyMatrix()[j][i] = 0;
                }

                this.graph.setSize(this.graph.getSize()-1);
            }

        }
    }

    @FXML
    protected void clearDrawingPane() {
        if (drawingPane.getChildren() != null) {

            vertexCounter = 0;
            selectedVertexList.clear();
            vertexList.clear();
            graph.clear();

            drawingPane.getChildren().clear();
        }
    }

    @FXML
    protected void colorsToDefault() {
        graph.getVertexes().forEach(Vertex::resetStyle);
        graph.getEdges().forEach(Edge::resetStyle);
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
        colorsToDefault();

        this.satSolverOutTextArea.clear();
        this.vertexCreationButton.setSelected(false);
        this.edgeCreationButton.setSelected(false);

        if ("Раскраска графа".equalsIgnoreCase(problemChoiceBox.getValue())) {
            currentProblem = Problem.GRAPH_COLORING;

            vertexCreationButton.setSelected(false);
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

                    HashMap<Integer, Color> colorMap = createColorMap(colorsAmount);
                    List<Integer> out = Arrays.stream(result.split(" ")).map(Integer::parseInt).toList();
                    int nodeIndex;
                    int colorIndex;
                    int i = 0;
                    for (var e : out) {
                        if (e > 0) {
                            nodeIndex = i / colorsAmount;
                            colorIndex = i % colorsAmount;
                            graph.getVertexes().get(nodeIndex).setFill(colorMap.get(colorIndex));
                        }
                        i++;
                    }
                }
                case HAMILTONIAN_CYCLE_PATH -> {
                    int[] out = Arrays.stream(result.split(" ")).mapToInt(Integer::parseInt).toArray();

                    HashMap<Integer, Integer> resultMap = new HashMap<>();

                    for (int j : out) {
                        if (j > 0) {
                            int temp = j % graph.getSize();
                            resultMap.put(temp == 0 ? graph.getSize() : temp, (j - 1) / graph.getSize() + 1);
                        }
                    }

                    for (int i = 1; i <= graph.getSize() - 1; i++) {
                        Vertex g1 = graph.getVertexes().get(resultMap.get(i) - 1);
                        Vertex g2 = graph.getVertexes().get(resultMap.get(i + 1) - 1);

                        graph.getEdges().forEach(e -> {
                            if (e.getFrom().equals(g1) && e.getTo().equals(g2) || e.getTo().equals(g1) && e.getFrom().equals(g2))
                            {
                                e.setStroke(HAMILTONIAN_PATH_COLOR);
                                e.setOpacity(OPACITY_ACTIVE);
                            }
                        });
                    }
                    graph.getVertexes().get(resultMap.get(1) - 1).getCircle().setFill(HAMILTONIAN_PATH_COLOR);
                    graph.getVertexes().get(resultMap.get(resultMap.size()) - 1).getCircle().setFill(HAMILTONIAN_PATH_COLOR);
                }
            }
        }

    }

    private Edge createEdge(Vertex n1, Vertex n2, Pane pane, List<Object> objectList) {
        Edge edge = Drawer.edgeFromVertexToVertex(n1, n2, pane);
        n1.setStartEdge(edge);
        n2.setEndEdgeList(edge);
        objectList.add(edge);
        return edge;
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
        VERTEX_RADIUS = 20;

        String[] problems = {"Раскраска графа", "Гамильтонов путь"};
        this.problemChoiceBox.getItems().addAll(problems);
        this.problemChoiceBox.setValue("Раскраска графа");
        currentProblem = Problem.GRAPH_COLORING;

        ToggleGroup edgeNodeGroup = new ToggleGroup();
        this.vertexCreationButton.setToggleGroup(edgeNodeGroup);
        this.edgeCreationButton.setToggleGroup(edgeNodeGroup);

        this.vertexCreationButton.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) drawingPane.getChildren().forEach(c -> {
                if (c instanceof Vertex) {
                    ((Vertex) c).getChildren().forEach(n -> n.setOpacity(OPACITY_ACTIVE));
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
                    ((Vertex) c).getChildren().forEach(n -> n.setOpacity(OPACITY_DISABLED));
                    ((Vertex) c).makeDraggable(false);

                } else if (c instanceof Edge) {
                    c.setOpacity(OPACITY_ACTIVE);
                }
            });
            else {
                selectedVertexList.forEach(Vertex::unselect);
                selectedVertexList.clear();

                drawingPane.getChildren().forEach(c -> {
                    if (c instanceof Vertex) {
                        ((Vertex) c).getChildren().forEach(n -> n.setOpacity(OPACITY_ACTIVE));
                        if (!vertexCreationButton.isSelected()) ((Vertex) c).makeDraggable(true);

                    } else if (c instanceof Edge) {
                        c.setOpacity(OPACITY_DISABLED);
                    }
                });
            }
        });

        this.colorsAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                colorsAmountTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        this.problemChoiceBox.setOnAction(event -> {
            if ("Раскраска графа".equalsIgnoreCase(this.problemChoiceBox.getValue())) this.colorsBox.setDisable(false);
            else if ("Гамильтонов путь".equalsIgnoreCase(this.problemChoiceBox.getValue())) this.colorsBox.setDisable(true);
        });
    }
}