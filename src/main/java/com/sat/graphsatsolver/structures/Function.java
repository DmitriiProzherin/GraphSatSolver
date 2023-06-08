package com.sat.graphsatsolver.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public final class Function implements Iterable<Clause> {

    private final ArrayList<Clause> clauses;

    private HashMap<Variable, Boolean> variableSet = new HashMap<>();

    public Function() {
        clauses = new ArrayList<>();
    }

    public Function(ArrayList<ArrayList<Integer>> list) {
        clauses = new ArrayList<>();
        list.forEach(this::add);
    }

    public String printVariablesAssignmentAsString() {
        StringBuilder res = new StringBuilder();
        for (var entity : variableSet.entrySet())
            if (entity.getValue()) res.append(entity.getKey().toString()).append(" ");
            else res.append("-").append(entity.getKey().toString()).append(" ");
        return res.toString();
    }

    public Function add(Clause clause) {
        this.clauses.add(clause);
        return this;
    }

    public Function add(ArrayList<Integer> list) {
        this.clauses.add(new Clause(list));
        return this;
    }

    public Function copy() {
        Function res = new Function();
        this.clauses.forEach(el -> {
            res.clauses.add(el.copy());
        });
        res.variableSet = this.variableSet;
        return res;
    }

    public Literal chooseLiteral() {
        assert !this.clauses.isEmpty() : "Невозможно выбрать литерал у пустой функции";
        return this.clauses.get(0).get(0);
    }

    public int size() {
        return this.clauses.size();
    }


    @Override
    public Iterator<Clause> iterator() {
        return new FunctionIterator();
    }

    public boolean isEmpty() {
        return this.clauses.isEmpty();
    }

    public boolean hasEmptyClause() {
        if (this.clauses.isEmpty()) return false;
        for (Clause clause : this.clauses) if (clause.isEmpty()) return true;
        return false;
    }

    public Function unitPropagation() {

        var unitLiteralsSet = this.removeUnitClauses();

        for (var literal : unitLiteralsSet) {
            this.variableSet.put(literal.getVariable(), literal.getSign());

            var functionIterator = this.iterator();

            while (functionIterator.hasNext()) {
                var nextClause = functionIterator.next();
                var clauseIterator = nextClause.iterator();

                while (clauseIterator.hasNext()) {
                    var nextLiteral = clauseIterator.next();
                    if (nextLiteral.equals(literal)) {
                        functionIterator.remove();
                        break;
                    }
                    if (nextLiteral.equals(literal.reverse())) clauseIterator.remove();
                }
            }

        }

        for (var c : this) if (c.size() == 1) this.unitPropagation();

        return this;
    }


    private class FunctionIterator implements Iterator<Clause> {

        int index;

        FunctionIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < clauses.size();
        }

        @Override
        public Clause next() {
            return clauses.get(index++);
        }

        @Override
        public void remove() {
            clauses.remove(--index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function clauses1 = (Function) o;

        return clauses.equals(clauses1.clauses);
    }

    @Override
    public int hashCode() {
        return clauses.hashCode();
    }

    @Override
    public String toString() {
        return clauses.stream().map(Clause::toString).collect(Collectors.joining("\n"));
    }

    private LinkedHashSet<Literal> removeUnitClauses() {
        var unitLiteralsSet = new LinkedHashSet<Literal>();

        var it = this.iterator();
        Clause next;
        while (it.hasNext()) {
            next = it.next();
            if (next.size() == 1) {
                unitLiteralsSet.add(next.get(0));
                it.remove();
            }
        }
        return unitLiteralsSet;
    }

}
