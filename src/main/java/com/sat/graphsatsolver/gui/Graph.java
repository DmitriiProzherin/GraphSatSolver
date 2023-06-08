package com.sat.graphsatsolver.gui;

import java.util.ArrayList;

public class Graph {
    private ArrayList<GraphNode> nodes;

    private int size;

    private int capacity;

    private int[][] hiddenAdjacencyMatrix;

    private int[][] adjacencyMatrix;

    public Graph(){
        nodes = new ArrayList<>();
        capacity = 10;
        hiddenAdjacencyMatrix = new int[capacity][capacity];
    }

    public void add(GraphNode node){
        nodes.add(node);
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

    public int[][] getMatrix(){
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

    public void setNodes(ArrayList<GraphNode> nodes) {
        this.nodes = nodes;
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

    public void clear(){
        capacity = 10;
        size = 0;
        hiddenAdjacencyMatrix = new int[capacity][capacity];
        nodes.clear();
    }

    public ArrayList<GraphNode> getNodes(){
        return nodes;
    }

    public void setEdge(int r, int c) {
        hiddenAdjacencyMatrix[r-1][c-1] = 1;
        hiddenAdjacencyMatrix[c-1][r-1] = 1;
    }
}
