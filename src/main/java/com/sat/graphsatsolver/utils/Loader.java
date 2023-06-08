package com.sat.graphsatsolver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Loader {
    public static ArrayList<ArrayList<Integer>> fromStringAsList(String inputString){

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new StringReader(inputString));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() <= 1) continue;
                char c = line.trim().charAt(0);
                if (Character.isDigit(c) || c == '-') {
                    var temp = new ArrayList<Integer>();
                    String s = line.trim();

                    Arrays.stream(s.split(" "))
                            .map(Integer::valueOf)
                            .filter(el -> el != 0)
                            .forEach(temp::add);
                    result.add(temp);
                }
            }
            // System.out.println(result.size());
            reader.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return result;
    }
}
