package com.sat.graphsatsolver.structures;

public record Variable(int name) {

    public Variable {
        assert name > 0 : "Названия переменных должны быть больше нуля";
    }

    public Variable copy() {
        return new Variable(this.name);
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
}
