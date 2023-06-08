package com.sat.graphsatsolver.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIMACSConverter {
    public static String graphColoring(int[][] matrix, int numberOfColors) {
        int numVertices = matrix.length;
        int numVariables = numVertices * numberOfColors;

        // Step 2: Generate Variables
        Map<String, Integer> variables = new HashMap<>();
        int varId = 1;
        for (int vertex = 1; vertex <= numVertices; vertex++) {
            for (int color = 1; color <= numberOfColors; color++) {
                variables.put(vertex + "_" + color, varId);
                varId++;
            }
        }

        // Step 3: Generate Clauses
        List<List<Integer>> clauses = new ArrayList<>();

        // a. At least one color per vertex
        for (int vertex = 1; vertex <= numVertices; vertex++) {
            List<Integer> clause = new ArrayList<>();
            for (int color = 1; color <= numberOfColors; color++) {
                clause.add(variables.get(vertex + "_" + color));
            }
            clauses.add(clause);
        }

        // b. At most one color per vertex
        for (int vertex = 1; vertex <= numVertices; vertex++) {
            for (int color1 = 1; color1 < numberOfColors; color1++) {
                for (int color2 = color1 + 1; color2 <= numberOfColors; color2++) {
                    List<Integer> clause = new ArrayList<>();
                    clause.add(-variables.get(vertex + "_" + color1));
                    clause.add(-variables.get(vertex + "_" + color2));
                    clauses.add(clause);
                }
            }
        }

        // c. Adjacent vertices have different colors
        for (int i = 1; i < numVertices; i++) {
            for (int j = i + 1; j <= numVertices; j++) {
                if (matrix[i - 1][j - 1] == 1) {  // If there's an edge between vertices i and j
                    for (int color = 1; color <= numberOfColors; color++) {
                        List<Integer> clause = new ArrayList<>();
                        clause.add(-variables.get(i + "_" + color));
                        clause.add(-variables.get(j + "_" + color));
                        clauses.add(clause);
                    }
                }
            }
        }

        // Step 4: Write DIMACS Input
        StringBuilder dimacs = new StringBuilder();
        dimacs.append("p cnf ").append(numVariables).append(" ").append(clauses.size()).append("\n");
        for (List<Integer> clause : clauses) {
            for (int literal : clause) {
                dimacs.append(literal).append(" ");
            }
            dimacs.append("0\n");
        }

        return dimacs.toString();
    }

    public static Map<Integer, Integer> decodeSATResults(int[] variableAssignments, int numberOfNodes, int numberOfColors) {
        Map<Integer, Integer> vertexColors = new HashMap<>();

        for (int i = 0; i < numberOfNodes * numberOfColors; i++) {
            if (variableAssignments[i] > 0) {
                System.out.println(i);
                int vertex = i / numberOfColors + 1;
                int color = i % numberOfColors + 1;
                vertexColors.put(vertex, color);
            }
        }

        return vertexColors;
    }

}