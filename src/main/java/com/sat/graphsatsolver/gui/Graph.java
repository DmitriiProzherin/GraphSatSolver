package com.sat.graphsatsolver.gui;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Vertex> vertexes;
    private ArrayList<Edge> edges;

    private int size;

    private int capacity;

    private int[][] hiddenAdjacencyMatrix;

    private int[][] adjacencyMatrix;

    public Graph() {
        vertexes = new ArrayList<>();
        edges = new ArrayList<>();
        capacity = 10;
        hiddenAdjacencyMatrix = new int[capacity][capacity];
    }

    public void add(Vertex node) {
        vertexes.add(node);
        size++;

        if (size > capacity) {
            int newCapacity = (int) (capacity * 1.5) + 1;
            int[][] temp = new int[newCapacity][newCapacity];

            for (int i = 0; i < capacity; i++) {
                System.arraycopy(hiddenAdjacencyMatrix[i], 0, temp[i], 0, capacity);
            }

            for (int i = capacity; i < newCapacity; i++) {
                for (int j = capacity; j < newCapacity; j++) {
                    temp[i][j] = 0;
                }
            }

            capacity = newCapacity;
            hiddenAdjacencyMatrix = temp;
        }
    }

    public int[][] getMatrix() {
        adjacencyMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            System.arraycopy(hiddenAdjacencyMatrix[i], 0, adjacencyMatrix[i], 0, size);
        }
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public void setHiddenAdjacencyMatrix(int[][] hiddenAdjacencyMatrix) {
        this.hiddenAdjacencyMatrix = hiddenAdjacencyMatrix;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int[][] getHiddenAdjacencyMatrix() {
        return hiddenAdjacencyMatrix;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void clear() {
        capacity = 10;
        size = 0;
        hiddenAdjacencyMatrix = new int[capacity][capacity];
        vertexes.clear();
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public void setEdge(int r, int c, Edge edge) {
        hiddenAdjacencyMatrix[r - 1][c - 1] = 1;
        hiddenAdjacencyMatrix[c - 1][r - 1] = 1;

        this.edges.add(edge);
    }
}
