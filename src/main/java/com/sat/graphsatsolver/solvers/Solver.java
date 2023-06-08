package com.sat.graphsatsolver.solvers;

import com.sat.graphsatsolver.structures.Function;
import com.sat.graphsatsolver.utils.StringToList;

import java.util.ArrayList;

public abstract class Solver {
    protected Function function;

    protected ArrayList<ArrayList<Integer>> inputList;

    public void init(Function function){
        this.function = function;
    }

    public void init(ArrayList<ArrayList<Integer>> list) {
        this.function = new Function(list);
    }

    public void init(String inputString){
        this.function = new Function(StringToList.fromStringAsList(inputString));
    }
    public abstract boolean solve();
    public abstract String result();
}
