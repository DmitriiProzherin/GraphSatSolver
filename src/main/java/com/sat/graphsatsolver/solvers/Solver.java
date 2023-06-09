package com.sat.graphsatsolver.solvers;

import com.sat.graphsatsolver.structures.Function;

import java.util.ArrayList;

public abstract class Solver {
    protected Function function;

    public void init(ArrayList<ArrayList<Integer>> list) {
        this.function = new Function(list);
    }

    public abstract boolean solve();
    public abstract String result();
}
